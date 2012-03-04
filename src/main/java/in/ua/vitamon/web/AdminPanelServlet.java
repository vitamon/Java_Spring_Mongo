package in.ua.vitamon.web;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author: vit.tam@gmail.com
 */
public class AdminPanelServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(IndexServlet.class);

    private DataPersister dataPersister = new DataPersister();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        log.debug("doGet");
        // get
        Collection<DataEntity> entries = dataPersister.getAll();
        request.setAttribute("messages", entries);

        forward(request, response, "index.jsp");
    }

    /**
     * Forwards request and response to given path. Handles any exceptions
     * caused by forward target by printing them to logger.
     *
     * @param request
     * @param response
     * @param path
     */
    protected void forward(HttpServletRequest request,
                           HttpServletResponse response, String path) {
        try {
            RequestDispatcher rd = request.getRequestDispatcher(path);
            rd.forward(request, response);
        } catch (Throwable tr) {
            if (log.isErrorEnabled()) {
                log.error("Cought Exception: " + tr.getMessage());
                log.debug("StackTrace:", tr);
            }
        }
    }

}
