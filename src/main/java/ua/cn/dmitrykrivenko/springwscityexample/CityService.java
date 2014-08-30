package ua.cn.dmitrykrivenko.springwscityexample;

import org.springframework.data.mongodb.core.MongoOperations;

/**
 * 
 * @author Dmitry Krivenko <dmitrykrivenko@gmail.com>
 */
public interface CityService {

	public String getTwoCitiesAndDistance(String param);

	public Integer getDistanceBetweenTwoCities(String city1, String city2);

	public void setMongo(MongoOperations mongoOperations);

}
