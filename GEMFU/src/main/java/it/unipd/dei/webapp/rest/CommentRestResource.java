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

package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.database.*;
import it.unipd.dei.webapp.resource.*;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


 
public final class CommentRestResource extends RestResource {

	/**
	 * Creates a new REST resource for managing {@code Employee} resources.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	public CommentRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		super(req, res, con);
	}


	/**
	 * Creates a new employee into the database. 
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void createComment() throws IOException {

		Comment e  = null;
		Message m = null;

		try{
			final Comment comment = Comment.fromJSON(req.getInputStream());
			
			// creates a new object for accessing the database and stores the employee
			e = new CreateCommentDatabase(con, comment).createComment();

			if(e != null) {
				res.setStatus(HttpServletResponse.SC_CREATED);
				e.toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot create the comment: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			if (t instanceof SQLException && ((SQLException) t).getSQLState().equals("23505")) {
				m = new Message("Cannot create the comment: it already exists.", "E5A2", t.getMessage());
				res.setStatus(HttpServletResponse.SC_CONFLICT);
				m.toJSON(res.getOutputStream());
			} else {
				m = new Message("Cannot create the comment: unexpected error in RestResource: " + req + "fibw" , "E5A1", t.getMessage());
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		}
	}
	/**
	 * Searches employees by their salary.
	 *
	 * @throws IOException
	 *             if any error occurs in the client/server communication.
	 */
	public void searchCommentByProblem()  throws IOException {

		List<Comment> el  = null;
		Message m = null;

		try{
			// parse the URI path to extract the salary
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("problem/") + 8);

			final int problem = (int)Integer.valueOf(path);


			// creates a new object for accessing the database and search the employees
			el = new SearchCommentByProblemDatabase(con, problem).SearchCommentByProblem();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search comment: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search comment: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	public void searchCommentByThread()  throws IOException {

		List<Comment> el  = null;
		Message m = null;

		try{
			// parse the URI path to extract the salary
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("thread/") + 7);

			final int thread = (int)Integer.valueOf(path);


			// creates a new object for accessing the database and search the employees
			el = new SearchCommentByThreadDatabase(con, thread).SearchCommentByThread();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search employee: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

	public void searchCommentByComment()  throws IOException {

		List<Comment> el  = null;
		Message m = null;

		try{
			// parse the URI path to extract the salary
			String path = req.getRequestURI();
			path = path.substring(path.lastIndexOf("comment/") + 8);

			final int com = (int)Integer.valueOf(path);


			// creates a new object for accessing the database and search the employees
			el = new SearchCommentByCommentDatabase(con, com).SearchCommentByComment();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot search employee: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}

}
