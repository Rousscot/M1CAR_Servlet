import client.Client;
import client.WrongInformationException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "login", urlPatterns = {"/Login"}, loadOnStartup = 1, initParams = {@WebInitParam(name = "host", value = "localhost"), @WebInitParam(name = "port", value = "8000")})
public class Login extends HttpServlet {

    protected String host;
    protected Integer port;
    protected Client client;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.host = config.getInitParameter("host");
        this.port = Integer.valueOf(config.getInitParameter("port"));
        this.client = new Client(this.host, this.port); //TODO Manage error
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("host", this.host);
        request.setAttribute("port", this.port);
        request.setAttribute("Error", "");
        this.getServletContext().getRequestDispatcher("/WebPages/LoginPage.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("host", this.host);
        request.setAttribute("port", this.port);
        try {
            this.client.connect(request.getParameter("identifiant"), request.getParameter("password"));
            this.getServletContext().getRequestDispatcher("/WebPages/LoggedPage.jsp").forward(request, response);
        } catch (WrongInformationException e) {
            request.setAttribute("Error", "<p> Wrong informations </p>");
            this.getServletContext().getRequestDispatcher("/WebPages/LoginPage.jsp").forward(request, response);
        } catch (IOException e) {
            request.setAttribute("Error", "<p> Network Error </p>");
            this.getServletContext().getRequestDispatcher("/WebPages/LoginPage.jsp").forward(request, response);
        }
    }
}