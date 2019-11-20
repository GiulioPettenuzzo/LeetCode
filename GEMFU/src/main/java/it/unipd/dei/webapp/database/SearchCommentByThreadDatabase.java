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
public final class SearchCommentByThreadDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT id, text, date, thread, username FROM comment INNER JOIN comthread ON comment.id = comthread.comment WHERE comment.id2 IS NULL AND comthread.thread = ? ORDER BY date DESC";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The id of thread
	 */
	private final int thread;

	public SearchCommentByThreadDatabase(final Connection con, final int thread) {
		this.con = con;
		this.thread = thread;
	}

	/**
	 * Searches employees by their salary.
	 * 
	 * @return a list of {@code Employee} object matching the salary.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for employees.
	 */
	public List<Comment> SearchCommentByThread() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Comment> comments = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, thread);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				comments.add(new Comment(rs.getInt("id"), rs.getString("username"), -1, rs.getInt("thread"),  -1, rs.getString("text"), rs.getString("date")));
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
