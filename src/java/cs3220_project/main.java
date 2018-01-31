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

public class main extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //Check if the user is logged in
        String result = methods.isLoggedIn(request.getCookies());
        if (result.equals("exception") || result.equals("")) {
            request.setAttribute("loggedIn", false);
        } else {
            request.setAttribute("loggedIn", true);
        }
        //Check if the user is an admin
        if (!methods.is_user_an_admin(result)) {
            request.setAttribute("admin", false);
        } else {
            request.setAttribute("admin", true);
        }

        //Get user's location, if the lon isn't in the url
        if (request.getParameter("lon") == null && request.getParameter("error") == null) {
            getServletContext().removeAttribute("longitude");
            response.sendRedirect("https://virock.com.ng/tools/location.php");
        } else {
            //If the lon is in the url
            //If the user is searching, place search string
            if (request.getParameter("search") != null) {
                request.setAttribute("search", request.getParameter("search"));
            }
            if (getServletContext().getAttribute("ID") != null) {
                //request.setAttribute("best_location", getServletContext().getAttribute("best_location"));
                //getServletContext().removeAttribute("best_location");
                String ID = getServletContext().getAttribute("ID").toString();
                getServletContext().removeAttribute("ID");
                //Go to database, get details about this particular POI

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
                    String sql = " SELECT TITLE, TYPE, LONGITUDE, LATITUDE, DESCRIPTION FROM POI WHERE ID =?";
                    PreparedStatement stmt = c.prepareStatement(sql);

                    //Bind values into the parameters.
                    stmt.setInt(1, Integer.parseInt(ID));
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        getServletContext().setAttribute("desc", rs.getString("DESCRIPTION"));
                        getServletContext().setAttribute("title", rs.getString("TITLE"));
                        String type = rs.getString("TYPE");
                        if (type.equals("Food_Drink")) {
                            type = "Food and Drink";
                        }
                        //getServletContext().setAttribute("type", rs.getString("TYPE"));
                        getServletContext().setAttribute("type", type);
                        getServletContext().setAttribute("longitude", rs.getString("LONGITUDE"));
                        getServletContext().setAttribute("latitude", rs.getString("LATITUDE"));
                        //send info to main.jsp
                    }

                } catch (Exception e) {
                    //If exception occurs, Send error code in url
                    response.sendRedirect(globalVariables.getLOCAL_MAIN() + "?error=" + e.getLocalizedMessage());
                    //response.getWriter().print(e.getLocalizedMessage());
                    return;
                } finally {
                    try {
                        if (c != null) {
                            c.close();
                        }
                    } catch (Exception e) {
                        response.sendRedirect(globalVariables.getLOCAL_MAIN() + "?error=exception");
                        return;
                    }
                }

            }
            //send to main.jsp
            request.getRequestDispatcher("WEB-INF/main.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
