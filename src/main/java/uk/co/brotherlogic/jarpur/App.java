package uk.co.brotherlogic.jarpur;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class App extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
	{

		res.setContentType("text/html");

		PrintWriter out = res.getWriter();

		/* Display some response to the user */

		out.println("<html><head>");
		out.println("<title>TestServlet</title>");
		out.println("\t<style>body { font-family: 'Lucida Grande', " + "'Lucida Sans Unicode';font-size: 13px; }</style>");
		out.println("</head>");
		out.println("<body>");

		out.println("<p>Current Date/Time: " + Calendar.getInstance().getTimeInMillis() + "</p>");
		out.println("</body></html>");

		out.close();

	}
}