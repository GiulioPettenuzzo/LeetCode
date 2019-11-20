package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.database.CreateThreadDatabase;
import it.unipd.dei.webapp.database.ListThreadDatabase;
import it.unipd.dei.webapp.database.ReadThreadDatabase;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.ResourceList;
import it.unipd.dei.webapp.resource.Thread;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ThreadRestResource extends RestResource {
    /**
     * Creates a new REST resource.
     *
     * @param req the HTTP request.
     * @param res the HTTP response.
     * @param con the connection to the database.
     */
    public ThreadRestResource(HttpServletRequest req, HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    public void createThread() throws IOException {
        Thread thread = null;
        Message m = null;
        try {
            Thread thread1 = Thread.fromJSON(req.getInputStream());

            thread = new CreateThreadDatabase(con,thread1).createRestThread();

            if(thread!=null){
                res.setStatus(HttpServletResponse.SC_CREATED);
                thread.toJSON(res.getOutputStream());
            }else{
                m = new Message("Cannot create the discussion: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                m.toJSON(res.getOutputStream());
            }
        } catch (Throwable t) {
            if (t instanceof SQLException) {
                m = new Message("Cannot create the discussion: it already exists.", "E5A2", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            }
            else if(t instanceof IOException){
                m = new Message("Cannot create the discussion: unexpected error in RestResource: " + req + "fibw" , "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                m.toJSON(res.getOutputStream());
            }
        }
    }

    public void loadThread() throws  IOException{
        Thread thread = null;
        Message m = null;
        try{
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("thread") + 6);
            final int id = Integer.parseInt(path.substring(1));

            //TODO readThreadFromDatabase
            thread = new ReadThreadDatabase(con,id).ReadThread();
            if(thread != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                thread.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Discussion %d not found.", id), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        }
        catch (Throwable t){
            m = new Message("Cannot read discussion: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    public void loadListThread() throws IOException{
        List<Thread> threads = null;
        Message m = null;
        try{
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("problem_id") + 10);
            final int id = Integer.parseInt(path.substring(1));

            //TODO readThreadFromDatabase
            threads = new ListThreadDatabase(con,id).readThreadList();
            if(threads != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList(threads).toJSON(res.getOutputStream());
                }
            else{
                m = new Message(String.format("Discussion %d not found.", id), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }
        }
        catch (Throwable t){
            m = new Message("Cannot read discussion: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    /*

    public void listCategory() throws IOException {

		List<Category> el  = null;
		Message m = null;

		try{
			// creates a new object for accessing the database and lists all the employees
			el = new ListCategoryDatabase(con).listCategory();

			if(el != null) {
				res.setStatus(HttpServletResponse.SC_OK);
				new ResourceList(el).toJSON(res.getOutputStream());
			} else {
				// it should not happen
				m = new Message("Cannot list employees: unexpected error.", "E5A1", null);
				res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				m.toJSON(res.getOutputStream());
			}
		} catch (Throwable t) {
			m = new Message("Cannot search employee: unexpected error.", "E5A1", t.getMessage());
			res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			m.toJSON(res.getOutputStream());
		}
	}
     */
}
