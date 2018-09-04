package com.pbs.web.jdbc.ParkingBookSystem;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookDbUtil {
	private final DataSource dataSource;

	// constructor
	public BookDbUtil() {
		try {
			/* TODO: przez adnotacje, albo jakos inaczej ladnie */
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/ParkingBookSystem");
		} catch (NamingException e) {
			throw new IllegalStateException("Could not initialize database", e);
		}
	}

	// create new method getBooks() which returns list of books List<book>
	public List<Book> getBooks() throws Exception {
		List<Book> books = new ArrayList<>();

		try (Connection connection = dataSource.getConnection()) {
			// create sql statement
			String sql = "SELECT * FROM `rezerwacje_miejsc` ORDER BY `NR_MIEJSCA`";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						// retrive data from result set row
						int placeNo = resultSet.getInt("NR_MIEJSCA");
						Date start = resultSet.getDate("START");
						Date end = resultSet.getDate("KONIEC");
						String userName = resultSet.getString("IMIE_NAZWISKO");
						int phone = resultSet.getInt("TELEFON");

						// create new temporary Book object
						Book tempBook = new Book(placeNo, start, end, userName, phone);

						// add it to our list of Books
						books.add(tempBook);
					}

					return books;
				}
			}
		}
	}

	// create new method getBook(id) which returns
	public List<Book> getSpecBook(int id) throws Exception {
		List<Book> books = new ArrayList<>();
		
		//int idInt = Integer.parseInt(id);

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = dataSource.getConnection();

			// create sql statement
			String sql = "SELECT * FROM `rezerwacje_miejsc` WHERE `NR_MIEJSCA`=?";

			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, id);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				// retrive data from result set row
				int placeNo = myRs.getInt("NR_MIEJSCA");
				Date start = myRs.getDate("START");
				Date end = myRs.getDate("KONIEC");
				String userName = myRs.getString("IMIE_NAZWISKO");
				int phone = myRs.getInt("TELEFON");

				// create new temporary Book object
				Book tempBook = new Book(placeNo, start, end, userName, phone);

				// add it to our list of Books
				books.add(tempBook);
			}
			
			return books;

		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	/* TODO: to mozna wyrzucic */
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // doesn't really close it ... just puts back in connection pool
			}
		} catch (SQLException exc) {
		}
	}

	public boolean doesBookExist(int number, Date start, Date end) throws Exception {

		boolean result;

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		/* TODO: to warto przerobic na try with resource - auto close */
		try {
			// get a connection
			myConn = dataSource.getConnection();

			// create sql for validation
			String sqlValidate = "SELECT * FROM `rezerwacje_miejsc`" + " WHERE `NR_MIEJSCA`=?"
					+ " AND (`START` BETWEEN ? AND ?" + " OR `KONIEC` BETWEEN ? AND ?)";

			myStmt = myConn.prepareStatement(sqlValidate);

			myStmt.setInt(1, number);
			myStmt.setDate(2, new java.sql.Date(start.getTime())); // zmieniam na java.sql.Date z java.util.Date bez
																	// globalnego importu
			myStmt.setDate(3, new java.sql.Date(end.getTime())); // zmieniam na java.sql.Date z java.util.Date bez
																	// globalnego importu
			myStmt.setDate(4, new java.sql.Date(start.getTime()));
			myStmt.setDate(5, new java.sql.Date(end.getTime()));

			// execute query
			myRs = myStmt.executeQuery();

			// check if there was a match
			result = myRs.next();
			return result;
		} finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}
	}

	public void addBook(Book theBook) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		/* TODO: to warto przerobic na try with resource - auto close */
		try {
			// get db connection
			myConn = dataSource.getConnection();

			// create sql for insert
			String sqlInsert = "INSERT INTO `rezerwacje_miejsc`"
					+ " (`NR_MIEJSCA`, `START`, `KONIEC`, `IMIE_NAZWISKO`, `TELEFON`)" + " VALUES (?, ?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sqlInsert);

			// set the param values for the book
			myStmt.setInt(1, theBook.getNumber());
			myStmt.setDate(2, new java.sql.Date(theBook.getStart().getTime()));
			myStmt.setDate(3, new java.sql.Date(theBook.getEnd().getTime()));
			myStmt.setString(4, theBook.getUserName());
			myStmt.setInt(5, theBook.getPhone());

			// execute sql for insert
			myStmt.execute();

		} finally {
			// clean up JDBC objects
			close(myConn, myStmt, null); // null is for myRs = myStmt.executeQuery(sql); which we dont have here
		}
	}

	public Book getBook(int id) {
		/* TODO: to implement */
		return null;
	}
}
