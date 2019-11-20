package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.CreateCompanyDatabase;
import it.unipd.dei.webapp.resource.Company;
import it.unipd.dei.webapp.resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new company into the database. 
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class CreateCompanyServlet extends AbstractDatabaseServlet {

	/**
	 * Creates a new company into the database. 
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
		String name = null;
		
		// model
		Company e  = null;
		Message m = null;

		try{
			// retrieves the request parameters
			name = req.getParameter("name");
			
			// creates a new company from the request parameters
			e = new Company(name);

			// creates a new object for accessing the database and stores the company
			new CreateCompanyDatabase(getDataSource().getConnection(), e).createCompany();
			
			m = new Message(String.format("Company %s successfully created.", name));

		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message(String.format("Cannot create the company: company %s already exists.", name),
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the company: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		
		// stores the company and the message as a request attribute
		req.setAttribute("company", e);
		req.setAttribute("message", m);
		
		// forwards the control to the create-company-result JSP
		req.getRequestDispatcher("/jsp/create-company-result.jsp").forward(req, res);
	}

}
