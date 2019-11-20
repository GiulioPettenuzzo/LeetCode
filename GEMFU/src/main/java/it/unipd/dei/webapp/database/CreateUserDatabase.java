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

import it.unipd.dei.webapp.resource.User;

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
public final class CreateUserDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO users (username, password, name, surname, date, gender, email, profileImage ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The user to be stored into the database
	 */
	private final User user;


	public CreateUserDatabase(final Connection con, final User user) {
		this.con = con;
		this.user = user;
	}

	/**
	 * Stores a new user into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the employee.
	 */
	public void createUser() throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getSurname());
			pstmt.setDate(5, java.sql.Date.valueOf(user.getDate()));
			pstmt.setInt(6, user.getGender());
			pstmt.setString(7, user.getEmail());
			pstmt.setString(8, user.getProfileImage());

			pstmt.execute();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}

	}
}
