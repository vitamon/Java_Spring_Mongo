package in.ua.vitamon.web;

import in.ua.vitamon.domain.CityPair;
import in.ua.vitamon.services.IDistanceService;
import in.ua.vitamon.helpers.Logger;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/")
public class AppController {
    private static final long serialVersionUID = 1L;

    @Logger
    private Log log;

    @Autowired
    private IDistanceService distanceService;

    /**
     * URL encoded interface
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void savePair(HttpServletRequest request,
                         @RequestParam("fromCity") String fromCity,
                         @RequestParam("toCity") String toCity,
                         @RequestParam("distance") String distance,
                         HttpServletResponse response) {
        saveCityPair(request, fromCity, toCity, distance, response);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void getDistance(HttpServletRequest request,
                            @RequestParam("fromCity") String fromCity,
                            @RequestParam("toCity") String toCity,
                            HttpServletResponse response) {
        getDistanceBetweenCities(request, fromCity, toCity, response);
    }


    /**
     * REST interface
     */
    @RequestMapping(value = "/{fromCity}/{toCity}/{distance}", method = RequestMethod.POST)
    public void savePairRest(HttpServletRequest request,
                             @PathVariable String fromCity,
                             @PathVariable String toCity,
                             @PathVariable String distance,
                             HttpServletResponse response) {
        saveCityPair(request, fromCity, toCity, distance, response);
    }


    @RequestMapping(value = "/{fromCity}/{toCity}", method = RequestMethod.GET)
    public void getDistanceRest(HttpServletRequest request,
                                @PathVariable String fromCity,
                                @PathVariable String toCity,
                                HttpServletResponse response) {
        getDistanceBetweenCities(request, fromCity, toCity, response);
    }


    private void saveCityPair(HttpServletRequest request,
                              String fromCity,
                              String toCity,
                              String distance,
                              HttpServletResponse response) {
        if (!setRequestEncoding(request, response)) return;

        if (log.isDebugEnabled()) {
            log.debug("request data: " + fromCity + " " + toCity + " " + distance);
        }

        // parse data
        CityPair data = CityPair.parse(fromCity, toCity, distance);
        if (data != null) {
            if (distanceService.upsert(data)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            log.debug("Invalid or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void getDistanceBetweenCities(HttpServletRequest request,
                                            String fromCity,
                                            String toCity,
                                            HttpServletResponse response) {
        if (!setRequestEncoding(request, response)) return;

        // parse data
        CityPair data = CityPair.parse(fromCity, toCity);
        if (data == null) {
            log.debug("Invalid or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // seek result
        CityPair result = null;
        try {
            result = distanceService.findOne(data.getCityPair());
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if (result == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        try {
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(Double.toString(result.getDistance()));
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private boolean setRequestEncoding(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
        return true;
    }
}
