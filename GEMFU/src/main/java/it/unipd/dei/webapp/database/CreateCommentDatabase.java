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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an employee in the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */
public final class CreateCommentDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO comment (id2, text, date) VALUES (?, ?, ?) RETURNING *";
	private static final String STATEMENT2 = "INSERT INTO addcomment (comment, problem, username) VALUES (?, ?, ?) RETURNING *";
	private static final String STATEMENT3 = "INSERT INTO comthread (comment, thread, username) VALUES (?, ?, ?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The employee to be store in the database
	 */
	private final Comment comment;

	public CreateCommentDatabase(final Connection con, final Comment comment) {
		this.con = con;
		this.comment = comment;
	}

	/**
	 * Creates an employee in the database.
	 * 
	 * @return the {@code Comment}.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the comment.
	 */
	public Comment createComment() throws SQLException {

		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		// the create comment
		Comment e = null;

		try {
			pstmt = con.prepareStatement(STATEMENT);
			int com = comment.getComment();
			if(com != -1){
				pstmt.setInt(1, comment.getComment());
			}
			else{
				pstmt.setObject(1, null);	
			}
			pstmt.setString(2, comment.getText());
			pstmt.setTimestamp(3, Timestamp.valueOf(comment.getDate()));
			int problem = comment.getProblem();
			
			rs = pstmt.executeQuery();
			if(rs.next()){

				if(problem != -1){
					pstmt2 = con.prepareStatement(STATEMENT2);
					pstmt2.setInt(1, rs.getInt("id"));
					pstmt2.setString(3, comment.getUsername());
					pstmt2.setInt(2, problem);
					rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
						e = new Comment(rs.getInt("id"), rs2.getString("username"), rs2.getInt("problem"), -1,  rs.getInt("id2"), rs.getString("text"), rs.getString("date"));
					}
				}
				else{
					pstmt2 = con.prepareStatement(STATEMENT3);
					pstmt2.setInt(1, rs.getInt("id"));
					pstmt2.setString(3, comment.getUsername());
					pstmt2.setInt(2, comment.getThread());
					rs2 = pstmt2.executeQuery();
					if (rs2.next()) {
						e = new Comment(rs.getInt("id"), rs2.getString("username"), -1, rs2.getInt("thread"),  rs.getInt("id2"), rs.getString("text"), rs.getString("date"));
					}
				}
			}


		} finally {
			if (rs != null) {
				rs.close();
			}

			if (pstmt != null) {
				pstmt.close();
			}
			if (rs2 != null) {
				rs2.close();
			}

			if (pstmt2 != null) {
				pstmt2.close();
			}

			con.close();
		}

		return e;
	}
}
