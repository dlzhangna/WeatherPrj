package cn.org.springmvc.bean;

/**
 * OneDayWeatherInf: Single weather information
 * @author Na Zhang
 * @since V1.0 2015/08/23
 */
public class OneDayWeatherInf {
	private    
    String city;
	String date;    
    String week;
    String weather;
    String tempertureNow;    
    String wind;
    
    
    public OneDayWeatherInf(){    
        city = "";    
        date = "";    
        week = "";
        weather = "";
        tempertureNow = "";    
        wind = "";    
      }
    
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTempertureNow() {
		return tempertureNow;
	}

	public void setTempertureNow(String tempertureNow) {
		this.tempertureNow = tempertureNow;
	}

	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

   public String toString(){    
       
       return "city: "+city+"\t"+"date: "+date+"\t"+"week: "+week+"\t"+"weather:"+weather+"\t"+"tempertureNow：  "+tempertureNow+"\t"+"wind:"+wind;
   }    
}
