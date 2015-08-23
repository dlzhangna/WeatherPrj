package cn.org.springmvc.controller.test;

import java.util.List;
import cn.org.springmvc.bean.OneDayWeatherInf;
import cn.org.springmvc.controller.QueryWeatherController;

/**
 * QueryWeatherControllerTest:Test class for QueryWeatherController
 * @author Na Zhang
 * @since V1.0 2015/08/22
 */
public class QueryWeatherControllerTest {
	public static void  main(String args[]){  
    	QueryWeatherController ggw = new QueryWeatherController();  
        String cityName = "Sydney";   
        List<OneDayWeatherInf> resultList = ggw.getWeather(cityName);    
        for(int j=0;j<resultList.size();j++){
        	OneDayWeatherInf onDayInfo = (OneDayWeatherInf) resultList.get(j);
          System.out.println("City:"+onDayInfo.getCity()+"\t"+"Date:"+onDayInfo.getDate()+" "+onDayInfo.getWeek()+"\t"+"Weather:"+onDayInfo.getWeather()+"\t"+"Temp:"+onDayInfo.getTempertureNow()+"\t"+"Wind:"+onDayInfo.getWind());
      }
    }
}
