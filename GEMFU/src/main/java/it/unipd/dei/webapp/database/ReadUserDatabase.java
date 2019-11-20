package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReadUserDatabase {


    /**
     * The SQL statement to be executed
     */
    private static final String STATEMENT = "SELECT * FROM users WHERE username = ?";

    /**
     * The connection to the database
     */
    private final Connection con;

    /**
     * The badge of the problem
     */
    private final String username;


    public ReadUserDatabase(final Connection con, final String username) {
        this.con = con;
        this.username = username;
    }

    /**
     * Reads an employee from the database.
     *
     * @return the {@code Problem} object.
     *
     * @throws java.sql.SQLException
     *             if any error occurs while reading the problem.
     */
    public User readUser() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        // the read problem
        User user = null;

        try {
            pstmt = con.prepareStatement(STATEMENT);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                    user = new User(rs.getString("username"),rs.getString("name"),rs.getString("surname"),rs.getInt("gender"),rs.getString("profileimage"),rs.getDate("date").toString(),rs.getString("email"),rs.getString("password"));
                   // user = new User(username,null,null,-1,null,null,null,null);
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

        return user;
    }
}
