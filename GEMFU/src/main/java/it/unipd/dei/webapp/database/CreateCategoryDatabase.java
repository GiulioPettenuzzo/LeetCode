
package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates an employee in the database.
 * 
 * @author GEMFU (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public final class CreateCategoryDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO category (name) VALUES (?) RETURNING *";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The category to be store in the database
	 */
	private final Category category;

	public CreateCategoryDatabase(final Connection con, final Category category) {
		this.con = con;
		this.category = category;
	}

	/**
	 * Creates a category in the database.
	 * 
	 * @return the {@code Category}.
	 * 
	 * @throws SQLException
	 *             if any error occurs while reading the category.
	 */
	public Category createCategory() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the create employee
		Category e = null;

		try {

			rs = pstmt.executeQuery();

			if (rs.next()) {
				e = new Category(rs.getString("name"));
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
