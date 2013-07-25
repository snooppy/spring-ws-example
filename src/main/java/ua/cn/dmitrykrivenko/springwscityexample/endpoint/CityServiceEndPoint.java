package ua.cn.dmitrykrivenko.springwscityexample.endpoint;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.soap.addressing.server.annotation.Action;
import ua.cn.dmitrykrivenko.domain.GetDistanceBetweenTwoCitiesRequest;
import ua.cn.dmitrykrivenko.domain.GetDistanceBetweenTwoCitiesResponse;
import ua.cn.dmitrykrivenko.domain.GetTwoCitiesAndDistanceRequest;
import ua.cn.dmitrykrivenko.domain.GetTwoCitiesAndDistanceResponse;
import ua.cn.dmitrykrivenko.domain.ObjectFactory;
import ua.cn.dmitrykrivenko.springwscityexample.CityService;

/**
 *
 * @author Dmitry Krivenko <dmitrykrivenko@gmail.com>
 */
@Endpoint
public class CityServiceEndPoint {

    private final CityService cityService;
    private final ObjectFactory objectFactory;

    public CityServiceEndPoint(CityService cityService, ObjectFactory factory) {
        this.cityService = cityService;
        this.objectFactory = factory;
    }

    @Action("http://localhost:8080/CityService/GetTwoCitiesAndDistance")
    public GetTwoCitiesAndDistanceResponse getTwoCitiesAndDistance(GetTwoCitiesAndDistanceRequest citiesAndDistanceRequest) {
        GetTwoCitiesAndDistanceResponse response = objectFactory.createGetTwoCitiesAndDistanceResponse();
        String responseText = cityService.getTwoCitiesAndDistance(citiesAndDistanceRequest.getParam());
        response.setResponse(responseText);
        return response;
    }

    @Action("http://localhost:8080/CityService/GetDistanceBetweenTwoCities")
    public GetDistanceBetweenTwoCitiesResponse getDistanceBetweenTwoCities(GetDistanceBetweenTwoCitiesRequest betweenTwoCities) {
        GetDistanceBetweenTwoCitiesResponse response = objectFactory.createGetDistanceBetweenTwoCitiesResponse();
        Integer responseInt = cityService.getDistanceBetweenTwoCities(betweenTwoCities.getCity1(), betweenTwoCities.getCity2());
        response.setResponse(responseInt);
        return response;
    }

    public void setMongo(MongoOperations mongo) {
        this.cityService.setMongo(mongo);
    }
}
