package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.SearchCompanyByNameDatabase;
import it.unipd.dei.webapp.resource.Company;
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
 * Searches employees by their salary.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class SearchCompanyByNameServlet extends AbstractDatabaseServlet {

	/**
	 * Searches companies by their name.
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
		String name = null;

		// model
		List<Company> el = null;
		Message m = null;

		try {
		
			// retrieves the request parameter
			name = req.getParameter("name");

			// creates a new object for accessing the database and searching the companies
			el = new SearchCompanyByNameDatabase(getDataSource().getConnection(), name)
					.searchCompanyByName();

			m = new Message("Companies successfully searched.");
			
		} catch (SQLException ex) {
				m = new Message("Cannot search for companies: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		// stores the company list and the message as a request attribute
		req.setAttribute("companyList", el);
		req.setAttribute("message", m);
		
		// forwards the control to the search-company-result JSP
		req.getRequestDispatcher("/jsp/search-company-result.jsp").forward(req, res);

	}

}
