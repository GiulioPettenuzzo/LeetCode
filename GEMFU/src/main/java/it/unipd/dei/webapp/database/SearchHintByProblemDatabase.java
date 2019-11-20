package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Hint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches hints by their problem.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class SearchHintByProblemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT id, problem, description FROM hint WHERE problem = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The problem associated to the hint
	 */
	private final int problem;

	/**
	 * Creates a new object for searching hint by associated problem.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param problem
	 *            the problem associated to the hint.
	 */
	public SearchHintByProblemDatabase(final Connection con, final int problem) {
		this.con = con;
		this.problem = problem;
	}

	/**
	 * Searches hints by their associated problem.
	 * 
	 * @return a list of {@code Hint} object matching the associated problem.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Hint> searchHintByProblem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Hint> hints = new ArrayList<Hint>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, problem);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				hints.add(new Hint(rs.getInt("id"), rs.getInt("problem"), rs.getString("description")));
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

		return hints;
	}
}
