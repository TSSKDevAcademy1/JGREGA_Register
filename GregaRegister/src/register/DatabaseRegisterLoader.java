package register;
//package register;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRegisterLoader implements RegisterLoader {

	public static final String URL = "jdbc:mysql://localhost/register"; 
	public static final String USER = "root";
	public static final String PASSWORD = "JaGre16041991";
	ArrayRegister register = new ArrayRegister(20);
	public static final String QUERY_SAVE = "INSERT INTO persons (id, name, phoneNumber) VALUES (?, ?, ?)";
	public static final String QUERY_LOAD = "SELECT name,phoneNumber FROM persons ORDER BY name DESC";
	public static final String QUERY_CREATE = "CREATE TABLE persons (id INT PRIMARY KEY, name VARCHAR(32) NOT NULL, phoneNumber INT NOT NULL)";
	public static final String QUERY_DROP = "DROP TABLE persons";

	@Override // nacitanie ulozenych udajov z databazy
	public Register load() {

		Connection conection;
		try {
			conection = DriverManager.getConnection(URL, USER, PASSWORD);

			Statement stmt = conection.createStatement();
			ResultSet rs = stmt.executeQuery(QUERY_LOAD);

			while (rs.next()) {
				register.addPerson(new Person(rs.getString(1), rs.getString(2))); 
			}
			
			rs.close();
			stmt.close();
			conection.close();
			return register;
		} catch (SQLException e) {
			e.printStackTrace();
			return register;
		}

	}

	@Override // Ulozenie udajov do databazy
	public void save(Register register) {

		Connection conection;
		try {
			conection = DriverManager.getConnection(URL, USER, PASSWORD);

			Statement stmtDelete = conection.createStatement();
			stmtDelete.executeUpdate(QUERY_DROP);

			Statement stmtCreate = conection.createStatement();
			stmtCreate.executeUpdate(QUERY_CREATE);

			PreparedStatement stmt = conection.prepareStatement(QUERY_SAVE);
			for (int i = 0; i < register.getCount(); i++) {
				stmt.setInt(1, i + 1);
				stmt.setString(2, register.getPerson(i).getName()); //
				stmt.setString(3, register.getPerson(i).getPhoneNumber());

				stmt.executeUpdate();
			}
			stmt.close();
			conection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
