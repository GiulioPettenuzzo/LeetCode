package it.unipd.dei.webapp.servlet;

import it.unipd.dei.webapp.database.CreateThreadDatabase;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.Thread;
import it.unipd.dei.webapp.resource.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;

public class CreateThreadServlet extends AbstractDatabaseServlet{

    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        // request parameters
        String username = null;
        String title = null;
        String text = null;
        int problemId = -1;
        Date date = null;

        // model
        Thread thread  = null;
        Message m = null;

        try{
            // retrieves the request parameters
            //username = req.getParameter("username");
            title = req.getParameter("title");
            text = req.getParameter("text");
            //problemId = Integer.parseInt(req.getParameter("problem-id"));
            username = "giulioPettenuzzo";
            problemId = 0;
            date = new Date();

            // creates a new employee from the request parameters
            thread = new Thread(title, text, username, date.toString(), problemId);

            // creates a new object for accessing the database and stores the employee
            new CreateThreadDatabase(getDataSource().getConnection(), thread).createThread();

            m = new Message(String.format("Thread %s successfully created.", title));

        } catch (NumberFormatException ex) {
            m = new Message("Cannot create the thread. Invalid input parameters: problem-id must be integer.",
                    "E100", ex.getMessage());
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("23505")) {
                m = new Message(String.format("Cannot create the thread: employee %s already exists.", title),
                        "E300", ex.getMessage());
            } else {
                m = new Message("Cannot create the thread: unexpected error while accessing the database.",
                        "E200", ex.getMessage());
            }
        }

        // stores the employee and the message as a request attribute
        req.setAttribute("thread", thread);
        req.setAttribute("message", m);

        // forwards the control to the create-employee-result JSP
        req.getRequestDispatcher("/jsp/create-thread-result.jsp").forward(req, res);
    }
}
