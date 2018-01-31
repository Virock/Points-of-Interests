package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import javax.servlet.http.Cookie;

public class login_user extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //If user clicks login
        //Check if user came here from the login page
        //Later, change this contains to equals
        String referer = request.getHeader("referer");
        if (referer == null) {
            response.sendRedirect(globalVariables.getLOGIN_PAGE());
            return;
        }
        final String loginPage = globalVariables.getLOGIN_PAGE();
        if (referer.contains(loginPage)) {
            //Put the user's email and password in servlet context
            String storeEmail, storePassword;
            storeEmail = storePassword = "";
            if (request.getParameter("email") != null) {
                storeEmail = request.getParameter("email");
            }
            if (request.getParameter("password") != null) {
                storePassword = request.getParameter("password");
            }
            getServletContext().setAttribute("storedEmail", storeEmail);
            getServletContext().setAttribute("storedPassword", storePassword);

            //Check if the email and password were sent here and they aren't empty
            if (request.getParameter("email") != null && request.getParameter("password") != null && request.getParameter("email").trim().length() > 0 && request.getParameter("password").trim().length() > 0) {
                final String salt = globalVariables.getSALT();
                String salted_password = salt + request.getParameter("password");
                String hash = methods.hash_SHA_256(salted_password);
                if (hash.equals("exception")) {
                    response.sendRedirect(loginPage + "?error=exception");
                    return;
                }

                //Check if parameters sent are valid
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

                    String sql = " SELECT * FROM " + TABLE_NAME + " WHERE EMAIL=? AND PASSWORD=?";
                    PreparedStatement stmt = c.prepareStatement(sql);

                    //Bind values into the parameters.
                    stmt.setString(1, request.getParameter("email"));
                    stmt.setString(2, hash);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next() != false) {
                        //If valid, Create loggedIn cookie
                        Cookie cookie = new Cookie("loggedIn", rs.getString("LOGGED_IN_COOKIE"));
                        cookie.setMaxAge(60 * 60 * 24 * 365);   //Cookie lasts for a year
                        response.addCookie(cookie);
                        //Remove email and password from servlet context
                        getServletContext().removeAttribute("storedEmail");
                        getServletContext().removeAttribute("storedPassword");
                        //Redirect to main page
                        response.sendRedirect(globalVariables.getMAIN());
                    } else {
                        //If not valid, Send error code in url to login page
                        response.sendRedirect(loginPage + "?error=loginFail");
                    }
                } catch (Exception e) {
                    //If exception occurs, Send error code in url
                    response.sendRedirect(loginPage + "?error=exception");
                } finally {
                    try {
                        if (c != null) {
                            c.close();
                        }
                    } catch (Exception e) {
                        response.sendRedirect(loginPage + "?error=exception");
                    }
                }
            } else {
                response.sendRedirect(loginPage + "?error=invalid_input");
            }

        } //If the user came here "illegally" (not through the form). Send the person to the login form
        else {
            //Change this to something specific later
            response.sendRedirect(loginPage);
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
