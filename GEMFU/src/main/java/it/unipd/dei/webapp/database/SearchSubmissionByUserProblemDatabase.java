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
public final class SearchSubmissionByUserProblemDatabase {

	private static final String STATEMENT = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY date DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT2 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY id ASC LIMIT 5 OFFSET ?";
	private static final String STATEMENT3 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY title asc LIMIT 5 OFFSET ?";
	private static final String STATEMENT4 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY valid asc LIMIT 5 OFFSET ?";
	private static final String STATEMENT5 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY date ASC LIMIT 5 OFFSET ?";
	private static final String STATEMENT6 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY id DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT7 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY title DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT8 = "SELECT id, username, problem, title, text, date, valid FROM submission WHERE username = ? AND problem = ? ORDER BY valid DESC LIMIT 5 OFFSET ?";
	
	private final Connection con;

	/**
	 * The salary of the problem
	 */
	private final String username;

	private final int problem;

	private final int offset;

	private final int order;

	public SearchSubmissionByUserProblemDatabase(final Connection con, final String username, final int problem, int offset, int order)
	{
		this.con = con;
		this.username = username;
		this.problem = problem;
		this.offset = offset;
		this.order = order;
	}

	public List<Submission> SearchSubmissionByUserProblem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Submission> submissions = new ArrayList<>();

		try {
			if(order == 1)
				pstmt = con.prepareStatement(STATEMENT);
			else if(order == 2)
				pstmt = con.prepareStatement(STATEMENT2);
			else if(order == 3)
				pstmt = con.prepareStatement(STATEMENT3);
			else if(order == 4)
				pstmt = con.prepareStatement(STATEMENT4);
			else if(order == 5)
				pstmt = con.prepareStatement(STATEMENT5);
			else if(order == 6)
				pstmt = con.prepareStatement(STATEMENT6);
			else if(order == 7)
				pstmt = con.prepareStatement(STATEMENT7);
			else 
				pstmt = con.prepareStatement(STATEMENT8);
			pstmt.setString(1, username);
			pstmt.setInt(2, problem);
			pstmt.setInt(3, offset);

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
