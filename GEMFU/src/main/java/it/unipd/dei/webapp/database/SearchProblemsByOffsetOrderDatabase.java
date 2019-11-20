package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches problems by various parameters.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */

public final class SearchProblemsByOffsetOrderDatabase {

	private static final String STATEMENT = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY date DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT2 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY id ASC LIMIT 5 OFFSET ?";
	private static final String STATEMENT3 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY title asc LIMIT 5 OFFSET ?";
	private static final String STATEMENT4 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY (CASE difficulty WHEN 'Easy' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Hard' THEN 3 END) LIMIT 5 OFFSET ?";
	private static final String STATEMENT5 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY date ASC LIMIT 5 OFFSET ?";
	private static final String STATEMENT6 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY id DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT7 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY title DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT8 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY (CASE difficulty WHEN 'Hard' THEN 1 WHEN 'Medium' THEN 2 WHEN 'Easy' THEN 3 END) LIMIT 5 OFFSET ?";
	private static final String STATEMENT9 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY solution DESC LIMIT 5 OFFSET ?";
	private static final String STATEMENT10 = "SELECT id, username, title, difficulty, description, date, solution FROM problem ORDER BY solution ASC LIMIT 5 OFFSET ?";

	private final Connection con;

	private final int offset;
	private final int order;

	public SearchProblemsByOffsetOrderDatabase(final Connection con, int offset, int order)
	{
		this.con = con;
		this.offset = offset;
		this.order = order;
	}

	public List<Problem> SearchProblemsByOffsetOrder() throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		final List<Problem> problems = new ArrayList<>();

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
			else if(order == 8)
				pstmt = con.prepareStatement(STATEMENT8);
			else if(order == 9)
				pstmt = con.prepareStatement(STATEMENT9);
			else 
				pstmt = con.prepareStatement(STATEMENT10);

			pstmt.setInt(1, offset);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				problems.add(new Problem(rs.getInt("id"), rs.getString("username"), rs.getString("title"), rs.getString("difficulty"), rs.getString("description"), rs.getString("date"), rs.getString("solution")));
			}
			}
			finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}
			return problems;

		}


}