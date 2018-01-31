package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class add_this_poi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String referer = request.getHeader("referer");
        if (referer == null) {
            response.sendRedirect(globalVariables.getADMIN_PAGE());
            return;
        }
        String title = request.getParameter("title");
        String type = request.getParameter("type");
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");
        String desc = request.getParameter("desc");

        String[] poi_details = {title, type, lon, lat, desc};
        getServletContext().setAttribute("poi_details", poi_details);
        if (!methods.paramValidator(referer, "add_poi", new String[]{title, lon, type, lat, desc})) {
            //If not, redirect to main page
            response.sendRedirect(globalVariables.getADD_POI() + "?error=invalid_input");
            return;
        }
        //Make sure user is logged in
        //If not, redirect to main page
        String result = methods.isLoggedIn(request.getCookies());
        if (result.equals("exception") || result.equals("")) {
            response.sendRedirect(globalVariables.getADD_POI());
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
            methods.insertIntoPOITable(c, new String[]{result, title, type, lon, lat, desc});
            getServletContext().removeAttribute("poi_details");
            response.sendRedirect(globalVariables.getMAIN());
        } catch (Exception e) {
            response.sendRedirect(globalVariables.getADD_POI() + "error=exception");
            return;
        } finally {
            try {
                if (c != null) {
                    c.close();
                }
            } catch (Exception e) {
                response.sendRedirect(globalVariables.getADD_POI() + "error=exception");
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
