package cs4640;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL; // URL
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
		out.println("	</head>");
		out.println("	<body>");
		out.println("		<h1 align='center'>Position Selector</h1>");
		out.println("		<form action='GameCreator' method='post'>"); // form contains the table
		out.println("			<table border='1' cellspacing='2' cellpadding='5' width='75%' align='center'>");

		// initialize variables to help with program
		int questionCount = 0; // keeps track of which question segment we're on - used for naming input text fields
		String currLine = sc.nextLine(); // to remove the initial blank line in data.txt and keep track of current line

		// begin iterating through lines of the data.txt file
		while (sc.hasNext()) {

			// sanity check - we've reached a new question/answer segment as segments are divided by empty lines as ""
			if (currLine.equals("")) {

				// new row per question segment
				out.println("<tr>");
				currLine = sc.nextLine(); // drop the "____Question" title in data.txt

				// new column for question/answer
				out.println("	<td>");
				out.println("		<b>Question: </b>" + sc.nextLine() + "<br>");
				currLine = sc.nextLine(); // drop the "____Answer" title in data.txt

				// assemble the answers to the question by building them into a single line
				String answersLine = "";
				while (!currLine.equals("") && sc.hasNextLine()) {
					currLine = sc.nextLine();
					answersLine += currLine + ", ";
					if (sc.hasNextLine()) // in case we have reached the end of the text file
						currLine = sc.nextLine();
				}
				answersLine = answersLine.substring(0, answersLine.length() - 2);
				out.println("		<b>Answer(s): </b>" + answersLine);

				// new column for row position
				out.println("	</td>");
				String rowName = "row" + questionCount;
				out.println("	<td>Row: <input type='text' name='" + rowName + "'></td>");

				// new column for column position
				String colName = "col" + questionCount;
				out.println("	<td>Column: <input type='text' name='" + colName + "'></td>");

				// new column for score
				String scoreName = "score" + questionCount;
				out.println("	<td>Score: <input type='text' name='" + scoreName + "'></td>");
				out.println("</tr>");
			}
			// increment the counter for each question segment
			questionCount++;
		}

		// finish the HTML page
		out.println("			</table>");
		out.println("			<input type='submit' value='createGame'>");
		out.println("		</form>");
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html>");
		out.println("	<head>");
		out.println("		<title>Position Selector</title>");
		out.println("	</head>");
		out.println("	<body>");
		out.println("	Hello world 1<br>");
		out.println("Parameter: " + request.getParameter("score1") + "<br>");
		out.println("	Hello world 2<br>");
		out.println("	</body>");
		out.println("</html>");
	}

}
