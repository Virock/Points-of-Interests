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

public class edit_poi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Check if the user is an admin
        String referer = request.getHeader("referer");
        if (referer == null) {
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
        }
        String poi = request.getParameter("POI");
        if (!methods.paramValidator(referer, globalVariables.getADMIN_PAGE(), new String[]{poi}) && request.getParameter("error") == null) {
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

        Connection c = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + NAME_OF_DATABASE, USERNAME, PASSWORD);
            c.setAutoCommit(true);

            //Get information from all tables
            String sql = " SELECT * FROM POI WHERE ID =?";
            PreparedStatement stmt = c.prepareStatement(sql);

            //Bind values into the parameters.
            stmt.setInt(1, Integer.parseInt(poi));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String[] poi_details = {rs.getString("TITLE"), rs.getString("TYPE"), rs.getString("LONGITUDE"), rs.getString("LATITUDE"), rs.getString("DESCRIPTION")};
                //Send these details to the jsp page
                request.setAttribute("poi_details", poi_details);
                request.setAttribute("poi", poi);
                //Forward request to jsp page
                request.getRequestDispatcher("WEB-INF/edit_poi.jsp").forward(request, response);
                return;
            } else {
                response.sendRedirect(globalVariables.getADMIN_PAGE() + "?error=exception");
                return;
            }

        } catch (Exception e) {
            //If exception occurs, Send error code in url
            response.sendRedirect(globalVariables.getADMIN_PAGE() + "?error=exception");
            //response.getWriter().print(e.getLocalizedMessage());
            return;
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                response.sendRedirect(globalVariables.getADMIN_PAGE() + "?error=exception");
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
