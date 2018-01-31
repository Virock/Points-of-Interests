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

public class edit_this_poi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String referer = request.getHeader("referer");
        if (referer == null) {
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
        }
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String poi = request.getParameter("poi");
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");
        String desc = request.getParameter("desc");

        if (!methods.paramValidator(referer, "edit_poi", new String[]{title, type, lon, lat, desc, poi})) {
            //If not, redirect to the referer page
            response.sendRedirect(referer + "&error=invalid_input");
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
        //Edit the poi
        String HOST = globalVariables.getHOST();
        String NAME_OF_DATABASE = globalVariables.getNAME_OF_DATABASE();
        String USERNAME = globalVariables.getNAME_OF_DATABASE();
        String PASSWORD = globalVariables.getPASSWORD();

        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + NAME_OF_DATABASE, USERNAME, PASSWORD);
            c.setAutoCommit(true);

            String sql = "UPDATE POI SET TITLE=?, LONGITUDE=?, LATITUDE=?, DESCRIPTION=?, TYPE=? WHERE ID=?";
            PreparedStatement stmt = c.prepareStatement(sql);

            //Bind values into the parameters.
            stmt.setString(1, title);
            stmt.setDouble(2, Double.parseDouble(lon));
            stmt.setDouble(3, Double.parseDouble(lat));
            stmt.setString(4, desc);
            stmt.setString(5, type);
            stmt.setInt(6, Integer.parseInt(poi));
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
