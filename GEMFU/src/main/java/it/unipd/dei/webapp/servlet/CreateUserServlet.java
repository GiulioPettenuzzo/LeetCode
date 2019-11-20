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

import it.unipd.dei.webapp.database.CreateUserDatabase;
import it.unipd.dei.webapp.resource.User;
import it.unipd.dei.webapp.resource.Message;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new user into the database. 
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateUserServlet extends AbstractDatabaseServlet {


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// request parameters
		String username = null;
		String name = null;
		String surname = null;
		int gender = -1;
		String date = null;
		String email = null;
		String password = null;
		String profileImage = null;

		// model
		User u  = null;
		Message m = null;

		try{
			// retrieves the request parameters
			username = req.getParameter("username");
			name = req.getParameter("name");
			surname = req.getParameter("surname");
			email = req.getParameter("email");
			password = req.getParameter("password");
			date = req.getParameter("date");
			gender = Integer.parseInt(req.getParameter("gender"));
			profileImage = req.getParameter("profileImage");

			// creates a new user from the request parameters
			u = new User(username, name, surname, gender, profileImage, date, email, password);

			// creates a new object for accessing the database and stores the user
			new CreateUserDatabase(getDataSource().getConnection(), u).createUser();
			
			m = new Message(String.format("user %s successfully created.", username));

		} catch (NumberFormatException ex) {
			m = new Message("Cannot create the user. Invalid input parameters: badge, age, and salary must be integer.", 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message(String.format("Cannot create the user: user %s already exists.", username),
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the user: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		
		// stores the user and the message as a request attribute
		req.setAttribute("user", u);
		req.setAttribute("message", m);
		
		// forwards the control to the create-user-result JSP
		req.getRequestDispatcher("/jsp/create-user-result.jsp").forward(req, res);
	}

}
