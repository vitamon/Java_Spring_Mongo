package in.ua.vitamon.web;

import in.ua.vitamon.model.CityPair;
import in.ua.vitamon.services.IDistanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final long serialVersionUID = 12L;

    private static final Logger log = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private IDistanceService distanceService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void savePair(HttpServletRequest request,
                         @RequestParam("fromCity") String fromCity,
                         @RequestParam("toCity") String toCity,
                         @RequestParam("distance") Double distance,
                         HttpServletResponse response)
    {
        saveCityPair(request, fromCity, toCity, distance, response);
    }

    @RequestMapping(value = "/{fromCity}/{toCity}/{distance}", method = RequestMethod.POST)
    public void savePairRest(HttpServletRequest request,
                             @PathVariable String fromCity,
                             @PathVariable String toCity,
                             @PathVariable Double distance,
                             HttpServletResponse response)
    {
        saveCityPair(request, fromCity, toCity, distance, response);
    }

    @RequestMapping(value = "/{fromCity}/{toCity}", method = RequestMethod.GET)
    public void getDistance(HttpServletRequest request,
                            @PathVariable String fromCity,
                            @PathVariable String toCity,
                            HttpServletResponse response)
    {
        if (!setRequestEncoding(request, response)) return;

        CityPair data = CityPair.parse(fromCity, toCity);

        if (data == null)
        {
            log.debug("wrong or no data in request: {}", request.getQueryString());
            setBadRequestStatus(response);
            return;
        }

        CityPair result = distanceService.lookup(data.getCityPair());
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        try
        {
            response.getWriter().write(Double.toString(result.getDistance()));
        } catch (IOException e)
        {
            log.error(e.getMessage());
            setBadRequestStatus(response);
        }
    }

    private void saveCityPair(HttpServletRequest request, String fromCity, String toCity, Double distance, HttpServletResponse response)
    {
        if (!setRequestEncoding(request, response)) return;
        log.debug("request data: {}, {}, {} ", fromCity, toCity, distance);

        CityPair data = CityPair.parse(fromCity, toCity, distance);

        if (data != null)
        {
            distanceService.upsert(data);
            distanceService.upsert(CityPair.parse(toCity, fromCity, distance));
            response.setStatus(HttpServletResponse.SC_OK);
        } else
        {
            log.debug("wrong or no data in request: " + request.getQueryString());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private boolean setRequestEncoding(HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e)
        {
            log.error(e.getMessage());
            setBadRequestStatus(response);
            return false;
        }
        return true;
    }

    private void setBadRequestStatus(HttpServletResponse response)
    {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}
