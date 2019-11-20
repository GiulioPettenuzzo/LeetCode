package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.database.ReadUserDatabase;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class UserRestResource extends RestResource{

    public UserRestResource(HttpServletRequest req, HttpServletResponse res, Connection con){
        super(req, res, con);
    }



    public void loadUser() throws IOException{
        User user = null;
        Message m = null;
        try{
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("user") + 4);
            final String username = path.substring(1);

            user = new ReadUserDatabase(con,username).readUser();
            if(user != null) {
                res.setStatus(HttpServletResponse.SC_OK);
                user.toJSON(res.getOutputStream());
            } else {
                m = new Message(String.format("Discussion %d not found.", 1), "E5A3", null);
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
}
