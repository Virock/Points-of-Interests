package cs3220_project;

import globals.globalVariables;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class search extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //if loc is empty, go back to main page
        String loc = request.getParameter("loc");
        String lon = request.getParameter("lon");
        String lat = request.getParameter("lat");
        if (loc == null || loc.trim().length() == 0) {
            response.sendRedirect("main");
        } else {
            
            if (lon == null || lon.trim().length() == 0) {
                
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
                    String sql = " SELECT LONGITUDE, LATITUDE, ID FROM POI WHERE TYPE =?";
                    PreparedStatement stmt = c.prepareStatement(sql);

                    //Bind values into the parameters.
                    stmt.setString(1, loc);
                    ResultSet rs = stmt.executeQuery();
                    //ArrayList<Location> locations = new ArrayList<>();
                    String query = "";
                    while (rs.next()) {
                        query += "&ID[]=" + rs.getInt("ID") + "&lon[]=" + rs.getString("LONGITUDE") + "&lat[]=" + rs.getString("LATITUDE");
                        //locations.add(new Location(rs.getString("LONGITUDE"), rs.getString("LATITUDE"), rs.getInt("ID")));
                    }
                    //Send all these locations to the search.php page
                    response.sendRedirect("https://virock.com.ng/tools/search.php?loc=" + loc + query);
                    
                } catch (Exception e) {
                    //If exception occurs, Send error code in url
                    response.sendRedirect(globalVariables.getMAIN() + "?error=" + e.getLocalizedMessage());
                    //response.getWriter().print(e.getLocalizedMessage());
                    return;
                } finally {
                    try {
                        if (c != null) {
                            c.close();
                        }
                    } catch (Exception e) {
                        response.sendRedirect(globalVariables.getMAIN() + "?error=exception");
                        return;
                    }
                }
            } else {
                //get the id from the request scope
                //put it in the servlet scope
                getServletContext().setAttribute("ID", request.getParameter("ID"));
                //Redirect to main page
                response.sendRedirect("main?lat=" + lat + "&lon=" + lon);
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
