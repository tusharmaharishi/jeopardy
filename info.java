package examples.ajax;

// Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

// Import Java Libraries
import java.io.*;

@WebServlet("/info")
public class info extends HttpServlet
{

   public void doGet  (HttpServletRequest req, HttpServletResponse res)
          throws ServletException, IOException
   {
      res.setContentType ("text/html");
      PrintWriter out = res.getWriter ();

      out.println ("<HTML>");
      out.println ("<HEAD>");
      out.println ("<TITLE>An example servlet program</TITLE>");
      out.println ("<script type=\"text/javascript\" src=\"http://localhost:8080/cs4640/examples/ajax/welcome.js\"></script>");
      out.println ("<SCRIPT>");
      out.println (" function setFocus()");
      out.println (" {");
      out.println ("    document.nameForm.txt1.focus();");
      out.println (" }");
      out.println ("</SCRIPT>");
      out.println ("</HEAD>");
      out.println ("<BODY onLoad=\"setFocus()\">");
      out.println ("<p>Simple Ajax example ");
      out.println ("<form name=\"nameForm\">");
      out.println ("First Name: <input type=\"text\" name=\"txt1\" id=\"txt1\" onkeyup=\"showHint(this.value)\" />");
      out.println ("</form>");
      out.println ("<p><span id=\"txtHint\"></span></p>");
      out.println ("</BODY>");
      out.println ("</HTML>");

      out.close ();
    }
}
