package ua.cn.dmitrykrivenko.springwscityexample;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

/**
 *
 * @author Dmitry Krivenko <dmitrykrivenko@gmail.com>
 */
public class CityServiceImpl implements CityService {

    private MongoOperations mongoOperation;

    public CityServiceImpl(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

    @Override
    public String getTwoCitiesAndDistance(String param) {
        if (param != null) {
            if (param.equals("1")) {
                return Cities.DEFAULT_CITIES_AND_DISTANCE_1;
            } else if (param.equals("2")) {
                return Cities.DEFAULT_CITIES_AND_DISTANCE_2;
            }
        }
        return null;
    }

    @Override
    public Integer getDistanceBetweenTwoCities(String city1, String city2) {
        Query searchCityQuery = new Query(Criteria.where("city1").is(city1.toString()).and("city2").is(city2.toString()));

        City city = mongoOperation.findOne(searchCityQuery, City.class);
        if (city != null) {
            System.out.println(city);
            return city.getDistanceBetween();
        }
        return -1;

    }

    @Override
    public void setMongo(MongoOperations mongoOperations) {
        this.mongoOperation = mongoOperations;
    }
}
