import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

//отсортировать по имени (алфавит)
public class HelloServlet extends HttpServlet {
    private static Logger logger = Logger.getLogger(HelloServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.getWriter().write("<h1>Hello world!</h1>");
        } catch (IOException e) {
            logger.info(e.toString());
        }
    }
}
