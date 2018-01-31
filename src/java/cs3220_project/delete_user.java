package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class delete_user extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Make sure user came in through the admin page
        String referer = request.getHeader("referer");
        if (referer == null) {
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
        }
        String user = request.getParameter("user");
        if (!methods.paramValidator(referer, globalVariables.getADMIN_PAGE(), new String[]{user})) {
            //If not, redirect to main page
            response.sendRedirect(globalVariables.getMAIN());
            return;
        }
        //Make sure user is an admin
        //If not, redirect to main page
        String result = methods.isLoggedIn(request.getCookies());
        if (result.equals("exception") || result.equals("")) {
            response.sendRedirect(globalVariables.getMAIN());
            return;
        }
        if (!methods.is_user_an_admin(result)) {
            response.sendRedirect(globalVariables.getMAIN());
            return;
        }

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

            String sql = " SELECT IS_ADMIN FROM " + TABLE_NAME + " WHERE EMAIL=? LIMIT 1";
            PreparedStatement stmt = c.prepareStatement(sql);

            //Bind values into the parameters.
            stmt.setString(1, user);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                //If the user to be deleted is an admin, go back to admin page
                if (rs.getBoolean("IS_ADMIN")) {
                    response.sendRedirect(globalVariables.getADMIN_PAGE());
                    return;
                } else {
                    //If the user exists and is not an admin, delete the user and go back to admin page
                    sql = "DELETE FROM " + TABLE_NAME + " WHERE EMAIL=?";
                    stmt = c.prepareStatement(sql);
                    stmt.setString(1, user);
                    stmt.executeUpdate();

                    response.sendRedirect(globalVariables.getADMIN_PAGE());
                    return;
                }
            } else {
                //If the user does not exist, go back to admin page
                response.sendRedirect(globalVariables.getADMIN_PAGE());
                return;
            }
        } catch (Exception e) {
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                response.sendRedirect(globalVariables.getADMIN_PAGE());
                return;
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
