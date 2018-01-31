package cs3220_project;

import globals.globalVariables;
import globals.methods;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class loginFormPage extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //If the user is logged in, redirect to main page
        String result = methods.isLoggedIn(request.getCookies());
        if (!result.equals("exception") && !result.equals("")) {
            response.sendRedirect(globalVariables.getMAIN());
            return;
        }
        //Check if there is an email and password in the servlet context
        //Send appropriate email and password to login page
        //Delete the email and password
        String email, password, registrationEmail, registrationUsername, registrationPassword;
        email = password = registrationEmail = registrationUsername = registrationPassword = "";
        if (getServletContext().getAttribute("storedEmail") != null) {
            email = (String) getServletContext().getAttribute("storedEmail");
            getServletContext().removeAttribute("storedEmail");
        }
        if (getServletContext().getAttribute("storedPassword") != null) {
            password = (String) getServletContext().getAttribute("storedPassword");
            getServletContext().removeAttribute("storedPassword");
        }
        if (getServletContext().getAttribute("storedRegistrationUsername") != null) {
            registrationUsername = (String) getServletContext().getAttribute("storedRegistrationUsername");
            getServletContext().removeAttribute("storedRegistrationUsername");
        }
        if (getServletContext().getAttribute("storedRegistrationEmail") != null) {
            registrationEmail = (String) getServletContext().getAttribute("storedRegistrationEmail");
            getServletContext().removeAttribute("storedRegistrationEmail");
        }
        if (getServletContext().getAttribute("storedRegistrationPassword") != null) {
            registrationPassword = (String) getServletContext().getAttribute("storedRegistrationPassword");
            getServletContext().removeAttribute("storedRegistrationPassword");
        }
        request.setAttribute("email", email);
        request.setAttribute("password", password);
        request.setAttribute("registrationEmail", registrationEmail);
        request.setAttribute("registrationUsername", registrationUsername);
        request.setAttribute("registrationPassword", registrationPassword);
        request.getRequestDispatcher("WEB-INF/http_referer.jsp").forward(request, response);
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
