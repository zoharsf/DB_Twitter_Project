import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Search extends DB_Connection {
	public static ArrayList<ArrayList<String>> searchByMetaData (String word, String dateStart, String dateEnd, String userID) throws ClassNotFoundException, SQLException{

		ResultSet results;
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>(); 

		getConnectionString();
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			
			//date,word,user => dwu
			//d--
			if (dateStart != null && dateEnd != null && word == null && userID == null){
				results = statement.executeQuery("select * from TweetDB.tweets where createdAt >= \"" + dateStart + "\" AND createdAt < \"" + dateEnd + "\"");
				table = convertRS2Arr(results);
			}
			//-w-
			if (dateStart == null && dateEnd == null && word != null && userID == null){
				results = statement.executeQuery("select * from TweetDB.tweets where tweet like \"%" + word + "%\"");
				table = convertRS2Arr(results);
			}
			//--u
			if (dateStart == null && dateEnd == null && word == null && userID != null){
				results = statement.executeQuery("select * from TweetDB.tweets where users = \"" + userID + "\"");
				table = convertRS2Arr(results);
			}
			//dw-
			if (dateStart != null && dateEnd != null && word != null && userID == null){
				results = statement.executeQuery("select * from TweetDB.tweets where createdAt >= \"" + dateStart + "\" AND createdAt < \"" + dateEnd + "\" AND tweet like \"%" + word + "%\"");
				table = convertRS2Arr(results);
			}
			//d-u
			if (dateStart != null && dateEnd != null && word == null && userID != null){
				results = statement.executeQuery("select * from TweetDB.tweets where createdAt >= \"" + dateStart + "\" AND createdAt < \"" + dateEnd + "\" AND users = \"" + userID + "\"");
				table = convertRS2Arr(results);
			}
			//-wu
			if (dateStart == null && dateEnd == null && word != null && userID != null){
				results = statement.executeQuery("select * from TweetDB.tweets where tweet like \"%" + word + "%\" AND users = \"" + userID + "\"");
				table = convertRS2Arr(results);
			}
			//dwu
			if (dateStart != null && dateEnd != null && word != null && userID != null){
				results = statement.executeQuery("select * from TweetDB.tweets where createdAt >= \"" + dateStart + "\" AND createdAt < \"" + dateEnd + "\" AND tweet like \"%" + word + "%\" AND users = \"" + userID + "\"");
				table = convertRS2Arr(results);
			}
			//---
			if (dateStart == null && dateEnd == null && word == null && userID == null){
				results = null;
				table = convertRS2Arr(results);
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
		return table;
	}
}
