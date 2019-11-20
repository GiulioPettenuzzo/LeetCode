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
 * Deletes a problem from the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class DeleteProblemDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "DELETE FROM problem WHERE id = ? RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The id of the problem
	 */
	private final int id;


	public DeleteProblemDatabase(final Connection con, final int id) {
		this.con = con;
		this.id = id;
	}

	/**
	 * Deletes an problem from the database.
	 * 
	 * @return the {@code problem}.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the problem.
	 */
	public Problem deleteProblem() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the deleted employee
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
