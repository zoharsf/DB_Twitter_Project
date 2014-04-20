import java.util.ArrayList;

public class Restore extends DB_Connection{

	public static String restoreExpressionsTable (ArrayList<ArrayList<String>> backup){

		int successCounter = 0;
		int failCounter = 0;
		
		for (ArrayList<String> row : backup){
			try{
				Expression.addExpression(row.get(0));
				successCounter++;
			}
			catch (Exception e){
				failCounter++;
			}
		}
		
		return "Added "+successCounter+" expressions successfully, "+failCounter+" errors.";
	}
	
	public static String restoreGroupsTable (ArrayList<ArrayList<String>> backup){

		int successCounter = 0;
		int failCounter = 0;
		
		for (ArrayList<String> row : backup){
			try{
				Group.addWordToGroup(row.get(1), row.get(0));
				successCounter++;
			}
			catch (Exception e){
				failCounter++;
			}

		}
		
		return "Added "+successCounter+" words to groups, "+failCounter+" errors.";
	}
	
	public static String restoreRelationsTable (ArrayList<ArrayList<String>> backup){

		int successCounter = 0;
		int failCounter = 0;
		
		for (ArrayList<String> row : backup){
			try{
				Relation.addWordsToRelation(row.get(1), row.get(2),row.get(3));
				successCounter++;
			}
			catch (Exception e){
				failCounter++;
			}
		}
		
		return "Added "+successCounter+" word couples to relations, "+failCounter+" errors.";
	}
	
	public static String restoreTweetTable (ArrayList<ArrayList<String>> backup){

		int successCounter = 0;
		int failCounter = 0;
		
		for (ArrayList<String> row : backup){
			try{
				Insert.addTweetToDB(row.get(0),row.get(1), row.get(2),row.get(3));
				successCounter++;
			}
			catch (Exception e){
				failCounter++;
			}
		}
		
		return "Added "+successCounter+" tweets successfully, "+failCounter+" errors.";
	}
}
