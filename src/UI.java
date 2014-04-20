import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class UI extends JFrame implements ActionListener
//Provides a user interface for accessing the DB.
{
    private static final long serialVersionUID = 1L; //Prevents an irrelevant warning.
    
    private JButton buttonSelectFile;
	private JFileChooser chooser;
	private JTextArea textDisplayPath;
	private JButton buttonLoadFile;	
	
    private JTextArea textArea;
	private JScrollPane scroll;
	
	private JButton clearDB;
	private JButton exportDB;
	private JButton importDB;
	
	private JLabel labelSearch;
	private JCheckBox checkSearchByWord;
	private JTextArea textSearchByWord;
	private JButton buttonPasteSearchByWord;
	private JCheckBox checkSearchByDate;
	private DateSelector dateSlctr;
	private JTextArea textStartSearchByDate;
	private JLabel labelArrow;
	private JTextArea textEndSearchByDate;
	private JPanel panelSearchByDate;
	private JCheckBox checkSearchByUser;
	private JTextArea textSearchByUser;
	private JButton buttonPasteSearchByUser;
	private JButton buttonSearch;
	private JButton buttonClearSearch;
	private JPanel panelSearchButtons;
	
	private JLabel labelGroups;
	private JRadioButton buttonNewGroup;	
	private JTextArea textNewGroup;
	private JRadioButton buttonExistingGroup;	
	private JComboBox<String> selectExistingGroup;
	private ButtonGroup groupRadioButtons;
	private JCheckBox checkAddWordToGroup;
	private JTextArea textAddWordToGroup;
	private JButton buttonPasteAddWordToGroup;
	private JButton buttonAddWordToGroup;
	private JButton buttonRemoveWordFromGroup;
	private JButton buttonDeleteGroup;
	private JButton buttonDisplayGroup;
	private JPanel panelGroupButtons;

	private JLabel labelExpressions;
	private JRadioButton buttonNewExpression;
	private JTextArea textNewExpression;
	private JButton buttonPasteNewExpression;
	private JRadioButton buttonExistingExpression;	
	private JComboBox<String> selectExistingExpression;
	private ButtonGroup expressionRadioButtons;
	private JButton buttonAddExpression;	
	private JButton buttonDeleteExpression;
	private JButton buttonSearchExpression;
	private JPanel panelExpressionButtons;

	
	private JLabel labelRelations;
	private JRadioButton buttonNewRelation;	
	private JTextArea textNewRelation;
	private JRadioButton buttonExistingRelation;	
	private JComboBox<String> selectExistingRelation;
	private ButtonGroup relationRadioButtons;
	private JCheckBox checkAddWord1ToRelation;
	private JTextArea textAddWord1ToRelation;
	private JButton buttonPasteAddWord1ToRelation;
	private JCheckBox checkAddWord2ToRelation;
	private JTextArea textAddWord2ToRelation;
	private JButton buttonPasteAddWord2ToRelation;
	private JButton buttonAddWordsToRelation;
	private JButton buttonRemoveWordsFromRelation;
	private JButton buttonDeleteRelation;
	private JButton buttonDisplayRelation;
	private JPanel panelRelationButtons;

	private enum Source {WORD,USER,EXPRESSION,GROUP,RELATION};
	
	public UI()
	{
		super("DB user interface");

		createInstancesUI();
		layoutUI();
		listenersUI();		
		
		//Clear DB and create empty tables. 
		CreateDB.initializeDB(this);
	}
	
	private void createInstancesUI()
	{
		int SIZE = 23;
		
		buttonSelectFile = new JButton("Select text file");
		chooser = new JFileChooser();
		textDisplayPath = new JTextArea(1,SIZE);
			textDisplayPath.setPreferredSize(new Dimension(1,SIZE));
		buttonLoadFile = new JButton("Load text file");
		
		textArea = new JTextArea(15,45);
		scroll = new JScrollPane(textArea);
		clearDB = new JButton("Clear DB");
			clearDB.setPreferredSize(new Dimension(100,25));
		exportDB = new JButton("Export DB");
			exportDB.setPreferredSize(new Dimension(100,25));
		importDB = new JButton("Import DB");
			importDB.setPreferredSize(new Dimension(100,25));
		
		Icon iconPaste = new ImageIcon(System.getProperty("user.dir")+"/Paste.png");
		//Icon image must be in the working directory.
		
		labelSearch = new JLabel("Search by:");
		checkSearchByWord = new JCheckBox("Word");
		textSearchByWord = new JTextArea(1,SIZE);
			textSearchByWord.setEditable(false);
			textSearchByWord.setPreferredSize(new Dimension(1,SIZE));
		buttonPasteSearchByWord = new JButton(iconPaste);
			buttonPasteSearchByWord.setPreferredSize(new Dimension(20,20));
		checkSearchByDate = new JCheckBox("Date");
		textStartSearchByDate = new JTextArea(1,10);
			textStartSearchByDate.setEditable(false);
			textStartSearchByDate.setPreferredSize(new Dimension(1,10));
		labelArrow = new JLabel("-->");
		textEndSearchByDate = new JTextArea(1,10);
			textEndSearchByDate.setEditable(false);
			textEndSearchByDate.setPreferredSize(new Dimension(1,10));
		panelSearchByDate = new JPanel();
			panelSearchByDate.setPreferredSize(new Dimension(250,30));
			//panelSearchByDate.setBorder(BorderFactory.createLineBorder(Color.black));
			GridBagLayout gridbagSearchByDate = new GridBagLayout();
			panelSearchByDate.setLayout(gridbagSearchByDate);
			GridBagConstraints cnst = new GridBagConstraints();
			cnst.gridx = 1;
			panelSearchByDate.add(textStartSearchByDate,cnst);
			cnst.gridx = 2;
			panelSearchByDate.add(labelArrow,cnst);
			cnst.gridx = 3;
			panelSearchByDate.add(textEndSearchByDate,cnst);
		checkSearchByUser = new JCheckBox("User");
		textSearchByUser = new JTextArea(1,SIZE);
			textSearchByUser.setEditable(false);
			textSearchByUser.setPreferredSize(new Dimension(1,SIZE));
		buttonPasteSearchByUser = new JButton(iconPaste);
			buttonPasteSearchByUser.setPreferredSize(new Dimension(20,20));
		buttonSearch = new JButton("Search");
		buttonClearSearch = new JButton("Clear search");
		panelSearchButtons = new JPanel();
			panelSearchButtons.add(buttonSearch);
			panelSearchButtons.add(buttonClearSearch);
		
		labelGroups = new JLabel("Groups:");
		buttonNewGroup = new JRadioButton("New Group");
		textNewGroup = new JTextArea(1,SIZE);
			textNewGroup.setEditable(false);
			textNewGroup.setPreferredSize(new Dimension(1,SIZE));
		buttonExistingGroup = new JRadioButton("Existing Group");
		selectExistingGroup = new JComboBox<String>();
			selectExistingGroup.setMaximumRowCount(10);
			selectExistingGroup.setEditable(false);
			selectExistingGroup.addItem(""); //blank item is the default selection.
			selectExistingGroup.setEnabled(false); //can't select from existing groups until the button is selected.
		groupRadioButtons = new ButtonGroup();
			groupRadioButtons.add(buttonNewGroup);
			groupRadioButtons.add(buttonExistingGroup);
		checkAddWordToGroup = new JCheckBox("Word");
		textAddWordToGroup = new JTextArea(1,SIZE);
			textAddWordToGroup.setEditable(false);
			textAddWordToGroup.setPreferredSize(new Dimension(1,SIZE));
		buttonPasteAddWordToGroup = new JButton(iconPaste);
			buttonPasteAddWordToGroup.setPreferredSize(new Dimension(20,20));
		buttonAddWordToGroup = new JButton("Add word");
		buttonRemoveWordFromGroup = new JButton("Remove word");
		buttonDeleteGroup = new JButton("Delete group");
		buttonDisplayGroup = new JButton("Display group");
		panelGroupButtons = new JPanel();
			GridLayout gridGroupButtons = new GridLayout(0,2);
			gridGroupButtons.setHgap(5);
			gridGroupButtons.setVgap(5);
			panelGroupButtons.setLayout(gridGroupButtons);
			panelGroupButtons.add(buttonAddWordToGroup);
			panelGroupButtons.add(buttonRemoveWordFromGroup);
			panelGroupButtons.add(buttonDeleteGroup);
			panelGroupButtons.add(buttonDisplayGroup);
		
		labelExpressions = new JLabel("Expressions:");
		buttonNewExpression = new JRadioButton("New Expression");
		textNewExpression = new JTextArea(1,SIZE);
			textNewExpression.setEditable(false);
			textNewExpression.setPreferredSize(new Dimension(1,SIZE));
		buttonPasteNewExpression = new JButton(iconPaste);
			buttonPasteNewExpression.setPreferredSize(new Dimension(20,20));
		buttonExistingExpression = new JRadioButton("Existing Expression");
		selectExistingExpression = new JComboBox<String>();
			selectExistingExpression.setMaximumSize(new Dimension(250,30));
			selectExistingExpression.setMaximumRowCount(10);
			selectExistingExpression.setEditable(false);
			selectExistingExpression.addItem(""); //blank item is the default selection.
			selectExistingExpression.setEnabled(false); //can't select from existing groups until the button is selected.
		expressionRadioButtons = new ButtonGroup();
			expressionRadioButtons.add(buttonNewExpression);
			expressionRadioButtons.add(buttonExistingExpression);
		buttonAddExpression = new JButton("Add Expr.");
		buttonDeleteExpression = new JButton("Delete Expr.");
		buttonSearchExpression = new JButton("Search Expr.");
		panelExpressionButtons = new JPanel();
			panelExpressionButtons.add(buttonAddExpression);
			panelExpressionButtons.add(buttonDeleteExpression);
			panelExpressionButtons.add(buttonSearchExpression);
		
		labelRelations = new JLabel("Relations:");
		buttonNewRelation = new JRadioButton("New Relation");
		textNewRelation = new JTextArea(1,SIZE);
			textNewRelation.setEditable(false);
			textNewRelation.setPreferredSize(new Dimension(1,SIZE));
		buttonExistingRelation = new JRadioButton("Existing Relation");
		selectExistingRelation = new JComboBox<String>();
			selectExistingRelation.setMaximumRowCount(10);
			selectExistingRelation.setEditable(false);
			selectExistingRelation.addItem(""); //blank item is the default selection.
			selectExistingRelation.setEnabled(false); //can't select from existing relations until the button is selected.
		relationRadioButtons = new ButtonGroup();
			relationRadioButtons.add(buttonNewRelation);
			relationRadioButtons.add(buttonExistingRelation);
		checkAddWord1ToRelation = new JCheckBox("Word1");
		textAddWord1ToRelation = new JTextArea(1,SIZE);
			textAddWord1ToRelation.setEditable(false);
			textAddWord1ToRelation.setPreferredSize(new Dimension(1,SIZE));
		buttonPasteAddWord1ToRelation = new JButton(iconPaste);
			buttonPasteAddWord1ToRelation.setPreferredSize(new Dimension(20,20));
		checkAddWord2ToRelation = new JCheckBox("Word2");
		textAddWord2ToRelation = new JTextArea(1,SIZE);
			textAddWord2ToRelation.setEditable(false);
			textAddWord2ToRelation.setPreferredSize(new Dimension(1,SIZE));
		buttonPasteAddWord2ToRelation = new JButton(iconPaste);
			buttonPasteAddWord2ToRelation.setPreferredSize(new Dimension(20,20));
		buttonAddWordsToRelation = new JButton("Add words");
		buttonRemoveWordsFromRelation = new JButton("Remove words");
		buttonDeleteRelation = new JButton("Delete relation");
		buttonDisplayRelation = new JButton("Display relation");
		panelRelationButtons = new JPanel();
			GridLayout gridRelationButtons = new GridLayout(0,2);
			gridRelationButtons.setHgap(5);
			gridRelationButtons.setVgap(5);
			panelRelationButtons.setLayout(gridRelationButtons);
			panelRelationButtons.add(buttonAddWordsToRelation);
			panelRelationButtons.add(buttonRemoveWordsFromRelation);
			panelRelationButtons.add(buttonDeleteRelation);
			panelRelationButtons.add(buttonDisplayRelation);
	}
	
	private void layoutUI()
	{
		//UI frame layout
		setSize(1000,800);
		setVisible(true);
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		GridBagConstraints c = new GridBagConstraints();

		//Loading and main display.
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 0;
		add(buttonSelectFile,c);

		c.gridwidth = 3;
		c.gridx = 1;
		c.gridy = 0;
		add(textDisplayPath,c);
		
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 3;
		c.gridy = 1;
		add(buttonLoadFile,c);

		c.anchor = GridBagConstraints.LINE_START;
		c.gridheight = 6;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		add(scroll,c);
		
		c.gridheight = 1;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_END;
		c.gridx = 5;
		c.gridy = 0;
		add(clearDB,c);
		
		c.gridx = 5;
		c.gridy = 1;
		add(exportDB,c);
		
		c.gridx = 5;
		c.gridy = 2;
		add(importDB,c);
		
		//Searching
		
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 9;	
		add(labelSearch,c);
		
		c.gridx = 0;
		c.gridy = 10;	
		add(checkSearchByWord,c);
		
		c.gridx = 1;
		c.gridy = 10;	
		add(textSearchByWord,c);
		
		c.gridx = 2;
		c.gridy = 10;
		add(buttonPasteSearchByWord,c);
	
		c.gridx = 0;
		c.gridy = 11;	
		add(checkSearchByDate,c);
		
		c.gridx = 1;
		c.gridy = 11;	
		add(panelSearchByDate,c);
		
		c.gridx = 0;
		c.gridy = 12;
		add(checkSearchByUser,c);
		
		c.gridx = 1;
		c.gridy = 12;	
		add(textSearchByUser,c);
		
		c.gridx = 2;
		c.gridy = 12;
		add(buttonPasteSearchByUser,c);
		
		c.gridx = 1;
		c.gridy = 13;
		add(panelSearchButtons,c);
		
		//Groups
		c.gridx = 0;
		c.gridy = 14;	
		add(labelGroups,c);
		
		c.gridx = 0;
		c.gridy = 15;	
		add(buttonNewGroup,c);

		c.gridx = 1;
		c.gridy = 15;	
		add(textNewGroup,c);
		
		c.gridx = 0;
		c.gridy = 16;	
		add(buttonExistingGroup,c);
		
		c.gridx = 1;
		c.gridy = 16;	
		add(selectExistingGroup,c);
		
		c.gridx = 0;
		c.gridy = 17;	
		add(checkAddWordToGroup,c);
		
		c.gridx = 1;
		c.gridy = 17;	
		add(textAddWordToGroup,c);
		
		c.gridx = 2;
		c.gridy = 17;
		add(buttonPasteAddWordToGroup,c);
		
		c.gridx = 1;
		c.gridy = 19;	
		c.gridwidth = 2;
		add(panelGroupButtons,c);
		
		//Expressions		
		c.gridx = 4;
		c.gridy = 9;
		c.gridwidth = 1;
		add(labelExpressions,c);		
		
		c.gridx = 4;
		c.gridy = 10;	
		add(buttonNewExpression,c);
		
		c.gridx = 5;
		c.gridy = 10;	
		add(textNewExpression,c);
		
		c.gridx = 6;
		c.gridy = 10;
		add(buttonPasteNewExpression,c);
		
		c.gridx = 4;
		c.gridy = 11;
		add(buttonExistingExpression,c);
		
		c.gridx = 5;
		c.gridy = 11;
		add(selectExistingExpression,c);
		
		c.gridx = 4;
		c.gridy = 12;
		c.gridwidth = 2;
		add(panelExpressionButtons,c);

		//Relations		
		c.gridx = 4;
		c.gridy = 14;
		c.gridwidth = 1;
		add(labelRelations,c);		
		
		c.gridx = 4;
		c.gridy = 15;	
		add(buttonNewRelation,c);	
		
		c.gridx = 5;
		c.gridy = 15;	
		add(textNewRelation,c);	
		
		c.gridx = 4;
		c.gridy = 16;	
		add(buttonExistingRelation,c);	
		
		c.gridx = 5;
		c.gridy = 16;	
		add(selectExistingRelation,c);	
		
		c.gridx = 4;
		c.gridy = 17;	
		add(checkAddWord1ToRelation,c);	
		
		c.gridx = 5;
		c.gridy = 17;	
		add(textAddWord1ToRelation,c);
		
		c.gridx = 6;
		c.gridy = 17;
		add(buttonPasteAddWord1ToRelation,c);
		
		c.gridx = 4;
		c.gridy = 18;	
		add(checkAddWord2ToRelation,c);	
		
		c.gridx = 5;
		c.gridy = 18;	
		add(textAddWord2ToRelation,c);
		
		c.gridx = 6;
		c.gridy = 18;
		add(buttonPasteAddWord2ToRelation,c);
		
		c.gridx = 5;
		c.gridy = 19;	
		c.gridwidth = 2;
		add(panelRelationButtons,c);	
	}
	
	private void listenersUI()
	//Listeners for the various buttons.
	{
		buttonLoadFile.addActionListener(this);
		buttonSelectFile.addActionListener(this);
		buttonSearch.addActionListener(this);
		checkSearchByUser.addActionListener(this);
		checkSearchByDate.addActionListener(this);
		checkSearchByWord.addActionListener(this);
		buttonNewGroup.addActionListener(this);
		buttonExistingGroup.addActionListener(this);
		checkAddWordToGroup.addActionListener(this);
		buttonAddWordToGroup.addActionListener(this);
		buttonDeleteGroup.addActionListener(this);
		buttonDisplayGroup.addActionListener(this);
		buttonNewRelation.addActionListener(this);
		buttonExistingRelation.addActionListener(this);
		checkAddWord1ToRelation.addActionListener(this);
		checkAddWord2ToRelation.addActionListener(this);
		buttonAddWordsToRelation.addActionListener(this);
		buttonDeleteRelation.addActionListener(this);
		buttonNewExpression.addActionListener(this);
		buttonAddExpression.addActionListener(this);
		buttonDisplayRelation.addActionListener(this);
		buttonPasteSearchByWord.addActionListener(this);
		buttonPasteSearchByUser.addActionListener(this);
		buttonPasteAddWordToGroup.addActionListener(this);
		buttonPasteNewExpression.addActionListener(this);
		buttonPasteAddWord1ToRelation.addActionListener(this);
		buttonPasteAddWord2ToRelation.addActionListener(this);
		buttonClearSearch.addActionListener(this);
		buttonExistingExpression.addActionListener(this);
		selectExistingExpression.addActionListener(this);
		buttonDeleteExpression.addActionListener(this);
		buttonSearchExpression.addActionListener(this);
		buttonRemoveWordFromGroup.addActionListener(this);
		buttonRemoveWordsFromRelation.addActionListener(this);
		clearDB.addActionListener(this);
		exportDB.addActionListener(this);
		importDB.addActionListener(this);
	}
	
	public void actionPerformed (ActionEvent event)
	//Actions to be taken based on the listener which was triggered.
	{
		if(event.getSource()==buttonSelectFile)
		//The Select file button opens a file choosing dialog.
		{
			chooser.setSelectedFile(null);
			chooser.setFileFilter(new FileNameExtensionFilter("Text file","txt"));
			chooser.showDialog(this, "Select");
			if (chooser.getSelectedFile()!=null)
				textDisplayPath.setText(chooser.getSelectedFile().toString());
		}
				
		if(event.getSource()==buttonLoadFile)
		//The load file button uses ReadText to get data from the text file. 
		{
			long startTime = System.currentTimeMillis();
			ArrayList<ArrayList<String>> parsedText=null;

			if (textDisplayPath.getText().length()==0)
			{
				JOptionPane.showMessageDialog(null,"You have not selected a file to load.");
				return;
			}
			
			String path = textDisplayPath.getText();
			
			try
			{
				ReadText file = new ReadText(path);
				parsedText = file.openFile();
				
				if (parsedText==null)
				//The user is alerted if the file is empty.
					JOptionPane.showMessageDialog(null,"Empty file.");
				else
				{
					int i,j;
					int successCounter=0;
					int failCounter=0;
					for (i=0;i<parsedText.size();i++)
					{
						for (j=0;j<parsedText.get(i).size();j++)
						{
							//Display data to the user. 
							addToTextArea("\n"+parsedText.get(i).get(j));
						}
						
						String valid = null;
						if((valid = ValidTweetData(parsedText.get(i)))!=null)
						{
							addToTextArea("\n"+valid); //Display the cause for invalidation. 
							failCounter++;
						}
						else
							try 
							{
								//Send data to the DB.
								Insert.addTweetToDB(parsedText.get(i).get(0),parsedText.get(i).get(1),parsedText.get(i).get(2),parsedText.get(i).get(3));
								addToTextArea("\nTweet added successfully.");
								successCounter++;
							}
							catch (MySQLIntegrityConstraintViolationException e)
							//Duplicates to the users and words tables were caught internally.
							//This can only be a duplicate tweet. 
							{
								addToTextArea("\nThis tweet was already in the DB (Duplicate entry rejected).");
								failCounter++;
							} 
							catch (Exception e)
							{
								addToTextArea("\nSQL Error."+e);
								failCounter++;
							}
						addToTextArea("\n");
					}
					addToTextArea("\n"+successCounter+" tweets added successfully, "+failCounter+" errors.");
					addToTextArea("\nTime elapsed: "+((System.currentTimeMillis()-startTime)/1000)+" seconds");
					addToTextArea("\n----------------------------");
				}
			}
			catch (IOException e1)
			{
				JOptionPane.showMessageDialog(null,"Cannot access file.");
			}
			catch (StringIndexOutOfBoundsException e2)
			{
				JOptionPane.showMessageDialog(null,"Format error, cannot open file."+e2.getMessage());
			}
		}
		
		if(event.getSource()==clearDB)
		{
			//ok=0,cancel=2
			if (JOptionPane.showConfirmDialog(null,"All data will be permanently removed! \nAre you Sure?","Delete DB!",2)==0)
				CreateDB.clearDB(this);
		}
		
		if(event.getSource()==exportDB)
		{
			chooser.setSelectedFile(null);
			chooser.setFileFilter(new FileNameExtensionFilter("XML file","xml"));
			chooser.showSaveDialog(null);
			String fileString = null;
			if(chooser.getSelectedFile()!=null)
				fileString = chooser.getSelectedFile().toString();
			if (fileString!=null)
			{
				if(!fileString.substring(fileString.length()-4, fileString.length()).equals(".xml"))
					fileString = fileString.concat(".xml");
				XML.exportXML(this, new File(fileString));
			}
		}
		
		if(event.getSource()==importDB)
		{
			chooser.setSelectedFile(null);
			chooser.setFileFilter(new FileNameExtensionFilter("XML file","xml"));
			chooser.showOpenDialog(null);
			File file = null;
			if(chooser.getSelectedFile()!=null)
				file = chooser.getSelectedFile();
			if (file!=null)
				if(file.toString().endsWith(".xml"))
					XML.importXML(this,file);
				else
					addToTextArea("\nYou must only import a proper XML file.");
		}		
		
		if(event.getSource()==checkSearchByWord && checkSearchByWord.isSelected())
		//The SearchByWord checkbox was applied.
		{
			manualInput(Source.WORD,checkSearchByWord,textSearchByWord);
		}
		
		if(event.getSource()==checkSearchByWord && !checkSearchByWord.isSelected())
		//The SearchByWord checkbox was removed. ----no action needed.
		{

		}
		
		if(event.getSource()==buttonPasteSearchByWord)
		{
			pasteInput(Source.WORD,checkSearchByWord,textSearchByWord);
		}
		
		if(event.getSource()==checkSearchByDate && checkSearchByDate.isSelected())
		//The SearchByDate checkbox was applied.
		{
			String defaultStart = null;
			if(textStartSearchByDate.getText().length()!=0)
				defaultStart = textStartSearchByDate.getText();
			String defaultEnd = null;
			if(textEndSearchByDate.getText().length()!=0)
				defaultEnd = textEndSearchByDate.getText();
			
			dateSlctr = new DateSelector(this,defaultStart,defaultEnd);
			//dateSlctr will update display using the method 'updateDates'.
		}
		
		if(event.getSource()==checkSearchByDate && !checkSearchByDate.isSelected())
			//The SearchByDate checkbox was removed. ----no action needed.
		{

		}
			
		if(event.getSource()==checkSearchByUser && checkSearchByUser.isSelected())
		//The SearchByUser checkbox was applied. 
		{
			manualInput(Source.USER,checkSearchByUser,textSearchByUser);
		}
		
		if(event.getSource()==checkSearchByUser && !checkSearchByUser.isSelected())
		//The SearchByUser checkbox was removed. ----no action needed. 
		{
 
		}
		
		if(event.getSource()==buttonPasteSearchByUser)
		{
			pasteInput(Source.USER,checkSearchByUser,textSearchByUser);
		}
		
		if(event.getSource()==buttonSearch)
		//The search button sends search terms to the DB, and retrieves the results for the user to see.
		{
			try
			{
				dateSlctr.dispose(); //Date selector not needed after search performed.
			}
			catch (NullPointerException e) { } //In case a search was performed without ever using the date selector.
			
			if ((textStartSearchByDate.getText().length()==0 || textEndSearchByDate.getText().length()==0) && checkSearchByDate.isSelected())
				checkSearchByDate.setSelected(false); //removes the check mark if there are no dates.
			
			if (textSearchByWord.getText().length()==0 && textStartSearchByDate.getText().length()==0
					&& textEndSearchByDate.getText().length()==0 && textSearchByUser.getText().length()==0)
				return; //click on the search button is ignored if there are no search terms. 
			
			String searchTermWord = null;
			String searchTermDateStart = null;
			String searchTermDateEnd = null;
			String searchTermUser = null;

			if(checkSearchByWord.isSelected())
				searchTermWord = textSearchByWord.getText();
			if(checkSearchByDate.isSelected())
			{
				searchTermDateStart = textStartSearchByDate.getText();
				searchTermDateEnd = textEndSearchByDate.getText();
			}
			if(checkSearchByUser.isSelected())
				searchTermUser = textSearchByUser.getText();
			
			if (searchTermWord!=null || searchTermDateStart!=null || searchTermDateEnd!=null || searchTermUser!=null)
			//Must have at least one search term.
				if (searchTermDateStart==null || searchTermDateEnd==null)
					searchDB(searchTermWord,null,null,searchTermUser);
				else
					searchDB(searchTermWord,searchTermDateStart+" 00:00:00",searchTermDateEnd+" 23:59:59",searchTermUser);
					//Automatically sends the time 00:00:00 to conform to date structure.
		}
		
		if(event.getSource()==buttonClearSearch)
		{
			checkSearchByWord.setSelected(false);
			checkSearchByDate.setSelected(false);
			checkSearchByUser.setSelected(false);
			
			textSearchByWord.setText("");
			textStartSearchByDate.setText("");
			textEndSearchByDate.setText("");
			textSearchByUser.setText("");
		}
		
		if(event.getSource()==buttonNewGroup)
		//The new group button was selected.
		{
			selectExistingGroup.setEnabled(false); //can't select existing groups if new group is clicked.
			
			manualInput(Source.GROUP, null, textNewGroup);
		}
		
		if(event.getSource()==buttonExistingGroup)
		//The existing group button was selected.
		{
			getList(Source.GROUP,selectExistingGroup);
		}
		
		if(event.getSource()==checkAddWordToGroup && checkAddWordToGroup.isSelected())
		//The AddWordToGroup checkbox was applied.
		{
			manualInput(Source.WORD,checkAddWordToGroup,textAddWordToGroup);
		}
		
		if(event.getSource()==checkAddWordToGroup && !checkAddWordToGroup.isSelected())
		//The AddWordToGroup checkbox was removed. ----no action needed.
		{

		}
		
		if(event.getSource()==buttonPasteAddWordToGroup)
		{
			pasteInput(Source.WORD,checkAddWordToGroup,textAddWordToGroup);
		}
		
		if(event.getSource()==buttonAddWordToGroup)
		//The add word to group button was selected.
		{
			addWord(Source.GROUP, textNewGroup, buttonNewGroup, buttonExistingGroup, textAddWordToGroup, null, selectExistingGroup, checkAddWordToGroup, null);
		}
		
		if(event.getSource()==buttonRemoveWordFromGroup)
		{
			removeWord(Source.GROUP,buttonExistingGroup,selectExistingGroup,checkAddWordToGroup,null,textAddWordToGroup,null);
		}
		
		
		if (event.getSource()==buttonDeleteGroup)
		//The delete group button was selected.
		{
			delete(Source.GROUP, buttonExistingGroup, selectExistingGroup);
		}
		
		if (event.getSource()==buttonDisplayGroup)
		//The display group button was selected.
		{
			display(Source.GROUP, buttonExistingGroup, selectExistingGroup);
		}
		
		if(event.getSource()==buttonNewRelation)
		//The new relation button was selected.
		{
			selectExistingRelation.setEnabled(false); //can't select existing relations if new relation is clicked.
			
			manualInput(Source.RELATION,null,textNewRelation);
		}
		
		if(event.getSource()==buttonExistingRelation)
		//The existing relation button was selected.
		{
			getList(Source.RELATION,selectExistingRelation);
		}
		
		if(event.getSource()==checkAddWord1ToRelation && checkAddWord1ToRelation.isSelected())
		//The checkAddWord1ToRelation checkbox was applied.
		{
			manualInput(Source.WORD,checkAddWord1ToRelation,textAddWord1ToRelation);
		}
			
		if(event.getSource()==checkAddWord1ToRelation && !checkAddWord1ToRelation.isSelected())
		//The checkAddWord1ToRelation checkbox was removed. ----no action needed.
		{

		}
		
		if(event.getSource()==buttonPasteAddWord1ToRelation)
		{
			pasteInput(Source.WORD,checkAddWord1ToRelation,textAddWord1ToRelation);
		}

		if(event.getSource()==checkAddWord2ToRelation && checkAddWord2ToRelation.isSelected())
		//The checkAddWord2ToRelation checkbox was applied.
		{
			manualInput(Source.WORD,checkAddWord2ToRelation,textAddWord2ToRelation);
		}
			
		if(event.getSource()==checkAddWord2ToRelation && !checkAddWord2ToRelation.isSelected())
		//The checkAddWord2ToRelation checkbox was removed. ----no action needed.
		{

		}
		
		if(event.getSource()==buttonPasteAddWord2ToRelation)
		{
			pasteInput(Source.WORD,checkAddWord2ToRelation,textAddWord2ToRelation);
		}

		if(event.getSource()==buttonAddWordsToRelation)
		//The add words to relation button was selected.
		{
			addWord(Source.RELATION, textNewRelation, buttonNewRelation, buttonExistingRelation, textAddWord1ToRelation, textAddWord2ToRelation, selectExistingRelation, checkAddWord1ToRelation, checkAddWord2ToRelation);
		}
		
		if(event.getSource()==buttonRemoveWordsFromRelation)
		{
			removeWord(Source.RELATION,buttonExistingRelation,selectExistingRelation,checkAddWord1ToRelation,checkAddWord2ToRelation,textAddWord1ToRelation,textAddWord2ToRelation);
		}
		

		if (event.getSource()==buttonDeleteRelation)
		//The delete relation button was selected.
		{
			delete(Source.RELATION, buttonExistingRelation, selectExistingRelation);
		}
		
		if (event.getSource()==buttonDisplayRelation)
		//The display relation button was selected.
		{
			display(Source.RELATION, buttonExistingRelation, selectExistingRelation);
		}
		
		if(event.getSource()==buttonNewExpression && buttonNewExpression.isSelected())
		//The New Expression button was selected.
		{
			selectExistingExpression.setEnabled(false); //can't select existing expressions if new expression is clicked.
			
			manualInput(Source.EXPRESSION,null,textNewExpression);
		}
		
		if(event.getSource()==buttonNewExpression && !buttonNewExpression.isSelected())
		//The New Expression button was removed. ----no action needed.
		{

		}
		
		if(event.getSource()==buttonPasteNewExpression)
		{
			buttonNewExpression.setSelected(true);
			pasteInput(Source.EXPRESSION,null,textNewExpression);
		}
		
		if(event.getSource()==buttonExistingExpression)
		{
			getList(Source.EXPRESSION,selectExistingExpression);
		}
		
		if(event.getSource()==buttonAddExpression)
		//The add expression button was selected.
		{
			addWord(Source.EXPRESSION, textNewExpression, buttonNewExpression, buttonExistingExpression, textNewExpression, null, selectExistingExpression, null, null);
		}
		
		if(event.getSource()==buttonDeleteExpression)
		{
			delete(Source.EXPRESSION, buttonExistingExpression, selectExistingExpression);
		}
		
		if(event.getSource()==buttonSearchExpression)
		{
			String expr = null;

			if(buttonNewExpression.isSelected() && textNewExpression.getText().length()!=0)
				expr = textNewExpression.getText();
			else
				if(buttonExistingExpression.isSelected() && selectExistingExpression.getSelectedItem().toString().length()!=0)
					expr = selectExistingExpression.getSelectedItem().toString();

			if (expr!=null)
			{
				addToTextArea("\nSearching for the expression '"+expr+"':");
				searchDB(expr,null,null,null);
			}
			else
				JOptionPane.showMessageDialog(null,"Expression required.");
		}
	}
	
	protected void updateDates(String from, String to)
	//Used by DateSelector to update the user selected dates into the date text areas.
	{
		textStartSearchByDate.setText(from);
		textEndSearchByDate.setText(to);
	}
	
	private int countWords(ArrayList<ArrayList<String>> s)
	//Counts words in a 2-d String array, in order to display statistics.
	{
		int i,sum=0;
		for (i=0;i<s.size();i++)
		{
			String trim = s.get(i).get(3).trim();
			if (!trim.isEmpty())
				sum+=trim.split("\\s+").length; //separates string around spaces, and sums the lengths of the results. 
		}
		return sum;
	}
	
	private int countLetters(ArrayList<ArrayList<String>> s)
	//Counts letters (more precisely, all non-space chars) in a 2-d String array, in order to display statistics.
	{
		int i,sum=0;
		for (i=0;i<s.size();i++)
		{
			sum+=s.get(i).get(3).replaceAll("\\s+","").length(); 
		}
		return sum;
	}

	protected void addToTextArea(String s)
	//Manually scrolls textArea with each addition to it (in case the user cursor prevents auto-scrolling).  
	{
		textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.append(s);
	}
	
	private void searchDB(String word, String dateStart, String dateEnd, String userID)
	//Searches the DB using any of 4 search terms.
	{
		ArrayList<ArrayList<String>> result;

		addToTextArea("\n*SEARCH TERMS*\n   Word: "+word+"\n   DateStart: "+dateStart+"\n   DateEnd: "+dateEnd+"\n   User: "+userID+"\n");
		try
		{
			result = Search.searchByMetaData(word, dateStart, dateEnd, userID);
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null,"SQL search error. ");
			return;
		}
		
		int i,j;
		for (i=0;i<result.size();i++)
		{
			for (j=0;j<4;j++)
				addToTextArea("\n"+result.get(i).get(j));
			addToTextArea("\n");
		}
		
		//Highlight search word
		int pos=0;
		Highlighter hilite = textArea.getHighlighter();
        HighlightPainter HlPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.lightGray);
        
        hilite.removeAllHighlights(); //Remove past highlights.
        
        if (word!=null)
        	while (textArea.getText().toLowerCase().indexOf(word.toLowerCase(),pos)!=-1)
        	{
        		try
        		{
        			hilite.addHighlight(textArea.getText().toLowerCase().indexOf(word.toLowerCase(),pos),textArea.getText().toLowerCase().indexOf(word.toLowerCase(),pos)+word.length(),HlPainter);
        		}
        		catch (BadLocationException e) {}
			
        		pos = textArea.getText().toLowerCase().indexOf(word.toLowerCase(),pos)+1;
        	}
		
        //Statistics
		addToTextArea("\n ---  Tweets found: "+result.size()+"  ---");
		addToTextArea("\n ---  Total words in tweets: "+countWords(result)+"  ---");
		addToTextArea("\n ---  Total letters in tweets: "+countLetters(result)+"  ---");
		addToTextArea("\n----------------------------");
	}

	private String[] userInputValidate(Source src, String text)
	//Validates words, userIDs, or expressions.
	//[0] Returns a possible error message for the user, or null if there is no error.
	//[1] Returns the original text which may have been corrected.
	{
		String[] result = new String[2];
		result[0] = null;
		result[1] = text;
		
		if(src==Source.EXPRESSION)
		{
			if (!text.matches(".*[a-zA-Z0-9] +[a-zA-Z0-9].*"))
			//Makes sure there is more than one word (two groups of letters/digits separated by at least one space). 
				result[0] = "Expressions cannot consist of a single word.";
			if(text.length()>45)
				result[0] = "Expressions are limited to 45 characters.";				
		}

		if(src==Source.USER)
			if (text.contains(" "))
					result[0] = "Username cannot contain spaces.";
			else
			{
				if (!text.startsWith("@"))
					result[1] = "@".concat(text);
				if(result[1].length()>32)
					result[0] = "Username is limited to 32 characters (including the mandatory '@' character).";
			}
		
		if(src==Source.WORD)
			if (text.contains(" "))
				result[0] = "A single word cannot contain spaces.";
			else
				if (text.length()>45)
					result[0] = "Words are limited to 45 characters.";
		
		if(src==Source.GROUP)
		{
			if(text.length()>20)
				result[0] = "Group names are limited to 20 characters.";
		}

		if(src==Source.RELATION)
		{
			if(text.length()>20)
				result[0] = "Relation names are limited to 20 characters.";
		}
		
		return result;
	}
	
	private void manualInput(Source src, JCheckBox box, JTextArea area)
	{
		String[] validate = null; 
		String userInput = JOptionPane.showInputDialog("Please type in your text:",area.getText());
		
		if (userInput==null || userInput.length()==0)
		{
			if (box!=null) //Radio buttons will send a null JCheckBox.
				box.setSelected(false);
		}
		else
		{
			validate = userInputValidate(src,userInput);
			
			if (validate[0]!=null) //There's an error message.
			{
				if (box!=null) //Radio buttons will send a null JCheckBox.
					box.setSelected(false);
				JOptionPane.showMessageDialog(null,validate[0]); //Displays error message.
			}
			else
				area.setText(validate[1]);
		}
	}
		
	private void pasteInput(Source src, JCheckBox box, JTextArea area)
	{
		String[] validate = null;
		String userInput = null;
		
		if (textArea.getSelectedText()==null)
			userInput = "No text was selected from the viewport";
		else
			userInput = textArea.getSelectedText();
		
		userInput = JOptionPane.showInputDialog("Please confirm your selected text:",userInput);
		
		if (userInput!=null && userInput.length()!=0)
		//Nothing is done following an empty/null input. The checkbox may be marked from previous interface, and should remain that way.
		{
			validate = userInputValidate(src,userInput);
			
			if (validate[0]!=null) //There's an error message.
				JOptionPane.showMessageDialog(null,validate[0]); //Displays error message.
			else
			{
				area.setText(validate[1]);
				if(box!=null) //Radio buttons will send a null JCheckBox.
					box.setSelected(true);
			}
		}
	}
	
	private void getList(Source src, JComboBox<String> select)
	{
		select.setEnabled(true);
		
		select.removeAllItems();
		select.addItem(""); //blank item is the default selection.

		try
		{
			ArrayList<String> list = null;
			if(src==Source.GROUP)
				list = Group.getGroupNames();
			if(src==Source.RELATION)
				list = Relation.getAllRelations();
			if(src==Source.EXPRESSION)
				list = Expression.getExpressions();

			int i;
			for (i=0;i<list.size();i++)
			{
				select.addItem(list.get(i));
			}
		}
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null,"SQL Error. Cannot get list.");
		}
	}
	
	private String ValidTweetData(ArrayList<String> data)
	//Input verification.
	{
		//Starts off as valid, unless proven otherwise.
		String invalidationCause = null;
		
		//the tweet key must contain only digits.
		String digits = "[0-9]+";
		if (!data.get(0).matches(digits))
			invalidationCause = "Tweet key must contain only digits.";
		
		//the tweet user must start with '@'.
		if(!(data.get(1).charAt(0)=='@'))
			invalidationCause = "Usernames must begin with '@'.";
				
		//the tweet user is limited to 32 characters.
		if((data.get(1).length()>32))
			invalidationCause = "Usernames cannot exceed 32 characters.";
		
		//The tweet date must conform to a format.
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			format.parse(data.get(2));
		}
		catch (ParseException e)
		{
			invalidationCause = "Date must conform to the format yyyy-MM-dd HH:mm:ss";
		}
		
		//A tweet cannot be longer than 140 characters.
		if(data.get(3).length()>140)
			invalidationCause = "Tweets cannot be longer than 140 characters.";
		
		return invalidationCause;
	}
	
	private void removeWord(Source src, JRadioButton radioNew, JComboBox<String> select, JCheckBox checkBox1, JCheckBox checkBox2, JTextArea word1, JTextArea word2)
	{
		String removeFrom = "";
		if (radioNew.isSelected())
			removeFrom = select.getSelectedItem().toString();
		
		if (removeFrom.length()==0)
			JOptionPane.showMessageDialog(null,"You must select a valid existing "+src.toString().toLowerCase());
		else 
			if (!checkBox1.isSelected() || (checkBox2!=null && !checkBox2.isSelected()))
				JOptionPane.showMessageDialog(null,"Please select word to remove.");
			else
			{
				if(src==Source.GROUP)
				{
					ArrayList<String> currentWordsGroup=null;
					try
					{
						currentWordsGroup = Group.getWordsInGroup(removeFrom);
						if(!currentWordsGroup.contains(word1.getText()))
						{
							JOptionPane.showMessageDialog(null,"Word doesn't exist in group.");
							return;
						}
						else
							//ok=0,cancel=2
							if (JOptionPane.showConfirmDialog(null,"Remove '"+word1.getText()+"' from the group '"+removeFrom+"'?","Remove",2)==0)
							{
								Group.deleteWordFromGroup(word1.getText(),removeFrom);
								JOptionPane.showMessageDialog(null,"Word removed successfully from group.");
							}
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null,"SQL Error. Cannot remove word from group.");
					}
				}
				
				if(src==Source.RELATION)
				{
					ArrayList<ArrayList<String>> currentWordsRelation=null;
					try
					{
						currentWordsRelation = Relation.getAllWordsInRelation(removeFrom);
						ArrayList<String> remove = new ArrayList<String> ();
						
						if(word1.getText().compareTo(word2.getText())<=0)
						{
							remove.add(word1.getText());
							remove.add(word2.getText());
						}
						else
						{
							remove.add(word2.getText());
							remove.add(word1.getText());
						}

						if(!(currentWordsRelation.contains(remove)))
							JOptionPane.showMessageDialog(null,"Words don't exist in relation.");
						else
							//ok=0,cancel=2
							if (JOptionPane.showConfirmDialog(null,"Remove '"+word1.getText()+"' and '"+word2.getText()+"' from the relation '"+removeFrom+"'?","Remove",2)==0)
							{
								Relation.deleteWordsFromRelation(removeFrom, word1.getText(), word2.getText());
								JOptionPane.showMessageDialog(null,"Words removed successfully from relation.");
							}
					}
					catch (Exception e)
					{
						JOptionPane.showMessageDialog(null,"SQL Error. Cannot remove words from relation.");
					}
				}
				
				String selected = select.getSelectedItem().toString();
				select.setSelectedIndex(0); //resets the selection to the default.
				getList(src,select); //Refresh list.
				select.setSelectedItem(selected); //Restores original selection (if it still exists) 
			}
	}
	
	private void addWord(Source src, JTextArea toWhere, JRadioButton radioNew, JRadioButton radioExisting, JTextArea word1, JTextArea word2, JComboBox<String> select, JCheckBox checkBox1, JCheckBox checkBox2)
	{
		String addTo = "";
		if (radioNew.isSelected())
			addTo = toWhere.getText();
		else
			if (radioExisting.isSelected())
				addTo = select.getSelectedItem().toString();
		
		if (addTo.length()==0)
			JOptionPane.showMessageDialog(null,"Please select "+src.toString().toLowerCase());
		else 
			if ((checkBox1!=null && !checkBox1.isSelected()) || (checkBox2!=null && !checkBox2.isSelected()))
				JOptionPane.showMessageDialog(null,"Please select a text to add.");
			else
			{
				if(src==Source.GROUP)
				{
					//ok=0,cancel=2
					if (JOptionPane.showConfirmDialog(null,"Add the word '"+word1.getText()+"' to the group '"+addTo+"'?","Add word",2)==0)
						try
						{
							Group.addWordToGroup(word1.getText(),addTo);
							JOptionPane.showMessageDialog(null,"Word added successfully to group.");
						}
						catch (MySQLIntegrityConstraintViolationException e)
						{
							JOptionPane.showMessageDialog(null,"This word was already in the group (Duplicate entry rejected).");
						}
						catch (Exception e)
						{
							JOptionPane.showMessageDialog(null,"SQL Error. Cannot add word to group.");
						}
				}
				
				if(src==Source.RELATION)
				{
					//ok=0,cancel=2
					if (JOptionPane.showConfirmDialog(null,"Add the words '"+word1.getText()+"' and '"+word2.getText()+"' to the relation '"+addTo+"'?","Add words",2)==0)
						try
						{
							Relation.addWordsToRelation(addTo,word1.getText(),word2.getText());
							JOptionPane.showMessageDialog(null,"Words successfully added to relation");
						}
						catch (MySQLIntegrityConstraintViolationException e)
						{
							JOptionPane.showMessageDialog(null,"These words were already in the relation (Duplicate entry rejected).");
						}
						catch (Exception e) 
						{
							JOptionPane.showMessageDialog(null,"SQL Error. Cannot add words to relation.");
						}
								
				}
				
				if(src==Source.EXPRESSION)
				{
					//ok=0,cancel=2
					if (JOptionPane.showConfirmDialog(null,"Add the expression '"+word1.getText()+"'?","Add expression",2)==0)
						try
						{
							Expression.addExpression(word1.getText());
							JOptionPane.showMessageDialog(null,"Expression added successfully.");
						}
						catch (MySQLIntegrityConstraintViolationException e)
						{
							JOptionPane.showMessageDialog(null,"Expression already exists in the DB (Duplicate entry rejected).");
						}
						catch (Exception e)
						{
							JOptionPane.showMessageDialog(null,"SQL Error. Cannot add expression.");
						}
				}
			}
	}
	
	private void delete(Source src, JRadioButton radioExisting, JComboBox<String> select)
	{
		if (!radioExisting.isSelected() || select.getSelectedItem()=="")
			JOptionPane.showMessageDialog(null,"You must select a valid existing "+src.toString().toLowerCase());
		else
			//ok=0,cancel=2
			if (JOptionPane.showConfirmDialog(null,"Delete the "+src.toString().toLowerCase()+" '"+select.getSelectedItem().toString()+"'?","Delete!",2)==0)
			{
				try
				{
					String current = select.getSelectedItem().toString();
					if(src==Source.GROUP)
						Group.deleteWholeGroup(current);
					if(src==Source.RELATION)
						Relation.deleteRelation(current);
					if(src==Source.EXPRESSION)
						Expression.deleteExpression(current);
					select.setSelectedIndex(0);
					select.removeItem(current);
					JOptionPane.showMessageDialog(null,src.toString().toLowerCase()+" deleted successfully.");
				}
				catch (Exception e) 
				{
					JOptionPane.showMessageDialog(null,"SQL Error. Cannot delete "+src.toString().toLowerCase());
				}
			}
	}
	
	private void display(Source src, JRadioButton radioExisting, JComboBox<String> select)
	{
		if (!radioExisting.isSelected() || select.getSelectedItem()=="")
			JOptionPane.showMessageDialog(null,"You must select a valid existing "+src.toString().toLowerCase());
		else
		{
			try
			{
				addToTextArea("\nThe words currently in the "+src.toString().toLowerCase()+" '"+select.getSelectedItem().toString()+"' are:");
				
				if(src==Source.GROUP)
				{
					ArrayList<String> words = Group.getWordsInGroup(select.getSelectedItem().toString());
					int i;
					for (i=0;i<words.size();i++)
					{
						addToTextArea("\n"+words.get(i));
					}
					addToTextArea("\n ---  Total words in the the "+src.toString().toLowerCase()+" '"+select.getSelectedItem().toString()+"': "+words.size()+"  ---");
					addToTextArea("\n----------------------------");
				}
				if(src==Source.RELATION)
				{
					ArrayList<ArrayList<String>> words = Relation.getAllWordsInRelation(select.getSelectedItem().toString());
					
					int i;
					for (i=0;i<words.size();i++)
					{
						addToTextArea("\n"+words.get(i).get(0)+" "+select.getSelectedItem().toString()+" "+words.get(i).get(1));
					}
					addToTextArea("\n ---  Total word couples in the the "+src.toString().toLowerCase()+" '"+select.getSelectedItem().toString()+"': "+words.size()+"  ---");
					addToTextArea("\n----------------------------");
				}
			}
			catch (Exception e) 
			{
				JOptionPane.showMessageDialog(null,"SQL Error. Cannot display "+src.toString().toLowerCase()+".");
			}
		}
	}
}

