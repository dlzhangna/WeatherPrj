<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.*"%>
<%@ page import="cn.org.springmvc.bean.OneDayWeatherInf"%>
<%@ page import="cn.org.springmvc.bean.City"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<OneDayWeatherInf> resultlist = (List<OneDayWeatherInf>)request.getAttribute("oneDayWeatherInfList");
OneDayWeatherInf oneDayInfo = null;
String responseCityName = (String) request.getAttribute("cityName");
String responseResult = (String) request.getAttribute("resposeResult");
%>  
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<title>Query weather page</title>
<link rel="stylesheet" type="text/css" href="../../css/weather.css" />
<style type="text/css">

   .hidden {
	display: none;
    }
    .basic-tr-list tr{
    border:solid 4px;
    }
    .basic-td-list td{
    margin: -1px -2px 3px -1px;
    color: #888;
    }
    /* Basic Grey */
    .basic-grey {
    margin-left:auto;
    margin-right:auto;
    max-width: 800px;
    max-height:300px;
    background: #F7F7F7;
    padding: 25px 15px 25px 10px;
    font: 14px Georgia, "Times New Roman", Times, serif;
    color: #888;
    text-shadow: 1px 1px 1px #FFF;
    border:1px solid #E4E4E4;
    }
    .basic-grey h1 {
    font-size: 25px;
    padding: 0px 0px 10px 40px;
    display: block;
    border-bottom:1px solid #E4E4E4;
    margin: -10px -15px 30px -10px;
    color: #888;
    }
    .basic-grey h1>span {
    display: block;
    font-size: 11px;
    }
    .basic-grey label {
    display: block;
    margin: -10px -15px 10px 150px;
    width: 80%;
    }
   .basic-grey label>span {
    float: left;
    width: 30%;
    text-align: left;
    padding-right: 10px;
    margin-top: 5px;
    color: #888;
    }
    .basic-grey input[type="text"], .basic-grey input[type="email"], .basic-grey textarea, .basic-grey select {
    border: 1px solid #DADADA;
    color: #888;
    height: 30px;
    margin-bottom: 16px;
    margin-right: 6px;
    margin-top: 2px;
    outline: 0 none;
    padding: 3px 3px 3px 5px;
    width: 70%;
    font-size: 12px;
    line-height:15px;
    box-shadow: inset 0px 1px 4px #ECECEC;
    -moz-box-shadow: inset 0px 1px 4px #ECECEC;
    -webkit-box-shadow: inset 0px 1px 4px #ECECEC;
    }
    .basic-grey select {
    background: #FFF no-repeat right;
    appearance:none;
    -webkit-appearance:none;
    -moz-appearance: none;
    text-indent: 0.01px;
    text-overflow: '';
    width: 100%;
    height: 35px;
    line-height: 25px;
    margin: -10px -15px 10px -230px;
    }
    .basic-grey-button {
    background: #E27575;
    text-align: center;
    color: #FFF;
    padding: 10px 25px 10px 25px;
    -webkit-border-radius: .5em; 
	-moz-border-radius: .5em;
    border-radius: .5em;
    box-shadow: 1px 1px 5px #B6B6B6;
    text-shadow: 1px 1px 1px #9E3F3F;
    cursor: pointer;
    margin: -10px -20px 30px -100px;
    }
    .basic-grey .button:hover {
    background: #CF7A7A
    }
    .basic-grey-result-list-table {
    margin-left:auto;
    margin-right:auto;
    background: #F7F7F7;
    color: #888;
    margin: -10px -10px 10px 130px;
    border:solid 5px;
    border-collapse:collapse;
    width: 60%;
    height: 80%;
    }  
</style>

<script type="text/javascript">
  window.onload = function(){
	 <%if(responseResult!=null && responseResult.equals("1")){%>
       document.getElementById("queryResultDiv").style.display = "block";
       document.getElementById("citySelect").value='<%=responseCityName%>';
     <%}%>	 
  }

 function selectValue(){
	 selectedValue = document.getElementById("citySelect").value;
	 document.getElementById("condition").value= selectedValue;
  }
 function checkValue(){
	 var selectedValue = document.getElementById("citySelect").value;
	 if(selectedValue!=null && selectedValue=="000"){
		 alert("City value can't be set blank,please select one city value!");
		 return false;
	 }else{
		 document.forms["cityform"].submit();
	 }
 }
</script>
</head>
<body>
<div id="queryDiv" class="basic-grey">
 <form id="cityform" method="post" action="queryWeather.action">
  <table>
   <h1>Welcome to weather site
    <span>Hope you happy every day</span>
   </h1>
   <tr>
    <label><span>Please select city:</span></label>
    <td>
     <label>
      <form:select id="citySelect" class="basic-grey select" path="cityList" items="${cityList}" itemLabel="cityName" itemValue="cityCode" onclick="selectValue()"></form:select>
     </label>
    </td>
    <td>
     <span>
     <input class="basic-grey-button" type="button" value="Submit" onclick="checkValue()"/>
     </span>
    </td>
    <td>
      <input type="hidden" id="condition" name = "condition" value = ""/>
    </td>
   </tr>
   </table>
 </form>
</div>
<div id="queryResultDiv" name="queryResultDiv" class="hidden basic-grey">
<form action="weatherList.action" method="post">  
   <table class="basic-grey-result-list-table">
   <tr class="basic-tr-list">
  <td class="basic-td-list">City:</td>
    <%
    for(int i=0;resultlist !=null && i<resultlist.size();i++){
    	oneDayInfo = (OneDayWeatherInf) resultlist.get(i);
    %>
    <td class="basic-td-list"><%=oneDayInfo.getCity()%></td>
     <%} %>
    </tr>
   <tr class="basic-tr-list">
    <td class="basic-td-list">Updated time:</td>
    <%
    for(int i=0;resultlist !=null && i<resultlist.size();i++){
    	oneDayInfo = (OneDayWeatherInf) resultlist.get(i);
    %>
    <td class="basic-td-list"><%=oneDayInfo.getDate() + " " + oneDayInfo.getWeek()%></td>
     <%} %>
    </tr>
    <tr class="basic-tr-list">
    <td class="basic-td-list">Weather:</td>
    <%
    for(int i=0;resultlist !=null && i<resultlist.size();i++){
    	oneDayInfo = (OneDayWeatherInf) resultlist.get(i);
    %>
    <td class="basic-td-list"><%=oneDayInfo.getWeather()%></td>
    <%} %>
    </tr>
    <tr class="basic-tr-list">
    <td class="basic-td-list">Temperature:</td>
    <%
    for(int i=0;resultlist !=null && i<resultlist.size();i++){
    	oneDayInfo = (OneDayWeatherInf) resultlist.get(i);
    %>
    <td class="basic-td-list"><%=oneDayInfo.getTempertureNow()%></td>
     <%} %>
    </tr>
    <tr class="basic-tr-list">
    <td class="basic-td-list">Wind:</td>
    <%
    for(int i=0;resultlist !=null && i<resultlist.size();i++){
    	oneDayInfo = (OneDayWeatherInf) resultlist.get(i);
    %>
    <td class="basic-td-list"><%=oneDayInfo.getWind()%></td>
     <%} %>
    </tr>
 </table>
</form>  
</div>
</body>
</html>