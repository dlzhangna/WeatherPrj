<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<head>
<title>Query weather page</title>
<link rel="stylesheet" type="text/css" href="../../css/weather.css" />
<style type="text/css">
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
    .basic-grey span{
    display: block;
    color: #888;
    font-size: 20px;
    }
    .basic-grey label {
    display: block;
    width: 80%;
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
</style>
</head>
<body>
<div id="queryDiv" class="basic-grey">
 <form id="cityform" method="post" action="queryWeather.action">
  <table>
   <h1>Error
   </h1>
   <span>Please see the log file first,then hope you can solve it</span>
   </table>
 </form>
</div>
</body>
</html>