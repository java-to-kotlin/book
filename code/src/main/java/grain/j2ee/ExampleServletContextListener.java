package grain.j2ee;

import com.scurrilous.uritemplate.URITemplate;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.http.HttpClient;

/// begin: listener
public class ExampleServletContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        context.setAttribute("example.httpClient", createHttpClient());
        context.setAttribute("example.serviceUriTemplate", new URITemplate(
            context.getInitParameter("example.serviceUriTemplate")));
        /// note: listener [...]
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        /// note: listener [...]
    }

    private HttpClient createHttpClient() {
        /// mute: listener [...]
        return HttpClient.newHttpClient();
        /// resume: listener
    }

    /// note: listener [...]
}
/// end: listener
