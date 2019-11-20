package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Thread;
import it.unipd.dei.webapp.resource.Vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostVoteForThreadDatabase {

    private static final String STATEMENT = "INSERT INTO votethread (username, thread, vote) VALUES (?, ?, ?) RETURNING *";
    private static final String DEL_STATEMENT = "DELETE FROM votethread WHERE username = ? AND thread = ?";

    private final Connection con;

    private final Vote vote;

    public PostVoteForThreadDatabase(Connection con, Vote vote){
        this.con = con;
        this.vote = vote;
    }

    public Vote postThread(){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Vote returnVote = null;

        try{
            pstmt = con.prepareStatement(DEL_STATEMENT);
            pstmt.setString(1,vote.getUsername());
            pstmt.setInt(2,vote.getThreadID());

            rs = pstmt.executeQuery();
        }catch (SQLException e){

        }

        try {

            try {
                pstmt = con.prepareStatement(STATEMENT);
                pstmt.setString(1, vote.getUsername());
                pstmt.setInt(2, vote.getThreadID());
                pstmt.setInt(3, vote.getVote());

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    returnVote = new Vote(rs.getString("username"), -1, rs.getInt("thread"), rs.getInt("vote"));
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }

                con.close();
            }
        }catch (SQLException e){

        }

        return returnVote;
    }
}
