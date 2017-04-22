
import javax.servlet.*; 
import javax.servlet.http.*;
import javax.servlet.annotation.*;

// to iterate through the boards created
import java.io.*;
import java.util.HashSet; // for list of gameIDs
import java.util.Scanner;
 
//*********************************************************************

@WebServlet("/JeopardyBrowse")
public class JeopardyBrowse extends HttpServlet
{
   private static final long serialVersionUID = 2L;
   
   // how many columns we see in the table
   private static final int NUM_OF_COLS_IN_DISPLAY = 5;
   
   // data structure to hold the gameIDs for all the games
   private static HashSet<String> gameIDs = new HashSet<String>();
   
   // List of Servlets
   private static String LoginServlet = "http://localhost:8080/Jeopardy/JeopardyLogin";
   private static String LogoutServlet = "http://localhost:8080/Jeopardy/JeopardyLogout";
   private static String BrowseServlet = "http://localhost:8080/Jeopardy/JeopardyBrowse";
   private static String CreateServlet = "http://localhost:8080/Jeopardy/GameCreator";
   private static String StartGameJSP = "http://localhost:8080/Jeopardy/StartGame.jsp";
   
   // location of all the GameData files
   private static String GamesDirectoryPath = "/Users/tusharmaharishi/Documents/workspace2/Jeopardy/src/GameData/";
   
   // global variable for the user
   private String user;

   /** *****************************************************
    *  Overrides HttpServlet's doGet().
    *  prints the login form.
   ********************************************************* */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// if user is not logged in, redirect to login servlet
		HttpSession session = request.getSession(true);
		user = (String) session.getAttribute("Username");
		if (user == null || user.length() == 0)
			response.sendRedirect(LoginServlet);
		
		// assume new game until otherwise
		session.setAttribute("NewGame", "true"); 
		session.setAttribute("UpdateGame", "false");
		
		// whatever is received will be produced into an html document
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// HTML methods
		PrintHead(out);
		PrintBody(out);
		PrintBottom(out);

