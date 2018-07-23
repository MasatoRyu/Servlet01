package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class UserDB {
	
	private static final String TABLE_NAME = "TODO_USER";

	public static void main(String[] args) {
		UserDB userDB = new UserDB();
		
		try{
			userDB.create();
			
			userDB.execute(args);
		}catch(Throwable t) {
			t.printStackTrace();
		}finally{

			userDB.close();
		}
	}
	

	private Connection _connection;
	

	private Statement _statement;
	

	public UserDB() {
		_connection = null;
		_statement = null;
	}
	

	public void create()
		throws ClassNotFoundException, SQLException{

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		_connection = DriverManager.getConnection("jdbc:sqlserver://rdb01.database.windows.net\\rdb02:1433;databaseName=rdb02;user=user01;password=Passw0rd2018;allowPortWithNamedInstance=true;EncryptionMethod=SSL;ValidateServerCertificate=false");
		_statement = _connection.createStatement();
	}
	

	public void close() {
		if(_statement != null) {
			try{
				_statement.close();
			}catch(SQLException e) {
				;
			}
			_statement = null;
		}
		if(_connection != null) {
			try{
				_connection.close();
			}catch(SQLException e) {
				;
			}
			_connection = null;
		}
	}
	

	public void execute(String[] args)
		throws SQLException {
		String command = args[0];
		if("select".equals(command)) {
			executeSelect();
		}else if("insert".equals(command)) {
			executeInsert(args[1], args[2], args[3]);
		}else if("update".equals(command)) {
			executeUpdate(args[1], args[2], args[3]);
		}else if("delete".equals(command)) {
			executeDelete(args[1]);
		}
	}
	

	private void executeSelect()
		throws SQLException{
		ResultSet resultSet = _statement.executeQuery("SELECT * FROM " + TABLE_NAME);
		try{
			boolean br = resultSet.first();
			if(br == false) {
				return;
			}
			do{
				String id = resultSet.getString("ID");
				String name = resultSet.getString("NAME");
				String password = resultSet.getString("PASSWORD");
				
				System.out.println("id: " + id + ", name: " + name + ", password: " + password);
			}while(resultSet.next());
		}finally{
			resultSet.close();
		}
	}
	

	private void executeInsert(String id, String name, String password)
		throws SQLException{
		// SQL���𔭍s
		int updateCount = _statement.executeUpdate("INSERT INTO " + TABLE_NAME + " (ID,NAME,PASSWORD) VALUES ('"+id+"','"+name+"','"+password+"')");
		System.out.println("Insert: " + updateCount);
	}
	

	private void executeUpdate(String id, String name, String password)
		throws SQLException{
		// SQL���𔭍s
		int updateCount = _statement.executeUpdate("UPDATE " + TABLE_NAME + " SET NAME='"+name+"', PASSWORD='"+password+"' WHERE ID='" + id + "'");
		System.out.println("Update: " + updateCount);
	}
	

	private void executeDelete(String id)
		throws SQLException{

		int updateCount = _statement.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE ID='" + id + "'");
		System.out.println("Delete: " + updateCount);
	}
	
}