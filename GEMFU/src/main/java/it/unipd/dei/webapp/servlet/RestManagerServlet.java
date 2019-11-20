

package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.resource.*;
import it.unipd.dei.webapp.rest.ProblemRestResource;
import it.unipd.dei.webapp.rest.CategoryRestResource;
import it.unipd.dei.webapp.rest.CommentRestResource;
import it.unipd.dei.webapp.rest.ThreadRestResource;
import it.unipd.dei.webapp.rest.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;


public final class RestManagerServlet extends AbstractDatabaseServlet {

	/**
	 * The JSON MIME media type
	 */
	private static final String JSON_MEDIA_TYPE = "application/json";

	/**
	 * The JSON UTF-8 MIME media type
	 */
	private static final String JSON_UTF_8_MEDIA_TYPE = "application/json; charset=utf-8";

	/**
	 * The any MIME media type
	 */
	private static final String ALL_MEDIA_TYPE = "*/*";

	@Override
	protected final void service(final HttpServletRequest req, final HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType(JSON_UTF_8_MEDIA_TYPE);
		final OutputStream out = res.getOutputStream();

		try {
			// if the request method and/or the MIME media type are not allowed, return.
			// Appropriate error message sent by {@code checkMethodMediaType}
			if (!checkMethodMediaType(req, res)) {
				return;
			}

			// if the requested resource was an Employee, delegate its processing and return
			if (processProblem(req, res)) {
				return;
			}

			if (processCategory(req, res)) {
				return;
			}
			if (processComment(req, res)) {
				return;
			}
			if (processCompany(req, res)) {
				return;
			}
			if (processHint(req, res)) {
				return;
			}
			if (processSubmission(req, res)) {
				return;
			}
			if (processThread(req,res)){
				return;
			}
			if (processVote(req,res)){
				return;
			}
			if (processUser(req,res)){
				return;
			}
			if (processProbCat(req,res)){
				return;
			}
			if (processProbComp(req,res)){
				return;
			}

			// if none of the above process methods succeeds, it means an unknow resource has been requested
			final Message m = new Message("Unknown resource requested.", "E4A6",
										  String.format("Requested resource is %s.", req.getRequestURI()));
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			m.toJSON(out);
		} finally {
			// ensure to always flush and close the output stream
			out.flush();
			out.close();
		}
	}

