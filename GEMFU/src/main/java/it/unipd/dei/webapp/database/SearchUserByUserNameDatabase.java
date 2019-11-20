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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches users by their date.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class SearchUserByUserNameDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT username, name, surname, date, gender, email, profileimage, password FROM users WHERE date > ?";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The date of the user
	 */
	private final String date;


	public SearchUserByUserNameDatabase(final Connection con, final String date) {
		this.con = con;
		this.date = date;
	}

	/**
	 * Searches users by their date.
	 * 
	 * @return a list of {@code User} object matching the date.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for users.
	 */
	public List<User> SearchUserByUserName() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<User> users = new ArrayList<User>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setDate(1, java.sql.Date.valueOf(date));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				users.add(new User(rs.getString("username"), rs.getString("name"), rs.getString("surname"), rs.getInt("gender"), rs.getString("profileimage"), rs.getString("date"), rs.getString("email"), rs.getString("password")));
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

		return users;
	}
}
