package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class delete_poi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Check if the user is an admin
        String referer = request.getHeader("referer");
        if (referer == null) {
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
        }
        String poi = request.getParameter("POI");
        if (!methods.paramValidator(referer, globalVariables.getADMIN_PAGE(), new String[]{poi})) {
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
        //delete the poi
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

            String sql = " DELETE FROM POI WHERE ID=?";
            PreparedStatement stmt = c.prepareStatement(sql);

            //Bind values into the parameters.
            stmt.setInt(1, Integer.parseInt(poi));
            stmt.executeUpdate();
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
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
