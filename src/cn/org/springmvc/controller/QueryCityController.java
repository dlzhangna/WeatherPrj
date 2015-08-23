package cn.org.springmvc.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import cn.org.springmvc.bean.City;
import cn.org.springmvc.constant.WeatherConstant;
import cn.org.springmvc.util.WeatherUtil;

	/**
	 * QueryCityController:Query city information from properties file
	 * @author Na Zhang
	 * @since V1.0 2015/08/22
	 */
    public class QueryCityController implements Controller {
    	
    	private static Logger logger = Logger.getLogger(Logger.class);
    	String cityCode = "";
    	String cityName = "";
    	String pageFlag = "0";
	 
	    @Override
	    public ModelAndView handleRequest(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
	    	
	    	List<City> cityList = null;
	    	ModelAndView modelAndView = new ModelAndView();
	    	ApplicationContext act = getApplicationContext();
	    	try{
	    	   //Get WeatherUtil bean
	    	   WeatherUtil weatherUtil=(WeatherUtil)act.getBean("weatherUtil");
	    	   //Get cityCode,cityName from Properties file, if get error, set pageFlag="0", response with cityCode,cityName together
	    	   String prop = weatherUtil.getProperties(act);
	    	   //Get pageFlag used for dispatching to error page
	    	   pageFlag = prop.substring(prop.indexOf("%")+1,prop.length());
	    	   //Get list with city bean
	    	   cityList = weatherUtil.setCityFromPropertiesFile(act,prop.substring(0, prop.indexOf("&")),prop.substring(prop.indexOf("&")+1,prop.indexOf("%")));
	    	}catch(NoSuchBeanDefinitionException e){
	    		logger.info("NoSuchBeanDefinitionException:"+e.getMessage());
             	pageFlag = "1";
	    	 }
	    	request.setAttribute("cityList", cityList);
	    	//if cityList is null or pageFlag is 1, invoke error page,if not, invoke successful page
	    	if(cityList == null || pageFlag.equals("1")){
	    		modelAndView.setViewName(WeatherConstant.FAIL_PAGE);
	    	}else{
	    		modelAndView.setViewName(WeatherConstant.SUCCESS_PAGE);
	    	}
	        return modelAndView;
	    }
	    
	    //Get applicationContext
	    public ApplicationContext getApplicationContext(){
	    	ApplicationContext act = new ClassPathXmlApplicationContext("spring-mvc-servlet.xml");
	    	return act;
	    }
  }
