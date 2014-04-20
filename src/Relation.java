import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Relation extends DB_Connection {

	public static void addWordsToRelation (String relation, String word1, String word2) throws ClassNotFoundException, SQLException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			String key = createKey(relation, word1, word2);
			statement.executeUpdate("INSERT INTO TweetDB.relations (tablekey, name, word1, word2) VALUES (\""+key+"\",\""+relation+"\",\""+word1+"\",\""+word2+"\");");
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
	public static void deleteRelation (String name) throws ClassNotFoundException, SQLException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("delete from TweetDB.relations where name = \"" + name + "\";");
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
	public static void deleteWordsFromRelation (String name, String word1, String word2) throws ClassNotFoundException, SQLException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("delete from TweetDB.relations where tablekey = \"" + createKey(name,word1,word2) + "\";");
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
	public static ArrayList<String> getAllRelations() throws ClassNotFoundException, SQLException{

		ResultSet rs;
		ArrayList<String> names = new ArrayList<String>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select distinct name from TweetDB.relations;");

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
	public static ArrayList<ArrayList<String>> getAllWordsInRelation(String name) throws ClassNotFoundException, SQLException{

		ResultSet rs;
		ArrayList<String> row;
		ArrayList<ArrayList<String>> words = new ArrayList<ArrayList<String>>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select name, word1, word2 from TweetDB.relations where relations.name = \"" + name + "\";");

			while (rs.next()){
				row = new ArrayList<String>();
				row.add(rs.getString("word1"));
				row.add(rs.getString("word2"));
				words.add(row);
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

	private static String createKey (String name, String word1, String word2){
		if(word1.compareTo(word2) <= 0){
			return name+word1+word2;
		}
		return name+word2+word1;
	}

}
