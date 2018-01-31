package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class register_user extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Make sure all parameters are valid
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        //If invalid, return error
        getServletContext().setAttribute("storedRegistrationUsername", username);
        getServletContext().setAttribute("storedRegistrationEmail", email);
        getServletContext().setAttribute("storedRegistrationPassword", password);
        if (!methods.paramValidator(request.getHeader("referer"), globalVariables.getLOGIN_PAGE(), new String[]{username, email, password})) {
            response.sendRedirect("loginFormPage?error=incomplete_input");
            return;
        } //else, make sure the email received is not a duplicate of a record in database
        else {
            String HOST = globalVariables.getHOST();
            String NAME_OF_DATABASE = globalVariables.getNAME_OF_DATABASE();
            String USERNAME = globalVariables.getNAME_OF_DATABASE();
            String PASSWORD = globalVariables.getPASSWORD();
            String TABLE_NAME = globalVariables.getTABLE_NAME();

            Connection c = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                c = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + NAME_OF_DATABASE, USERNAME, PASSWORD);
                c.setAutoCommit(true);

                String sql = " SELECT EMAIL FROM " + TABLE_NAME + " WHERE EMAIL=?";
                PreparedStatement stmt = c.prepareStatement(sql);

                //Bind values into the parameters.
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();
                //If a user has already registered with this email address
                if (rs.next()) {
                    //return duplicate error
                    response.sendRedirect(globalVariables.getLOGIN_PAGE() + "?error=emailExists");
                    rs.close();
                    return;
                }
                sql = " SELECT EMAIL FROM " + TABLE_NAME + " WHERE USERNAME=?";
                stmt = c.prepareStatement(sql);

                //Bind values into the parameters.
                stmt.setString(1, username);
                rs = stmt.executeQuery();
                //If a user has already registered with this username
                if (rs.next()) {
                    //return duplicate error
                    response.sendRedirect(globalVariables.getLOGIN_PAGE() + "?error=usernameExists");
                    rs.close();
                    return;
                } else {
                    //If it's not a duplicate, add user to database
                    Random rand = new Random();
                    String loggedInCookie = methods.hash_SHA_256(new Date().toString() + String.valueOf(rand.nextInt(1000)));
                    sql = "INSERT INTO " + TABLE_NAME + " (EMAIL, USERNAME, PASSWORD, LOGGED_IN_COOKIE) "
                            + "VALUES (?, ?, ?, ?);";
                    PreparedStatement pstmt = c.prepareStatement(sql);
                    pstmt.setString(1, email);
                    pstmt.setString(2, username);
                    pstmt.setString(3, methods.hash_SHA_256(globalVariables.getSALT() + password));
                    pstmt.setString(4, loggedInCookie);

                    pstmt.executeUpdate();
                    //Create loggedIn cookie
                    Cookie cookie = new Cookie("loggedIn", loggedInCookie);
                    cookie.setMaxAge(60 * 60 * 24 * 365);   //Cookie lasts for a year
                    response.addCookie(cookie);
                    //Remove servlet attributes
                    getServletContext().removeAttribute("storedRegistrationUsername");
                    getServletContext().removeAttribute("storedRegistrationEmail");
                    getServletContext().removeAttribute("storedRegistrationPassword");
                    //Redirect the person to the main page
                    response.sendRedirect(globalVariables.getMAIN());
                }

            } catch (Exception e) {
                //If exception occurs, Send error code in url
                response.sendRedirect(globalVariables.getLOGIN_PAGE() + "?error=exception");
            } finally {
                try {
                    if (c != null) {
                        c.close();
                    }
                } catch (Exception e) {
                    response.sendRedirect(globalVariables.getLOGIN_PAGE() + "?error=exception");
                }
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
