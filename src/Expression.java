import java.sql.DriverManager;
import java.sql.ResultSet;
//import java.sql.ResultSet; //not needed without 'searchForExpression'
//import java.util.ArrayList; //not needed without 'searchForExpression'
import java.sql.SQLException;
import java.util.ArrayList;


public class Expression extends DB_Connection{

	public static void addExpression (String expression) throws ClassNotFoundException, SQLException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("INSERT INTO TweetDB.expressions (expression) VALUES (\"" + expression + "\");");
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
	
	public static void deleteExpression (String expression) throws ClassNotFoundException, SQLException{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			statement.executeUpdate("delete from TweetDB.expressions where expression = \"" + expression + "\";");
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
	
	public static ArrayList<String> getExpressions () throws ClassNotFoundException, SQLException {

		ResultSet rs;
		ArrayList<String> expressions = new ArrayList<String>();

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection(connectionString);
			statement = connect.createStatement();
			rs = statement.executeQuery("select distinct expression from TweetDB.Expressions;");

			while (rs.next()){
				expressions.add(rs.getString("expression"));
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
		return expressions;
	}

}
