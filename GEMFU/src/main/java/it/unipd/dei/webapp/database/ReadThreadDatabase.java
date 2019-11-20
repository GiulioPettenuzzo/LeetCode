package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Thread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadThreadDatabase {

    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT id, username, problem_id, title, text, date, views FROM thread WHERE id = ?";
    private static final String VIEWS_STATEMENT = "UPDATE thread SET views = ? WHERE id = ?";
    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the problem
     */
    private final int id;


    public ReadThreadDatabase(final Connection con, final int id) {
        this.con = con;
        this.id = id;
    }

    /**
     * Reads an employee from the database.
     *
     * @return the {@code Problem} object.
     *
     * @throws java.sql.SQLException
     *             if any error occurs while reading the problem.
     */
    public Thread ReadThread() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the read problem
        Thread e = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                e = new Thread(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getString("username"), rs.getString("date"), rs.getInt("problem_id"),rs.getInt("views"));
            }

            try {
                int views = e.getViews();
                pstmt = con.prepareStatement(VIEWS_STATEMENT);
                views++;
                pstmt.setInt(1, views);
                pstmt.setInt(2, id);
                rs = pstmt.executeQuery();
            }catch (Exception ex){

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

        return e;
    }
}
