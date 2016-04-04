/*
 * Shawn Huntzberry
 * 
 * The below code is a combination of my implementation of the JavaSound API and examples from the 
 * TarsoDSP library and examples. Not all the code below has been created by me, but the code I have used
 * I have noted the credit where it has come from. 
 * 
 */
package currentAI;

/*
 * JavaSound API imports required
 */
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * TarsoDSP imports required
 */
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.Oscilloscope;
import be.tarsos.dsp.Oscilloscope.OscilloscopeEventHandler;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm;


/*
 * Additional Imports required
 */
import javax.imageio.ImageIO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/*
 * Imports from this package
 */
import currentAI.PlayBackThread;
import currentAI.OscilloscopePanel;
import currentAI.UpNextPanel;
import currentAI.FretBoardPanel;
import currentAI.FretLesson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Lessons.*;

/*
 *  ArtificalInstructor currently:
 *  	-Gets audioInput from a line-in
 *  	-graphs realtime input using TarsoDSP oscilloscope program
 *  	-maps pitch of input to note using getNoteValue() method and TarsoDSP pitchDetectionHandler 
 *  	-outputs sound input using playBack() method and class playThread
 *  	-(in red)shows the user which note they are playing and(or) the notes that have that same frequency
 *  	-(in blue)shows the notes the user is expected to play next
 *  	-Allows the user to use a tuner, that is accurate with all types of instruments up to the 4th octave 
 */

public class ArtificialInstructor extends JFrame implements PitchDetectionHandler, OscilloscopeEventHandler {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8665749655886066537L;
	/**
	 * 
	 */
	
	/*
	 * GLOBAL Constant variables
	 */
	private final int MIXERVAL = 1;				//mixer value in array to set(1 = microphone, 2 = line-in)
	private final int BUTTON_WIDTH = 150;		//button width of 150 for all in "buttonsPanel"
	private final int BUTTON_HEIGHT = 50;		//button height of 50 for all buttons in"buttonsPanel"
	private final static int FRAME_WIDTH = 1200;
	private final static int FRAME_HEIGHT = 750;
	private final int HOME_BUTTON_OFFSET = 75;
	
	
    /*
     * Global variables for pitch detection(PitchDetectionHandler) and oscilloscope(OscilloscopeEventHandler) 
     */
    private AudioDispatcher dispatcher;			//runnable job the PitchProcessors are added to(
    private PitchEstimationAlgorithm algo;		//Algorithm to be used by the PitchProcessor PitchDetectionHandler

    /*
     * Variables used to trigger events by buttons
     */
    private boolean startRecording = false;				//when start button pressed this is set to true, if stop pressed then set to false
    private boolean userPrompt = false;					//triggers if it is appropriate for the GUI to prompt the user
    private boolean allNotesOn = false;					//variable to track which mode is active, if false then single note mode on else all notes mode is on


    /*
     * Variables for overall GUI
     */
    public static JFrame frame;							//frame for the ArtificalInstructor Object
    private JPanel buttonsPanel;						//panel to hold all of the buttons on the lesson player page
    private boolean homeLoaded, classLoaded, tunerLoaded; //statsLoaded;
    private boolean lessonSLoaded, createLesLoaded;
    
    /*
     * Variables for top half of the lessonPage GUI and the TunerPage GUI
     */
    TargetDataLine line;
    private JPanel tunerPanel, lPanel, createLessonP;			//two panels the user may user(lesson player and tuner player)
    private JScrollPane sp;						//scroll pane to apply to lessonTextArea to allow old info to be stored with new info
    private JTextArea lessonTextArea, tunerTextArea;	//different textareas used by the different screens
    private JButton start;					//buttons used by tuner panel and lesson panel
    private JButton stop, allNotes, singleNote;			//more buttons used by tuner/lesson
    private FretBoardPanel fretBoardTuner;				//FretBoardPanel that is used/updated by the tuner			
    private FretBoardPanel fretBoardPlayer;				//FretBoardPanel that is used/updated by the lesson page
    private int octave = 0;								//value to know where to display current note on fretboard	
    private double startTime = -1;						//to properly display time based on when user presses start, not when program loads			
    private JButton loadTuneFromLesson, loadHomeFromLesson;
    
    
    /*
     * Variables for the bottom half of the lessonPage GUI
     */
    private OscilloscopePanel oPanel;				//Custom panel, from this package, but edited from TarsoDSP to show soundwave
    private UpNextPanel upNext;
    private JPanel bottomP;
    
    /*
     * Variables for the homepage GUI
     */
    private JPanel homePage;
    private JButton loadTune, loadStat, loadClass, loadHomePage, closeProgram, loadLessonSelect, loadLessonCreate;
	private JPanel homeButtons;
    private JLabel homeLabel;
    
    
    /*
     * Variables for the lesson selector page
     */
    private JPanel lessonSelect;
    private JButton lesson1, lesson2, lesson3, lesson4, lesson5, backHome;
    
    /*
     * Variables for createLessonPanel
     */
    private createLessonPanel createPanel;
    private JButton homeFromCreate;
    
    
    /*
     * Variables used to create a new lesson
     * 		**HARD CODED HERE FOR NOW, EVENTUALLY THEY WILL BE STORED SOMEWHERE ELSE AND ONLY READ IN
     * 		  WHEN THE PROGRAM HAS A SPECIFIC LESSON CALLED
     */
   // private double [] nTimes = {3.00, 6.00, 9.00, 12.00, 15.00, 18.00, 21.00, 14.00, 16.00};			//times to play notes at
    private double [] nTimes = {1.00};			//times to play notes at
    private String [] nNotes = {"Z"};					//value of notes 
    private int [] nOcts = {3};										//octaves of notes
    private String [] nType = {"Whole"};
    private double [] nGracePeriod = {1};
    private FretLesson lessonOne;															//Lesson object to store operate using data
    private String lessonName = "lesson1";
    private double BPM = 60;
    
    /*
     * Variables for storing user attempt 
     */
    private String [] uNotes = {"NEW SET"};
    private int [] uOcts = {1000};
    private double [] uTimes = {1000};
    private double [] uFreq = {1000};
    private int userAttemptCounter = 0;
    private boolean newNote = false;
    
    
    
    JTextArea inputLesson;
    JButton selectCustLesson;
    
    //*************BEING ACTION LISTENERS*****************//
   
    /*
     * Actionlistener for START button
     * 	If variable startRecording is set to false 
     * 		>set it to true
     * 		>this will allow the program to know that the lesson should start 
     * 	 	 and input should be processed
     * 		>set userPrompt equal to false so GUI may be properly updated with output prompt
     *	Else
     * 		>simply output a prompt to the user to let them know the lesson has already started
    */
    private ActionListener startRec = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
           
        	classLoaded = true;
        	//statsLoaded = false;
        	homeLoaded = false;
        	tunerLoaded = false;
        	
