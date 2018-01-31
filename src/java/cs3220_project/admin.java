package cs3220_project;

import globals.globalVariables;
import globals.methods;
import static globals.methods.convertArrayListOfPOIsToString;
import static globals.methods.convertArrayListOfUsersToString;
import models.POI;
import models.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class admin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Only a logged in admin can get to this page
        //First check if the user is logged in
        String result = methods.isLoggedIn(request.getCookies());
        //If the user is not logged in, return the user to the main page
        if (result.equals("exception") || result.equals("")) {
            response.sendRedirect(globalVariables.getMAIN());
            return;
        } else {
            //Then check if the user is an admin
            //If the user is not an admin, send the user back to the main page
            if (!methods.is_user_an_admin(result)) {
                response.sendRedirect(globalVariables.getMAIN());
                return;
            } else {
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

                    //Get information from all tables
                    String sql = " SELECT EMAIL, USERNAME FROM " + TABLE_NAME + " WHERE IS_ADMIN !=?";
                    PreparedStatement stmt = c.prepareStatement(sql);

                    //Bind values into the parameters.
                    stmt.setBoolean(1, true);
                    ResultSet rs = stmt.executeQuery();
                    ArrayList<User> users = new ArrayList<>();
                    while (rs.next()) {
                        users.add(new User(rs.getString("EMAIL"), rs.getString("USERNAME")));
                    }

                    sql = " SELECT * FROM POI";
                    stmt = c.prepareStatement(sql);

                    rs = stmt.executeQuery();
                    ArrayList<POI> pois = new ArrayList<>();
                    while (rs.next()) {
                        String type = rs.getString("TYPE");
                        if (type.equals("Food_Drink")) {
                            type = "Food and Drink";
                        }
                        pois.add(new POI(rs.getInt("ID"), rs.getString("CREATOR"), rs.getString("TITLE"), type, rs.getString("LONGITUDE"), rs.getString("LATITUDE"), rs.getString("DESCRIPTION"), rs.getDate("DATE_OF_CREATION")));
                    }
                    //admin can edit and delete POIs

                    //Send these details to the jsp page
                    //request.setAttribute("users", users);
                    request.setAttribute("display_users", convertArrayListOfUsersToString(users));
                    request.setAttribute("display_pois", convertArrayListOfPOIsToString(pois));
                    //request.setAttribute("pois", pois);
                    //Forward request to jsp page
                    request.getRequestDispatcher("WEB-INF/admin.jsp").forward(request, response);
                    return;
                } catch (Exception e) {
                    //If exception occurs, Send error code in url
                    response.sendRedirect(globalVariables.getMAIN() + "?error=exception");
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
