package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Thread;
import it.unipd.dei.webapp.resource.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CreateThreadDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "INSERT INTO thread (username, problem_id, title, text, date) VALUES (?, ?, ?, ?, ?)";
    private static final String STATEMENT_REST = "INSERT INTO thread (username, problem_id, title, text, date) VALUES (?, ?, ?, ?, ?) RETURNING *";


    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The thread to be stored into the database
     */
    private final Thread thread;


    public CreateThreadDatabase(final Connection con, final Thread thread) {
        this.con = con;
        this.thread = thread;
    }

    /**
     * Stores a new employee into the database
     *
     * @throws SQLException
     *             if any error occurs while storing the employee.
     */
    public void createThread() throws SQLException {

        PreparedStatement pstmt = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, thread.getUser());
            pstmt.setInt(2, thread.getProblemId());
            pstmt.setString(3, thread.getTitle());
            pstmt.setString(4, thread.getText());
            pstmt.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));


            pstmt.execute();

        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            con.close();
        }

    }

    public Thread createRestThread() throws SQLException{
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Thread threadReturn = null;

        try {
            pstmt = con.prepareStatement(STATEMENT_REST);
            pstmt.setString(1, thread.getUser());
            pstmt.setInt(2, thread.getProblemId());
            pstmt.setString(3, thread.getTitle());
            pstmt.setString(4, thread.getText());
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(thread.getDate()));

            rs = pstmt.executeQuery();
            if(rs.next()) {
                threadReturn = new Thread(rs.getInt("id"),rs.getString("title"),rs.getString("text"),rs.getString("username"),rs.getString("date"),rs.getInt("problem_id"),rs.getInt("views"));
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

        return threadReturn;
    }


}
