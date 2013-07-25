package ua.cn.dmitrykrivenko.springwscityexample;

import static org.junit.Assert.*;
import org.junit.BeforeClass;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class CityServiceImplTest {

    private static CityService cityService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        cityService = (CityServiceImpl) context.getBean("cityServiceImpl");
    }

    @Test
    public void testGetTwoCitiesAndDistance() {
        String text = cityService.getTwoCitiesAndDistance("1");
        assertEquals(Cities.DEFAULT_CITIES_AND_DISTANCE_1, text);

        text = cityService.getTwoCitiesAndDistance("2");
        assertEquals(Cities.DEFAULT_CITIES_AND_DISTANCE_2, text);

        assertNull(cityService.getTwoCitiesAndDistance(null));
    }
}
