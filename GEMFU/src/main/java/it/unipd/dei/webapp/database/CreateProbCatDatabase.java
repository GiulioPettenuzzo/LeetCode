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

import it.unipd.dei.webapp.resource.ProbCat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an employee in the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class CreateProbCatDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO problemcat (problem, category) VALUES (?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be store in the database
	 */
	private final ProbCat probcat;

	public CreateProbCatDatabase(final Connection con, final ProbCat probcat) {
		this.con = con;
		this.probcat = probcat;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public ProbCat createProbCat() throws SQLException {

		PreparedStatement pstmt = null;
	
		ResultSet rs = null;

		// the create employee
		ProbCat e = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);

			pstmt.setInt(1, probcat.getProblem());
			pstmt.setString(2, probcat.getCategory());
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new ProbCat(rs.getInt("problem"), rs.getString("category"));
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
