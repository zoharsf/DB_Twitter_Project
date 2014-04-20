import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ReadText
//Reads text from a file, and prepares the data for DB use. 
{
	private String path;
	
	public ReadText(String filePath)
	//Constructor requires the location of the text file.
	{
		path = filePath;
	}
	
	public ArrayList<ArrayList<String>> openFile() throws IOException
	//Accesses the text file, and reads it into a String array.  
	{
		InputStreamReader reader = new InputStreamReader(new FileInputStream(path),"UTF-8");
		BufferedReader textReader = new BufferedReader(reader);
		
		ArrayList<String> rawText = new ArrayList<String>();//An array for the unprocessed text lines.
		ArrayList<ArrayList<String>> parsedText; //An array for the text after parsing.
		
		if(textReader.readLine()==null || textReader.readLine()==null) 
		//Empty file (Twitter files should contain at least 2 header lines, followed by a 3rd line with a tweet).
		//Regardless of the result, this skips the first 2 lines, which contain only a header.
		{
			textReader.close( );
			return null; 
		}

		String temp;
		while ((temp = textReader.readLine())!=null)
		{
			rawText.add(temp); //adds each line into the array.
		}
		
		textReader.close( );
		
		//Parse the 1-dimension array of lines, into a 2-dimension array which contains the various elements in each line.
		if (rawText.isEmpty())
			return null; //Empty file (no tweets after the 22 lines of header).
		else
		{
			parsedText = ParseText(rawText);
			return parsedText;
		}
	}
	
	public String ReformatDate(String origDate)
	//Reformats the date to accommodate DB requirements.
	//Original format: Fri Oct 11 22:40:47 IST 2013
    //Required format: 2013-10-11 22:40:47 (YYYY-MM-DD HH:MM:SS)
	{
        String newDate = "";
        String month = "";

        switch (origDate.substring(4,7))
        //The substring contains the month as found in the original format (3 letter word).
        //The switch matches the corresponding month in the new format (2 digit number).
        {

        case ("Jan"):
            month = "01";
        break;
        case ("Feb"):
            month = "02";
        break;
        case ("Mar"):
            month = "03";
        break;
        case ("Apr"):
            month = "04";
        break;
        case ("May"):
            month = "05";
        break;
        case ("Jun"):
            month = "06";
        break;
        case ("Jul"):
            month = "07";
        break;
        case ("Aug"):
            month = "08";
        break;
        case ("Sep"):
            month = "09";
        break;
        case ("Oct"):
            month = "10";
        break;
        case ("Nov"):
            month = "11";
        break;
        case ("Dec"):
            month = "12";
        break;
        default:
            month = "00";
        }

        //After finding the month, the rest of the date is rearranged to meet the format.
        newDate = (origDate.substring(24,28)
        		+ "-" + month
        		+ "-" + origDate.substring(8,10)
        		+ " " + origDate.substring(11,19));

        return newDate;
	}
	
	public ArrayList<ArrayList<String>> ParseText(ArrayList<String> text) throws StringIndexOutOfBoundsException
	//Parses a 1-dimension array of lines, into a 2-dimension array which contains the various elements in each line.
	{
		ArrayList<ArrayList<String>> parsed = new ArrayList<ArrayList<String>>();
		
		//Each part of a line is seperated by "::".
		String separator = "::";
		
		int cuttingPointA, cuttingPointB, cuttingPointC;
		
		int i=0; //location in original text
		
		for (i=0;i<text.size();i++)
		{		
			try
			{
				//Finds the 3 cutting points using the separator. 
				cuttingPointA = text.get(i).indexOf(separator);
				cuttingPointB = text.get(i).indexOf(separator,cuttingPointA+1);
				cuttingPointC = text.get(i).indexOf(separator,cuttingPointB+1);
				
				//Cuts into 4 substrings using the cutting points.
				ArrayList<String> row = new ArrayList<String>();
				row.add(text.get(i).substring(0,cuttingPointA));
				row.add(text.get(i).substring(cuttingPointA+2,cuttingPointB));
				row.add(ReformatDate(text.get(i).substring(cuttingPointB+2,cuttingPointC)));
				row.add(text.get(i).substring(cuttingPointC+2,text.get(i).length()));
				parsed.add(row);
			}
			catch (StringIndexOutOfBoundsException e)
			{
				if (parsed.size()!=0) 
				//Rows in improper format are assumed to belong to the previous tweet
				// (accidently separated by the textReader due to an end-of-line character).
				{
					parsed.get(parsed.size()-1).set(3,parsed.get(parsed.size()-1).get(3) + text.get(i));
				}
				else
					throw e; //If the improper format is found on the 1st line, the whole file cannot be read.
							// The exception is handled in UI by alerting the user.
				
			}
		}

		return parsed;
	}
}
