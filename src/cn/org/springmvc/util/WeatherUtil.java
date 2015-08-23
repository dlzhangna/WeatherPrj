package cn.org.springmvc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import cn.org.springmvc.bean.City;

public class WeatherUtil {
	
	 private static Logger logger = Logger.getLogger(Logger.class);
	 String cityCode = "";
	 String cityName = "";
	 String pageFlag = "0";
	 
	 /**
	  * Method:getProperties:Get all cityCode and cityName from properties file
	  * @author Na Zhang
	  * @since V1.0 2015/08/22
	*/
	 public String getProperties(ApplicationContext act){
		  try{
			//Get properties file from xml file
	    	Properties p=(Properties)act.getBean("configproperties");
	    	//Get property from properties file
	    	cityCode = p.getProperty("cityCode");
	    	cityName = p.getProperty("cityName");
		  }catch(NoSuchBeanDefinitionException e){
			  logger.info("NoSuchBeanDefinitionException:"+e.getMessage());
          	  pageFlag = "1";
		  }
		    //assemble cityCode, cityName and pageFlag
	    	return cityCode+"&"+cityName+"%"+pageFlag;
	    }
	  
	 /**
	  * Method:setCityFromPropertiesFile:Get a list which has many city bean which is set cityCode and cityName value
	  * @author Na Zhang
	  * @since V1.0 2015/08/22
	*/
	  public List<City> setCityFromPropertiesFile(ApplicationContext act,String cityCode,String cityName){
	    	 List<City> cityList = new ArrayList<City>();
	    	 String pageFlag = "0";
	    	 City city = null;
	    	 String city_code = "";
	    	 String city_name = "";
	    	 //parse all cityCode and cityName to signal cityCode and cityName
	    	 StringTokenizer cityCodeToken = new StringTokenizer(cityCode.substring(1, cityCode.length()-1),",");
	    	 StringTokenizer cityNameToken = new StringTokenizer(cityName.substring(1, cityName.length()-1),",");
	    	 try{
	    	 //Get city bean from xml file and add a default blank value
	    	 city = (City) act.getBean("city");
	    	 city.setCityCode("000");
      	     city.setCityName("");
      	     cityList.add(city);
      	     //parse StringToken circularly for setting property value to City bean
	    	 while(cityCodeToken.hasMoreElements() && cityNameToken.hasMoreElements()){
	    		   city = (City) act.getBean("city");
	    		   city_code = cityCodeToken.nextToken();
	    		   city_name = cityNameToken.nextToken();
	        	   city.setCityCode(city_code);
	        	   city.setCityName(city_name);
	        	   cityList.add(city); 
	    	   }
	    	 }catch(NoSuchBeanDefinitionException e){
	    		 logger.info("NoSuchBeanDefinitionException:"+e.getMessage());
             	 pageFlag = "1";
	    	 }
	    	//if pageFlag is 1, means had error, set pageFlag is 1
            if(pageFlag.equals("1")){
         	   cityList = null;
            }
	      return cityList;
	    }
  }
