package grain.j2ee;

import com.scurrilous.uritemplate.URITemplate;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.http.HttpClient;

/// begin: listener
public class ExampleServletContextListener
    implements ServletContextListener {

    public void contextInitialized(ServletContextEvent contextEvent) {
        ServletContext context = contextEvent.getServletContext();
        context.setAttribute("example.httpClient", createHttpClient());
        context.setAttribute("example.template", new URITemplate(
            context.getInitParameter("example.template")));
        /// note: listener [...]
    }

    /// mute: listener [...]
    public void contextDestroyed(ServletContextEvent contextEvent) {
    }

    private HttpClient createHttpClient() {
        return HttpClient.newHttpClient();
    }
    /// resume: listener
}
/// end: listener
