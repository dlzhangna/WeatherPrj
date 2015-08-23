package cn.org.springmvc.bean;

/**
 * City: Single city information
 * @author Na Zhang
 * @since V1.0 2015/08/23
 */
public class City {
	
	String cityCode;
	String cityName;
	
	public City(){
	  }
	
    public City(String cityCode,String cityName){
	  this.cityCode = cityCode;
	  this.cityName = cityName;
   }
   public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
 }
