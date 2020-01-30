package application;   // goal: update seller salary

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		Statement st = null;
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false); // do not confirm operations automatically
			
			st = conn.createStatement();
			
			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

			/*int x = 1;
			if (x < 2 ) {
				throw new SQLException("Fake error");
			}*/  // error simulation
			
			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
		
			
			conn.commit(); // manual confirmation of operations
			
			System.out.println("rows1 " + rows1);
			System.out.println("rows2 " + rows2);
			
		}
		catch(SQLException e) {
			try {
				conn.rollback();   // return to initial state
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: " + e.getMessage());
			}
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
