package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Category;
import it.unipd.dei.webapp.resource.Thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListThreadDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM thread WHERE problem_id = ? ORDER BY date DESC";

    /**
     * The connection to the database
     */
    private final Connection con;
    private final int problemId;


    public ListThreadDatabase(final Connection con, final int problemId) {
        this.con = con;
        this.problemId = problemId;
    }

    /**
     * Lists all the category in the database.
     *
     * @return a list of {@code Category}.
     *
     * @throws SQLException
     *             if any error occurs while searching for category.
     */
    public List<Thread> readThreadList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the results of the search
        final List<Thread> threads = new ArrayList<>();

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, problemId);


            rs = pstmt.executeQuery();

            while (rs.next()) {
                threads.add(new Thread(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getString("username"), rs.getString("date"), rs.getInt("problem_id"), rs.getInt("views")));
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

        return threads;
    }


}
