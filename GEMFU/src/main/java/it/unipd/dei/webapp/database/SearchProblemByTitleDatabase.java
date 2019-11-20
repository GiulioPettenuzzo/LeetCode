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

import it.unipd.dei.webapp.resource.Problem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches problem by their title.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class SearchProblemByTitleDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT id, username, title, description, difficulty, date, solution FROM problem WHERE title = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The salary of the problem
	 */
	private final String title;

	public SearchProblemByTitleDatabase(final Connection con, final String title) {
		this.con = con;
		this.title = title;
	}

	/**
	 * Searches problems by their title.
	 * 
	 * @return a list of {@code Problem} object matching the title.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for problems.
	 */
	public List<Problem> searchProblemByTitle() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Problem> problems = new ArrayList<>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, title);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				problems.add(new Problem(rs.getInt("id"), rs.getString("username"), rs.getString("title"), rs.getString("difficulty"), rs.getString("description"), rs.getString("date"), rs.getString("solution")));
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

		return problems;
	}
}
