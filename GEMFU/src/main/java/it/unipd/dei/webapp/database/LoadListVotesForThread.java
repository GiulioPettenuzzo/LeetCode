package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Thread;
import it.unipd.dei.webapp.resource.Vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoadListVotesForThread {

    private static final String STATEMENT = "SELECT * FROM votethread WHERE thread = ?";

    private final Connection con;
    private final int threadID;

    public LoadListVotesForThread(Connection con, int threadID){
        this.con = con;
        this.threadID = threadID;
    }

    public List<Vote> loadVotes() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Vote> votes = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, threadID);


            rs = pstmt.executeQuery();

            while (rs.next()) {
                votes.add(new Vote(rs.getString("username"), -1, rs.getInt("thread"), rs.getInt("vote")));
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

        return votes;
    }

}
