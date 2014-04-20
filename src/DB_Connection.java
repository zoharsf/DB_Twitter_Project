import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_Connection {

	protected static Connection connect = null;
	protected static Statement statement = null;
	protected static ResultSet resultSet = null;
	protected static String connectionString = null;
	protected static String defaultConnectionString = "jdbc:mysql://localhost/TweetDB?user=TwitterProject&password=SgfghbREs46B42!3P";
	

	protected static void getConnectionString() {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("connectionString.txt"));
			if ((connectionString = br.readLine()) == null){
				connectionString = defaultConnectionString;
			}
		} 
		catch (FileNotFoundException e) {
			connectionString = defaultConnectionString;
		}
		catch (IOException e) {
			connectionString = defaultConnectionString;		
		}
		finally {
			try {
				if (br != null)
					br.close();
			} 
			catch (IOException ex) {
			}
		}
	}

	//assisting method
	protected static ArrayList<ArrayList<String>> convertRS2Arr (ResultSet rs) throws SQLException{
		ArrayList<ArrayList<String>> table = new ArrayList<ArrayList<String>>();
		ArrayList<String> row;


		while (rs.next()){
			row = new ArrayList<String>();
			row.add(rs.getString("id"));
			row.add(rs.getString("users"));
			row.add(rs.getString("createdAt"));
			row.add(rs.getString("tweet"));
			table.add(row);
		}
		return table;
	}
	// close the resultSet
	protected static void close() {
		try {
			if (resultSet != null) 
				resultSet.close();
			if (statement != null) 
				statement.close();
			if (connect != null) 
				connect.close();
		} 
		catch (SQLException e) {
		}
	}
}
