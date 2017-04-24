<%@ page import="java.util.HashMap" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

      <script type='text/javascript' src='http://localhost:8080/Jeopardy/InputValidation.js'></script>
      <script>
      	function setFocus() {
      		document.getElementById("numTeams").focus();
      	}
      </script> 

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Start Game</title>
</head>
<body onLoad='setFocus()'>

	<h1 align='center'>Start Game</h1>

	<!-- Create form for number of teams -->
	<form action='http://localhost:8080/Jeopardy/PlayGame.jsp' method='post' align='center' > 
		Number of Teams: <input type='text' name='numTeams' id='numTeams' onkeyup='inputValidation(this.value)'>
		<br>
		<p><span id='errorMessage'></span></p>
		<input type='submit' name='submit' id='submit' value='Start'>
	</form>
	
	<!-- detail the rules
	<h3> Rules </h3>
	1. This is the first rule <br>
	2. This is the second rule <br>
	3. This is the third rule <br>	
	--> 

<%
	
	// initialize HashMap of completed questions (also serves to reset HashMap on new games)
	HashMap<String, Boolean> completed = new HashMap<String, Boolean>();
	session.setAttribute("completed", completed);

%>

</body>
</html>