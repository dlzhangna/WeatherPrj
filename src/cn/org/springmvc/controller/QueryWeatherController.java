package cn.org.springmvc.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import cn.org.springmvc.bean.City;
import cn.org.springmvc.bean.OneDayWeatherInf;
import cn.org.springmvc.util.WeatherUtil;
import cn.org.springmvc.constant.WeatherConstant;
	/**
	 * QueryWeatherController:Query weather information from public web URL
	 * @author Na Zhang
	 * @since V1.0 2015/08/23
	 */
	public class QueryWeatherController implements Controller {
		
       
		private static Logger logger = Logger.getLogger(Logger.class);
		URLConnection connectionData;
		BufferedReader br;
		StringBuilder sb;
		JSONObject jsonData;
		String pageFlag ="0";
	    @Override
	    public ModelAndView handleRequest(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
	    	
	    	ModelAndView modelAndView = new ModelAndView();
	        List<City> cityList = null;
	        ApplicationContext act = new ClassPathXmlApplicationContext("spring-mvc-servlet.xml");
	        try{
	          //Get WeatherUtil bean
	    	  WeatherUtil weatherUtil=(WeatherUtil)act.getBean("weatherUtil");
	    	  //Get cityCode,cityName from Properties file, if get error, set pageFlag="0", response with cityCode,cityName together
	    	  String prop = weatherUtil.getProperties(act);
	    	  //Get list with city bean
	    	  cityList = weatherUtil.setCityFromPropertiesFile(act,prop.substring(0, prop.indexOf("&")),prop.substring(prop.indexOf("&")+1,prop.indexOf("%")));
	    	  //Set cityList to attribute used for display select for front-end.
	    	  request.setAttribute("cityList", cityList);
	        }catch(NoSuchBeanDefinitionException e){
				  logger.info("NoSuchBeanDefinitionException:"+e.getMessage());
	          	  pageFlag = "1";
			}
	        //Get selected cityCode
	        String cityCode = request.getParameter("condition");
	        //Get cityName based on cityCode
	        String cityName = getCityNameFromConstant(cityCode);
	        //Get weather from public web URL
	        List<OneDayWeatherInf> resultList = getWeather(cityName); 
	        request.setAttribute("oneDayWeatherInfList", resultList);
	        request.setAttribute("cityList",cityList);
	        request.setAttribute("cityName",cityName);
	        request.setAttribute("resposeResult", "1");
	        if(pageFlag.equals("1")){
	        	//Set fail page
	        	modelAndView.setViewName(WeatherConstant.FAIL_PAGE);
	        }else{
	        	//Set fail page
	            modelAndView.setViewName(WeatherConstant.SUCCESS_PAGE);
	        }
	        return modelAndView;
	    }
	    
	    private String getCityNameFromConstant(String cityCode){
	    	String cityName = "";
	    	 if (cityCode.equals(WeatherConstant.CITYCODE_SYDNEY)){
		        	cityName = "Sydney";
		        }else if (cityCode.equals(WeatherConstant.CITYCODE_MELBOURNE)){
		        	cityName = "Melbourne";
		        }else if(cityCode.equals(WeatherConstant.CITYCODE_WOLLONGONG)){
		        	cityName = "Wollongong";
		        }else if (cityCode.equals(WeatherConstant.CITYCODE_BEIJING)){
		        	cityName = "Beijing";
		        }
	    	return cityName;
	     }
	    /** 
        *  
        * @param cityName 注意weather那写入城市的拼音转化一下就行， 
        * 打开之后是XML格式的然后再提取。 
        * @return 
        */  
        public List<OneDayWeatherInf> getWeather(String cityName){  
          List<OneDayWeatherInf> list = new ArrayList<OneDayWeatherInf>();
        //获取google上的天气情况，写入文件  
        try{
        	//Create connection
        	URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+cityName);
        	connectionData = url.openConnection();
            connectionData.setConnectTimeout(1000);
            try {
               //Get weather information from public weather URL
               br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
               sb = new StringBuilder();
               //Write weather into StringBuffer
               String line = null;
               while ((line = br.readLine()) != null)
                  sb.append(line);
                } catch (SocketTimeoutException e) {
                	logger.info("Connection timeout:"+e.getMessage());
                	pageFlag = "1";
                } catch (FileNotFoundException e) {
                	logger.info("loading file error:"+e.getMessage());
                	pageFlag = "1";
                } 
            br.close();  
          }catch(IOException e){
        	  logger.info("exception on Class:QueryWeatherController,Method:getWeather "+e.getMessage());
        	  pageFlag = "1";
              e.printStackTrace();  
          }  
           //parse json object with weather
           String datas = sb.toString();
           try{
           jsonData = JSONObject.fromObject(datas);
           String cityInfo = jsonData.getString("name");
           String weatherAll = jsonData.getString("weather");
           String tempAll = jsonData.getString("main");
           String windAll = jsonData.getString("wind");
           String weather = "";
           String temp = "";
           String wind = "";
           StringTokenizer weatherToken = new StringTokenizer(weatherAll.substring(2, weatherAll.length()-2),",");
           StringTokenizer tempToken = new StringTokenizer(tempAll.substring(1, tempAll.length()-1),",");
           StringTokenizer windToken = new StringTokenizer(windAll.substring(1, windAll.length()-1),",");
           while(weatherToken.hasMoreElements() ){
        	   String weatherEveryItem = weatherToken.nextToken();
        	   String[] weatherItem = weatherEveryItem.split(":");
        	   if(weatherItem[0].substring(1, weatherItem[0].length()-1).equals("main")){
        	     weather = weatherItem[1];
        	   }
           }
           
           while(tempToken.hasMoreElements() ){
        	   String tempEveryItem = tempToken.nextToken();
        	   String[] tempItem = tempEveryItem.split(":");
        	   if(tempItem[0].substring(1, tempItem[0].length()-1).equals("temp")){
        		   temp = tempItem[1];
        	   }
           }
           
           while(windToken.hasMoreElements() ){
        	   String windEveryItem = windToken.nextToken();
        	   String[] windItem = windEveryItem.split(":");
        	   if(windItem[0].substring(1, windItem[0].length()-1).equals("speed")){
        		   wind = windItem[1];
        	   }
           }
           Calendar cal = Calendar.getInstance();
           cal.add(Calendar.DAY_OF_YEAR, 0);
           Date date = cal.getTime();
           SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
           //Set property value to bean
           OneDayWeatherInf oneDayWeaInfo = new OneDayWeatherInf();
           oneDayWeaInfo.setCity(cityInfo);
           oneDayWeaInfo.setDate(sf.format(date));
           oneDayWeaInfo.setWeek(getWeek(cal.get(Calendar.DAY_OF_WEEK)));
           oneDayWeaInfo.setWeather(weather.substring(1,weather.length()-1));
           oneDayWeaInfo.setTempertureNow(temp);
           oneDayWeaInfo.setWind(wind);
           list.add(oneDayWeaInfo);
           }catch(net.sf.json.JSONException e){
        	   logger.info("JSON parse error: "+e.getMessage());
        	   pageFlag = "1";
           }
        return list;
         }
        
        //Get week information
        private String getWeek(int iw){
           String weekStr = "";
           switch (iw) {
           case 1:weekStr = "Sunday";break;
           case 2:weekStr = "Monday";break;
           case 3:weekStr = "Tuesday";break;
           case 4:weekStr = "Wednesday";break;
           case 5:weekStr = "Thursday";break;
           case 6:weekStr = "Friday";break;
           case 7:weekStr = "Saturday";break;
           default:
               break;
             }
            return weekStr;
         }         
    }