	/**
	 * Checks that the request method and MIME media type are allowed.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request method and the MIME type are allowed; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean checkMethodMediaType(final HttpServletRequest req, final HttpServletResponse res)
			throws IOException {

		final String method = req.getMethod();
		final String contentType = req.getHeader("Content-Type");
		final String accept = req.getHeader("Accept");
		final OutputStream out = res.getOutputStream();

		Message m = null;

		switch (method) {
			case "GET":
			case "DELETE":
				if(accept == null) {
					m = new Message("Output media type not specified.", "E4A1", "Accept request header missing.");
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					m.toJSON(out);
					return false;
				}

				if(!accept.contains(JSON_MEDIA_TYPE) && !accept.equals(ALL_MEDIA_TYPE)) {
					m = new Message("Unsupported output media type. Resources are represented only in application/json.",
									"E4A2", String.format("Requested representation is %s.", accept));
					res.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
					m.toJSON(out);
					return false;
				}

				break;
			case "POST":
			case "PUT":
				if(contentType == null) {
					m = new Message("Input media type not specified.", "E4A3", "Content-Type request header missing.");
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					m.toJSON(out);
					return false;
				}

				if(!contentType.contains(JSON_MEDIA_TYPE)) {
					m = new Message("Unsupported input media type. Resources are represented only in application/json.",
									"E4A4", String.format("Submitted representation is %s.", contentType));
					res.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
					m.toJSON(out);
					return false;
				}

				break;
			default:
				m = new Message("Unsupported operation.",
								"E4A5", String.format("Requested operation %s.", method));
				res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
				m.toJSON(out);
				return false;
		}

		return true;
	}

	private boolean processThread(HttpServletRequest req, HttpServletResponse res) throws IOException {
		final String method = req.getMethod();
		String path = req.getRequestURI();
		Message m = null;

		if(path.lastIndexOf("rest/thread") <= 0) {
			return false;
		}

		try {
			path = path.substring(path.lastIndexOf("thread") + 6);

			if (path.length() == 0 || path.equals("/")) {
				switch (method) {
					case "POST":
						new ThreadRestResource(req, res, getDataSource().getConnection()).createThread();
						break;
					default:
						m = new Message("Unsupported operation for URI /user.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
			else if(path.contains("problem_id")){
				path = path.substring(path.lastIndexOf("problem_id") + 10);
				if (path.length() == 0 || path.equals("/")) {
					m = new Message("Wrong format for URI /thread/problem_id/{problemID}: no {problemID} specified.",
							"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					m.toJSON(res.getOutputStream());
				}else{
					switch (method) {
						case "GET":
							new ThreadRestResource(req, res, getDataSource().getConnection()).loadListThread();
							break;
						default:
							m = new Message("Unsupported operation for URI /thread.",
									"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				}
			}
			else{
				switch (method) {
					case "GET":
						new ThreadRestResource(req, res, getDataSource().getConnection()).loadThread();
						break;
					default:
						m = new Message("Unsupported operation for URI /thread.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
		}
		catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}

	private boolean processUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		final String method = req.getMethod();
		String path = req.getRequestURI();
		Message m = null;

		if(path.lastIndexOf("rest/user") <= 0) {
			return false;
		}

		try{
			path = path.substring(path.lastIndexOf("user") + 4);

			if (path.length() == 0 || path.equals("/")) {
				switch (method) {
					case "GET":
						new UserRestResource(req, res, getDataSource().getConnection()).loadUser();
						break;
					default:
						m = new Message("Unsupported operation for URI /image.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}else{
				switch (method) {
					case "GET":
						new UserRestResource(req, res, getDataSource().getConnection()).loadUser();
						break;
					default:
						m = new Message("Unsupported operation for URI /image.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}

			}

		}
		catch (Throwable t){
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}


	/**
	 * Checks whether the request if for an {@link User} resource and, in case, processes it.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @return {@code true} if the request was for an {@code User}; {@code false} otherwise.
	 *
	 * @throws IOException if any error occurs in the client/server communication.
	 */
	private boolean processProblem(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		final String prova = req.getParameter("problem");
		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an User
		if(path.lastIndexOf("rest/problem") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("problem") + 7);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new ProblemRestResource(req, res, getDataSource().getConnection()).listProblem();
						break;
					case "POST":
						new ProblemRestResource(req, res, getDataSource().getConnection()).createProblem();
						break;
					default:
						m = new Message("Unsupported operation for URI /user.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			} else {
				// the request URI is: /employee/salary/{salary}
				if (path.contains("username")) {
					path = path.substring(path.lastIndexOf("username/") + 9);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /user/date/{date}: no {date} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									//Integer.parseInt(path.substring(1));
									//path.substring(1);
									new ProblemRestResource(req, res, getDataSource().getConnection()).searchProblemByUserName();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /user/date/{date}: {date} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /user/date/{date}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				} 
				else if (path.contains("title")) {
					path = path.substring(path.lastIndexOf("title/") + 6);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /user/date/{date}: no {date} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									//Integer.parseInt(path.substring(1));
									//path.substring(1);
									new ProblemRestResource(req, res, getDataSource().getConnection()).searchProblemByTitle();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /user/date/{date}: {date} is not an integer." + path,
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /user/date/{date}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				} 

				else if (path.contains("varorder"))
				{
					path = path.substring(path.lastIndexOf("varorder") + 8);
					Scanner scan = new Scanner(path);
					scan.useDelimiter("/");
					if(scan.hasNext())
					{
						int offset = scan.nextInt();
						int order = scan.nextInt();
						switch (method) {
							case "GET":
							new ProblemRestResource(req, res, getDataSource().getConnection()).searchProblemsByOffset(offset,order);
							break;
							default:
							m = new Message("Unsupported operation for URI /submission.",
											"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
						}
					}
				}




				else {
					// the request URI is: /employee/{badge}
					try {

						// check that the parameter is actually an integer
						Integer.parseInt(path.substring(1));

						switch (method) {
							case "GET":
								new ProblemRestResource(req, res, getDataSource().getConnection()).readProblem();
								break;
							case "PUT":
								new ProblemRestResource(req, res, getDataSource().getConnection()).updateProblem();
								break;
							case "DELETE":
								new ProblemRestResource(req, res, getDataSource().getConnection()).deleteProblem();
								break;
							default:
								m = new Message("Unsupported operation for URI /user/{username}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					} catch (NumberFormatException e) {
						m = new Message("Wrong format for URI /user/{username}: {username} is not a string.",
										"E4A7", e.getMessage());
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
				}
			}
		} catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}
	private boolean processCategory(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		final String prova = req.getParameter("category");
		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an User
		if(path.lastIndexOf("rest/category") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("category") + 8);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new CategoryRestResource(req, res, getDataSource().getConnection()).listCategory();
						break;
					default:
						m = new Message("Unsupported operation for URI /user.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
		} catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}

	private boolean processVote(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		String path = req.getRequestURI();
		Message m = null;

		if(path.lastIndexOf("rest/vote") <= 0) {
			return false;
		}
		try{
			path = path.substring(path.lastIndexOf("vote") + 4);
			if(path.contains("thread")) {
				path = path.substring(path.lastIndexOf("thread") + 6);
				if (path.length() == 0 || path.equals("/")) {
					switch (method) {
						case "POST":
							new VoteRestResource(req, res, getDataSource().getConnection()).postThreadVote();
							break;
						default:
							m = new Message("Unsupported operation for URI /comment.",
									"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				}else{
					switch (method) {
						case "GET":
							new VoteRestResource(req, res, getDataSource().getConnection()).loadThreadVote();
							break;
						default:
							m = new Message("Unsupported operation for URI /comment.",
									"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				}
			}
			else if(path.contains("problem")){
				path = path.substring(path.lastIndexOf("problem") + 7);
				if (path.length() == 0 || path.equals("/")) {
					switch (method) {
						case "POST":
							new VoteRestResource(req, res, getDataSource().getConnection()).postProblemVote();
							break;
						default:
							m = new Message("Unsupported operation for URI /comment.",
									"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				}else{
					switch (method) {
						case "GET":
							new VoteRestResource(req, res, getDataSource().getConnection()).loadProblemVote();
							break;
						default:
							m = new Message("Unsupported operation for URI /comment.",
									"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				}
			}
			else{
				return false;
			}
		}
		catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}

	private boolean processComment(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		final String prova = req.getParameter("comment");
		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an User
		if(path.lastIndexOf("rest/comment") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("comment") + 7);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "POST":
						new CommentRestResource(req, res, getDataSource().getConnection()).createComment();
						break;
					default:
						m = new Message("Unsupported operation for URI /comment.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			} else {
				// the request URI is: /employee/salary/{salary}
				if (path.contains("problem")) {
					path = path.substring(path.lastIndexOf("problem/") + 8);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /comment/problem/{problem}: no {problem} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									//Integer.parseInt(path.substring(1));
									//path.substring(1);
									new CommentRestResource(req, res, getDataSource().getConnection()).searchCommentByProblem();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /user/date/{date}: {date} is not an integer.",
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /user/date/{date}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				} 
				else if (path.contains("thread")) {
					path = path.substring(path.lastIndexOf("thread/") + 7);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /user/date/{date}: no {date} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									//Integer.parseInt(path.substring(1));
									//path.substring(1);
									new CommentRestResource(req, res, getDataSource().getConnection()).searchCommentByThread();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /user/date/{date}: {date} is not an integer." + path,
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /user/date/{date}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				else if (path.contains("comment")) {
					path = path.substring(path.lastIndexOf("comment/") + 8);

					if (path.length() == 0 || path.equals("/")) {
						m = new Message("Wrong format for URI /user/date/{date}: no {date} specified.",
										"E4A7", String.format("Requesed URI: %s.", req.getRequestURI()));
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					} else {
						switch (method) {
							case "GET":

								// check that the parameter is actually an integer
								try {
									//Integer.parseInt(path.substring(1));
									//path.substring(1);
									new CommentRestResource(req, res, getDataSource().getConnection()).searchCommentByComment();
								} catch (NumberFormatException e) {
									m = new Message(
											"Wrong format for URI /user/date/{date}: {date} is not an integer." + path,
											"E4A7", e.getMessage());
									res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
									m.toJSON(res.getOutputStream());
								}

								break;
							default:
								m = new Message("Unsupported operation for URI /user/date/{date}.", "E4A5",
												String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
								break;
						}
					}
				}
				/*else {
					// the request URI is: /employee/{badge}
					try {

						// check that the parameter is actually an integer
						Integer.parseInt(path.substring(1));

						switch (method) {
							case "GET":
								new CommentRestResource(req, res, getDataSource().getConnection()).readProblem();
								break;
							case "PUT":
								new CommentRestResource(req, res, getDataSource().getConnection()).updateProblem();
								break;
							case "DELETE":
								new CommentRestResource(req, res, getDataSource().getConnection()).deleteProblem();
								break;
							default:
								m = new Message("Unsupported operation for URI /user/{username}.",
												"E4A5", String.format("Requested operation %s.", method));
								res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
								m.toJSON(res.getOutputStream());
						}
					} catch (NumberFormatException e) {
						m = new Message("Wrong format for URI /user/{username}: {username} is not a string.",
										"E4A7", e.getMessage());
						res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
						m.toJSON(res.getOutputStream());
					}
				}*/
			}
		} catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}

		private boolean processSubmission(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		final String prova = req.getParameter("submission");
		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an User
		if(path.lastIndexOf("rest/submission") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("submission") + 10);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "POST":
						new SubmissionRestResource(req, res, getDataSource().getConnection()).createSubmission();
						break;
					default:
						m = new Message("Unsupported operation for URI /submission.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
			else if(path.contains("userProblem"))
			{
				path = path.substring(path.lastIndexOf("userProblem") + 11);
				Scanner scan = new Scanner(path);
				scan.useDelimiter("/");
				String username = scan.next();
				int problem = scan.nextInt();
				if(scan.hasNext())
				{
					int offset = scan.nextInt();
					int order = scan.nextInt();
					switch (method) {
						case "GET":
							new SubmissionRestResource(req, res, getDataSource().getConnection()).searchProblemByUserName(username,problem, offset, order);
							break;
						default:
							m = new Message("Unsupported operation for URI /submission.",
											"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				}
				else
				{
					switch (method) {
						case "GET":
							new SubmissionRestResource(req, res, getDataSource().getConnection()).searchProblemByUserNameNoOffset(username,problem);
							break;
						default:
							m = new Message("Unsupported operation for URI /submission.",
											"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
				}
			}
			}
			else
			{
				switch (method) {
					case "GET":
						new SubmissionRestResource(req, res, getDataSource().getConnection()).readSubmission();;
						break;
					default:
						m = new Message("Unsupported operation for URI /submission.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}

			}
		} catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;
	}

	private boolean processHint(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		final String prova = req.getParameter("hint");
		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an User
		if(path.lastIndexOf("rest/hint") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("hint") + 4);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "POST":
						new HintRestResource(req, res, getDataSource().getConnection()).createHint();
						break;
					default:
						m = new Message("Unsupported operation for URI /hint.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
			else if(path.contains("list"))
			{
				path = path.substring(path.lastIndexOf("list") + 4);
				Scanner scan = new Scanner(path);
				scan.useDelimiter("/");
				int problem = scan.nextInt();
				
					switch (method) {
						case "GET":
							new HintRestResource(req, res, getDataSource().getConnection()).searchHintByProblem(problem);
							break;
						default:
							m = new Message("Unsupported operation for URI /hint.",	"E4A5", String.format("Requested operation %s.", method));
							res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
							m.toJSON(res.getOutputStream());
							break;
					}
				
				
			}
			else
			{
				switch (method) {
					case "GET":
						new HintRestResource(req, res, getDataSource().getConnection()).readHint();
						break;
					default:
						m = new Message("Unsupported operation for URI /submission.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}

			}
		} catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;
	}
	private boolean processCompany(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		final String method = req.getMethod();
		final OutputStream out = res.getOutputStream();
		final String prova = req.getParameter("company");
		String path = req.getRequestURI();
		Message m = null;

		// the requested resource was not an User
		if(path.lastIndexOf("rest/company") <= 0) {
			return false;
		}

		try {
			// strip everyhing until after the /employee
			path = path.substring(path.lastIndexOf("company") + 7);

			// the request URI is: /employee
			// if method GET, list employees
			// if method POST, create employee
			if (path.length() == 0 || path.equals("/")) {

				switch (method) {
					case "GET":
						new CompanyRestResource(req, res, getDataSource().getConnection()).searchCompany();
						break;
					case "POST":
						new CompanyRestResource(req, res, getDataSource().getConnection()).createCompany();
						break;
					default:
						m = new Message("Unsupported operation for URI /user.",
										"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
		} catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}

		return true;

	}

	private boolean processProbCat(HttpServletRequest req, HttpServletResponse res) throws IOException {
		final String method = req.getMethod();
		String path = req.getRequestURI();
		Message m = null;

		if(path.lastIndexOf("rest/probcat") <= 0) {
			return false;
		}

		try {
			path = path.substring(path.lastIndexOf("probcat") + 7);
			if (path.length() == 0 || path.equals("/")) {
				switch (method) {
					case "GET":
					case "POST":
						new ProbCatRestResource(req, res, getDataSource().getConnection()).createProbCat();
						break;
					default:
						m = new Message("Unsupported operation for URI /user.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
		}
		catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}

	private boolean processProbComp(HttpServletRequest req, HttpServletResponse res) throws IOException {
		final String method = req.getMethod();
		String path = req.getRequestURI();
		Message m = null;

		if(path.lastIndexOf("rest/probcomp") <= 0) {
			return false;
		}

		try {
			path = path.substring(path.lastIndexOf("probcomp") + 8);
			if (path.length() == 0 || path.equals("/")) {
				switch (method) {
					case "GET":
					case "POST":
						new ProbCompRestResource(req, res, getDataSource().getConnection()).createProbComp();
						break;
					default:
						m = new Message("Unsupported operation for URI /user.",
								"E4A5", String.format("Requested operation %s.", method));
						res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
						m.toJSON(res.getOutputStream());
						break;
				}
			}
		}
		catch(Throwable t) {
			m = new Message("Unexpected error. " + method, "E5A1", t.getMessage());//DA MODIFICARE
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
		return true;
	}
}




