import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Insert extends DB_Connection {

	//inserting methods
	public static void addTweetToDB(String tweetid, String user, String createdAt, String tweet) throws MySQLIntegrityConstraintViolationException, SQLException{

		getConnectionString();
		
		try{
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate ("INSERT INTO TweetDB.Tweets VALUES (\"" + tweetid + "\",\"" + user + "\",\"" + createdAt + "\",\"" + tweet.replaceAll("\"","\\\\\"") + "\")");
		
			addWordsToWordTable(tweetid, tweet.replaceAll("\"","\\\\\""));
			addUsersToUserTable(user);
		}
		catch (MySQLIntegrityConstraintViolationException e){
			throw e;
		} 
		catch (SQLException e) {
			throw e;
		} 
		finally {
			close();
		}
	}
	public static void addWordsToWordTable(String id, String tweet) throws SQLException{

		try {
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();

			String words[] = tweet.split(" ");
			for (int i=0; i<words.length; i++){
				if(words[i].length()!=0 && words[i].length()<=45){					
					try{
						statement.executeUpdate ("INSERT INTO TweetDB.words (word,tweetId) VALUES(\"" + words[i] + "\"," + id + ");");
					}
					catch (MySQLIntegrityConstraintViolationException e){
						//Word appears twice in the same tweet, no handling needed.
					}
				}
			}
		}
		catch (SQLException e) {
			throw e;
		} 
		finally {
			close();
		}
	}
	public static void addUsersToUserTable(String user) throws SQLException{

		try {
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate ("INSERT INTO TweetDB.users VALUES (\"" + user + "\");");
		}

		catch (MySQLIntegrityConstraintViolationException e){
			//Duplicate user wasn't added to table, no handling needed.
		} 
		catch (SQLException e){
			throw(e);
		}
		finally {
			close();
		}
	}
}
