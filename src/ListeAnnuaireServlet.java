import client.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "listAnnuaire", urlPatterns = {"/listAnnuaire"})
public class ListeAnnuaireServlet extends HttpServlet {

    protected Client client;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.client = (Client) request.getSession(false).getAttribute("client");
        String[] repertoires = client.getRepertoires().listerRepertoires();
        request.setAttribute("annuairesSize", repertoires.length);
        request.setAttribute("annuairesOptions",  this.createOptionsForm(repertoires));
        this.getServletContext().getRequestDispatcher("/WebPages/ListAnnuairePage.jsp").forward(request, response);
    }

    public String createOptionsForm(String[] annuaires) {
        StringBuilder sb = new StringBuilder();
        for ( Integer i = 0 ; i < annuaires.length ; i ++){
            sb.append("<option value=\"");
            sb.append(i.toString());
            sb.append("\"");
            if( i == 0){
                sb.append(" selected=\"true\"");
            }
            sb.append(">");
            sb.append(annuaires[i]);
            sb.append("</option>\n");
        }
        return sb.toString();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /*public HttpSession getSession(HttpServletRequest request){
        Cookie cookie = null;
        for(Cookie cook : request.getCookies()){
            if(cook.getName().equals("SessionCAR")){
                cookie = cook;
            }
        }
        System.out.println(cookie.getValue());
    }*/
}