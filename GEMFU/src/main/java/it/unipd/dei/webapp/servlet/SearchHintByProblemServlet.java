package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.SearchHintByProblemDatabase;
import it.unipd.dei.webapp.resource.Hint;
import it.unipd.dei.webapp.resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Searches hints by their associated problem.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class SearchHintByProblemServlet extends AbstractDatabaseServlet {

	/**
	 * Searches hints by their associated problem.
	 * 
	 * @param req
	 *            the HTTP request from the client.
	 * @param res
	 *            the HTTP response from the server.
	 * 
	 * @throws ServletException
	 *             if any error occurs while executing the servlet.
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// request parameter
		int problem;

		// model
		List<Hint> el = null;
		Message m = null;

		try {
		
			// retrieves the request parameter
			problem = Integer.parseInt(req.getParameter("problem"));

			// creates a new object for accessing the database and searching the hints
			el = new SearchHintByProblemDatabase(getDataSource().getConnection(), problem).searchHintByProblem();

			m = new Message("Hints successfully searched.");
			
		} catch (NumberFormatException ex) {
			m = new Message("Cannot search for hints. Invalid input parameters: problem must be integer.", "E100", ex.getMessage());
		} catch (SQLException ex) {
			m = new Message("Cannot search for hints: unexpected error while accessing the database.", "E200", ex.getMessage());
		}

		// stores the hint list and the message as a request attribute
		req.setAttribute("hintList", el);
		req.setAttribute("message", m);
		
		// forwards the control to the search-hint-result JSP
		req.getRequestDispatcher("/jsp/search-hint-result.jsp").forward(req, res);

	}

}
