package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Searches companies by their name.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */

public final class SearchCompanyByNameDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "SELECT name FROM Company WHERE name = ?";
	private static final String STATEMENT2 = "SELECT name FROM Company ORDER BY name ASC";

	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The name of the company
	 */
	private final String name;

	/**
	 * Creates a new object for searching companies by their name.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param name
	 *            the name of the company.
	 */
	public SearchCompanyByNameDatabase(final Connection con, final String name) {
		this.con = con;
		this.name = name;
	}

	/**
	 * Searches companies by their name.
	 * 
	 * @return a list of {@code Company} object matching the name.
	 * 
	 * @throws SQLException
	 *             if any error occurs while searching for companies.
	 */

	public List<Company> searchCompanyByName() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Company> companies = new ArrayList<Company>();

		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				companies.add(new Company(rs.getString("name")));
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

		return companies;

	}
	public List<Company> searchCompanyRest() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		// the results of the search
		final List<Company> companies = new ArrayList<Company>();

		try {
			pstmt = con.prepareStatement(STATEMENT2);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				companies.add(new Company(rs.getString("name")));
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

		return companies;

	}

}