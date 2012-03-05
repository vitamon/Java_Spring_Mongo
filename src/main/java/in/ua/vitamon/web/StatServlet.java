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
public class StatServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(StatServlet.class);

    //private DataPersister dataPersister = new DataPersister();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        /*request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        log.debug("doGet");
        DataEntity d = DataEntity.parseEntry(request.getParameterMap());*/
        //if (d != null) {
           /* Collection<DataEntity> entries = dataPersister.findAllMatches(d.getAppId());
            log.debug("found " + entries.size() + " entries: " + entries.toString());
            request.setAttribute("entries", entries);*/
            forward(request, response, "fx.html");
        /*} else {
            log.info("wrong or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }*/

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
