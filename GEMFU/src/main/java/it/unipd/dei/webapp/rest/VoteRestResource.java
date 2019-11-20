package it.unipd.dei.webapp.rest;

import it.unipd.dei.webapp.database.LoadListVotesForThread;
import it.unipd.dei.webapp.database.PostVoteForThreadDatabase;
import it.unipd.dei.webapp.resource.Message;
import it.unipd.dei.webapp.resource.ResourceList;
import it.unipd.dei.webapp.resource.Vote;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VoteRestResource extends RestResource{

    public VoteRestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
        super(req, res, con);
    }

    public void postThreadVote() throws IOException{
        Vote vote = null;
        Message m = null;
        try{
            Vote vote1 = Vote.fromJSON(req.getInputStream());
            vote = new PostVoteForThreadDatabase(con,vote1).postThread();
            if(vote!=null){
                res.setStatus(HttpServletResponse.SC_OK);
                vote.toJSON(res.getOutputStream());
            }else{
                m = new Message("Cannot create the discussion: unexpected error.", "E5A1", null);
                res.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
                m.toJSON(res.getOutputStream());
            }

        }
        catch (Throwable t){
            if (t instanceof SQLException) {
                m = new Message("Cannot post the vote: it already exists.", "E5A2", t.getMessage());
                res.setStatus(HttpServletResponse.SC_CONFLICT);
                m.toJSON(res.getOutputStream());
            }
            else if(t instanceof IOException){
                m = new Message("Cannot post the vote: unexpected error in RestResource: " + req + "fibw" , "E5A1", t.getMessage());
                res.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
                m.toJSON(res.getOutputStream());
            }
        }
    }

    public void postProblemVote() throws IOException{

    }

    public void loadThreadVote() throws IOException{

        List<Vote> votes = null;
        Message m = null;
        try{
            String path = req.getRequestURI();
            path = path.substring(path.lastIndexOf("thread") + 6);
            final int id = Integer.parseInt(path.substring(1));

            votes = new LoadListVotesForThread(con,id).loadVotes();
            if(votes!=null){
                res.setStatus(HttpServletResponse.SC_OK);
                new ResourceList<>(votes).toJSON(res.getOutputStream());
            }else{
                m = new Message(String.format("Discussion %d not found.", id), "E5A3", null);
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                m.toJSON(res.getOutputStream());
            }


        }catch (Throwable t){
            m = new Message("Cannot read vote: unexpected error.", "E5A1", t.getMessage());
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            m.toJSON(res.getOutputStream());
        }
    }

    public void loadProblemVote() throws IOException{

    }


}
