import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


public class XML
{
	public static void exportXML(UI ptrToTextArea, File location)
	{
		long startTime = System.currentTimeMillis();
		
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element tweetsDB = doc.createElement("TweetDB");
			doc.appendChild(tweetsDB);
			int i;
			
			ArrayList<ArrayList<String>> arrayTableTweets = Backup.backupTweetTable();
			Element tweetTable = doc.createElement("Tweets");
			tweetsDB.appendChild(tweetTable);
			for (i=0;i<arrayTableTweets.size();i++)
			{
				Element tweet = doc.createElement("tweet");
				tweetTable.appendChild(tweet);
				
				Element tweetsID = doc.createElement("id");
				tweet.appendChild(tweetsID);
				tweetsID.appendChild(doc.createTextNode(arrayTableTweets.get(i).get(0)));
				
				Element tweetsUsers = doc.createElement("users");
				tweet.appendChild(tweetsUsers);
				tweetsUsers.appendChild(doc.createTextNode(arrayTableTweets.get(i).get(1)));
				
				Element tweetsDate = doc.createElement("createdAt");
				tweet.appendChild(tweetsDate);
				tweetsDate.appendChild(doc.createTextNode(arrayTableTweets.get(i).get(2)));
				
				Element tweetsText = doc.createElement("text");
				tweet.appendChild(tweetsText);
				tweetsText.appendChild(doc.createTextNode(arrayTableTweets.get(i).get(3)));
			}

			ArrayList<ArrayList<String>> arrayTablewords = Backup.backupWordsTable();
			Element wordTable = doc.createElement("Words");
			tweetsDB.appendChild(wordTable);
			for (i=0;i<arrayTablewords.size();i++)
			{
				Element word = doc.createElement("word");
				wordTable.appendChild(word);
				
				Element wordsWord = doc.createElement("word");
				word.appendChild(wordsWord);
				wordsWord.appendChild(doc.createTextNode(arrayTablewords.get(i).get(0)));
				
				Element tweetsID = doc.createElement("tweetID");
				word.appendChild(tweetsID);
				tweetsID.appendChild(doc.createTextNode(arrayTablewords.get(i).get(1)));
			}
			
			ArrayList<ArrayList<String>> arrayTableUsers = Backup.backupUsersTable();
			Element userTable = doc.createElement("Users");
			tweetsDB.appendChild(userTable);
			for (i=0;i<arrayTableUsers.size();i++)
			{
				Element user = doc.createElement("user");
				userTable.appendChild(user);
				user.appendChild(doc.createTextNode(arrayTableUsers.get(i).get(0)));
			}
			
			
			ArrayList<ArrayList<String>> arrayTableGroups = Backup.backupGroupsTable();
			Element groupTable = doc.createElement("Groups");
			tweetsDB.appendChild(groupTable);
			for (i=0;i<arrayTableGroups.size();i++)
			{
				Element group = doc.createElement("group");
				groupTable.appendChild(group);
				
				Element groupsName = doc.createElement("name");
				group.appendChild(groupsName);
				groupsName.appendChild(doc.createTextNode(arrayTableGroups.get(i).get(0)));
				
				Element groupsWord = doc.createElement("word");
				group.appendChild(groupsWord);
				groupsWord.appendChild(doc.createTextNode(arrayTableGroups.get(i).get(1)));
			}
			
			
			ArrayList<ArrayList<String>> arrayTableRelations = Backup.backupRelationsTable();
			Element relationTable = doc.createElement("Relation");
			tweetsDB.appendChild(relationTable);
			for (i=0;i<arrayTableRelations.size();i++)
			{
				Element relation = doc.createElement("relation");
				relationTable.appendChild(relation);
				
				Element relationsTablekey = doc.createElement("tablekey");
				relation.appendChild(relationsTablekey);
				relationsTablekey.appendChild(doc.createTextNode(arrayTableRelations.get(i).get(0)));
				
				Element relationsName = doc.createElement("name");
				relation.appendChild(relationsName);
				relationsName.appendChild(doc.createTextNode(arrayTableRelations.get(i).get(1)));
				
				Element relationsWord1 = doc.createElement("word1");
				relation.appendChild(relationsWord1);
				relationsWord1.appendChild(doc.createTextNode(arrayTableRelations.get(i).get(2)));
				
				Element relationsWord2 = doc.createElement("word2");
				relation.appendChild(relationsWord2);
				relationsWord2.appendChild(doc.createTextNode(arrayTableRelations.get(i).get(3)));
			}
			
			
			ArrayList<ArrayList<String>> arrayTableExpressions = Backup.backupExpressionsTable();
			Element expressionTable = doc.createElement("Expression");
			tweetsDB.appendChild(expressionTable);
			for (i=0;i<arrayTableExpressions.size();i++)
			{
				Element expression = doc.createElement("expression");
				expressionTable.appendChild(expression);
				expression.appendChild(doc.createTextNode(arrayTableExpressions.get(i).get(0)));
			}
			
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(location);
	 
			transformer.transform(source, result);
	 
			ptrToTextArea.addToTextArea("\nFile saved.");
			ptrToTextArea.addToTextArea("\nTime elapsed: "+((System.currentTimeMillis()-startTime)/1000)+" seconds.\n");
		}
		catch (Exception e)
		{
			ptrToTextArea.addToTextArea("\nCannot save data to XML."+e);
		}
	}
	
	public static void importXML(UI ptrToTextArea, File location)
	{
		long startTime = System.currentTimeMillis();
		
		try
		{
			ptrToTextArea.addToTextArea("\n\nAccessing XML file...");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(location);
			doc.getDocumentElement().normalize();
			NodeList allTables = doc.getChildNodes().item(0).getChildNodes();

			int i;
	
			try
			{
				ArrayList<ArrayList<String>> arrayTableTweets = new ArrayList<ArrayList<String>>();
				NodeList tableTweets = allTables.item(0).getChildNodes();
				for (i=0;i<tableTweets.getLength();i++)
				{
					Node node = tableTweets.item(i);
					
					if (node.getNodeType()==Node.ELEMENT_NODE)
					{
						Element eElement = (Element)node;
						ArrayList<String> row = new ArrayList<String>();
			 
						row.add(eElement.getElementsByTagName("id").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("users").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("createdAt").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("text").item(0).getTextContent());
						arrayTableTweets.add(row);
					}
				}
				Restore.restoreTweetTable(arrayTableTweets);
				ptrToTextArea.addToTextArea("\nRestored the Tweets/Words/Users tables.");
			}
			catch (Exception e)
			{
				ptrToTextArea.addToTextArea("\nCannot restore the Tweet/Words/Users tables.");
			}
			
			try
			{
				ArrayList<ArrayList<String>> arrayTableGroups = new ArrayList<ArrayList<String>>();
				NodeList tableGroups = allTables.item(3).getChildNodes();
				for (i=0;i<tableGroups.getLength();i++)
				{
					Node node = tableGroups.item(i);
					
					if (node.getNodeType()==Node.ELEMENT_NODE)
					{
						Element eElement = (Element)node;
						ArrayList<String> row = new ArrayList<String>();
			 
						row.add(eElement.getElementsByTagName("name").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("word").item(0).getTextContent());
						arrayTableGroups.add(row);
					}
				}
				Restore.restoreGroupsTable(arrayTableGroups);
				ptrToTextArea.addToTextArea("\nRestored the Groups table.");
			}
			catch (Exception e)
			{
				ptrToTextArea.addToTextArea("\nCannot restore the Groups table.");
			}
				
			try
			{
				ArrayList<ArrayList<String>> arrayTableRelations = new ArrayList<ArrayList<String>>();
				NodeList tableRelations = allTables.item(4).getChildNodes();
				for (i=0;i<tableRelations.getLength();i++)
				{
					Node node = tableRelations.item(i);
					
					if (node.getNodeType()==Node.ELEMENT_NODE)
					{
						Element eElement = (Element)node;
						ArrayList<String> row = new ArrayList<String>();
			 
						row.add(eElement.getElementsByTagName("tablekey").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("name").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("word1").item(0).getTextContent());
						row.add(eElement.getElementsByTagName("word2").item(0).getTextContent());
						arrayTableRelations.add(row);
					}
				}
				Restore.restoreRelationsTable(arrayTableRelations);
				ptrToTextArea.addToTextArea("\nRestored the Relations table.");
			}
			catch (Exception e)
			{
				ptrToTextArea.addToTextArea("\nCannot restore the Relations table.");
			}
				
			try
			{
				ArrayList<ArrayList<String>> arrayTableExpressions = new ArrayList<ArrayList<String>>();
				NodeList tableExpressions = allTables.item(5).getChildNodes();
				for (i=0;i<tableExpressions.getLength();i++)
				{
					Node node = tableExpressions.item(i);
					
					if (node.getNodeType()==Node.ELEMENT_NODE)
					{
						ArrayList<String> row = new ArrayList<String>();
						row.add(node.getTextContent());
						arrayTableExpressions.add(row);
					}
				}
				Restore.restoreExpressionsTable(arrayTableExpressions);
				ptrToTextArea.addToTextArea("\nRestored the Expressions table.");
				ptrToTextArea.addToTextArea("\nTime elapsed: "+((System.currentTimeMillis()-startTime)/1000)+" seconds.\n");
			}
			catch (Exception e)
			{
				ptrToTextArea.addToTextArea("\nCannot restore the Expressions table.");
			}
		}
		catch (Exception e)
		{
			ptrToTextArea.addToTextArea("\nCannot access XML file, import aborted.");
		}
	}
}