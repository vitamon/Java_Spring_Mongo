package in.ua.vitamon.web;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersisterService;
import in.ua.vitamon.services.IXMLReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: vit.tam@gmail.com
 */
@Controller
@SuppressWarnings(value = "serial")
public class AppController {
    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    private DataPersisterService dataPersisterService;

    @Autowired
    public void setDataPersisterService(DataPersisterService dataPersisterService) {
        this.dataPersisterService = dataPersisterService;
    }

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public String redirectToFlex() {
        log.info("Controller: flex");
        return "flex";
    }

    @RequestMapping(value = "/xml/APP_ID={appID}", method = RequestMethod.GET)
    public void getXMLReport(@PathVariable String appID,
                             HttpServletRequest request,
                             HttpServletResponse response, IXMLReportService reportService) {
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(reportService.getXMLreport(appID));
        } catch (IOException e) {
            log.error("Error " + e.getMessage());
        }
        log.info("Controller: xml report");
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void saveData(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        log.debug("Save");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        DataEntity d = DataEntity.parseEntry(request.getParameterMap());
        if (d != null) {
            dataPersisterService.create(d);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            log.info("wrong or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
