package cn.org.springmvc.controller.test;

import java.util.List;
import org.springframework.context.ApplicationContext;
import cn.org.springmvc.bean.City;
import cn.org.springmvc.controller.QueryCityController;
import cn.org.springmvc.util.WeatherUtil;

/**
 * QueryCityControllerTest:Test class for QueryCityControlle
 * @author Na Zhang
 * @since V1.0 2015/08/22
 */
public class QueryCityControllerTest {
	 public static void  main(String args[]){  
	    	QueryCityController ggw = new QueryCityController();  
	    	ApplicationContext actTest = ggw.getApplicationContext();
	    	WeatherUtil weatherUtil=(WeatherUtil)actTest.getBean("weatherUtil");
	    	String prop = weatherUtil.getProperties(actTest);
	    	List<City> cityListTest = weatherUtil.setCityFromPropertiesFile(actTest,prop.substring(0, prop.indexOf("&")),prop.substring(prop.indexOf("&")+1,prop.length()));
	        for(int j=0;j<cityListTest.size();j++){
          System.out.println("cityCode:"+cityListTest.get(j).getCityCode()+"\t"+"cityName:"+cityListTest.get(j).getCityName());
        }
	     }
}
