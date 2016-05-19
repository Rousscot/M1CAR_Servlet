import client.Client;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@WebServlet(name = "listAnnuaire", urlPatterns = {"/listAnnuaire"})
public class ListeAnnuaireServlet extends HttpServlet {

    protected Client client;
    protected String nameRep;
    protected String[] listRep;
    protected Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> commands;

    @Override
    public void init() throws ServletException {
        super.init();
        this.commands = new HashMap<>();
        this.commands.put("Selection", (HttpServletRequest request, HttpServletResponse response) -> {
            this.nameRep = this.listRep[Integer.valueOf(request.getParameter("annuaire"))];
            this.launchPage(request, response);
        });
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.client = (Client) request.getSession(false).getAttribute("client");
        this.listRep = client.getRepertoires().listerRepertoires();
        if (this.listRep.length > 0) {
            this.nameRep = this.listRep[0];
        }
        this.launchPage(request, response);
    }

    public void launchPage(HttpServletRequest request, HttpServletResponse response){
        this.setRepertoiresIn(request);
        try {
            this.getServletContext().getRequestDispatcher("/WebPages/ListAnnuairePage.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();//TODO
        } catch (IOException e) {
            e.printStackTrace();//TODO
        }
    }

    public void setRepertoiresIn(HttpServletRequest request) {
        this.listRep = client.getRepertoires().listerRepertoires();
        request.setAttribute("annuaireName", this.nameRep);
        request.setAttribute("annuairesSize", this.listRep.length);
        request.setAttribute("annuairesOptions", this.createOptions());
    }

    public String createOptions() {
        StringBuilder sb = new StringBuilder();
        for (Integer i = 0; i < this.listRep.length; i++) {
            sb.append("<option value=\"");
            sb.append(i.toString());
            sb.append("\"");
            if (i == 0) {
                sb.append(" selected=\"true\"");
            }
            sb.append(">");
            sb.append(this.listRep[i]);
            sb.append("</option>\n");
        }
        return sb.toString();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.commands.getOrDefault(this.getActionName(request), defaultClosureForError()).accept(request, response);
    }

    public BiConsumer<HttpServletRequest, HttpServletResponse> defaultClosureForError() {
        return this::launchPage;
    }

    public String getActionName(HttpServletRequest request) {
        for (String value : request.getParameterMap().keySet()) {
            if (value.startsWith("action:")) {
                return value.split(":")[1];
            }
        }
        throw new IndexOutOfBoundsException();
    }

}