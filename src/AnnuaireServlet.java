import tpRepertoire.client.Client;
import tpRepertoire.repertoire.Personne;
import tpRepertoire.server.ServerRepertoire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

@WebServlet(name = "annuaire", urlPatterns = {"/annuaire"})
public class AnnuaireServlet extends AbstractServlet {

    protected ServerRepertoire repertoire;
    protected String nameEntry;
    protected String[] entries;

    @Override
    public void initPostCommands(Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> map) {
        map.put("Access", (HttpServletRequest request, HttpServletResponse response) -> {
            this.repertoire = ((Client) request.getSession(false).getAttribute("client")).getRepertoires().chercherRepertoire((String) request.getAttribute("annuaireName"));
            this.selectFirstEntry();
            this.launchPage(request, response);
        });
        map.put("Selection", (HttpServletRequest request, HttpServletResponse response) -> {
            this.nameEntry = this.entries[Integer.valueOf(request.getParameter("entry"))];
            this.launchPage(request, response);
        });
        map.put("Delete", (HttpServletRequest request, HttpServletResponse response) -> {
            this.repertoire.retirerPersonne(this.nameEntry);
            this.selectFirstEntry();
            this.launchPage(request, response);
        });
        map.put("Add", (HttpServletRequest request, HttpServletResponse response) -> {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String url = request.getParameter("url");
            String descr = request.getParameter("descr");
            this.repertoire.ajouterPersonne(new Personne(name, email, url, descr));
            this.launchPage(request, response);
        });
        map.put("Update", (HttpServletRequest request, HttpServletResponse response) -> {
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String url = request.getParameter("url");
            String descr = request.getParameter("descr");
            this.repertoire.modifierPersonne(new Personne(name, email, url, descr));
            this.launchPage(request, response);
        });
    }

    /**
     * I will create a list of options that should be used by the presentation.
     * This list will contains all the entries I have.
     * @return The HTML template of the list.
     */
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

    @Override
    public void launchPage(HttpServletRequest request, HttpServletResponse response) {
        this.entries = this.repertoire.listerPersonnes();
        if (this.nameEntry == null) {
            request.setAttribute("email", "");
            request.setAttribute("url", "");
            request.setAttribute("descr", "");
            request.setAttribute("entryName", "No entry selected");
        } else {
            Personne entry = this.repertoire.chercherPersonne(this.nameEntry);
            request.setAttribute("email", entry.getEmail());
            request.setAttribute("url", entry.getUrl());
            request.setAttribute("descr", entry.getInfo());
            request.setAttribute("entryName", this.nameEntry);
        }
        request.setAttribute("entriesSize", this.entries.length);
        request.setAttribute("entryOptions", this.createOptions());
        try {
            this.getServletContext().getRequestDispatcher("/WebPages/RepertoirePage.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();//TODO write in response that the request did not succeeded and why. Change the status
        }
    }
}