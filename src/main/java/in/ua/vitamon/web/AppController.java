package in.ua.vitamon.web;

import in.ua.vitamon.model.DataEntity;
import in.ua.vitamon.server.DataPersisterService;
import in.ua.vitamon.services.IXMLReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author: vit.tam@gmail.com
 */
@Controller
public class AppController {
   // private static final long serialVersionUID = 12L;

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private DataPersisterService dataPersisterService;

    @Autowired
    private IXMLReportService reportService;

    @RequestMapping(value = "/stat", method = RequestMethod.GET)
    public ModelAndView redirectToFlex() {
        log.info("Controller: redirect to flex");
        return new ModelAndView("redirect:/static/flex.html");
    }

    @RequestMapping(value = "/xml")
    public void getXMLReport(HttpServletRequest request,
                             HttpServletResponse response) {
        DataEntity d = DataEntity.parseEntry(request.getParameterMap());
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(reportService.getXMLreport(d.getAppId()));
        } catch (IOException e) {
            log.error("Error " + e.getMessage());
        }
        log.info("Controller: xml report served");
    }

    @RequestMapping(value = "/save")
    public void saveData(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

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
