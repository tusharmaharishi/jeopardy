//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

//Import Java Libraries
import java.io.*;

@WebServlet("/inputValidator")
public class InputValidator extends HttpServlet
{

   public void processRequest (HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException
   {
      PrintWriter out = res.getWriter ();

      out.print ("<font color=red> Error! There must be at least 1 team to play, please enter a numeric value greater than 0.");
      out.print ("</font>\n");

      out.close ();
   }

    // Method doPost - just calls processRequest
    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       processRequest (request, response);
    }

    // Method doGet - just calls processRequest
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
       processRequest (request, response);
    }

}
