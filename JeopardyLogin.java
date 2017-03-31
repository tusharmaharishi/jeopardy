//package cs4640.session;
package cs4640;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

//*********************************************************************

@WebServlet("/JeopardyLogin")
public class JeopardyLogin extends HttpServlet {
	private static final long serialVersionUID = 2L;

	// **** setting for local ****/
	private static String LoginServlet = "http://localhost:8080/Jeopardy/JeopardyLogin";
	private static String BrowseServlet = "http://localhost:8080/Jeopardy/JeopardyBrowse";

	// the data file containing username and password
	public static String userCredentials = "/Users/tusharmaharishi/Documents/workspace2/Jeopardy/src/cs4640/userCredentials.txt";

	// button that will represent whichever was submit - Login or Register
	private static String button;

	// info for returning users
	private static String id_login;
	private static String pwd_login;

	// info for new users
	private static String id_register;
	private static String pwd1_register;
	private static String pwd2_register;

	// Input validation - doPost() tells doGet() when the login is invalid.
	private static boolean flag_invalidInput = false;
	private static boolean flag_duplicateUsername = false;
	private static boolean flag_nonAlphabetChars = false;

	//private int numAttempts = 0;

	/**
	 * ***************************************************** Overrides
	 * HttpServlet's doGet(). prints the login form.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// whatever is received will be produced into an html document
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// begin HTML page
		out.println("<html>");

		// head
		out.println("<head>");
		out.println("  <title>Jeopardy Login Page</title>");
		out.println("</head>");

		// body
		out.println("<body onLoad='setFocus()' >");
		out.println("<b><center><h1>Welcome to Jeopardy!<h1></center></b>");
		out.println("<br/><br/>");

//		String locked = ((String)session.getAttribute("Locked"));
//		if(locked != null && locked.equals("true")) {
//			out.println("<br><font color='red'>The username and password entered did not match or were not filled out. You have been LOCKED. Please try again in 10 seconds.</font><br><br>");
//		}

		// Input validation - username/password mismatch
		if (flag_invalidInput) {
			flag_invalidInput = false;
			out.println("<br><font color='red'>The username and password entered did not match or were not filled out. Please try again.</font><br><br>");
		}
		// Input validation - duplicate username attempted in registration
		if (flag_duplicateUsername) {
			flag_duplicateUsername = false;
			out.println("<br><font color='red'>The username provided is already associated with an account. Please choose a different username or login with the correct password.</font><br><br>");
		}
		// Input validation - non-alphanumeric characters used in registration
		if (flag_nonAlphabetChars) {
			flag_nonAlphabetChars = false;
			out.println("<br><font color='red'>A username must only contain alphanumeric characters (letters and numbers). Please try again.</font><br><br>");
		}

		// Login page for returning users
		out.println("<form method='post' action='" + LoginServlet + "' id='login' name='login'>");
		out.println("  <table Cellspacing='0' Cellpadding='3' Border='0' >");
		out.println("    <tr><td colspan='4'><b>Returning Users:</b></td></tr>");
		out.println("    <tr>");
		out.println("      <td>Username:</td>");
		out.println("      <td><input autofocus type='text' name='id_login' size='15' maxLength='20'><td>");
		out.println("      <td>Password:</td>");
		out.println("      <td><input type='password' name='pwd_login' size='15' maxlength='20'></td>");
		out.println("    </tr>");
		out.println("    <tr>");
		out.println("      <td colspan='4' ><input type='submit' value='Log in' name='button'></input");
		out.println("    </tr>");
		out.println("  </table>");
		out.println("</form>");

		// spacing and line
		out.println("<br />");
		out.println("<hr />");
		out.println("<br />");

		// Register new user
		out.println("<form method='post' action='" + LoginServlet + "' id='register' name='register'>");
		out.println("  <table Cellspacing='0' Cellpadding='3' Border='0' >");
		out.println("    <tr><td colspan='4'><b>Register New User:</b></td></tr>");
		out.println("    <tr>");
		out.println("      <td>Username:</td>");
		out.println("      <td><input type='text' name='id_register' size='15' maxLength='20'></td>");
		out.println("    </tr>");
		out.println("    <tr>");
		out.println("      <td>Password:</td>");
		out.println("      <td><input type='password' name='pwd1_register' size='15' maxlength='20'></td>");
		out.println("      <td>Confirm Password:</td>"); // second password for confirmation
		out.println("      <td><input type='password' name='pwd2_register' size='15' maxlength='20'></td>");
		out.println("    </tr>");
		out.println("    <tr>");
		out.println("      <td colspan='4'><input type='submit' value='Register' name='button'></input");
		out.println("    </tr>");
		out.println("  </table>");
		out.println("</form>");

		out.println("<br />");
		out.println("<hr />");
		out.println("<br />");
		out.println("<br />");

		out.println("</body>");
		out.println("</html>");

		out.close();
		
//		if (locked != null && locked.equals("true")) {
//			try {
//				TimeUnit.SECONDS.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			session.setAttribute("Locked", "false");
//			doGet(request, response);
//		}
	}

	/*******************************************************
	 * Overrides HttpServlet's doPost().
	 * 
	 * // assume an account will locked after 3 failed attempts // write code to
	 * check and handle this business logic // (optional)
	 ********************************************************* 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		// retrieve parameters from HTML post submission
		button = request.getParameter("button");

		id_login = request.getParameter("id_login");
		pwd_login = request.getParameter("pwd_login");

		id_register = request.getParameter("id_register");
		pwd1_register = request.getParameter("pwd1_register");
		pwd2_register = request.getParameter("pwd2_register");

		// If the button that was clicked was "Register", overwrite the "Login" fields and attempt login
		if (button.equals("Register")) {
			if (id_register != null) {

				// input validation - only alphanumeric characters allowed
				String allowedChars = "abcdefghijklmnopqrstuvwxyz1234567890";
				for (char c : id_register.toCharArray()) {
					if (!allowedChars.contains((c + "").toLowerCase())) {
						session.setAttribute("isLogin", "No");
						session.setAttribute("Username", "");
						flag_nonAlphabetChars = true;
						doGet(request, response);
						return;
					}
				}
			}

			// input validation - duplicate username
			if (isDuplicateUsername(id_register)) {
				session.setAttribute("isLogin", "No");
				session.setAttribute("Username", "");
				flag_duplicateUsername = true;
				doGet(request, response);
				return;
			}

			// set login vars to equal register vars, allows us to process data later using only the login vars
			if (pwd1_register.equals(pwd2_register) && id_register.length() > 0 && pwd1_register.length() > 0) {
				registerNewUser(id_register, pwd1_register);
				id_login = id_register;
				pwd_login = pwd1_register;
			}
		}

		// checks input, if valid, successfully logs the user in
		if (isValid(id_login, pwd_login)) {
			session.setAttribute("isLogin", "Yes");
			session.setAttribute("Username", id_login);
			response.sendRedirect(BrowseServlet);
		}

		// otherwise, returns to doGet() and prints an error message to the user
		else {
			session.setAttribute("isLogin", "No");
			session.setAttribute("Username", "");
			
//			// lock the system for X seconds after Y failed login attempts.
//			Integer numAttempts = (Integer) session.getAttribute("numAttempts");
//			if(numAttempts != null) {
//				numAttempts += 1;
//				if(numAttempts >= 3) {
//					session.setAttribute("numAttempts",  0);
//					session.setAttribute("Locked", "true");
//					doGet(request, response);
//					return;
//				}
//			}
//			else
//				numAttempts = 1;
//			session.setAttribute("numAttempts", numAttempts);
			
			// input validation - flag for mismatched username/password attempt
			flag_invalidInput = true;
			doGet(request, response);
			return;
		}

	}

	// duplicate username checking
	private boolean isDuplicateUsername(String userid) {
		boolean isDuplicate = false;
		if (userid == null || userid.length() == 0) // ensure valid input to method
			return false;
		try {

			// scan userCredentials file for all existing users and return true if a match is found
			Scanner scanner = new Scanner(new BufferedReader(new FileReader(userCredentials)));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String existing_user = line.substring(0, line.indexOf(";")); 
				if (userid.equals(existing_user)) {
					isDuplicate = true;
					break;
				}
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("Unable to verify the user information.");
		}
		return isDuplicate;
	}

	// determines if the given username/password match data in userCredentials file
	private boolean isValid(String userid, String password) {
		boolean isValid = false;
		if (userid == null || password == null || userid.length() == 0 || password.length() == 0) // ensure valid input to method
			return false;
		try {

			// scan userCredentials file for username/password match
			Scanner scanner = new Scanner(new BufferedReader(new FileReader(userCredentials)));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String existing_user = line.substring(0, line.indexOf(";"));
				line = line.substring(line.indexOf(";") + 1);
				String existing_hash = line.substring(0, line.indexOf(";"));

				// hash input password and compare it to the hashed password stored in the textfile
				if (userid.equals(existing_user) && hashPassword(password).equals(existing_hash)) {
					isValid = true;
					break;
				}
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("Unable to verify the user information.");
		}
		return isValid;
	}

	// write new username/password combination to userCredentials file
	private void registerNewUser(String userID, String pwd) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(userCredentials, true);
			pw = new PrintWriter(fw);

			// store only the hashed value of the password, not the password itself
			pw.println(userID + ";" + hashPassword(pwd) + ";");
		} catch (Exception e) {
			System.out.println("ERROR while registering new users " + e);
		} finally { // make sure that cleanup always occurs!
			try {
				pw.flush(); // flush output stream to file
				pw.close(); // close print writer
			} catch (Exception ignored) {
			}
			try {
				fw.close();
			} catch (Exception ignored) {
			}
		}
	}

	// secure salt creation - UNUSED
	private static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	// create a hash for a given password
	private static String hashPassword(String passwordToHash) {
		// create a static salt value
		byte[] salt = new byte[16];
		for (int i = 0; i < 16; i++)
			salt[i] = (byte) i;

		// generate a hash for the given password using SHA-256 and the created hash
		String generatedPassword = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}
}