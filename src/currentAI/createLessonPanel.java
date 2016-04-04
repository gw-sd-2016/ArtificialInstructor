package currentAI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class createLessonPanel extends JPanel {


    /**
	 * 
	 */
	private static final long serialVersionUID = 4139798146217404624L;
	/**
     * 
     */
    
    
    private JPanel bodyPanel, createLesInput, noteAREA, octAREA, typeAREA, gpAREA, buttonAREA, nameAREA, scoreAREA, finalPrompt;
    private JLabel noteVal, octVal, typeVal, gpVal, currentNumNotes, lesName, reqScore ;
    JTextPane inputNoteVal;
	JTextPane inputOctVal;
	private JTextPane inputTypeVal, inputGPVal, inputLesName, inputReqScore;
    private JButton prevNote, nextNote, saveLesson;
    private String [] notes; 
    //private int [] octs;
    private String [] octs;
    private String [] types;
    private double [] times;
    private double [] gps;
    private int currentNoteCnt;
    private final int MAX_NOTES = 1000;
    private int noteCounter = 0;
    private int BPM = 60;
    private double MEASURE = 4/4;
    private boolean successfulSave = false;
    private Integer translateInt;
    
    private ActionListener loadPrev = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    		if(currentNoteCnt > 0)
    		{
    			currentNoteCnt--;
    			
    			
    			//Change Text Area Values back to that of previous Note
    			inputNoteVal.setText(notes[currentNoteCnt]);
    			inputOctVal.setText(octs[currentNoteCnt]);
    			inputTypeVal.setText(types[currentNoteCnt]);
    			//inputGPVal.setText("0");
    			
    			
    		}
    		else
    		{
    			inputGPVal.setText("NO NOTE TO LOAD");
    		}
        	//set value of currentNote count
        	translateInt = new Integer(noteCounter);
    		currentNumNotes.setFont(new Font("Times New Roman", 1, 22));
    		currentNumNotes.setText( "NUMBER OF NOTES ADDED TO LESSON: " + translateInt.toString() + "          -------------          CURRENT NUMBER OF NOTE BEING EDITED: " + new Integer(currentNoteCnt+1).toString() );
    		
    	}
    };
    
    private ActionListener loadNext = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    		//variables used to check for valid input in text areas
    		boolean checkN, checkO, checkT, checkG;
    		
    		//set boolean values to return of textarea check
    		checkN = checkNote(inputNoteVal.getText());
    		checkO = checkOct();
    		checkT = checkType();
    		//checkG = checkGP();
    		
    		//if all entries in textareas are valid
    		if(checkN == true && checkO == true && checkT == true )
    		{
    			if(currentNoteCnt < MAX_NOTES - 2){
    				
    				//if this is a new note being added, increment total count
    				if(currentNoteCnt == noteCounter)
    				{
    					noteCounter++;
    					//store values in array, check for valid input
        				notes[currentNoteCnt] = inputNoteVal.getText();
        				octs[currentNoteCnt] = inputOctVal.getText();
        				types[currentNoteCnt] = inputTypeVal.getText();
        				gps[currentNoteCnt] = 0.00;
        				
        				//reset the TextAreas
        				inputNoteVal.setText(null);
        				inputOctVal.setText(null);
        				inputTypeVal.setText(null);
        				//inputGPVal.setText(null);
        				
        				//increment counter
        				currentNoteCnt++;
        				
        				inputGPVal.setText("Note Successfully Added");
    				}
    				else if(currentNoteCnt + 1 == noteCounter)
    				{
    					//store values, incase anything was edited 
        				notes[currentNoteCnt] = inputNoteVal.getText();
        				octs[currentNoteCnt] = (inputOctVal.getText());
        				types[currentNoteCnt] = inputTypeVal.getText();
        				gps[currentNoteCnt] = 0.00;
        				
    					
        				//reset the TextAreas
        				inputNoteVal.setText(null);
        				inputOctVal.setText(null);
        				inputTypeVal.setText(null);
        				//inputGPVal.setText(null);
        				
        				currentNoteCnt++;
        				
        				inputGPVal.setText("Note Successfully Modified");
    				}
    				else if(currentNoteCnt < noteCounter - 1)
    				{
    					//store values, incase anything was edited 
        				notes[currentNoteCnt] = inputNoteVal.getText();
        				octs[currentNoteCnt] = (inputOctVal.getText());
        				types[currentNoteCnt] = inputTypeVal.getText();
        				gps[currentNoteCnt] = 0.00;
        				
    					//reset the TextAreas
    					
    					
        				inputNoteVal.setText(notes[currentNoteCnt+1]);
        				inputOctVal.setText(octs[currentNoteCnt+1]);
        				inputTypeVal.setText(types[currentNoteCnt+1]);
        				//inputGPVal.setText(new Double(gps[currentNoteCnt]).toString());
        				
        				currentNoteCnt++;
        				
        				inputGPVal.setText("Note Successfully Modified");
    				}
    				
    			}
    			else
    			{
    				inputGPVal.setText("CANNOT ADD MORE NOTES");

    			}
    		}
    		else
    		{
    			inputGPVal.setText("");
    			
    			if(checkN == false)
    			{
    				inputGPVal.setText("INVALID NOTE ENTRY, "
    						+ "Valid NOTES [A, A#, B, C, C#, D, D#, E, F, F#, G, G#, REST] "
    							+ "(CASE SENSITIVE)\n" );
    			}
    			
    			if(checkO == false)
    			{
    				inputGPVal.setText(inputGPVal.getText() + " INVALID OCTAVE ENTRY, Valid OCTAVES [1, 2, 3, 4]\n");
    			}
    			
    			if(checkT == false)
    			{
    				inputGPVal.setText(inputGPVal.getText() + " INVALID TYPE ENTRY, Valid TYPES [Whole, Half, Quarter, Eighth, Sixteenth]"
    						+ "(NOT CASE SENSITVE)\n");
    			}
    			
    			/*
    			if(checkG == false)
    			{
    				System.out.println("INVALID GRACE PERIOD ENTRY");
    			}
    			*/
    		}
    		
        	//set value of currentNote count
        	translateInt = new Integer(noteCounter);
    		currentNumNotes.setFont(new Font("Times New Roman", 1, 22));
    		currentNumNotes.setText( "NUMBER OF NOTES ADDED TO LESSON: " + translateInt.toString() + "      -------------      CURRENT NUMBER OF NOTE BEING EDITED: " + new Integer(currentNoteCnt+1).toString() );	
    	}
    };
    
    
    //save lesson data, and format into lesson file
    private ActionListener saveLes = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		//variable used to store identifying data for parser
    		int lastLocation = 0;
    		
        	//set value of currentNote count
        	translateInt = new Integer(noteCounter);
    		currentNumNotes.setFont(new Font("Times New Roman", 1, 22));
    		currentNumNotes.setText( "NUMBER OF NOTES ADDED TO LESSON: " + translateInt.toString() + "      -------------      CURRENT NUMBER OF NOTE BEING EDITED: " + new Integer(currentNoteCnt+1).toString() );
    		
    		if(noteCounter == 0)
    		{
    			inputGPVal.setText("No notes have been added!");
    		}
    		else 
    		{
    			if( noteCounter < MAX_NOTES)
    			{
    				for(int i = 0; i < notes.length - 2; i++)
    				{
    					if(notes[i] == null)
    					{
    						lastLocation = i;
    					}
    				}
    			
    				
    			
    			
    			//add values to mark this is the last value to be used
				notes[noteCounter] = "Z";
				octs[noteCounter] = "5";
				types[noteCounter] = "Whole";
				gps[noteCounter] = 0;
				
				
    			
    			/*
    			 *  Use the type array to generate values for times array
    			 */
    			for(int j = 0; j < types.length; j++){
    			
    				if(notes[j].equals("Z") == true){
    					break;
    				}
    				
    				//if note at last value
    				if(notes[j].equals("Z") == false)
    				{
    					double tempVal = convertTypesToDouble(types[j]);
    				
    					if(j == 0)
    					{
    						times[j] = 0 + tempVal;
    					}
    					else
    					{
    						times[j] = times[j-1] + tempVal;
    						//times[noteCounter] = times[j] + 1;
    						
    					}//end check for first value
    				
    					if(notes[j+1].equals("Z") == true )
    					{
    						times[j+1] = times[j] + 1;
    						System.out.println(times[noteCounter - 1]);
    					}
    					
    				}//end check for last note
    				
    			}//end for
    		
    			
    			currentNumNotes.setVisible(false);
        		noteAREA.setVisible(false);
        		octAREA.setVisible(false);
        		typeAREA.setVisible(false);
        		gpAREA.setVisible(false);
        		nameAREA.setVisible(false);
        		scoreAREA.setVisible(false);
        		buttonAREA.setVisible(false);
    		
    			}
        		
        		/*
        		 * 
        		 * START TO REFORMAT FROM HERE DOWN 
        		 */
    		/*
    		 * Code to convert arrays into JSON file 
    		 */
    		 String lesName = inputLesName.getText();
    		 int scoreReq = new Integer(inputReqScore.getText());
    		 
    		 //create lesssonObj to create JSON file 
    		 JSONObject lessonObj = new JSONObject();
    		 
    		 //add single elements to lessonObj
    		 lessonObj.put("Name", lesName);
    		 lessonObj.put("ScoreReq", new Integer(scoreReq));
    		 lessonObj.put("HighScore", new Integer(0));
    		 lessonObj.put("BPM", new Double(BPM));
    		 
    		 //create list of values with arrays then add them to lessonObj
    		 JSONArray noteList = new JSONArray();
    		 JSONArray octList = new JSONArray();
    		 JSONArray timeList = new JSONArray();
    		 JSONArray gpList = new JSONArray();
    		 for(int i = 0; i < noteCounter + 1; i++){
    			 noteList.add(notes[i]);
    			 octList.add(octs[i]);
    			 timeList.add(times[i]);
    			 gpList.add(gps[i]);
    		 }
    		 lessonObj.put("Notes", noteList);
    		 lessonObj.put("Octaves", octList);
    		 lessonObj.put("Times", timeList);
    		 lessonObj.put("GracePeriods", gpList);
    		 
    		 
    		/*
    		 * Code to store JSON file in specific location 
    		 */
    		
    		 try {
    			 File f = new File("Lessons/"+ lesName + ".json");
    	    		//Object obj = parser.parse(new FileReader("/Users/Shawn/Desktop/Lessons/"+lessonName+".json") );
    	    		
    			 FileWriter file = new FileWriter(f);
    			 file.write(lessonObj.toJSONString());
    			 file.flush();
    			 file.close();

    		 } catch (IOException ex) {
    			 ex.printStackTrace();
    		 }
    		 
    		 
    		}//end if/else for check for no notes
    	}
    };
    
    public void initCreation(){
    	notes = new String[MAX_NOTES];
    	octs = new String[MAX_NOTES];
    	types = new String[MAX_NOTES];
    	times = new double[MAX_NOTES];
    	gps = new double[MAX_NOTES];
    	currentNoteCnt = 0;
    	noteCounter = 0;
    	
    	inputNoteVal.setText(null);
    	inputOctVal.setText(null);
    	inputTypeVal.setText(null);
    	inputGPVal.setText(null);
    	inputLesName.setText(null);
    	inputReqScore.setText(null);
    	
    	currentNumNotes.setVisible(true);
    	//set value of currentNote count
    	translateInt = new Integer(noteCounter);
		currentNumNotes.setFont(new Font("Times New Roman", 1, 22));
		currentNumNotes.setText( "NUMBER OF NOTES ADDED TO LESSON: " + translateInt.toString() + "      -------------      CURRENT NUMBER OF NOTE BEING EDITED: " + new Integer(currentNoteCnt+1).toString() );
		
    	noteAREA.setVisible(true);
		octAREA.setVisible(true);
		typeAREA.setVisible(true);
		gpAREA.setVisible(true);
		nameAREA.setVisible(true);
		scoreAREA.setVisible(true);
		buttonAREA.setVisible(true);
    }//end initCreation
    
    
    public boolean checkNote( String input )
    {
    	//check if there is input in text area, if so check for valid entry
    	if(inputNoteVal.getText() != null)
    	{
    		//return true for valid input, false for invalid
    		if(input.equals("A") == true)
    		{
    			return true;
    		}
    		else if(input.equals("A#") == true)
    		{
    			return true;
    		}
    		else if(input.equals("B") == true)
    		{
    			return true;
    		}
    		else if(input.equals("C") == true)
    		{
    			return true;
    		}
    		else if(input.equals("C#") == true)
    		{
    			return true;
    		}
    		else if(input.equals("D") == true)
    		{
    			return true;
    		}
    		else if(input.equals("D#") == true)
    		{
    			return true;
    		}
    		else if(input.equals("E") == true)
    		{
    			return true;
    		}
    		else if(input.equals("F") == true)
    		{
    			return true;
    		}
    		else if(input.equals("F#") == true)
    		{
    			return true;
    		}
    		else if(input.equals("G") == true)
    		{
    			return true;
    		}
    		else if(input.equals("G#") == true)
    		{
    			return true;
    		}
    		else if(input.equals("REST") == true)
    		{
    			return true;
    		}
    	}
    	
    	//nothing entered
		return false;
    }//end checkNote
    
    public boolean checkOct(  )
    {
    	//check if there is input in text area, if so check for valid entry
    	if(inputOctVal.getText() != null)
    	{
    		if(inputOctVal.getText().equals("1") == true)
    		{
    			//first octave
    			return true;
    		}
    		else if (inputOctVal.getText().equals("2") == true)
    		{
    			//second octave
    			return true;
    		}
    		else if(inputOctVal.getText().equals("3") == true)
    		{
    			//third octave
    			return true;
    		}
    		else if(inputOctVal.getText().equals("4") == true)
    		{
    			//fourth octave
    			return true;
    		}
    		else
    		{
    			//octave entered not valid return false
    			return false;
    		}
    	}
    	else
    	{
    		//nothing entered return false
    		return false;
    	}
    }
    
    public boolean checkType()
    {
    	//if there is an entry, check for valid input
    	if(inputTypeVal.getText() != null)
    	{
    		if(inputTypeVal.getText().equalsIgnoreCase("Whole") == true)
    		{
    			//WHOLE NOTE
    			return true;
    		}
    		else if(inputTypeVal.getText().equalsIgnoreCase("Half") == true)
    		{
    			//HALF NOTE
    			return true;
    		}
    		else if(inputTypeVal.getText().equalsIgnoreCase("Quarter") == true)
    		{
    			//QUARTER NOTE
    			return true;
    		}
    		else if(inputTypeVal.getText().equalsIgnoreCase("Eighth") == true)
    		{
    			//EIGHTH NOTE
    			return true;
    		}
    		else if(inputTypeVal.getText().equalsIgnoreCase("Sixteenth") == true)
    		{
    			//SIXTEENTH NOTE
    			return true;
    		}
    		else
    		{
    			//invalid entry, return null
    			return false;
    		}
    	}
    	else
    	{
    		//nothing entered, return null
    		return false;
    	}
    }//end checkType
    
    public boolean checkGP( )
    {
    	if(inputGPVal.getText() == null)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }//end checkGP
    
    public createLessonPanel() {
    	//Init JLabels, center the text, and set font size/style
    	currentNumNotes = new JLabel("Current Number of Notes in Lesson: ", SwingConstants.CENTER);
    	noteVal = new JLabel("NOTE VALUE (Required to Add Note): ", SwingConstants.CENTER);
    	noteVal.setFont(new Font(this.getFont().getName(), 1, 22));
    	
    	octVal = new JLabel("OCTAVE VALUE (Required to Add Note): ", SwingConstants.CENTER);
    	octVal.setFont(new Font(this.getFont().getName(), 1, 22));
    	
    	typeVal = new JLabel("NOTE TYPE (Required to Add Note): ", SwingConstants.CENTER);
    	typeVal.setFont(new Font(this.getFont().getName(), 1, 22));
    	
    	gpVal = new JLabel("Input Prompt", SwingConstants.CENTER);
    	gpVal.setFont(new Font(this.getFont().getName(), 1, 22));
    	
    	lesName = new JLabel("LESSON NAME (Required to Save Lesson): ", SwingConstants.CENTER);
    	lesName.setFont(new Font(this.getFont().getName(), 1, 22));
    	
    	reqScore = new JLabel("SCORE REQUIRED (Required to Save Lesson): ", SwingConstants.CENTER);
    	reqScore.setFont(new Font(this.getFont().getName(), 1, 22));
    	
    	
    	
    	
    	//Set up Style to use for the TextPane in order to center the Text
    	StyleContext context = new StyleContext();
    	StyleContext context1 = new StyleContext();
    	
        StyledDocument documentN = new DefaultStyledDocument(context);
        StyledDocument documentO = new DefaultStyledDocument(context);
        StyledDocument documentT = new DefaultStyledDocument(context);
        StyledDocument documentG = new DefaultStyledDocument(context1);
        StyledDocument documentL = new DefaultStyledDocument(context);
        StyledDocument documentS = new DefaultStyledDocument(context);
        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);
        
        Style style1 = context1.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style1, StyleConstants.ALIGN_CENTER);
        
        //Init the TextPanes and add a font
    	inputNoteVal = new JTextPane(documentN);
    	inputNoteVal.setFont(new Font(this.getFont().getName(), 1, 25));
    	
    	inputOctVal = new JTextPane(documentO);
    	inputOctVal.setFont(new Font(this.getFont().getName(), 1, 25));
    	
    	inputTypeVal = new JTextPane(documentT);
    	inputTypeVal.setFont(new Font(this.getFont().getName(), 1, 25));
    	
    	inputGPVal = new JTextPane(documentG);
    	inputGPVal.setFont(new Font(this.getFont().getName(), 1, 14));
    	inputGPVal.setEditable(false);
    	
    	inputLesName = new JTextPane(documentL);
    	inputLesName.setFont(new Font(this.getFont().getName(), 1, 25));
    	
    	inputReqScore = new JTextPane(documentS);
    	inputReqScore.setFont(new Font(this.getFont().getName(), 1, 25));
    	
    	
    	//Init JButtons & add actionListeners
    	prevNote = new JButton("Previous Note");
    	prevNote.addActionListener(loadPrev);
    	prevNote.setPreferredSize(new Dimension(50, 50));
    	
    	nextNote = new JButton("Next Note");
    	nextNote.addActionListener(loadNext);
    	nextNote.setPreferredSize(new Dimension(50, 50));
    	
    	saveLesson = new JButton("Save Lesson");
    	saveLesson.addActionListener(saveLes);
    	saveLesson.setPreferredSize(new Dimension(50, 50));
    	
    	//Init Arrays and Variables
    	notes = new String[MAX_NOTES];
    	octs = new String[MAX_NOTES];
    	types = new String[MAX_NOTES];
    	times = new double[MAX_NOTES];
    	gps = new double[MAX_NOTES];
    	currentNoteCnt = 0;
    	noteCounter = 0;
    	
    	//set value of currentNote count
    	translateInt = new Integer(noteCounter);
		currentNumNotes.setFont(new Font("Times New Roman", 1, 22));
		currentNumNotes.setText( "NUMBER OF NOTES ADDED TO LESSON: " + translateInt.toString() + "      -------------      CURRENT NUMBER OF NOTE BEING EDITED: " + new Integer(currentNoteCnt).toString() );
	
    	//Init JPanels for each compartment & set layouts & add elements
    	noteAREA = new JPanel();
    	noteAREA.setLayout(new GridLayout(2, 0));
    	noteAREA.add(noteVal);
    	noteAREA.add(inputNoteVal);
    	
    	octAREA = new JPanel();
    	octAREA.setLayout(new GridLayout(2, 0));
    	octAREA.add(octVal);
    	octAREA.add(inputOctVal);
    	
    	typeAREA = new JPanel();
    	typeAREA.setLayout(new GridLayout(2, 0));
    	typeAREA.add(typeVal);
    	typeAREA.add(inputTypeVal);
    	
    	gpAREA = new JPanel();
    	gpAREA.setLayout(new GridLayout(2, 0));
    	gpAREA.add(gpVal);
    	gpAREA.add(inputGPVal);
    	
    	buttonAREA = new JPanel();
    	buttonAREA.setLayout(new GridLayout(1, 3));
    	buttonAREA.add(prevNote);
    	buttonAREA.add(nextNote);
    	buttonAREA.add(saveLesson);
    	
    	scoreAREA = new JPanel();
    	scoreAREA.setLayout(new GridLayout(2, 0));
    	scoreAREA.add(reqScore);
    	scoreAREA.add(inputReqScore);
    	
    	nameAREA = new JPanel();
    	nameAREA.setLayout(new GridLayout(2, 0));
    	nameAREA.add(lesName);
    	nameAREA.add(inputLesName);
    	
    	
    	
    	/*
    	 * Set preferred size of panels in center of frame and add border
    	 */
    	noteAREA.setPreferredSize(new Dimension(100, 100));
    	noteAREA.setBorder(BorderFactory.createLineBorder(Color.BLACK) ) ;
    	octAREA.setPreferredSize(new Dimension(100, 100));
    	octAREA.setBorder(BorderFactory.createLineBorder(Color.BLACK) ) ;
    	typeAREA.setPreferredSize(new Dimension(100, 100));
    	typeAREA.setBorder(BorderFactory.createLineBorder(Color.BLACK) ) ;
    	gpAREA.setPreferredSize(new Dimension(100, 100));
    	gpAREA.setBorder(BorderFactory.createLineBorder(Color.BLACK) ) ;
    	scoreAREA.setPreferredSize(new Dimension(100, 100));
    	scoreAREA.setBorder(BorderFactory.createLineBorder(Color.BLACK) ) ;
    	nameAREA.setPreferredSize(new Dimension(100, 100));
    	nameAREA.setBorder(BorderFactory.createLineBorder(Color.BLACK) ) ;
    	
    	bodyPanel = new JPanel();
    	bodyPanel.setLayout(new GridLayout(3, 1));
    	bodyPanel.add(noteAREA);
    	bodyPanel.add(octAREA);
    	bodyPanel.add(typeAREA);
    	bodyPanel.add(gpAREA);
    	bodyPanel.add(nameAREA);
    	bodyPanel.add(scoreAREA);

    	
    	
    	//add components to GUI
    	this.setLayout(new BorderLayout());
    	
    	this.add(currentNumNotes, BorderLayout.PAGE_START);
    	this.add(bodyPanel, BorderLayout.CENTER);
    	this.add(buttonAREA, BorderLayout.PAGE_END);
    	
    	}
    	
    	//Converts string input to int return of -1 is error 
    	public int getIntOct(String input)
    	{
    		int retVal = -1;
    		
    		if(input.equals("1") == true )
    		{
    			retVal = 1;
    		}
    		else if(input.equals("2") == true )
    		{
    			retVal = 2;
    		}
    		else if(input.equals("3") == true )
    		{
    			retVal = 3;
    		}
    		else if(input.equals("4") == true )
    		{
    			retVal = 4;
    		}
    		
    		return retVal;
    	}
    	
    	//convert int val to String
    	public String getOctString(int input){
    		String retVal = "";
    		
    		if(input == 1)
    		{
    			retVal = "1";
    		}
    		else if(input == 2)
    		{
    			retVal = "2";
    		}
    		else if (input == 3)
    		{
    			retVal = "3";
    		}
    		else if(input == 4)
    		{
    			retVal = "4";
    		}
    		else
    		{
    			retVal = "ERROR";
    		}
    		
    		return retVal;
    	}
    	
    	public double convertTypesToDouble(String input)
    	{
    		double retVal = -1;
    		
    		if(input.equalsIgnoreCase("Whole") == true)
			{
    			retVal = ( BPM / (60 * MEASURE) ) * 1;
			}
			else if(input.equalsIgnoreCase("Half") == true)
			{
				retVal = ( BPM / (60 * MEASURE) ) * .5;
			}
			else if(input.equalsIgnoreCase("Quarter") == true)
			{
				retVal = ( BPM / (60 * MEASURE) ) * .25;
			}
			else if(input.equalsIgnoreCase("Eighth") == true)
			{
				retVal = ( BPM / (60 * MEASURE) ) * .125;
			}
			else if(input.equalsIgnoreCase("Sixteenth") == true)
			{
				retVal = ( BPM / (60 * MEASURE) ) * .0625;
			}
    		
    		return retVal;
    	}//end convertTypesToDouble
    }
