package cs4640;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL; // URL
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner; // Scanner

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GameCreator
 */
@WebServlet("/GameCreator")
public class GameCreator extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// create flag variables for input validation
	private static boolean invalidInput = false;
	private static boolean flag_emptyFields = false;
	private static boolean flag_nonIntegers = false;
	private static boolean flag_duplicatePos = false;
	private static boolean flag_noQuestions = false;
	
	// create variable to count number of questions in the data.txt file
	private static int questionCount = 0;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameCreator() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// creates a scanner from the URL containing our data.txt file
		URL url = new URL("http://plato.cs.virginia.edu/~tm5gf/CS4640/jeopardy/project3/data.txt");
		Scanner sc = new Scanner(url.openStream());

		// whatever is received will be produced into an html document
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// begin HTML page
		out.println("<html>");
		out.println("	<head>");
		out.println("		<title>Position Selector</title>");
		out.println("       <link rel='stylesheet' type='text/css' href='http://localhost:8080/Jeopardy/game.css'>"); // include external CSS
		out.println("	</head>");
		out.println("   <body class='body1'>");
		out.println("       <div class='form-container'>");
		out.println("		<h1 class='position'>Position Selector</h1>");
		out.println("		<h3 class='names'>Tushar Maharishi (tm5gf) & Hannah Pham (htp3xj)</h3>");
		
		// if input was invalid, relay useful message to the user
		invalidInput = flag_emptyFields || flag_nonIntegers || flag_duplicatePos || flag_noQuestions;
		if(invalidInput) {
			out.println("	<center><font color='red'><b>Invalid Input! Please correct the following and try again: </b>");
			if(flag_emptyFields) out.println("	<br>If a question IS intended for use, ALL of that question's text fields must be filled out.<br>If a question is NOT intended for use, NONE of its text fields should be filled out.");
			if(flag_nonIntegers) out.println("	<br>Make sure entered values for Row, Column, and Score are integers.");
			if(flag_duplicatePos) out.println("	<br>No duplicate row/col entries - ie: no two questions can have row=4 and col=4.");
			if(flag_noQuestions) out.println("	<br>At least one question must be submit to create the Jeopardy game.");
			out.println(" 	<br></font></center>");
			out.println("	<br>");
			flag_emptyFields = false; // reset flags after displaying error messages
			flag_nonIntegers = false;
			flag_duplicatePos = false;
			flag_noQuestions = false;
		}
		
		// begin the form and table
		out.println("		<form class='formbg' action='GameCreator' method='post'>"); 
		out.println("			<table class='table1' border='1' cellspacing='2'>");
		
		// initialize variables to help with program
		questionCount = 0; // initialize count at 0
		String currLine = sc.nextLine(); // to remove the initial blank line in data.txt and keep track of current line

		// begin iterating through lines of the data.txt file
		while (sc.hasNext()) {

			// sanity check - we've reached a new question/answer segment as segments are divided by empty lines as ""
			if (currLine.equals("")) {

				// new row per question segment
				out.println("<tr>");
				currLine = sc.nextLine(); // drop the "____Question" title in data.txt

				// new column for question/answer, hidden input for question 
				out.println("	<td style width=475px>");
				currLine = sc.nextLine();
				out.println("		<b>Question: </b>" + currLine + "<br>");
				String questionName = "question" + questionCount;
				out.println("		<input type='hidden' name='" + questionName + "' value='" + currLine + "'>");
				
				// assemble the answers to the question by building them into a single line, hidden input for answers
				currLine = sc.nextLine(); // drop the "____Answer" title in data.txt
				String answersLine = "";
				String answersLineSemicolon = ""; // for storing in the text file
				while (!currLine.equals("") && sc.hasNextLine()) {
					currLine = sc.nextLine();
					answersLine += currLine + ", ";
					answersLineSemicolon += currLine + ";";
					if (sc.hasNextLine()) // in case we have reached the end of the text file
						currLine = sc.nextLine(); // drop the "____Answer" title in data.txt
				}
				answersLine = answersLine.substring(0, answersLine.length() - 2); // -2 to get rid of the final comma
				out.println("		<b>Answer(s): </b>" + answersLine);
				String answersName = "answers" + questionCount;
				out.println("		<input type='hidden' name='" + answersName + "' value='" + answersLineSemicolon.substring(0, answersLineSemicolon.length()-1) + "'>");

				// new column for row position
				out.println("	</td>");
				String rowName = "row" + questionCount;
				out.println("	<td>Row: <input class='textfield' type='text' name='" + rowName + "' id='" + rowName + "'></td>");
				
				// new column for column position
				String colName = "col" + questionCount;
				out.println("	<td>Column: <input class='textfield' type='text' name='" + colName + "' id='" + colName + "'></td>");

				// new column for score
				String scoreName = "score" + questionCount;
				out.println("	<td>Score: <input class='textfield' type='text' name='" + scoreName + "' id='" + scoreName + "'></td>");
				out.println("</tr>");
			}
			// increment the counter for each question segment
			questionCount++;
		}
		out.println("			</table>");
		
		// begin javascript - before unload, store all the form data into sessionStorage to save it in case of invalid input and page reloading
		out.println("		<script type='text/javascript'>");
		out.println("			window.onbeforeunload = function() {");
		for(int i = 0; i < questionCount; i++) {
			out.println("			sessionStorage.row" + i + " = document.getElementById('row" + i + "').value;");			
			out.println("			sessionStorage.col" + i + " = document.getElementById('col" + i + "').value;");			
			out.println("			sessionStorage.score" + i + " = document.getElementById('score" + i + "').value;");			
		}
		out.println("			}");
		
		// still javascript - before load, retrieve non-null form data from sessionStorage
		out.println("			window.onload = function() {");
		for(int i = 0; i < questionCount; i++) {
			out.println("			var row = sessionStorage.row" + i + ";");
			out.println("			var col = sessionStorage.col" + i + ";");
			out.println("			var score = sessionStorage.score" + i + ";");
			out.println("				if (row != null) document.getElementById('row" + i + "').value = row;");			
			out.println("				if (col != null) document.getElementById('col" + i + "').value = col;");			
			out.println("				if (score != null) document.getElementById('score" + i + "').value = score;");			
		}
		out.println("			}");
		out.println("		</script>");
		
		// finish the HTML page
		out.println("			<input type='hidden' name='numQuestions' value='" + questionCount + "'>"); // to pass questionCount data to POST
		out.println("			<br>");
		out.println("			<input class='btn1' type=\"button\" onclick=\"location.href='http://plato.cs.virginia.edu/~tm5gf/CS4640/jeopardy/project3/jeopardy.html';\" value=\"Make another Q/A\" style='font-size: 16px;'/>");
		out.println("			<input class='btn2' type='submit' value='Create Game' style='font-size: 16px;'>");
		out.println("		</form>");
		out.println("       </div>");
		out.println("	</body>");
		out.println("</html>");

		// close the scanner
		sc.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// whatever is received will be produced into an html document
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// begin HTML page
		out.println("<html>");
		out.println("	<head>");
		
		// initialize printers
		String filename = "/Users/tusharmaharishi/Documents/workspace2/Jeopardy/src/cs4640/gameCreationData.txt"; // hardcoded path
		File file = new File(filename);
		FileWriter fw = new FileWriter(file);
		BufferedWriter outfile = new BufferedWriter(fw);
		//System.out.println(file.getAbsolutePath()); // check where the file ended up... 
		
		// store posted data into lists
		ArrayList<String> questionList = new ArrayList<String>();
		ArrayList<String> answersList = new ArrayList<String>();
		ArrayList<Integer> rowList = new ArrayList<Integer>();
		ArrayList<Integer> colList = new ArrayList<Integer>();
		ArrayList<Integer> scoreList = new ArrayList<Integer>();
		int questionCount = Integer.parseInt(request.getParameter("numQuestions"));
		for(int i = 0; i < questionCount; i++) {

			// retrieving the information from the post request
			String question = request.getParameter("question" + i);
			String answers = request.getParameter("answers" + i);
			String row = request.getParameter("row" + i);
			String col = request.getParameter("col" + i);
			String score = request.getParameter("score" + i);

			// create flags for empty fields
			boolean flag_row = row.equals("");
			boolean flag_col = col.equals("");
			boolean flag_score = score.equals("");
			
			// skip questions that are left entirely blank
			if(flag_row && flag_col && flag_score) {  
				continue;
			}
			
			// input validation - all fields of a question must be filled out if that question is intended for use
			else if (flag_row || flag_col || flag_score) {
				flag_emptyFields = true;
				doGet(request, response);
				return;
			}

			// add information to their respective lists
			else {
				questionList.add(question);
				answersList.add(answers);
				try {
					rowList.add(Integer.parseInt(row));
					colList.add(Integer.parseInt(col));
					scoreList.add(Integer.parseInt(score));
				} catch (NumberFormatException e) { // input validation - text fields for row, col, score must be integers!
					flag_nonIntegers = true;
					doGet(request, response);
					return;
				}
				
				// printing the information to the text file, separated by semicolons
				outfile.write(question + ";" + answers + ";" + row + ";" + col + ";" + score);
				outfile.newLine();
			}
		}

		// properly close BufferedWriter as printing is finished
		outfile.flush();
		outfile.close();
		
		// input validation - at least one entry must be made
		if(questionList.size() == 0) {
			flag_noQuestions = true;
			doGet(request, response);
			return;
		}
		
		// initialize board with -1 values
		int tableMaxRow = Collections.max(rowList);
		int tableMaxCol = Collections.max(colList);
		int[][] board = new int[tableMaxRow][tableMaxCol];
		for(int x = 0; x < board.length; x++) 
			for(int y = 0; y < board[0].length; y++)
				board[x][y] = -1;
		
		// adding the scores to the rows/cols specified from form input
		for(int i = 0; i < questionList.size(); i++) {
			int row = rowList.get(i)-1;
			int col = colList.get(i)-1;
			
			// input validation - no row/col duplicates! ie, row=4 col=4 for multiple questions
			if(board[row][col] == -1)
				board[rowList.get(i)-1][colList.get(i)-1] = scoreList.get(i);
			else {
				flag_duplicatePos = true; 
				doGet(request, response);
				return;
			}
		}
		
		// continue the HTML page
		out.println("		<title>Welcome to Jeopardy!</title>");
		out.println("       <link rel='stylesheet' type='text/css' href='http://localhost:8080/Jeopardy/game.css'>"); // include external CSS
		out.println("	</head>");
		out.println("	<body class='body2'>");
		out.println("       <h1 align='center' class='welcome'> Welcome to Jeopardy! </font></h1>");
		out.println("		<table class='table2' border='1px solid black' cellspacing='2' cellpadding='5' width='75%' align='center'>");

		// start creating HTML table for game board
		for(int x = 0; x < board.length; x++) {
			
			// create new row
			out.println("<tr>");
			for(int y = 0; y < board[0].length; y++) {

				// create new column
				if(board[x][y] != -1)
					out.println("<td class='score' align='center'><b>" + board[x][y] + "</b></font></td>");
				else
					out.println("<td class='score' align='center'><b>" + "-" + "</b></font></td>");
			}
			out.println("</tr>");
		}
		
		// finish the HTML page
		out.println("		</table>");
		out.println("		<br>");
		out.println("		<div align='center'>");
		out.println("			<button class='backbutton' onclick='history.go(-1);' style='font-size: 16px;' align='center'> Back </button>");
		out.println("		</div>");		
		out.println("	</body>");
		out.println("</html>");
	}

}