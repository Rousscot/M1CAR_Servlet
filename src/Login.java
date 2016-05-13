import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", urlPatterns = {"/Login"}, loadOnStartup = 1, initParams = {@WebInitParam(name = "host", value = "localhost"), @WebInitParam(name = "port", value = "8000")})
public class Login extends HttpServlet {

    protected String host;
    protected Integer port;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.host = config.getInitParameter("host");
        this.port = Integer.valueOf(config.getInitParameter("port"));
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("host", this.host);
        request.setAttribute("port", this.port);
        this.getServletContext().getRequestDispatcher("/WebPages/LoginPage.jsp").forward( request, response );
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
