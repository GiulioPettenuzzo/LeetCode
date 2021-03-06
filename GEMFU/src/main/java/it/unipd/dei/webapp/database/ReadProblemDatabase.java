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
 * Reads an problem from the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class ReadProblemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT id, username, title, description, difficulty, date, solution FROM problem WHERE id = ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The badge of the problem
	 */
	private final int id;


	public ReadProblemDatabase(final Connection con, final int id) {
		this.con = con;
		this.id = id;
	}

	/**
	 * Reads an employee from the database.
	 * 
	 * @return the {@code Problem} object.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the problem.
	 */
	public Problem ReadProblem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the read problem
		Problem e = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new Problem(rs.getInt("id"), rs.getString("username"), rs.getString("title"), rs.getString("difficulty"), rs.getString("description"), rs.getString("date"), rs.getString("solution"));
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
