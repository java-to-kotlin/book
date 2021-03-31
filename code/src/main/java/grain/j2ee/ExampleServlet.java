package grain.j2ee;

import com.scurrilous.uritemplate.URITemplate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpClient;

/// begin: servlet
public class ExampleServlet extends HttpServlet {
    private HttpClient httpClient;
    private URITemplate routeServiceTemplate;
    /// note: servlet [...]

    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        ServletContext context = servletConfig.getServletContext();
        this.httpClient =
            (HttpClient) context.getAttribute("example.httpClient");
        this.routeServiceTemplate =
            (URITemplate) context.getAttribute("example.serviceUriTemplate");
        /// note: servlet [...]
    }

    /// mute: servlet [...]
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }
    /// resume: servlet
}
/// end: servlet
