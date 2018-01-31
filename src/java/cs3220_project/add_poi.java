package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class add_poi extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Make sure user is logged in
        //If not, redirect to main page
        String result = methods.isLoggedIn(request.getCookies());
        if (result.equals("exception") || result.equals("")) {
            response.sendRedirect(globalVariables.getMAIN());
            return;
        }
        //If there is an error
        //Place the values back into the form
        //Remove them from the servlet context
        if (getServletContext().getAttribute("poi_details") != null) {
            request.setAttribute("poi_details", (String[]) getServletContext().getAttribute("poi_details"));
            getServletContext().removeAttribute("poi_details");
        }
        request.getRequestDispatcher("WEB-INF/add_poi.jsp").forward(request, response);
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
