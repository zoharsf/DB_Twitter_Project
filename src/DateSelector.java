import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class DateSelector extends JFrame implements ActionListener
//provides a way for the user to input dates. 
{
    private static final long serialVersionUID = 1L; //Prevents an irrelevant warning.
	
    private static int minYear = 2006;
    private static int maxYear = 2014;
	private JComboBox<Integer> startDay;
	private JComboBox<Integer> startMonth;
	private JComboBox<Integer> startYear;
	private JComboBox<Integer> endDay;
	private JComboBox<Integer> endMonth;
	private JComboBox<Integer> endYear;
	private JLabel startLabel;
	private JLabel endLabel;
	private JButton setDates;
	private UI returnDatePtr;
	
	public DateSelector(UI returnDatePointer, String defaultStart, String defaultEnd)
	//Constructor receives a pointer to an object, allowing it to return the dates input by the user.
	{
		super("Date selection");
		returnDatePtr = returnDatePointer;
		
		int i;
		
		//Days are populated initially as 31 days, but this may change with every selection of either month or year.
		startDay = new JComboBox<Integer>();
		for (i=1;i<=31;i++)
			startDay.addItem(new Integer(i));		
		endDay = new JComboBox<Integer>();
		for (i=1;i<=31;i++)
			endDay.addItem(new Integer(i));	

		startMonth = new JComboBox<Integer>();
		for (i=1;i<=12;i++)
			startMonth.addItem(new Integer(i));		
		endMonth = new JComboBox<Integer>();
		for (i=1;i<=12;i++)
			endMonth.addItem(new Integer(i));		

		startYear = new JComboBox<Integer>();
		for (i=minYear;i<=maxYear;i++)
			startYear.addItem(new Integer(i));
		endYear = new JComboBox<Integer>();
		for (i=minYear;i<=maxYear;i++)
			endYear.addItem(new Integer(i));		
		
		if(defaultStart!=null)
		{
			startDay.setSelectedItem(Integer.parseInt(defaultStart.substring(8,10)));
			startMonth.setSelectedItem(Integer.parseInt(defaultStart.substring(5,7)));
			startYear.setSelectedItem(Integer.parseInt(defaultStart.substring(0,4)));			
		}
		
		if(defaultEnd!=null)
		{
			endDay.setSelectedItem(Integer.parseInt(defaultEnd.substring(8,10)));
			endMonth.setSelectedItem(Integer.parseInt(defaultEnd.substring(5,7)));
			endYear.setSelectedItem(Integer.parseInt(defaultEnd.substring(0,4)));			
		}
		
		
		startLabel = new JLabel("Select the start date:");
		endLabel = new JLabel("Select the end date:");
		
		setDates = new JButton("Set dates");
		
		setSize(300,300);
		setVisible(true);
		
		//GridBag Layout.
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;

		c.gridx = 0;
		c.gridy = 0;
		gridbag.setConstraints(startLabel,c);
		add(startLabel);

		c.gridx = 1;
		c.gridy = 1;
		gridbag.setConstraints(startYear,c);
		add(startYear);
		
		c.gridx = 2;
		c.gridy = 1;
		gridbag.setConstraints(startMonth,c);
		add(startMonth);
		
		c.gridx = 3;
		c.gridy = 1;
		gridbag.setConstraints(startDay,c);
		add(startDay);

		c.gridx = 0;
		c.gridy = 4;
		gridbag.setConstraints(endLabel,c);
		add(endLabel);
		
		c.gridx = 1;
		c.gridy = 5;
		gridbag.setConstraints(endYear,c);
		add(endYear);
		
		c.gridx = 2;
		c.gridy = 5;
		gridbag.setConstraints(endMonth,c);
		add(endMonth);
		
		c.gridx = 3;
		c.gridy = 5;
		gridbag.setConstraints(endDay,c);
		add(endDay);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 6;
		gridbag.setConstraints(setDates,c);
		add(setDates);
		
		//Listeners
		setDates.addActionListener(this);
		startYear.addActionListener(this);
		startMonth.addActionListener(this);
		endYear.addActionListener(this);
		endMonth.addActionListener(this);

	}
	
	public void actionPerformed (ActionEvent event)
	{
		if(event.getSource()==startYear || event.getSource()==startMonth)
		//If either the year or the month change, the days need to be re-populated accordingly.
		//(each month has a different number of days, and the year is relevant in case of leap year)
		{
			repopulateDays (startYear, startMonth, startDay);
		}
		
		if(event.getSource()==endYear || event.getSource()==endMonth)
		//If either the year or the month change, the days need to be re-populated accordingly.
		//(each month has a different number of days, and the year is relevant in case of leap year)
		{
			repopulateDays (endYear, endMonth, endDay);
		}
		
		if(event.getSource()==setDates)
		//When the button is pressed, the returnDatePtr pointer is used to call 'updateDates'.
		{
			GregorianCalendar start = new GregorianCalendar((Integer)startYear.getSelectedItem(),
					(Integer)startMonth.getSelectedItem()-1,(Integer)startDay.getSelectedItem()-1);
			GregorianCalendar end = new GregorianCalendar((Integer)endYear.getSelectedItem(),
					(Integer)endMonth.getSelectedItem()-1,(Integer)endDay.getSelectedItem()-1);

			if(start.after(end))
				JOptionPane.showMessageDialog(null,"Start date cannot be later than end date");
			else
			{
				returnDatePtr.updateDates(
						startYear.getSelectedItem()+"-"+addZero((Integer)startMonth.getSelectedItem())+"-"+addZero((Integer)startDay.getSelectedItem()),
						endYear.getSelectedItem()+"-"+addZero((Integer)endMonth.getSelectedItem())+"-"+addZero((Integer)endDay.getSelectedItem()));
				this.dispose();
			}
		}
	}
	
	private void repopulateDays (JComboBox<Integer> year, JComboBox<Integer> month, JComboBox<Integer> day)
	{
		Integer selection = (Integer)day.getSelectedItem();
		day.removeAllItems();
		
		GregorianCalendar start = new GregorianCalendar((Integer)year.getSelectedItem(),
				(Integer)month.getSelectedItem()-1,new Integer(1));
				//The day is added as 1 and not by selection, to make sure getActualMaximum will know which is
				// the relevant month. Otherwise, 31/2/2006 may seem like March.
				//The month has a -1 because GregorianCalendar needs months ranging 0-11.
		
		//re-populate days.
		int i;
		for (i=1;i<=start.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);i++)
			day.addItem(new Integer(i));
		
		//restore selection (or as close to it as the month has)
		if(selection<start.getActualMaximum(GregorianCalendar.DAY_OF_MONTH))
			day.setSelectedItem(selection);
		else
			day.setSelectedItem(start.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));
	}
	
	private String addZero (Integer input)
	//Single digit numbers are padded with an extra 0.
	{
		String output;
		if(input<=9)
			output = "0"+input;
		else
			output = ""+input;
		
		return output;
	}
}