		out.close();
	}

   /*******************************************************
    *  Overrides HttpServlet's doPost().
    *            
      // assume an account will locked after 3 failed attempts
      // write code to check and handle this business logic 
      // (optional) 

   ********************************************************* */
   public void doPost (HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException
	{

	    // get the session and, if user is not logged in, redirect to login servlet
	    HttpSession session = request.getSession(true);
	    user = (String)session.getAttribute("Username");
		if (user == null || user.length() == 0)
			response.sendRedirect(LoginServlet);
		
		// whatever is received will be produced into an html document
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// determine and build the instruction & gameID associated with the submit button that was clicked
		String instruction = "";
		String gameID = ""; 
		if(request.getParameter("n") != null) // (n) for (n)ew game
			instruction = "n";
		else {
			String[] instrTypes = { "u", "p", "d" }; // (u)pdate, (p)lay, (d)elete
			for (String instr : instrTypes)
				for (String id : gameIDs)
					if (request.getParameter(instr + id) != null) {
						instruction = instr;
						gameID = id;
					}
		}
		
		// On New Game
		if(instruction.equals("n")) {
			session.setAttribute("NewGame",  "true");
			session.setAttribute("UpdateGame",  "false");
			session.setAttribute("FirstLoad",  "true"); 
			response.sendRedirect(CreateServlet);
			return;
		}
		
		// On Update
		if(instruction.equals("u")) {
			session.setAttribute("GameID", gameID);
			session.setAttribute("NewGame", "false");
			session.setAttribute("UpdateGame", "true");
			session.setAttribute("FirstLoad",  "true");
			response.sendRedirect(CreateServlet);
			return;
		}
		
		// On Play
		if(instruction.equals("p")) {
			session.setAttribute("filename",  "gameData_" + gameID);
			response.sendRedirect(StartGameJSP);
			// to be completed in the next assignment...
		}
			
		
		// On Delete
		if(instruction.equals("d")) {
			String filename = "/Users/tusharmaharishi/Documents/workspace2/Jeopardy/src/GameData/gameData_" + gameID + ".txt";
			File file = new File(filename);
			file.delete();
			doGet(request, response);
			return;
		}
		
		// A page should never be loaded, but just in case - begin HTML page
		out.println("<html>");
		out.println("</html>");
		
	}
      
	public void PrintHead(PrintWriter out) {
		
		// begin HTML page (extra text is for logout button formatting)
		out.println("<?xml version='1.0' encoding='UTF-8'?>");
		out.println("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' "
				+ " 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> ");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en' lang='en'>");

		// head
		out.println("<head>");
		out.println("       <link rel='stylesheet' type='text/css' href='http://localhost:8080/Jeopardy/browse.css'>");
		out.println("   <meta http-equiv='content-type' content='text/xhtml; charset=utf-8'>");
		out.println("   <title>Browse Games</title>");
		out.println("</head>");
	}  
   
	public void PrintBody(PrintWriter out) throws FileNotFoundException { // exception thrown as directory is iterating only through known and existing files
		
		// body
		out.println("<body>");

		// Logout ability on every page
		out.println("  <table width='25%' align='right' border='0' cellspacing='2' cellpadding='5'");
		out.println("    <tr>");
		out.println("      <td align='right'>Username:  " + user + "</td>");
		out.println("      <td>");
		out.println("        <form action='" + LogoutServlet + "' method='post'>");
		out.println("          <input type='submit' value='Logout'></input>");
		out.println("        </form>");
		out.println("      </td>");
		out.println("    </tr>");
		out.println("  </table>");

		// Welcome the user, form to create new game
		out.println("  <br /><br />"); 
		out.println("  <center><h1>Jeopardy Boards</h1></center>");
		out.println("  <br /><h3>Welcome " + user + ",</h3>");
		out.println("  <p>");
		out.println("  <form action='" + BrowseServlet + "' method='post'>");
		out.println("    Would you like to create a new game? <input type='submit' name='n' id='n' value='New Game'></input>");
		out.println("    <br><br>Below are all the Jeopardy boards that have been created by all users:");
		out.println("  </form>");
		out.println("  </p>");

		// iterate through all the game textfiles in the GameData directory
		File gameFolder = new File(GamesDirectoryPath);
		File[] games = gameFolder.listFiles();
		if (games != null && games.length > 0) { // if at least one game exists...

			// begin form for Update, Play, and Delete buttons, begin table
			out.println("  <form action='JeopardyBrowse' method='post'>");
			out.println("  <table border='1' cellspacing='25' align='center'>");
			int fileCount = 0;
			
			// for each game, print the owner and gameID, and create the 3 buttons (U, P, D)
			for (File game : games) {
				Scanner sc = new Scanner(game);
				if (sc.hasNextLine()) { // sanity check - not empty file
					if(fileCount % NUM_OF_COLS_IN_DISPLAY == 0) // create new row after numCols is exceeded
						out.println("  <tr>");
					out.println("  <td>");
					String owner = sc.nextLine();
					String gameID = sc.nextLine();
					gameIDs.add(gameID); // add gameID to list of gameIDs, used in doPost() to determine which gameID to send to GameCreator
					out.println("    Owner: " + owner + "<br>");
					out.println("    GameID: " + gameID + "<br>");
					
					String disabled = (owner.equals(user) ? "" : "disabled"); // disable update/delete if the current user is not the creator of those games
					out.println("    <input type='submit' name='u" + gameID + "' id='u" + gameID + "' value='Update' " + disabled + ">");
					out.println("    <input type='submit' name='p" + gameID + "' id='p" + gameID + "' value='Play'>");
					out.println("    <input type='submit' name='d" + gameID + "' id='d" + gameID + "' value='Delete' onclick='return confirm(\"Are you sure?\");' " + disabled + ">");
					out.println("  </td>");
					fileCount++;
					if(fileCount % NUM_OF_COLS_IN_DISPLAY == 0) // end row after numCols is exceeded
						out.println("  </tr>");
				}
				sc.close();
			}
			out.println("  </table>");
			out.println("  </form>");
		}
		
		// no games exist
		else {
			out.println("  <div align='center'>");
			out.println("    <br><p>There are currently no games! Click the \"New Game\" button above to make the first one!</p>");
			out.println("  </div>");
		}
	}
   
   public void PrintBottom(PrintWriter out)
   {
	   
	   // spacing and line created for aesthetic purposes
      out.println("<br />");
      out.println("<hr />");
      out.println("<br />");
      out.println("<br />");
	      
      out.println("</body>");
      out.println("</html>");
   }
   
} 