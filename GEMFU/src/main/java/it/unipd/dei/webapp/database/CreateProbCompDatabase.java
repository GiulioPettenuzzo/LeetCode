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

import it.unipd.dei.webapp.resource.ProbComp;

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
public final class CreateProbCompDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO problemcomp (problem, company) VALUES (?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be store in the database
	 */
	private final ProbComp probcomp;

	public CreateProbCompDatabase(final Connection con, final ProbComp probcomp) {
		this.con = con;
		this.probcomp = probcomp;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Employee} object matching the badge.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the employee.
	 */
	public ProbComp createProbComp() throws SQLException {

		PreparedStatement pstmt = null;
	
		ResultSet rs = null;

		// the create employee
		ProbComp e = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);

			pstmt.setInt(1, probcomp.getProblem());
			pstmt.setString(2, probcomp.getCompany());
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new ProbComp(rs.getInt("problem"), rs.getString("company"));
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
