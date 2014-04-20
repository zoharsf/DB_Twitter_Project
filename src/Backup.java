import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Backup extends DB_Connection{

	public static ArrayList<ArrayList<String>> backupExpressionsTable () throws ClassNotFoundException, SQLException{

		getConnectionString();
		
		ResultSet rs;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		
		try{

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select * from TweetDB.expressions");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("expression"));
				table.add(row);
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		return table;
	}
	public static ArrayList<ArrayList<String>> backupGroupsTable () throws ClassNotFoundException, SQLException{

		getConnectionString();
		
		ResultSet rs;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select * from TweetDB.groups");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("name"));
				row.add(rs.getString("word"));
				table.add(row);
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		return table;
	}
	public static ArrayList<ArrayList<String>> backupRelationsTable () throws ClassNotFoundException, SQLException{

		getConnectionString();
		
		ResultSet rs;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select * from TweetDB.relations");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("tablekey"));
				row.add(rs.getString("name"));
				row.add(rs.getString("word1"));
				row.add(rs.getString("word2"));
				table.add(row);
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		return table;
	}
	public static ArrayList<ArrayList<String>> backupTweetTable () throws ClassNotFoundException, SQLException{

		getConnectionString();
		
		ResultSet rs;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select * from TweetDB.tweets");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("id"));
				row.add(rs.getString("users"));
				row.add(rs.getString("createdAt"));
				row.add(rs.getString("tweet"));
				table.add(row);
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		return table;
	}
	public static ArrayList<ArrayList<String>> backupUsersTable () throws ClassNotFoundException, SQLException{

		getConnectionString();
		
		ResultSet rs;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select * from TweetDB.users");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("user"));
				table.add(row);
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		return table;
	}
	public static ArrayList<ArrayList<String>> backupWordsTable () throws ClassNotFoundException, SQLException{

		getConnectionString();
		
		ResultSet rs;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select * from TweetDB.words");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("word"));
				row.add(rs.getString("tweetId"));
				table.add(row);
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		return table;
	}
}
