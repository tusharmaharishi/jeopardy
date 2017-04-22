<%@ page import="jeopardyBeans.*" %>
<%@ page import="java.util.ArrayList" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

	<%
	
		// Because this page is used as a footer for multiple pages, it refreshes the score upon submission and redirects to the current page
		out.println("	<br>");
		out.println("	<form action='" + (String) session.getAttribute("currPage") + "' method='post'>");
				
		// create table to hold team scores
		out.println("	<table width='25%' align='center' border='0' cellspacing='2' cellpadding='5'>");
		out.println("		<tr>");
		
		// iterate through the teams
		for(int team = 0; team < (Integer) (session.getAttribute("numTeams")); team++) {
			String teamName = "T" + (team+1); // build team name
			
			// check which button (+ points or - points) was clicked and act accordingly
			if(request.getParameter("+" + teamName) != null) {
				int scoreChange = (Integer) session.getAttribute("score");
				int scoreCurr = (Integer) session.getAttribute(teamName);
				session.setAttribute(teamName, scoreCurr + scoreChange);
			}
			if(request.getParameter("-" + teamName) != null) {
				int scoreChange = (Integer) session.getAttribute("score");
				int scoreCurr = (Integer) session.getAttribute(teamName);
				session.setAttribute(teamName, scoreCurr - scoreChange);
			}
			
			// display the score values by accessing the score associated with each team name in session
			out.println("		<td align='center'>");
			out.println("			" + teamName);
			out.println("			<br><hr>");
			out.println("			" + session.getAttribute(teamName) + "<br>");
			
			// create buttons for score change
			out.println("			<input type='submit' name='+" + teamName + "' value='+'/>");
			out.println("			<input type='submit' name='-" + teamName + "' value='-'/>");
			out.println("		</td>");
		}
		
		// end the table and form
		out.println("		</tr>");
		out.println("	</table>");
		out.println("	</form>");
	
	%>
			
</body>
</html>