        	   if (startRecording == false) {
                	startRecording = true;
                	userPrompt = false;
                	upNext.resetLesson();
                	upNext.setNotes();
                	upNext.repaint();
                	
                	try {
						performTasks();
					} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	lessonTextArea.setText("");
            	} 
        		else {
        			lessonTextArea.append("RECORDING ALREADY BEGAN!\n");
            		lessonTextArea.setCaretPosition(lessonTextArea.getDocument().getLength());
                
            	}
           }
        
    };

    /*
     * Actionlistener for STOP button
     * 		If startRecording is true( start button has been pressed )
     * 			>set startRecording to false
     * 			>set startTime = -1 so program will start lesson again when start pressed next
     * 		Else
     * 			>print out a prompt to the user saying recording is not in progress
     */
    private ActionListener stopRec = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
        	classLoaded = true;
        	//statsLoaded = false;
        	homeLoaded = false;
        	tunerLoaded = false;
        	
            	if (startRecording == true) {
            		startRecording = false;
            		userPrompt = false;
            		startTime = -1;
            		upNext.resetLesson();
            		upNext.setNotes();
            		upNext.repaint();
                
            		
            		line.stop();
            		line.close();
            		
            		lessonTextArea.setText("");
            	} 
            	else {
            		lessonTextArea.append("NO RECORDING IN PROGRESS!! PRESS START TO BEGIN\n");
            		lessonTextArea.setCaretPosition(lessonTextArea.getDocument().getLength());
                
            	}
            }
        
    };

    /*
     * ActionListener to determine which notes to draw on FretBoardPanel
     * 		If variable allNotesOn is true
     * 			>set allNotes to false
     * 			>remove the "Show single notes" button from buttonsPanel
     * 			>add the "Show all notes" button from the buttonsPanel
     * 			>repaint the panel and the frame
     * 		Else, allNotesOn is false
     * 			>set allNotesOn to true
     * 			>remove the "Show all notes" button
     * 			>add the "Show single notes" button
     * 			>repaint panel and the frame
     */
    private ActionListener allNotesPressed = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            
        	if(allNotesOn == true)
        	{
        		allNotesOn = false;
        		buttonsPanel.remove(singleNote);
        		buttonsPanel.add(allNotes);
        		allNotes.setVisible(true);
        		buttonsPanel.repaint();
        		frame.repaint();
        	}
        	else
        	{
        		allNotesOn = true;
        		buttonsPanel.remove(allNotes);
        		buttonsPanel.add(singleNote);
        		singleNote.setVisible(true);
        		buttonsPanel.repaint();
        		frame.repaint();
        	}
        }
    };
    
    /*
     * 	Loads the tuner page whenever it is triggered
     */
    private ActionListener loadTuner = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            
        	try {
				addProperPanels(1);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	
        }
    };
    
    /*
     * loads the classroom whenever it is triggered
     */
    private ActionListener loadClassroom = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		 
    		try {
				addProperPanels(3);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    		startRecording = false;
            userPrompt = false;
            startTime = -1;
            
            
    	}
    };
    
    /*
     * Loads the homepage whenever it is triggered
     */
    private ActionListener loadHome = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		try {
				addProperPanels(2);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		startRecording = false;
            userPrompt = false;
            startTime = -1;
            
    	}
    };
    
    /*
     * 	Loads the user stats page from homepage
     */
    private ActionListener loadStats = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    	}
    };
   
    /*
     * 	ActionListener to load lesson creation from homepage
     */
    private ActionListener loadCreate = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    		try {
				addProperPanels(5);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    	}
    };
    
    /*
     * Actionlistener to load the homepage from lessonCreate
     */
    private ActionListener cancelLessonCreation = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		try {
				addProperPanels(2);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}
    };
    
    /*
     * 	ActionListener to exit the program from the homepage
     */
    private ActionListener exitProgram = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    		//if line has been initiated then stop/close before exiting program
    		if(line != null){
    			
    			if(line.isOpen() == true)
    			{
    				if(line.isActive() == true){
    					line.stop();
    				}
    			
    				line.close();
    			}
    		}
    		System.exit(1);
    	}
    };
    
    /*
     * 	ActionListener to select level
     */
    private ActionListener custLevelSelector = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    		if(inputLesson.getText().equals("") == false && inputLesson.getText().equals(null) == false )
    		{
    			lessonName = inputLesson.getText();
    		
    			//System.out.println(lessonName);
    		
    			assignArrayValues();
    		
    			upNext.assignGP(nGracePeriod);
            	upNext.assignNotes(nNotes);
            	upNext.assignOcts(nOcts);
            	upNext.assignType(nType);
            	upNext.assignTimes(nTimes);
            
            	upNext.resetLesson();
    			upNext.setNotes();
    			upNext.repaint();
            
    			try {
					addProperPanels(3);
				} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	}
    };
   
    /*
     * 	ActionListener to select level
     */
    private ActionListener levelSelector = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		JButton x = (JButton) e.getSource();
    		
    		
    		lessonName = x.getText();
    		
    		
    		System.out.println(lessonName);
    		
    		assignArrayValues();
    		
    		upNext.assignGP(nGracePeriod);
            upNext.assignNotes(nNotes);
            upNext.assignOcts(nOcts);
            upNext.assignType(nType);
            upNext.assignTimes(nTimes);
            
            upNext.resetLesson();
    		upNext.setNotes();
    		upNext.repaint();
            
    		try {
				addProperPanels(3);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    	}
    };
    
    /*
     * 	ActionListener to load Lessons selection from homepage
     */
    private ActionListener loadLessonSel = new ActionListener() {
    	@Override
    	public void actionPerformed(final ActionEvent e){
    		
    		try {
				addProperPanels(4);
			} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		
    	}
    };
    //*************END ACTION LISTENERS*****************//

    
    /*
     * This method is used by the buttons in order to update frame with proper panel
     * 
     * Setting == button that triggered this method call
     * 		1) Tuner Pressed
     * 		2) Home Pressed
     * 		3) Classroom Pressed
	 *		4) Levels Pressed
	 *		5) Lesson Create Pressed
     */
    public void addProperPanels(int setting) throws LineUnavailableException, UnsupportedAudioFileException{
    	
    	/*
		 * Remove the correct panels from the frame depending on the boolean set
		 */
    	if( setting == 1)
    	{
    		if(classLoaded == true)
    		{
    			lPanel.setVisible(false);
    			bottomP.setVisible(false);
    			frame.remove(lPanel);
    			frame.remove(bottomP);
    			frame.setLayout(new GridLayout(1, 1));
    			tunerPanel.setVisible(true);
    			frame.add(tunerPanel);
    		}
    		
    		if( homeLoaded == true)
    		{
    			homePage.setVisible(false);
    			frame.remove(homePage);
    			frame.setLayout(new GridLayout(1, 1));
    			tunerPanel.setVisible(true);
    			frame.add(tunerPanel);
    		}
    		
    		homeLoaded = false;
			classLoaded = false;
			createLesLoaded = false;
			lessonSLoaded = false;
			tunerLoaded = true;
			
			performTasks();
    	}
    	else if( setting == 2 )
    	{
    		if(tunerLoaded == true)
    		{
    			tunerPanel.setVisible(false);
    			frame.remove(tunerPanel);

    			homePage.setVisible(true);
    			frame.setLayout(new GridLayout());
    			frame.add(homePage);
    		}
    		
    		if( classLoaded == true)
    		{
    			lPanel.setVisible(false);
    			bottomP.setVisible(false);
    			frame.remove(lPanel);
    			frame.remove(bottomP);
    			
    			homePage.setVisible(true);
    			frame.setLayout(new GridLayout());
    			frame.add(homePage);
    		}
    		
    		if(lessonSLoaded == true)
    		{
    			lessonSelect.setVisible(false);
    			frame.remove(lessonSelect);
    			
    			homePage.setVisible(true);
    			frame.setLayout(new GridLayout());
    			frame.add(homePage);
    		}
    		
    		if(createLesLoaded == true){
    			createLessonP.setVisible(false);
    			frame.remove(createLessonP);
    			
    			homePage.setVisible(true);
    			frame.setLayout(new GridLayout());
    			frame.add(homePage);
    		}
    		
    		tunerLoaded = false;
    		classLoaded = false;
    		lessonSLoaded = false;
    		createLesLoaded = false;
    		homeLoaded = true;
    		
    	}
    	else if( setting == 3)
    	{
    		if(tunerLoaded == true)
    		{
    			tunerPanel.setVisible(false);
    			frame.remove(tunerPanel);
    			
    			lPanel.setVisible(true);
    			bottomP.setVisible(true);
    			frame.setLayout(new GridLayout(2, 1));
    			
                
    			frame.add(lPanel);
    			frame.add(bottomP);
    		}
    		
    		if( homeLoaded == true)
    		{
    			homePage.setVisible(false);
    			frame.remove(homePage);

    			lPanel.setVisible(true);
    			bottomP.setVisible(true);
    			frame.setLayout(new GridLayout(2, 1));
    			
    			frame.add(lPanel);
    			frame.add(bottomP);
    			
    		}
    		
    		if( lessonSLoaded == true)
    		{
    			lessonSelect.setVisible(false);
    			frame.remove(lessonSelect);
    			
    			lPanel.setVisible(true);
    			bottomP.setVisible(true);
    			frame.setLayout(new GridLayout(2, 1));
    			
    			frame.add(lPanel);
    			frame.add(bottomP);
    		}
    		
    		tunerLoaded = false;
    		homeLoaded = false;
    		lessonSLoaded = false;
    		createLesLoaded = false;
    		classLoaded = true;
    		
    		performTasks();
    	}
    	else if(setting == 4)
    	{
    		if(homeLoaded == true)
    		{
    			homePage.setVisible(false);
    			frame.remove(homePage);
    			
    			lessonSelect.setVisible(true);
    			frame.add(lessonSelect);
    		}
    		
    		if(classLoaded == true)
    		{
    			lPanel.setVisible(false);
    			bottomP.setVisible(false);
    			frame.remove(lPanel);
    			frame.remove(bottomP);
    			
    			lessonSelect.setVisible(true);
    			frame.add(lessonSelect);
    		}
    		tunerLoaded = false;
    		homeLoaded = false;
    		classLoaded = false;
    		createLesLoaded = false;
    		lessonSLoaded = true;
    		
    	}
    	else if(setting == 5)
    	{
    		if(homeLoaded == true)
    		{
    			homePage.setVisible(false);
    			frame.remove(homePage);
    			
    			createLessonP.setVisible(true);
    			frame.add(createLessonP);
    			
    			createPanel.initCreation();
    			
    		}
    		
    		tunerLoaded = false;
    		homeLoaded = false;
    		classLoaded = false;
    		lessonSLoaded = false;
    		createLesLoaded = true;
    		
    	}
		
    	frame.pack();								//pack the frame
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);	//set the default size of the frame
        frame.setResizable(false);
        frame.setVisible(true);						//make visible to the user
        frame.repaint();
    
    }//end addProperPanels()


	//Main method taken from TarsoDSP pitchDetectionExample program and modified to fit my program
	public static void main(String...strings) throws InterruptedException,
    InvocationTargetException {

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                try {
					frame = new ArtificialInstructor();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			//initilize frame to have new object
                frame.pack();								//pack the frame
                frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);	//set the default size of the frame
                frame.setResizable(false);
                frame.setVisible(true);						//make visible to the user
                frame.repaint();
            }
        });
        
    }//end main

    
    /*
     * Similar code to pitchDetectionExample from TarsoDSP library, modified to work with my program
     * Creates the GUI, sets algorithm, and calls necessary functions to start input
     */
    public ArtificialInstructor() throws IOException {
    	
    	/*
    	 * set properties of the frame
    	 */
        this.setLayout(new GridLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Artifical Instructor");

        /*
         * Set global variable for the PitchProcessor, PitchDetectionHandler to user
         * 
         * currently: FFT_YIN has proved to be useful for accurate results
         * 		>Problems:
         * 			The pitch returned with FFT_YIN gives a return value both when 
         * 				1) a new note is initially strumed 
         * 				2) a note is ringing out
         * 		>Solutions:
         * 			1) could use the DYNAMIC_WAVELET but does not give as accurate readings
         * 			2) could require the hard coding of notes to account for how long a note will 
         * 			   ring and when user needs to silence
         */
        algo = PitchEstimationAlgorithm.FFT_YIN;			
        	//algo = PitchEstimationAlgorithm.DYNAMIC_WAVELET;
        	//algo = PitchEstimationAlgorithm.MPM;
        	//algo = PitchEstimationAlgorithm.YIN;

        assignArrayValues();
        
        /*
         * Init objects needed for the homepage Panel
         * Add objects to the homepage Panel
         * 		>Tuner Button
         * 		>Stats Button
         * 		>Classroom Button
         * 		>Exit Button
         * 		>Home page Label
         */
        loadTune = new JButton("Enter Tuner");					//set up tuner button
        loadTune.addActionListener(loadTuner);
        loadTune.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + HOME_BUTTON_OFFSET));
        
        loadStat = new JButton("View Stats");					//set up stat button
        loadStat.addActionListener(loadStats);
        loadStat.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + HOME_BUTTON_OFFSET));
        
        
        loadLessonCreate = new JButton("Create Lesson");
        loadLessonCreate.addActionListener(loadCreate);
        loadLessonCreate.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + HOME_BUTTON_OFFSET));
        
        loadLessonSelect = new JButton("Enter Classroom");
        loadLessonSelect.addActionListener(loadLessonSel);
        loadLessonSelect.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + HOME_BUTTON_OFFSET));
        
        /*
        loadClass = new JButton("Enter Classroom");				//set up classroom button
        loadClass.addActionListener(loadClassroom);
        loadClass.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + HOME_BUTTON_OFFSET));
        lessonSLoaded = false;
        */
        
        classLoaded = false;
        
        closeProgram = new JButton("Close Program");			//set up exit button
        closeProgram.addActionListener(exitProgram);
        closeProgram.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT + HOME_BUTTON_OFFSET));
        
        
        homeLabel = new JLabel("ARTIFICIAL INSTRUCTOR");		//set up label for home page
        
        BufferedImage homeLogo = ImageIO.read(new File("src/HomeLogo1.jpg"));
        homeLabel = new JLabel(new ImageIcon(homeLogo));
        
        homeButtons = new JPanel();
        homeButtons.setLayout(new GridLayout());
        homeButtons.add(loadTune);
        homeButtons.add(loadLessonCreate);
        homeButtons.add(loadLessonSelect);
        //homeButtons.add(loadClass);
        homeButtons.add(closeProgram);
        
        homePage = new JPanel();								//set up main panel for homepage
        homePage.setLayout(new BorderLayout());
        
        homePage.setBackground(Color.WHITE);
        homePage.add(homeLabel, BorderLayout.CENTER);								//add buttons
        homePage.add(homeButtons, BorderLayout.AFTER_LAST_LINE);
        
        homePage.setVisible(true);
        
        
        /*
         * 1) Init the TextArea object and dont allow users to edit 
         * 		Create custom font in order to have a larger font size 
         * 2) Init the ScrollPane object which will allow the user to scroll 
         * 		through the output in textarea
         */
        lessonTextArea = new JTextArea();						//init textArea1
        lessonTextArea.setLineWrap(true);
        lessonTextArea.setEditable(false);						//dont allow user to edit this
        Font font1 = lessonTextArea.getFont();					//get font, manipulate it, and set new font
        float fSize = font1.getSize() ;
        lessonTextArea.setFont(font1.deriveFont(fSize));
        sp = new JScrollPane(lessonTextArea);					//init sp, using textArea1 just created
        sp.setPreferredSize(new Dimension(200, 100));			//set the size of the scroll pane 
        sp.setVisible(true);

        
        /*
         * Init the button to be used to trigger single note mode
         * Add the action listener that starts the input and tracks correct variables
         * set a prefered size to match other buttons
         */
        start = new JButton("Start");
        start.addActionListener(startRec);
        start.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        start.setForeground(Color.green);
        start.setVisible(true);
        
        
        /*
         * Init the button to be used to trigger stop button for input
         * Add the action listener that stops and makes correct variable changes
         * set a prefered size to match other buttons
         */
        stop = new JButton("Stop");
        stop.addActionListener(stopRec);
        stop.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        stop.setForeground(Color.red);
        stop.setVisible(true);
        
        
        /*
         * Init the button to be used to trigger all note mode
         * Add the action listener that changes mode boolean 
         * set a prefered size to match other buttons
         */
        allNotes = new JButton("Show All Notes");
        allNotes.addActionListener(allNotesPressed);
        allNotes.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        allNotes.setVisible(true);
    
        /*
         * Init the button to be used to trigger single note mode
         * Add the action listener that changes mode boolean 
         * set a prefered size to match other buttons
         * set Visible and add to the buttons panel 
         */
        singleNote = new JButton("Show Single Note");
        singleNote.addActionListener(allNotesPressed);
        singleNote.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        singleNote.setVisible(false);
        
        loadTuneFromLesson = new JButton("Tuner");
        loadTuneFromLesson.addActionListener(loadTuner);
        loadTuneFromLesson.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        loadTuneFromLesson.setVisible(true);
        
        loadHomeFromLesson = new JButton("Home");
        loadHomeFromLesson.addActionListener(loadHome);
        loadHomeFromLesson.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        loadHomeFromLesson.setVisible(true);
        
        /*
         * Init the JPanel to hold all of the buttons and set visible
         * Add all necessary buttons
         */
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout());
        buttonsPanel.add(start);
        buttonsPanel.add(stop);
		buttonsPanel.add(loadTuneFromLesson);
		buttonsPanel.add(loadHomeFromLesson);
		buttonsPanel.add(allNotes);
        buttonsPanel.setVisible(true);
        
        /*
         * Init the FretBoardPanel used by the lesson player
         * Set the current note value to nothing to start
         */
        fretBoardPlayer = new FretBoardPanel();
        fretBoardPlayer.setNoteVal("");

        /*
         * Init the JPanel that holds the main screen
         * 		-Set the layout
         * 		-Add (the scrollpane(w/ textArea), panel with buttons, and FretBoardPanel used by player
         * 		-repaint and set visible
         */
        lPanel = new JPanel();
        lPanel.setLayout(new BorderLayout());
        lPanel.add(sp, BorderLayout.BEFORE_LINE_BEGINS);
        lPanel.add(buttonsPanel, BorderLayout.PAGE_START);
        lPanel.add(fretBoardPlayer, BorderLayout.CENTER);
        lPanel.repaint();

        /* 
         * Create/Init the content and the panel for the bottom half of the lesson screen
         * 		This will include the:
         * 			>OscilloscopePanel 
         * 			>UpNextPanel
         */
        oPanel = new OscilloscopePanel();	// Init the panel to be used to show the soundwave of input
        upNext = new UpNextPanel(nNotes, nType, nTimes, nOcts, nGracePeriod);
        
        bottomP = new JPanel();
        bottomP.setLayout(new GridLayout(1, 2));
        bottomP.add(oPanel);
        bottomP.add(upNext);

        
        /*
         * Init the textArea to display the notes read by the tuner
         * 		-change size of font in order to have notes appear really big 
         * 		-set size of the box in order to give FretBoardPanel used by the tuner has enough space
         */
        tunerTextArea = new JTextArea();
        Font fontTuner = tunerTextArea.getFont();
        tunerTextArea.setDisabledTextColor(Color.RED);
        float fTunerSize = fontTuner.getSize() + 200;
        tunerTextArea.setFont(font1.deriveFont(fTunerSize));
        tunerTextArea.setBorder(BorderFactory.createEmptyBorder(-200, 425, 0, 0));
        tunerTextArea.setVisible(true);
        tunerTextArea.setPreferredSize(new Dimension(1200, 300));
        /*
         * Initialize back button to be used by the tunerPanel
         * Add the actionlistener to the button
         * Add the button to the buttonsPanel
         */
        loadHomePage = new JButton("Home");
        loadHomePage.addActionListener(loadHome);
        loadHomePage.setPreferredSize(new Dimension(1200, BUTTON_HEIGHT));
        
        buttonsPanel.add(loadHomePage);
        
        /*
         * Initialize the FretBoardPanel to be used by the tuner, set first note to nothing to display
         */
        fretBoardTuner = new FretBoardPanel();
        fretBoardTuner.setNoteVal("");

        JPanel fretBoardTunerPH = new JPanel();
        fretBoardTunerPH.setLayout(new GridLayout());
        fretBoardTunerPH.setBorder(BorderFactory.createEmptyBorder(0, 100, 0 , 0));
        fretBoardTunerPH.add(fretBoardTuner);
        fretBoardTunerPH.setVisible(true);
        
        
        /*
         * 1) Init the panel that is used by the tuner functionality
         * 		-set the layout
         * 
         * 2) Add the necessary elements to the tunerPanel
         * 		-fretBoardTuner, in the center of the panel
         * 		-tunerTextArea, to the left side of the panel
         * 		-back button to the bottom of the panel
         */
        tunerPanel = new JPanel();
        tunerPanel.setLayout(new BorderLayout());
        tunerPanel.add(fretBoardTunerPH, BorderLayout.CENTER);
        tunerPanel.add(tunerTextArea, BorderLayout.BEFORE_FIRST_LINE);
        tunerPanel.add(loadHomePage, BorderLayout.PAGE_END);
        
        
        /*
         * Add lessonSelection panel and components
         */
        lessonSelect = new JPanel();
        lessonSelect.setLayout(new GridLayout());
        
        inputLesson = new JTextArea();
        selectCustLesson = new JButton();
        selectCustLesson.addActionListener(custLevelSelector);
        selectCustLesson.setText("Select Entered Lesson");
        
        lesson1 = new JButton();
        lesson1.addActionListener(levelSelector);
        lesson1.setText("lesson1");
        
        lesson2 = new JButton();
        lesson2.addActionListener(levelSelector);
        lesson2.setText("lesson2");
        
        lesson3 = new JButton();
        lesson3.addActionListener(levelSelector);
        lesson3.setText("lesson3");
        
        lesson4 = new JButton();
        lesson4.addActionListener(levelSelector);
        lesson4.setText("lesson4");
        
        lesson5 = new JButton();
        lesson5.addActionListener(levelSelector);
        lesson5.setText("lesson5");
        
        backHome = new JButton();
        backHome.addActionListener(loadHome);
        backHome.setText("Return Home");
        
        lessonSelect.add(inputLesson, BorderLayout.PAGE_START);
        lessonSelect.add(selectCustLesson, BorderLayout.PAGE_START);
        lessonSelect.add(lesson1);
        lessonSelect.add(lesson2);
        lessonSelect.add(lesson3);
        lessonSelect.add(lesson4);
        lessonSelect.add(lesson5);
        lessonSelect.add(backHome);
        
        createLessonP = new JPanel();
        createLessonP.setLayout(new BorderLayout());
        
        createPanel = new createLessonPanel();
        homeFromCreate = new JButton("Return Home");
        homeFromCreate.setPreferredSize(new Dimension(100, 100));
        homeFromCreate.addActionListener(cancelLessonCreation);
        
        createLessonP.add(createPanel, BorderLayout.CENTER);
        createLessonP.add(homeFromCreate, BorderLayout.AFTER_LAST_LINE);
        
        /*
         * add homepage panel to frame
         */
        this.add(homePage);
        homeLoaded = true;
        classLoaded = false;
        tunerLoaded = false;
        lessonSLoaded = false;
        createLesLoaded = false;
        //statsLoaded = false;
        
       
    }//end ArtificialInstructor() constructor


    /*
     * 	Runs real time playback while the user is playing
     * 	Uses a different format than analysis in order to output the correct sound
     */
    public void playBack() {
            AudioFormat playBackFormat = new AudioFormat(4096, 16, 1, true, true);		//input & output
            
            
            /*
             * Variables needed for input
             */
            ByteArrayOutputStream out; 				//InputStream reads data into this object
            int numBytesRead; 						//stores the number of bytes read from stream, to know how to allocate memory
            byte[] data; 							//Stores the data that is converted from ByteArrayOutputStream "out"
            TargetDataLine tdl = null; 			//Init object to create TargetDataLine for incoming data
            DataLine.Info infoIN; 					//input & output
            
            /*
             * Variables needed for output
             */
            byte audioData[]; 						//Array to store values for output of data to call the thread
            SourceDataLine sdl = null; 				//Init object to create SourceDataLine for outgoing data
            AudioInputStream ais; 					//output
            DataLine.Info infoOUT; 					//output

            /*
             *  1)sets up targetDataLine to read in using specific format
             *  2)gets default line in order to read in/play out
             *  3)opens the targetDataLine using specific line
             *  4)initializes arrays and output streams to be used
             *  5)tries, to read in data from targetDataLine into byteArrayOutputStream then converts to byte array
             *  6)sets up sourceDataLine
             *  7)creates job for playThread
             *  8)creates new playThread and runs using sourceDataLine and audio data read in
             */
            try {
                tdl = AudioSystem.getTargetDataLine(playBackFormat);

                infoIN = new DataLine.Info(TargetDataLine.class, playBackFormat);
                tdl = (TargetDataLine) AudioSystem.getLine(infoIN);
                tdl.open(playBackFormat);
                tdl.start();

                out = new ByteArrayOutputStream();
                data = new byte[tdl.getBufferSize()/4];

                try {
                	/*
                	 * Try and read in data, then set data to array audioData
                	 * reset ByteArrayOutputStream to be used again
                	 */
                    numBytesRead = tdl.read(data, 0, 1024);
                    out.write(data, 0, numBytesRead);
                    audioData = out.toByteArray();
                    out.reset();

                    /*
                     * Start setting up to output data and create/call thread
                     */
                    InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
                    ais = new AudioInputStream(byteArrayInputStream, playBackFormat, audioData.length / playBackFormat.getFrameSize());
                    infoOUT = new DataLine.Info(SourceDataLine.class, playBackFormat);
                    sdl = (SourceDataLine) AudioSystem.getLine(infoOUT);
                    sdl.open(playBackFormat, 1024);

                    /*
                     * 1) Create runnable job for thread using inputs
                     * 		SourceDataLine to know where to play out to
                     * 		AudioInputSteam to be able to send the data
                     * 2) Create and start thread
                     */
                    Runnable threadJob = new PlayBackThread(sdl, ais);
                    Thread t1 = new Thread(threadJob);

                    t1.start();

                } catch (Exception e) {
                    e.printStackTrace();		//print for exceptions
                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();			//print error for errors in the line input/output 
            }

        } //end playBack()


    //Modified method from pitchDetectionExample from TarsoDSP library
    private void performTasks() throws LineUnavailableException,
        UnsupportedAudioFileException {

            Mixer.Info[] availMixers = AudioSystem.getMixerInfo();
            Mixer mixer = AudioSystem.getMixer(availMixers[MIXERVAL]);


            if (dispatcher != null) {
                dispatcher.stop();
            }

            //float sampleRate = 8192;  
            float sampleRate = 4096; //best sample size
            //float sampleRate = 1024;
            int bufferSize = 1024;
            int overlap = 0;

            AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, true);
            //final AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);

            final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
            
            line = (TargetDataLine) mixer.getLine(dataLineInfo);
            //final int numberOfSamples = bufferSize;


            //CHANGED LINE.OPEN -- FIX IF DOESNT WORK FOR TESTING WITH BASS
            //line.open(format, numberOfSamples);
            line.open(format, (int) sampleRate);

            line.start();
            AudioInputStream stream = new AudioInputStream(line);
            JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);


            // create a new dispatcher and add the pitchprocessor and oscilloscope to it
            dispatcher = new AudioDispatcher(audioStream, bufferSize, overlap);
            dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, this));
            dispatcher.addAudioProcessor(new Oscilloscope(this));

            //start thread for dispatcher
            new Thread(dispatcher, "Audio dispatching").start();
            
        } //end performTasks


    
    	//Modified from TarsoDSP pitchDetectionExample from TarsoDSP example by adding conditionals and output msg
    	@Override
    	public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
    	
    	
        //check if valid screens open
        if(tunerLoaded == true || classLoaded == true)
        {
        
            /*
            *   If startTime equals -1 Then the lesson has not been initalized
            */
            if(startTime == -1)
            {
            	
                /*
                *   If startRecording is set to true then user has pressed the start button 
                *    
                *       Must initialize the lesson object and update proper panels
                *   NOTE:
                *       startRecording can only be set to true if classLoaded is true as well
                */
                if(startRecording == true)
                {
                    /*
                    *   Initalize the lesson data structure with necessary information
                    *   Initialize the fretBoardPlayer panel with the lesson information
                    */
                    startTime = audioEvent.getTimeStamp();
                  //call the JSON parser
                    
                    lessonOne = new FretLesson(startTime, nTimes, nNotes, nOcts, nType, nGracePeriod, BPM);
                   
                    upNext.assignGP(nGracePeriod);
                    upNext.assignNotes(nNotes);
                    upNext.assignOcts(nOcts);
                    upNext.assignType(nType);
                    upNext.assignTimes(nTimes);
                    
                    uNotes = new String[1000];
                    uNotes[0] = "NEW SET";
                    
                    uOcts = new int[1000];
                    uOcts[0] = 1000;
                    
                    uTimes = new double[1000];
                    uTimes[0] = 1000;
                    
                    uFreq = new double[1000];
                    uFreq[0] = 1000;
                    userAttemptCounter = 0;
                    
                    fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                    
                    if(line.isActive() == false)
                    {
                    	line.start();
                    }
                
                    /*
                    *   Initalize the upNext panel with correct information
                    */
                    upNext.setCounter(lessonOne.getLesCounter());
                    upNext.setDotColor(lessonOne.getNoteColor());
                    upNext.repaint();
                }
            }
            else
            {
                /*
                *   Check if lesson object is initialized
                */
                if(lessonOne != null){
            		
                    /*
                    *   Check for lesson completion
                    *       IF true reset data
                    */
                    if(lessonOne.getLessonPlace(audioEvent.getTimeStamp()) == true )
            		{
                    	if(line.isActive() == true)
                    	{
                    		line.stop();
                    	}
                    	uNotes[userAttemptCounter] = "LAST";
                    	boolean checkDone = false;
                    	
                    	if(uNotes[0].equals("NEW SET") == false){
                        	for(int xxx = 0; xxx < uNotes.length; xxx++){
                        		if(uNotes[xxx].equals("LAST") == true)
                        		{
                        			checkDone = true;
                        			break;
                        		}
                        		
                        		//lessonTextArea.append("/n");
                        		//lessonTextArea.append("Note: " + uNotes[xxx], "\n Time: " + uTimes[xxx] + "\n Oct:" + uOcts[xxx]);
                        		//System.out.println("Note "+ uNotes[xxx] );
                        		//System.out.println("Oct "+ uOcts[xxx] );
                        		//System.out.println("Time "+ uTimes[xxx] );
                        		//System.out.println("Freq "+ uFreq[xxx] );
                        		System.out.println(xxx );
                        		
                        		/*
                        		 * Add Analyze data part here for lesson one
                        		 */
                        	}
                        }//end if 
                    	
                    	lessonOne.produceFeedback(uNotes, uOcts, uTimes);
                    	
            			startRecording = false;
            			startTime = -1;
            			lessonOne = null;
            			userPrompt = false;
                        
            			upNext.lessonFinished();
                        upNext.resetLesson();
                        
                        line.stop();
                        line.close();
                        
            		}//end check for completion
            	}//end lesson check
                
                
                
                /*
                *   If startRecording is true then lesson has been started, must update the panels
                *   NOTE:
                *       startRecording can only be set to true if classLoaded is true as well
                */
                if(startRecording == true)
                {
                    /*
                    *   Increment the counter held by the lesson object
                    *   Update the panel with the correct; MODE, LESSON VAL, LESSON OCT, LESSON COLOR
                    */
                    lessonOne.incrementCnt(audioEvent.getTimeStamp());
                    fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                    fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
                    fretBoardPlayer.setLesOctave(lessonOne.getNoteOct());
                    fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
                    fretBoardPlayer.repaint(); 
    		
                    /*
                    *   Update the counter and display color of the upNext panel with lesson info
                    */
                    upNext.setCounter(lessonOne.getLesCounter());
                    upNext.setDotColor(lessonOne.getNoteColor());
                    upNext.repaint();
                    
                }
                else
                {
                    /*
                    *   Reset Panels and repaint them because lesson is no longer in progress
                    *       Either STOP button pressed or lesson ended
                    */
                    fretBoardPlayer.resetFretBoard(fretBoardPlayer.getGraphics());
                    fretBoardPlayer.repaint();
                    upNext.resetLesson();
                    upNext.repaint();
                }
                
            }//end check for initialization/update
            
            
            
            
             
             
            /*
            *   Check for valid input from USER
            *       IF valid input(getPitch() != -1)
            *           then update the correct panels with information and process data
            *       Else
            *           then update the correct panels to reflect no input
            *            >>>>> process data if lesson requires <<<<<
            */
            if( pitchDetectionResult.getPitch() != -1)
            {
                
            	/*
                 *   set the values of PITCH and NOTE VALUE to the correct variables to be used
                 */
                 float pitch = pitchDetectionResult.getPitch();
                 String note = getNoteValue(pitch);
                 
                /*
                *   Update the correct panel with the input information
                *       PROCESS DATA IF user is currently playing a lesson
                */
                if(tunerLoaded == true)
                {
                    if (note.equals("COULD NOT RECOGNIZE INPUT") == false || note.equals("REST") == false) 
                    {
                        tunerTextArea.setText("");
                        tunerTextArea.setText("\n");
                        tunerTextArea.append(note + octave);
                        fretBoardTuner.setOctave(octave);
                        fretBoardTuner.setNoteVal(note);
                        fretBoardTuner.repaint();
                    }//end valid note
                }
                else if(classLoaded == true)
                {
                    /*
                    *   Determine if attempt is in progress, IF TRUE
                    *       1) Update the text area to reflect the input
                    *       2) Update the upNext panel to show correct times
                    *       3) Check for the display mode and update FretBoardPlayer accordingly
                    */
                    if(startRecording == true)
                    {
                    	
                    	if(newNote == false)
                    	{
                    		newNote = true;
                    	
                    		
                    		/*
                    		 *   Update text area and upNext panel
                    		 */
                    		//lessonTextArea.append("SCORE " + lessonOne.getScore() + ")\n");
                    		lessonTextArea.setCaretPosition(lessonTextArea.getDocument().getLength());
                        
                    		uNotes[userAttemptCounter] = note;
                    		uOcts[userAttemptCounter] = octave;
                    		uTimes[userAttemptCounter] = audioEvent.getTimeStamp();
                    		uFreq[userAttemptCounter] = pitch;
                    		userAttemptCounter++;
                    		
                    	}//end newNote == false
                    	
                    	/*
                		 *   Update the upNext Panel with the current counter and lesson color
                		 */
                		upNext.setCounter(lessonOne.getLesCounter());
                		upNext.setDotColor(lessonOne.getNoteColor());
                		upNext.setTimeUntil(audioEvent.getTimeStamp(), startTime);
                		upNext.repaint();
                    
                		/*
                		 *   Update the lesson object using current time and check for accuracy
                		 *   Update the fretBoardPlayer with the current Lesson information
                		 */
                		
                		lessonOne.incrementCnt(audioEvent.getTimeStamp());
  //              		lessonOne.checkNoteAccuracy(audioEvent.getTimeStamp(), note, octave);
                		fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                		fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
                		fretBoardPlayer.setLesOctave(lessonOne.getNoteOct());
                		fretBoardPlayer.setLesType(lessonOne.getNoteType());
                		fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
                		fretBoardPlayer.repaint(); 
                    
                		/*
                		 *   Update the fretBoardPlayer with the User input
                		 *   Repaint to reflect changes
                		 */
                		fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                		fretBoardPlayer.setNoteVal(note);
                		fretBoardPlayer.setOctave(octave);
                		fretBoardPlayer.repaint();
                    }
                    else
                    {
                        /*
                        *   Lesson has not been started, prompt the user, but only once
                        */
                        if (userPrompt == false) 
                        {
                        	lessonTextArea.append("RECORDING NOT IN PROGRESSS, PRESS START BUTTON\n");
                            userPrompt = true;
                        }
                        
                        
                        
                    }//end conditional for current state of attempt
                    
                }//end conditionals for current screen
            }
            else
            {
            	
            	newNote = false;
            	
                /*
                *   Check for update even if no input read, for LESSON
                */
                if(classLoaded == true)
                {
                    /*
                    *   If lesson attempt in progress uptdate the Panels
                    */
                    if(startRecording == true)
                    {
                        /*
                        *   Increment the lesson counter
                        *   Check for accuracy
                        *   repaint fretBoardPlayer
                        */
                    	
                        lessonOne.incrementCnt(audioEvent.getTimeStamp());
                        fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
                        fretBoardPlayer.setLesOctave(lessonOne.getNoteOct());
                        fretBoardPlayer.setLesType(lessonOne.getNoteType());
                        fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
                        fretBoardPlayer.repaint(); 
        		
                       
                        if(userAttemptCounter != 0){
                        	if(uNotes[userAttemptCounter - 1].equals("REST") == false && uTimes[userAttemptCounter - 1] + 0.02 < audioEvent.getTimeStamp()){
                        		/*
                                 * Way to ensure that the user isnt constanly losing points for same rest value
                                 */
       //                         lessonOne.checkNoteAccuracy(audioEvent.getTimeStamp(), "REST", -1);
                                
                        		/*
                        		 * Store the user attempt info, to show nothing was played when they were supposed to
                        		 */
                        		uNotes[userAttemptCounter] = "REST";
                        		uOcts[userAttemptCounter] = 1;
                        		uTimes[userAttemptCounter] = audioEvent.getTimeStamp();
                        		uFreq[userAttemptCounter] = -1;
                        		userAttemptCounter++;
                        	}
                        }
                        
                      
                        /*
                        *   Update the upNext panel with current information
                        */
                        upNext.setTimeUntil(audioEvent.getTimeStamp(), startTime);
                        upNext.setCounter(lessonOne.getLesCounter());
                        upNext.setDotColor(lessonOne.getNoteColor());
                        upNext.repaint();
                    }
                    
                    
                }//end page check
                
            }//end check for valid input
            
        }//end check for valid screens
        
        frame.repaint();            //UPDATE THE FRAME 
        
    	
    }//end handlePitch

  
    
    //Modified from TarsoDSP oscilloscopeExample program by adding thread creation to run playback
    @Override
    public void handleEvent(float[] data, AudioEvent event) {
        if (startRecording == true) {
            
        	/*
        	 * for loop to increase the audio data so the wave is more noticable
        	 */
        	for (int i = 0; i < data.length; i++) {
                data[i] = data[i] * 5;
            }
            
            oPanel.paint(data, event);			//paint the panel using the new data
            oPanel.repaint();					//repaint to display to the user
            frame.repaint();
            /*
             * Create new thread and override run method
             * Set thread to call playBack method to do real time output
             * 		>Problem: current delay with output, also causing delays with graph 
             * Start the thread to run
             */
            new Thread(new Runnable(){
            	@Override
            	public void run(){
            		playBack();
            	}
            }).start();
            
        }
    	
        
    }

    
    
    /*
     * Pitch Values for notes taken from website "SeventhString"
     *  URL: http://www.seventhstring.com/resources/notefrequencies.html 
     */
    public String getNoteValue(float inputPitch) {

            String note = null;
            float pitch = inputPitch; //offsetTuning set during tuning process to allow for proper pitch detection. 

            /*
             * Set of conditionals to get pitch.
             * --Outer conditionals determine which octave the input pitch belongs to
             * --Inner conditionals determine which note in that octive the pitch is
             */
            //Octave 0
            if (pitch >= 0 && pitch <= 31.785) {
                octave = 0;
                if (pitch >= 0 && pitch <= 16.835) {
                    note = "C";
                } else if (pitch >= 16.836 && pitch <= 17.835) {
                    note = "C#";
                } else if (pitch >= 17.836 && pitch <= 18.900) {
                    note = "D";
                } else if (pitch >= 18.901 && pitch <= 20.025) {
                    note = "Eb";
                } else if (pitch >= 20.026 && pitch <= 21.215) {
                    note = "E";
                } else if (pitch >= 21.216 && pitch <= 22.475) {
                    note = "F";
                } else if (pitch >= 22.476 && pitch <= 23.810) {
                    note = "F#";
                } else if (pitch >= 23.811 && pitch <= 25.230) {
                    note = "G";
                } else if (pitch >= 25.231 && pitch <= 26.730) {
                    note = "G#";
                } else if (pitch >= 26.731 && pitch <= 28.320) {
                    note = "A";
                } else if (pitch >= 28.321 && pitch <= 30.005) {
                    note = "Bb";
                } else if (pitch >= 30.006 && pitch <= 31.785) {
                    note = "B";
                } //end inner-conditionals
            }
            //Octave 1
            else if (pitch >= 31.786 && pitch <= 63.575) {
                octave = 1;
                if (pitch >= 31.786 && pitch <= 33.675) {
                    note = "C";
                } else if (pitch >= 33.676 && pitch <= 35.680) {
                    note = "C#";
                } else if (pitch >= 35.681 && pitch <= 37.800) {
                    note = "D";
                } else if (pitch >= 37.801 && pitch <= 40.045) {
                    note = "Eb";
                } else if (pitch >= 40.046 && pitch <= 42.425) {
                    note = "E";
                } else if (pitch >= 42.426 && pitch <= 44.950) {
                    note = "F";
                } else if (pitch >= 44.951 && pitch <= 47.625) {
                    note = "F#";
                } else if (pitch >= 47.626 && pitch <= 50.455) {
                    note = "G";
                } else if (pitch >= 50.456 && pitch <= 53.455) {
                    note = "G#";
                } else if (pitch >= 53.456 && pitch <= 56.635) {
                    note = "A";
                } else if (pitch >= 56.636 && pitch <= 60.005) {
                    note = "Bb";
                } else if (pitch >= 60.006 && pitch <= 63.575) {
                    note = "B";
                } //end inner-conditionals
            }
            //Octave 2
            else if (pitch >= 63.576 && pitch <= 127.150) {
                octave = 2;
                if (pitch >= 63.576 && pitch <= 67.355) {
                    note = "C";
                } else if (pitch >= 67.356 && pitch <= 71.360) {
                    note = "C#";
                } else if (pitch >= 71.361 && pitch <= 75.600) {
                    note = "D";
                } else if (pitch >= 75.601 && pitch <= 80.095) {
                    note = "Eb";
                } else if (pitch >= 80.096 && pitch <= 84.860) {
                    note = "E";
                } else if (pitch >= 84.861 && pitch <= 89.905) {
                    note = "F";
                } else if (pitch >= 89.906 && pitch <= 95.250) {
                    note = "F#";
                } else if (pitch >= 96.251 && pitch <= 100.900) {
                    note = "G";
                } else if (pitch >= 100.901 && pitch <= 106.900) {
                    note = "G#";
                } else if (pitch >= 106.901 && pitch <= 113.250) {
                    note = "A";
                } else if (pitch >= 113.251 && pitch <= 120.000) {
                    note = "Bb";
                } else if (pitch >= 120.001 && pitch <= 127.150) {
                    note = "B";
                } //end inner-conditionals

            }
            //Octave 3
            else if (pitch >= 127.151 && pitch <= 254.250) {
                octave = 3;
                if (pitch >= 127.151 && pitch <= 134.700) {
                    note = "C";
                } else if (pitch >= 134.701 && pitch <= 142.700) {
                    note = "C#";
                } else if (pitch >= 142.701 && pitch <= 151.200) {
                    note = "D";
                } else if (pitch >= 151.201 && pitch <= 160.200) {
                    note = "Eb";
                } else if (pitch >= 160.201 && pitch <= 169.700) {
                    note = "E";
                } else if (pitch >= 169.701 && pitch <= 179.800) {
                    note = "F";
                } else if (pitch >= 179.801 && pitch <= 190.500) {
                    note = "F#";
                } else if (pitch >= 190.501 && pitch <= 201.850) {
                    note = "G";
                } else if (pitch >= 201.851 && pitch <= 213.850) {
                    note = "G#";
                } else if (pitch >= 213.851 && pitch <= 226.550) {
                    note = "A";
                } else if (pitch >= 226.551 && pitch <= 240.000) {
                    note = "Bb";
                } else if (pitch >= 240.001 && pitch <= 254.250) {
                    note = "B";

                } //end inner-conditionals
            }
            //Octave 4
            else if (pitch >= 254.251 && pitch <= 508.600) {
                octave = 4;
                if (pitch >= 254.251 && pitch <= 269.400) {
                    note = "C";
                } else if (pitch >= 269.401 && pitch <= 285.450) {
                    note = "C#";
                } else if (pitch >= 285.451 && pitch <= 302.400) {
                    note = "D";
                } else if (pitch >= 302.401 && pitch <= 320.350) {
                    note = "Eb";
                } else if (pitch >= 320.351 && pitch <= 339.400) {
                    note = "E";
                } else if (pitch >= 339.401 && pitch <= 359.600) {
                    note = "F";
                } else if (pitch >= 359.601 && pitch <= 381.000) {
                    note = "F#";
                } else if (pitch >= 381.001 && pitch <= 403.650) {
                    note = "G";
                } else if (pitch >= 403.651 && pitch <= 427.650) {
                    note = "G#";
                } else if (pitch >= 427.651 && pitch <= 453.100) {
                    note = "A";
                } else if (pitch >= 453.101 && pitch <= 480.050) {
                    note = "Bb";
                } else if (pitch >= 480.051 && pitch <= 508.600) {
                    note = "B";

                } //end inner-conditionals

            }
            else if(pitch == -1)
            {
            	note = "REST";
            }
            else 
            {
                note = "COULD NOT RECOGNIZE INPUT";
            } //end outer-conditionals

            return note;

        } //end getNoteValue()

    public void assignArrayValues()
    {
    	JSONParser parser = new JSONParser();
    	int assignCnt = 0;
    	
    	try {
    		
    		File f = new File("src/Lessons/"+ lessonName + ".json");
    		//Object obj = parser.parse(new FileReader("/Users/Shawn/Desktop/Lessons/"+lessonName+".json") );
    		Object obj = parser.parse(new FileReader( f) );

    		JSONObject jsonObject = (JSONObject) obj;

    		
    		// loop array
    		JSONArray notes = (JSONArray) jsonObject.get("Notes");
    		Iterator<String> iterator = notes.iterator();
    		
    		int lenNUM = notes.size();
    		nNotes = new String[lenNUM];
    		nTimes = new double[lenNUM];
    		nOcts = new int[lenNUM];
    		nType = new String[lenNUM];
    		nGracePeriod = new double[lenNUM];
    		
    		while (iterator.hasNext()) {
    			nNotes[assignCnt] = iterator.next();
    			assignCnt++;
    		}
    		assignCnt = 0;
    		
    		JSONArray times = (JSONArray) jsonObject.get("Times");
    		Iterator<Double> iteratorD = times.iterator();
    		while (iteratorD.hasNext()) {
    			nTimes[assignCnt] = iteratorD.next();
    			assignCnt++;
    		}
    		assignCnt = 0;
    		
    		JSONArray octs = (JSONArray) jsonObject.get("Octaves");
    		Iterator<String> iteratorI = octs.iterator();
    		while (iteratorI.hasNext()) {
    			String temp = iteratorI.next();
    			
    			if(temp.equals("0")){
    				nOcts[assignCnt] = 0;
    			}
    			else if(temp.equals("1"))
    			{
    				nOcts[assignCnt] = 1;
    			}
    			else if(temp.equals("2"))
    			{
    				nOcts[assignCnt] = 2;
    			}
    			else if(temp.equals("3"))
    			{
    				nOcts[assignCnt] = 3;
    			}
    			else 
    			{
    				nOcts[assignCnt] = 4;
    			}
    	
    			assignCnt++;
    		}
    		assignCnt = 0;
    		
    		JSONArray types = (JSONArray) jsonObject.get("Types");
    		while (iterator.hasNext()) {
    			nType[assignCnt] = iterator.next();
    			assignCnt++;
    		}
    		assignCnt = 0;
    		
    		JSONArray gp = (JSONArray) jsonObject.get("GracePeriods");
    		iteratorD = gp.iterator();
    		while (iteratorD.hasNext()) {
    			nGracePeriod[assignCnt] = iteratorD.next();
    			assignCnt++;
    		}
    		assignCnt = 0;
    		
    		
    		
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	
    }
}//end ArtificalInstructor


