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

import it.unipd.dei.webapp.resource.Submission;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Creates a new employee into the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class CreateSubmissionDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO submission (username, problem, title, text, date, valid) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String STATEMENT2 = "INSERT INTO submission (username, problem, title, text, date, valid) VALUES (?, ?, ?, ?, ?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be stored into the database
	 */
	private final Submission submission;


	public CreateSubmissionDatabase(final Connection con, final Submission submission) {
		this.con = con;
		this.submission = submission;
	}

	/**
	 * Stores a new employee into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the employee.
	 */
	public void createSubmission() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, submission.getUsername());
			pstmt.setInt(2, submission.getProblem());
			pstmt.setString(3, submission.getTitle());
			pstmt.setString(4, submission.getText());
			pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(submission.getDate()));
			pstmt.setInt(6, 0);

			pstmt.execute();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}

	public Submission createSubmissionRest() throws SQLException {

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		// the create employee
		Submission s = null;

		try {
			pstmt = con.prepareStatement(STATEMENT2);
			pstmt.setString(1, submission.getUsername());
			pstmt.setInt(2, submission.getProblem());
			pstmt.setString(3, submission.getTitle());
			pstmt.setString(4, submission.getText());
			pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(submission.getDate()));
			pstmt.setInt(6, submission.getValid());

		rs = pstmt.executeQuery();

			if (rs.next()) {
				s = new Submission(rs.getString("username"), rs.getInt("problem"), rs.getString("title"), rs.getString("text"), rs.getString("date"), rs.getInt("valid"), -1);
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

		return s;
	}
}
