<%@ page import="java.util.Scanner" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.io.File"  %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel='stylesheet' type='text/css' href='http://localhost:8080/Jeopardy/playgame.css'>
	<title>Play Game</title>
</head>
<body>	
	<%
			// begin reading from file and display initial information (creator/gameID) to top of the screen
			Scanner sc = new Scanner(new File("/Users/tusharmaharishi/Documents/workspace2/Jeopardy/src/GameData/" + (String) session.getAttribute("filename") + ".txt"));
			out.println("<div class='gameinfo'>");
			out.println("Creator: " + sc.nextLine());
			out.println("<br>");
			out.println("GameID: " + sc.nextLine());
			out.println("<br>");
			out.println("<br>");
			out.println("</div>");
			
			out.println("<br>");
			out.println("<br>");
			out.println("<br>");
			out.println("<h1>Jeopardy!</h1>");
			
			// create lists to hold game information
			ArrayList<String> questionList = new ArrayList<String>();
			ArrayList<String> answersList = new ArrayList<String>();
			ArrayList<Integer> rowList = new ArrayList<Integer>();
			ArrayList<Integer> colList = new ArrayList<Integer>();
			ArrayList<Integer> scoreList = new ArrayList<Integer>();
			
			// process file for game information
			while(sc.hasNextLine()) {

				// game information is divided by semicolons (;)
				String line = sc.nextLine();
				String[] parts = line.split(";");
				
				questionList.add(parts[0]);
				// answers is more complicated since there is often more than once answer, and they are also separated by semicolons (;)
				String answers = "";
				for(int i = 1; i < parts.length-3; i++) {
			answers += parts[i] + ";";
				}
				answers.substring(0, answers.length()-1);
				answersList.add(answers);
				rowList.add(Integer.parseInt(parts[parts.length-3]));
				colList.add(Integer.parseInt(parts[parts.length-2]));
				scoreList.add(Integer.parseInt(parts[parts.length-1]));
			}
			
			// keep track of information across files - necessary for QuestionInfo.jsp 
			session.setAttribute("questionList", questionList);
			session.setAttribute("answersList", answersList);
			session.setAttribute("scoreList", scoreList);
			
			// initialize board with -1 values and create matching matrices to hold the question/answer values
			int tableMaxRow = Collections.max(rowList);
			int tableMaxCol = Collections.max(colList);
			int[][] board = new int[tableMaxRow][tableMaxCol];
			String[][] questions = new String[tableMaxRow][tableMaxCol];
			String[][] answers = new String[tableMaxRow][tableMaxCol];
			for(int x = 0; x < board.length; x++) 
				for(int y = 0; y < board[0].length; y++) {
					board[x][y] = -1;
					questions[x][y] = "";
					answers[x][y] = "";
				}
			
			// adding the scores,questions,answers to the rows/cols specified 
			for(int i = 0; i < questionList.size(); i++) {
				int row = rowList.get(i)-1;
				int col = colList.get(i)-1;
				board[row][col] = scoreList.get(i);
				questions[row][col] = questionList.get(i);
				answers[row][col] = answersList.get(i);
			}
			
			// start creating HTML table for game board
			out.println("<div class='gameboard-div'>");
			out.println("		<form action='QuestionInfo.jsp' method='post'>");
			out.println("			<table border='1px solid black' cellspacing='2' cellpadding='5' width='75%' align='center'>");
			for(int x = 0; x < board.length; x++) {
				
				// create new row
				out.println("			<tr>");
				for (int y = 0; y < board[0].length; y++) {
					// create data chunk to pass through input name to QuestionInfo.jsp
					String data = questions[x][y] + ";" + answers[x][y] + ";" + board[x][y];
					HashMap<String, Boolean> completed = (HashMap<String, Boolean>) session.getAttribute("completed");
					
					// create new column with buttons for each score, only if that question has not been selected before
					if (board[x][y] != -1 && !completed.containsKey(data)) {
						out.println("		<td align='center'><input type='submit' style='width:100%' name='" + data + "' value='" + board[x][y] + "'/></td>");
					} 
					else
						out.println("		<td align='center'><b>" + "-" + "</b></td>");
				}
				out.println("			</tr>");
			}

			// finish the table
			out.println("			</table>");
			out.println("		</form>");
			/* out.println("</div>"); */

			// retrieve numTeams from StartGame.jsp and insert it into session's attributes to make it accessable across all files
			if (request.getParameter("numTeams") != null) {
				Integer numTeams = Integer.parseInt(request.getParameter("numTeams"));
				if (numTeams != null) {
					session.setAttribute("numTeams", numTeams);
					// initialize scores at 0
					for (int team = 0; team < numTeams; team++) {
						String teamName = "T" + (team + 1);
						session.setAttribute(teamName, 0);
					}
				}
			}
		%>

<% // update the current page before TeamScores.jsp is called %>
<%  session.setAttribute("currPage", "PlayGame.jsp"); %>
<%@ include file="TeamScores.jsp" %>

<% // reset session variables so that scores cannot be added from PlayGame screen and showAnswer is not auto-enabled %>
<% session.setAttribute("score", 0); %>
<% session.setAttribute("showAns", false); %>

	<br>
	<form action='http://localhost:8080/Jeopardy/JeopardyBrowse' method='get' align='center'>
		<input type='submit' value='Quit'/>
	</form>
	</div>
</body>
</html>


