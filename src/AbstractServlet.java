import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * I am an Abstract class to help with the creation of Servlets.
 *
 * Every buttons of the page will evaluate a Closure (Lambda).
 * To keep those closures I store them in a Map initialized at startup. I associate the name of an action to each Closure.
 *
 * I can be improved: For now I use the POST protocol for everything, but the class can be easily extended to be able to manage other HTTP methods.
 */
public abstract class AbstractServlet extends HttpServlet {

     /*
      *  I am a Map that will associate to a HTTP protocol a collection of actions. Those action have a name and a Closure.
      *
      */
    protected Map<String, Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>>> commands;

    @Override
    public void init() throws ServletException {
        super.init();
        this.initCommands();
    }

    public void initCommands() {
        this.commands = new HashMap<>();
        Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> postMap = new HashMap<>();
        this.initPostCommands(postMap);
        this.commands.put("POST", postMap);
    }

    /**
     * I take a Map as parameter and my subclasses should add their POST actions to the map.
     * @param map The map I should complete.
     */
    public abstract void initPostCommands(Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> map);

    public Map<String, BiConsumer<HttpServletRequest, HttpServletResponse>> getPostCommands(){
        return this.commands.get("POST");
    }

    /**
     * I should launch the default page managed by the servlet.
     * @param request the request
     * @param response the response
     */
    public abstract void launchPage(HttpServletRequest request, HttpServletResponse response);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getPostCommands().getOrDefault(this.getActionName(request), defaultClosureForError()).accept(request, response);
    }

    public BiConsumer<HttpServletRequest, HttpServletResponse> defaultClosureForError() {
        return this::launchPage;
    }

    /**
     * I will check in a request which action the form should launch
     * @param request the request
     * @return THe name of the action that should be launch for this request.
     */
    public String getActionName(HttpServletRequest request) {
        for (String value : request.getParameterMap().keySet()) {
            if (value.startsWith("action:")) {
                return value.split(":")[1];
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
