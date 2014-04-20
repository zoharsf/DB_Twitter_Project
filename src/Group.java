import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Group extends DB_Connection{

	public static void addWordToGroup (String word, String name) throws ClassNotFoundException, SQLException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO TweetDB.groups (name, word) VALUES (\"" + name + "\",\"" + word + "\");");
		} 
		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		finally {
			close();
		}

	}
	public static void deleteWordFromGroup (String word, String name) throws ClassNotFoundException, SQLException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("delete from TweetDB.groups where name = \"" + name + "\" AND word = \"" + word + "\";");
		} 
		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		finally {
			close();
		}
	}
	public static void deleteWholeGroup (String name) throws ClassNotFoundException, SQLException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("delete from TweetDB.groups where name = \"" + name + "\";");
		} 
		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		finally {
			close();
		}
	}
	public static ArrayList<String> getGroupNames () throws ClassNotFoundException, SQLException {

		ResultSet rs;
		ArrayList<String> names = new ArrayList<String>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select distinct name from TweetDB.groups;");

			while (rs.next()){
				names.add(rs.getString("name"));
			}
		} 

		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		finally {
			close();
		}
		return names;
	}
	public static ArrayList<String> getWordsInGroup (String name) throws ClassNotFoundException, SQLException {

		ResultSet rs;
		ArrayList<String> words = new ArrayList<String>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select word from TweetDB.groups where name = \"" + name + "\";");

			while (rs.next()){
				words.add(rs.getString("word"));
			}
		} 
		catch (ClassNotFoundException e) {
			throw(e);
		} 
		catch (SQLException e) {
			throw(e);
		}
		finally {
			close();
		}
		return words;
	}

}
