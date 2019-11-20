package it.unipd.dei.webapp.database;

import it.unipd.dei.webapp.resource.Company;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Creates a new company into the database.
 * 
 * @author GEMFU
 * @version 1.00
 * @since 1.00
 */

public final class CreateCompanyDatabase {

	/**
	 * The SQL statement to be executed
	 */
	private static final String STATEMENT = "INSERT INTO company (name) VALUES (?)";
	private static final String STATEMENT2 = "INSERT INTO company (name) VALUES (?) RETURNING *";


	/**
	 * The connection to the database
	 */
	private final Connection con;

	/**
	 * The company to be stored into the database
	 */
	private final Company company;

	/**
	 * Creates a new object for storing a company into the database.
	 * 
	 * @param con
	 *            the connection to the database.
	 * @param company
	 *            the company to be stored into the database.
	 */
	public CreateCompanyDatabase(final Connection con, final Company company) {
		this.con = con;
		this.company = company;
	}

	/**
	 * Stores a new company into the database
	 * 
	 * @throws SQLException
	 *             if any error occurs while storing the company.
	 */
	public void createCompany() throws SQLException {

		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(STATEMENT);
			pstmt.setString(1, company.getName());
			
			pstmt.execute();

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}
	}

	public Company createCompanyRest() throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Company e = null;
		
		try {
			pstmt = con.prepareStatement(STATEMENT2);
			pstmt.setString(1, company.getName());
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				e = new Company(rs.getString("name"));
			}

		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			con.close();
		}
		return e;
	}

}