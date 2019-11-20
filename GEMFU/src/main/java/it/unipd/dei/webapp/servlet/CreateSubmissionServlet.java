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

import it.unipd.dei.webapp.database.CreateSubmissionDatabase;
import it.unipd.dei.webapp.resource.Submission;
import it.unipd.dei.webapp.resource.Message;
import java.util.*;
import java.text.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates a new employee into the database. 
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateSubmissionServlet extends AbstractDatabaseServlet {


	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		// request parameters
		String username = null;
		String title = null;
		String text = null;
		int problem = -1;
		String date = null;
		int valid = -1;
		int id = -1;
		// model
		Submission u  = null;
		Message m = null;

		try{
			// retrieves the request parameters
			username = req.getParameter("username");
			problem = Integer.parseInt(req.getParameter("problem"));
			title = req.getParameter("title");
			text = req.getParameter("text");
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			valid = Integer.parseInt(req.getParameter("valid"));
			
			// creates a new employee from the request parameters
			u = new Submission(username, problem, title, text, date, valid, id);

			// creates a new object for accessing the database and stores the employee
			new CreateSubmissionDatabase(getDataSource().getConnection(), u).createSubmission();
			
			m = new Message(String.format("submission %s successfully created.", title));

		} catch (NumberFormatException ex) {
			m = new Message("Cannot create the submission. Invalid input parameters: badge, age, and salary must be integer.", 
					"E100", ex.getMessage());
		} catch (SQLException ex) {
			if (ex.getSQLState().equals("23505")) {
				m = new Message(String.format("Cannot create the submission: submission %s already exists.", title),
						"E300", ex.getMessage());
			} else {
				m = new Message("Cannot create the submission: unexpected error while accessing the database.", 
						"E200", ex.getMessage());
			}
		}
		
		// stores the employee and the message as a request attribute
		req.setAttribute("submission", u);
		req.setAttribute("message", m);
		
		// forwards the control to the create-employee-result JSP
		req.getRequestDispatcher("/jsp/create-submission-result.jsp").forward(req, res);
	}

}
