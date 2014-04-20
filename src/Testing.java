import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Testing extends DB_Connection {

	public static void readDataBase(String DB) throws Exception {	
		try {
			//loads the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");

			// Setup the connection with the DB
			connect = DriverManager.getConnection(connectionString);

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();

			// Result set get the result of the SQL query
			resultSet = statement.executeQuery("select * from TweetDB." + DB);

			printTableContent(resultSet);
		} 

		catch (Exception e) {
			throw (e);
		} 
		finally {
			close();
		}
	}
	public static void printMetaData(ResultSet resultSet) throws SQLException {
		//get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
			System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		}
	}
	public static void printTableContent(ResultSet resultSet) throws SQLException {

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1) +".");

		System.out.print("Row\\Param\t");
		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++)
			System.out.print("\t" + resultSet.getMetaData().getColumnName(i) + "\t");
		System.out.println("");

		while(resultSet.next()){
			System.out.println( resultSet.getRow() + "\t" +
					resultSet.getString("id") + "\t" + 
					resultSet.getString("users") + "\t" +
					resultSet.getString("createdAt") + "\t" +
					resultSet.getString("tweet"));
		}
	}
}
