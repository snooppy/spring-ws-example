package ua.cn.dmitrykrivenko.springwscityexample.endpoint;

import com.mongodb.Mongo;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.RuntimeConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import java.util.Arrays;
import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import ua.cn.dmitrykrivenko.domain.GetDistanceBetweenTwoCitiesRequest;
import ua.cn.dmitrykrivenko.domain.GetDistanceBetweenTwoCitiesResponse;
import ua.cn.dmitrykrivenko.domain.GetTwoCitiesAndDistanceRequest;
import ua.cn.dmitrykrivenko.domain.GetTwoCitiesAndDistanceResponse;
import ua.cn.dmitrykrivenko.domain.ObjectFactory;
import ua.cn.dmitrykrivenko.springwscityexample.Cities;
import ua.cn.dmitrykrivenko.springwscityexample.City;

/**
 *
 * @author Dmitry Krivenko <dmitrykrivenko@gmail.com>
 */
public class CityServiceEndPointTest {

    private static final String LOCALHOST = "127.0.0.1";
    private static final String DB_NAME = "test_city_db";
    private static final int MONGO_TEST_PORT = 27018;
    private static MongodProcess mongoProcess;
    private static Mongo mongo;
    private static MongoTemplate template;
    private static CityServiceEndPoint endpoint;
    private static final ObjectFactory factory = new ObjectFactory();
    private static MongoOperations mongoOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        endpoint = (CityServiceEndPoint) context.getBean("cityServiceEndPoint");
        // mongoOperation = (MongoOperations) context.getBean("mongoTemplate");

        RuntimeConfig config = new RuntimeConfig();
        config.setExecutableNaming(new UserTempNaming());

        MongodStarter starter = MongodStarter.getInstance(config);

        MongodExecutable mongoExecutable = starter.prepare(new MongodConfig(Version.V2_2_0, MONGO_TEST_PORT, false));
        mongoProcess = mongoExecutable.start();

        mongo = new Mongo(LOCALHOST, MONGO_TEST_PORT);
        mongo.getDB(DB_NAME);
        template = new MongoTemplate(mongo, DB_NAME);
        endpoint.setMongo(template);
    }

    @AfterClass
    public static void shutdownDB() throws InterruptedException {
        mongo.close();
        mongoProcess.stop();
    }

    @Test
    public void testGetTwoCitiesAndDistance() {
        GetTwoCitiesAndDistanceRequest request = factory.createGetTwoCitiesAndDistanceRequest();
        request.setParam("1");
        GetTwoCitiesAndDistanceResponse response = endpoint.getTwoCitiesAndDistance(request);
        String responseText = response.getResponse();
        assertEquals(Cities.DEFAULT_CITIES_AND_DISTANCE_1, responseText);

        List<String> splitResponce = Arrays.asList(responseText.split(","));
        City city = new City(splitResponce.get(0), splitResponce.get(1), Integer.valueOf(splitResponce.get(2)));

        template.save(city);
        System.out.println(city);

        request.setParam("2");
        response = endpoint.getTwoCitiesAndDistance(request);
        responseText = response.getResponse();
        assertEquals(Cities.DEFAULT_CITIES_AND_DISTANCE_2, responseText);

        splitResponce = Arrays.asList(responseText.split(","));
        city = new City(splitResponce.get(0), splitResponce.get(1), Integer.valueOf(splitResponce.get(2)));

        template.save(city);
        System.out.println(city);
    }

    @Test
    public void testGetDistanceBetweenTwoCities() throws Exception {
        GetDistanceBetweenTwoCitiesRequest dist = factory.createGetDistanceBetweenTwoCitiesRequest();
        dist.setCity1(Cities.DEFAULT_CITY_1);
        dist.setCity2(Cities.DEFAULT_CITY_2);
        GetDistanceBetweenTwoCitiesResponse response = endpoint.getDistanceBetweenTwoCities(dist);
        assertEquals(Integer.valueOf(Cities.DEFAULT_DISTANCE_1).intValue(), response.getResponse());

        dist.setCity1(Cities.DEFAULT_CITY_1);
        dist.setCity2(Cities.DEFAULT_CITY_3);
        response = endpoint.getDistanceBetweenTwoCities(dist);
        assertEquals(Integer.valueOf(Cities.DEFAULT_DISTANCE_2).intValue(), response.getResponse());

        dist.setCity1("test");
        response = endpoint.getDistanceBetweenTwoCities(dist);
        assertEquals(-1, response.getResponse());
    }
}
