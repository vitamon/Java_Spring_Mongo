package in.ua.vitamon.web;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class SaveDataServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(SaveDataServlet.class);

    private DataPersister dataPersister = new DataPersister();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        log.debug("doGet");

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        log.debug("doPost");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        DataEntity d = DataEntity.parseEntry(request.getParameterMap());
        if (d != null) {
            dataPersister.create(d);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            log.info("wrong or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void saveEntry(DataEntity data) {

    }
}
