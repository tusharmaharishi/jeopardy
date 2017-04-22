<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.HashMap" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Question Info</title>
</head>
<body>

	<%
	
		// determine which question/answer was asked based on the button clicked and store the information into session variables
		ArrayList<String> questionList = (ArrayList<String>) session.getAttribute("questionList");
		ArrayList<String> answersList = (ArrayList<String>) session.getAttribute("answersList");
		ArrayList<Integer> scoreList = (ArrayList<Integer>) session.getAttribute("scoreList");
		for(int i = 0; i < questionList.size();  i++) {
			String data = questionList.get(i) + ";" + answersList.get(i) + ";" + scoreList.get(i);
			if(request.getParameter(data) != null) {
				session.setAttribute("question", questionList.get(i));
				session.setAttribute("answers", answersList.get(i));
				session.setAttribute("score", scoreList.get(i));
				
				// add current question to a hashmap of completed questions
				HashMap<String, Boolean> completed = (HashMap<String, Boolean>) session.getAttribute("completed");
				completed.put(data, true);
				session.setAttribute("completed", completed);
			}
		}
		
		// print score and question to screen
		out.println("<h1 align='right'>" + session.getAttribute("score") + "</h1>");
		out.println("<h2 align='center'>" + session.getAttribute("question") + "</h2>");

		// retrieve answers information
		String[] answersArr = ((String)session.getAttribute("answers")).split(";");
		
		// div to center all answer choices, regardless of type
		out.println("<div align='center'>");
		
		// if type is multiple choice or true/false
		if(true) {
			String answerChoices = "ABCD"; // iterate through letters for structured display
			
			// iterate through answer choices
			for(int i = 0; i < answersArr.length; i++) {
				String currAns = answersArr[i];
				String letter = (answersArr.length == 4 ? answerChoices.charAt(i) + ". " : ""); // only show letter option for multiple choice
				
				// if your current answer is the correct answer...
				if(currAns.contains("(correct)")) {
					
					// upon clicking "show correct answer", highlight the correct answer in green
					if(request.getParameter("showAns") != null) {
						session.setAttribute("showAns", true); // set session attribute to keep information present on refreshes from TeamScores.jsp
					}
					if((Boolean) session.getAttribute("showAns")) {
						out.println("<font color='#7CFC00'><b>" + letter + currAns.substring(0, currAns.indexOf("(correct)")) + "</b></font><br>");					
					}
					
					// otherwise show the correct answer like any other answer
					else {
						if(answersArr.length != 1)
							out.println(letter + currAns.substring(0, currAns.indexOf("(correct)")) + "<br>");
					}
				}
				else {
					out.println(letter + currAns + "<br>");
				}
			}
		}
		out.println("	<br>");
		
		// forms have display:inline-block to keep them on the same line as each other
		out.println("	<div>");
		out.println("		<form action='QuestionInfo.jsp' method='get' style='display:inline-block;'>");
		out.println("   		<input type='submit' name='showAns' value='Show Correct Answer'/>");
		out.println("		</form>");

		out.println("		<form action='PlayGame.jsp' method='get' style='display:inline-block;'>");
		out.println("			<input type='submit' value='Continue'/>");
		out.println("		</form>");
		out.println("	</div>");
		out.println("</div>");
		
		out.println("<br>");
		out.println("<hr>");
		
	%>

</body>
</html>

<% // update the current page before TeamScores.jsp is called %>
<%  session.setAttribute("currPage", "QuestionInfo.jsp"); %>
<%@ include file="TeamScores.jsp" %>
