/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Comment;

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
public final class SearchCommentByProblemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT id, text, date, problem, username FROM comment INNER JOIN addcomment ON comment.id = addcomment.comment WHERE comment.id2 IS NULL AND addcomment.problem = ? ORDER BY date DESC";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The problem of the comment
	 */
	private final int problem;

	public SearchCommentByProblemDatabase(final Connection con, final int problem) {
		this.con = con;
		this.problem = problem;
	}

	/**
	 * Searches comment by problem.
	 * 
	 * @return a list of {@code Employee} object matching the problem.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Comment> SearchCommentByProblem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Comment> comments = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, problem);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				comments.add(new Comment(rs.getInt("id"), rs.getString("username"), rs.getInt("problem"), -1,  -1, rs.getString("text"), rs.getString("date")));
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

		return comments;
	}
}
