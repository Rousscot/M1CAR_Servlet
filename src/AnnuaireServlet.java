import client.Client;
import server.ServerRepertoire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@WebServlet(name = "annuaire", urlPatterns = {"/annuaire"})
public class AnnuaireServlet extends HttpServlet {

    protected Client client;
    protected ServerRepertoire repertoire;
    protected String nameEntry;
    protected String[] entries;
    protected Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> commands;

    @Override
    public void init() throws ServletException {
        super.init();
        this.commands = new HashMap<>();
        this.commands.put("Access", (HttpServletRequest request, HttpServletResponse response) -> {
            this.client = (Client) request.getSession(false).getAttribute("client");
            this.repertoire = this.client.getRepertoires().chercherRepertoire((String) request.getAttribute("nameRep"));
            this.selectFirstEntry();
            this.launchPage(request, response);
        });
    }

    public String createOptions() {
        StringBuilder sb = new StringBuilder();
        for (Integer i = 0; i < this.entries.length; i++) {
            sb.append("<option value=\"");
            sb.append(i.toString());
            sb.append("\"");
            if (i == 0) {
                sb.append(" selected=\"true\"");
            }
            sb.append(">");
            sb.append(this.entries[i]);
            sb.append("</option>\n");
        }
        return sb.toString();
    }

    public void selectFirstEntry() {
        this.entries = this.repertoire.listerPersonnes();
        if (this.entries.length > 0) {
            this.nameEntry = this.entries[0];
        }
    }

    public void launchPage(HttpServletRequest request, HttpServletResponse response){
        this.entries = this.repertoire.listerPersonnes();
        request.setAttribute("annuaireName", this.nameEntry);
        request.setAttribute("annuairesSize", this.entries.length);
        request.setAttribute("annuairesOptions", this.createOptions());
        try {
            this.getServletContext().getRequestDispatcher("/WebPages/ListAnnuairePage.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();//TODO write in response that the request did not succeeded and why. Change the status
        }
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