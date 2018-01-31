package cs3220_project;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class details extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //If servlet context variable 'longitude' is empty, redirect to main page
        if (getServletContext().getAttribute("longitude") == null) {
            response.sendRedirect(globals.globalVariables.getMAIN());
        } else {
            //else, display details of POI
            String referer = request.getHeader("referer");
            if (referer == null) {
                request.setAttribute("main", "main");
            } else {
                request.setAttribute("main", referer);
            }
            request.getRequestDispatcher("WEB-INF/details.jsp").forward(request, response);
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
