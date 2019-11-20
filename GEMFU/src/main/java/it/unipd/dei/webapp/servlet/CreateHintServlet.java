package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.CreateHintDatabase;
import it.unipd.dei.webapp.resource.Hint;
import it.unipd.dei.webapp.resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new hint into the database. 
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class CreateHintServlet extends AbstractDatabaseServlet {

	/**
	 * Creates a new hint into the database. 
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

		// request parameters
		int id = -1;
		int problem = -1;
		String description = null;
		
		// model
		Hint e  = null;
		Message m = null;

		try{
			// retrieves the request parameters
			id = Integer.parseInt(req.getParameter("id"));
			problem = Integer.parseInt(req.getParameter("problem"));
			description = req.getParameter("description");

			// creates a new hint from the request parameters
			e = new Hint(id, problem, description);

			// creates a new object for accessing the database and stores the hint
			new CreateHintDatabase(getDataSource().getConnection(), e).createHint();
			
			m = new Message(String.format("Hint %s successfully created.", id));

		} catch (NumberFormatException ex) {
			m = new Message("Cannot create the hint. Invalid input parameters: id, problem must be integer.", 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message(String.format("Cannot create the hint: hint %s already exists.", id),
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the hint: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		
		// stores the employee and the message as a request attribute
		req.setAttribute("hint", e);
		req.setAttribute("message", m);
		
		// forwards the control to the create-hint-result JSP
		req.getRequestDispatcher("/jsp/create-hint-result.jsp").forward(req, res);
	}

}
