package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Submission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches employees by their salary.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class SearchSubmissionByUserProblemNoOffsetDatabase {

	private static final String STATEMENT = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ?";

	private final Connection con;

	/**
	 * The salary of the problem
	 */
	private final String username;

	private final int problem;

	public SearchSubmissionByUserProblemNoOffsetDatabase(final Connection con, final String username, final int problem)
	{
		this.con = con;
		this.username = username;
		this.problem = problem;
	}

	public List<Submission> SearchSubmissionByUserProblemNoOffset() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Submission> submissions = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, username);
			pstmt.setInt(2, problem);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				submissions.add(new Submission(rs.getString("username"), rs.getInt("problem"), rs.getString("title"), rs.getString("text"), rs.getString("date"), rs.getInt("valid"), rs.getInt("id")));
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

		return submissions;
	}

}
