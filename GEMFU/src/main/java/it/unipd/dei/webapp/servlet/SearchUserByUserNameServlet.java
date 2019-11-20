/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.SearchUserByUserNameDatabase;
import it.unipd.dei.webapp.resource.User;
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
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class SearchUserByUserNameServlet extends AbstractDatabaseServlet {

	/**
	 * Searches employees by their salary.
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
		String date = null;

		// model
		List<User> ul = null;
		Message m = null;

		String s = null;

		try {
		
			// retrieves the request parameter
			date = req.getParameter("date");
		

			// creates a new object for accessing the database and searching the employees
			ul = new SearchUserByUserNameDatabase(getDataSource().getConnection(), date)
					.SearchUserByUserName();

			m = new Message("User successfully searched.");
			
		} catch (NumberFormatException ex) {
			m = new Message("Cannot search for user. Invalid input parameters: " + s, 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
				m = new Message("Cannot search for user: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
		}

		// stores the employee list and the message as a request attribute
		req.setAttribute("users", ul);
		req.setAttribute("message", m);
		
		// forwards the control to the search-employee-result JSP
		req.getRequestDispatcher("/jsp/search-user-result.jsp").forward(req, res);

	}

}
