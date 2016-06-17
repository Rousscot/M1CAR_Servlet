import tpRepertoire.client.Client;
import tpRepertoire.client.ClientRepertoireList;
import tpRepertoire.server.ServerRepertoire;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.function.BiConsumer;

@WebServlet(name = "listAnnuaire", urlPatterns = {"/listAnnuaire"})
public class ListeAnnuaireServlet extends AbstractServlet {

    protected ClientRepertoireList repertoireList;
    protected String nameRep;
    protected String[] repertoires;

    @Override
    public void initPostCommands(Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> map) {
        map.put("Selection", (HttpServletRequest request, HttpServletResponse response) -> {
            this.nameRep = this.repertoires[Integer.valueOf(request.getParameter("annuaire"))];
            this.launchPage(request, response);
        });
        map.put("Add", (HttpServletRequest request, HttpServletResponse response) -> {
            this.repertoireList.ajouterRepertoire(new ServerRepertoire(request.getParameter("annuaireName")));
            this.launchPage(request, response);
        });
        map.put("Delete", (HttpServletRequest request, HttpServletResponse response) -> {
            this.repertoireList.retirerRepertoire(this.nameRep);
            this.selectFirstRep();
            this.launchPage(request, response);
        });
        map.put("Access", (HttpServletRequest request, HttpServletResponse response) -> {
            this.repertoireList.accederRepertoire(this.nameRep);
            request.setAttribute("annuaireName", this.nameRep);
            try {
                this.getServletContext().getRequestDispatcher("/annuaire").forward(request, response);
            } catch (IOException | ServletException e) {
                e.printStackTrace(); //TODO
            }
        });
    }

    /**
     * I will create a list of options that should be used by the presentation.
     * This list will contains all the repertoires I have.
     * @return The HTML template of the list.
     */
    public String createOptions() {
        StringBuilder sb = new StringBuilder();
        for (Integer i = 0; i < this.repertoires.length; i++) {
            sb.append("<option value=\"");
            sb.append(i.toString());
            sb.append("\"");
            if (i == 0) {
                sb.append(" selected=\"true\"");
            }
            sb.append(">");
            sb.append(this.repertoires[i]);
            sb.append("</option>\n");
        }
        return sb.toString();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        this.repertoireList = ((Client) request.getSession(false).getAttribute("client")).getRepertoires();
        this.selectFirstRep();
        this.launchPage(request, response);
    }

    public void selectFirstRep() {
        this.repertoires = this.repertoireList.listerRepertoires();
        if (this.repertoires.length > 0) {
            this.nameRep = this.repertoires[0];
        }
    }

    public void launchPage(HttpServletRequest request, HttpServletResponse response) {
        this.repertoires = this.repertoireList.listerRepertoires();
        request.setAttribute("annuaireName", this.nameRep);
        request.setAttribute("annuairesSize", this.repertoires.length);
        request.setAttribute("annuairesOptions", this.createOptions());
        try {
            this.getServletContext().getRequestDispatcher("/WebPages/ListAnnuairePage.jsp").forward(request, response);
        } catch (IOException | ServletException e) {
            e.printStackTrace();//TODO write in response that the request did not succeeded and why. Change the status
        }
    }
}