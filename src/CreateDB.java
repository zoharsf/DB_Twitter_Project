import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateDB extends DB_Connection{

	private static String drop = "Drop database TweetDB;";
	private static String db = "Create database TweetDB;";
	private static String tweets = "CREATE table TweetDB.Tweets(id char (18) NOT NULL primary key,users char(32) NOT NULL,createdAt datetime,tweet char(140) NOT NULL);";
	private static String words = "CREATE table TweetDB.Words(word char(140) NOT NULL,tweetId char (18) NOT NULL references Tweets (id),primary key(word, tweetId));";
	private static String users = "CREATE table TweetDB.Users(user char(32) NOT NULL primary key references Tweets (users));";
	private static String groups = "CREATE table TweetDB.Groups(name char(20) NOT NULL,word char(140) NOT NULL references Words (word),primary key(name, word));";
	private static String relations = "CREATE table TweetDB.Relations(tablekey char(110) not null primary key,name char(20) NOT NULL,word1 char(45) NOT NULL references Words (word),word2 char(45) NOT NULL references Words (word));";
	private static String expressions = "CREATE table TweetDB.Expressions(expression char(45) NOT NULL primary key);";
	
	
	public static void clearDB(UI ptrToTextArea){

		long startTime = System.currentTimeMillis();
		
		getConnectionString();
		
		try{			
			ptrToTextArea.addToTextArea("\nConnecting...");
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();

			ptrToTextArea.addToTextArea("\nClearing old DB entries...");
			try{
				statement.executeUpdate(drop);
			}
			catch(SQLException e){} //Nothing to drop, the DB was empty (first time running project).
			
			ptrToTextArea.addToTextArea("\nCreating tables...");
			statement.executeUpdate(db);
			statement.executeUpdate(tweets);
			statement.executeUpdate(words);
			statement.executeUpdate(users);
			statement.executeUpdate(groups);
			statement.executeUpdate(relations);
			statement.executeUpdate(expressions);
			
			ptrToTextArea.addToTextArea("\nDatabase ready.");
			ptrToTextArea.addToTextArea("\nTime elapsed: "+((System.currentTimeMillis()-startTime)/1000)+" seconds.\n\n");
		} 
		catch (Exception e) {
			ptrToTextArea.addToTextArea("\nCould not complete DB startup. Please check connection and try again.");
		}
	}
	
	public static void initializeDB(UI ptrToTextArea){
		
		getConnectionString();

		try{			
			ptrToTextArea.addToTextArea("\nConnecting...");
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();

			ptrToTextArea.addToTextArea("\nVerifying DB exists...");
			try{
				statement.executeUpdate(db);
			}
			catch(SQLException e){}
			
			ptrToTextArea.addToTextArea("\nVerifying tables exist...");
			try{
				statement.executeUpdate(tweets);
			}
			catch(SQLException e){}
			try{
				statement.executeUpdate(words);
			}
			catch(SQLException e){}
			try{
				statement.executeUpdate(users);
			}
			catch(SQLException e){}
			try{
				statement.executeUpdate(groups);
			}
			catch(SQLException e){}
			try{
				statement.executeUpdate(relations);
			}
			catch(SQLException e){}
			try{
				statement.executeUpdate(expressions);
			}
			catch(SQLException e){}
			
			ptrToTextArea.addToTextArea("\nDatabase ready.\n\n");
		} 
		catch (Exception e) {
			ptrToTextArea.addToTextArea("\nCould not complete DB startup. Please check connection and try again.");
		}
	}
}
