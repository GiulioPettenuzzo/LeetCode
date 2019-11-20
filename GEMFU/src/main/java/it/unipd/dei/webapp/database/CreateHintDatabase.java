package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Hint;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new hint into the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class CreateHintDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO hint (id, problem, description) VALUES (?, ?, ?)";
	private static final String STATEMENT2 = "INSERT INTO hint ( problem, description) VALUES ( ?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The hint to be stored into the database
	 */
	private final Hint hint;

	/**
	 * Creates a new object for storing a hint into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param hint
	 *            the hint to be stored into the database.
	 */
	public CreateHintDatabase(final Connection con, final Hint hint) {
		this.con = con;
		this.hint = hint;
	}

	/**
	 * Stores a new hint into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the employee.
	 */
	public void createHint() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, hint.getId());
			pstmt.setInt(2, hint.getProblem());
			pstmt.setString(3, hint.getDescription());
			
			pstmt.execute();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
	public Hint createHintRest() throws SQLException {

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		// the create employee
		Hint s = null;

		try {
			pstmt = con.prepareStatement(STATEMENT2);
			pstmt.setInt(2, hint.getProblem());
			pstmt.setString(3, hint.getDescription());

		rs = pstmt.executeQuery();

			if (rs.next()) {
				s = new Hint(rs.getInt("id"), rs.getInt("problem"), rs.getString("description"));
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

		return s;
	}
}
