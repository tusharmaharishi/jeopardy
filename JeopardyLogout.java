import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 

@WebServlet("/JeopardyLogout")
public class JeopardyLogout extends HttpServlet 
{
   private static final long serialVersionUID = 1L;
   
   //**** setting for local  ****/    
   private static String LoginServlet = "http://localhost:8080/Jeopardy/JeopardyLogin";
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		    throws ServletException, IOException 
   {
      response.setContentType("text/html");
      
      // invalidate the session if exists
      HttpSession session = request.getSession(true);
      session.setAttribute ("isLogin", "No");
      session.setAttribute ("UserID", "");

      if (session != null)
      {
         session.invalidate();
      }
      response.sendRedirect(LoginServlet);   
   }
 
}