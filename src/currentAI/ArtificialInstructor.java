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
 * Shawn Huntzberry
 * 
 * The below code is a combination of my implementation of the JavaSound API and examples from the 
 * TarsoDSP library and examples. Not all the code below has been created by me, but the code I have used
 * I have noted the credit where it has come from. 
 * 
 */

/*
*      _______                       _____   _____ _____  
*     |__   __|                     |  __ \ / ____|  __ \ 
*        | | __ _ _ __ ___  ___  ___| |  | | (___ | |__) |
*        | |/ _` | '__/ __|/ _ \/ __| |  | |\___ \|  ___/ 
*        | | (_| | |  \__ \ (_) \__ \ |__| |____) | |     
*        |_|\__,_|_|  |___/\___/|___/_____/|_____/|_|     
*                                                         
* -------------------------------------------------------------
*
* TarsosDSP is developed by Joren Six at IPEM, University Ghent
*  
* -------------------------------------------------------------
*
*  Info: http://0110.be/tag/TarsosDSP
*  Github: https://github.com/JorenSix/TarsosDSP
*  Releases: http://0110.be/releases/TarsosDSP/
*  
*  TarsosDSP includes modified source code by various authors,
*  for credits and info, see README.
* 
*
*   @inproceedings{six2014tarsosdsp,
*	  author      = {Joren Six and Olmo Cornelis and Marc Leman},
*	  title       = {{TarsosDSP, a Real-Time Audio Processing Framework in Java}},
*	  booktitle   = {{Proceedings of the 53rd AES Conference (AES 53rd)}}, 
*	  year        =  2014
*	}
*	
*/


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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * Imports from this package
 */
import currentAI.PlayBackThread;
import currentAI.OscilloscopePanel;
import currentAI.UpNextPanel;


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
	private static final long serialVersionUID = 1L;

	/*
	 * GLOBAL Constant variables
	 */
	private final int MIXERVAL = 1;				//mixer value in array to set(1 = microphone, 2 = line-in)
	private final int BUTTON_WIDTH = 150;		//button width of 150 for all in "buttonsPanel"
	private final int BUTTON_HEIGHT = 50;		//button height of 50 for all buttons in"buttonsPanel"
	private final static int FRAME_WIDTH = 1200;
	private final static int FRAME_HEIGHT = 800;
	
	
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
    private boolean tunerP = false;						//if tuner button pressed this is set to true and tunerPanel is shown, if back pressed this is false
    private boolean allNotesOn = false;					//variable to track which mode is active, if false then single note mode on else all notes mode is on


    /*
     * variables for GUI
     */
    public static JFrame frame;							//frame for the ArtificalInstructor Object
    private JPanel buttonsPanel;						//panel to hold all of the buttons on the lesson player page
    private final OscilloscopePanel oPanel;				//Custom panel, from this package, but edited from TarsoDSP to show soundwave
    private final JPanel lPanel, tunerPanel;			//two panels the user may user(lesson player and tuner player)
    private final JScrollPane sp;						//scroll pane to apply to lessonTextArea to allow old info to be stored with new info
    private JTextArea lessonTextArea, tunerTextArea;	//different textareas used by the different screens
    private JButton tuner, back, start;					//buttons used by tuner panel and lesson panel
    private JButton stop, allNotes, singleNote;			//more buttons used by tuner/lesson
    private FretBoardPanel fretBoardTuner;				//FretBoardPanel that is used/updated by the tuner			
    private FretBoardPanel fretBoardPlayer;				//FretBoardPanel that is used/updated by the lesson page
    private int octave = 0;								//value to know where to display current note on fretboard	
    private double startTime = -1;						//to properly display time based on when user presses start, not when program loads			
    
    private UpNextPanel upNext;
    JPanel bottomP;
    
    /*
     * Variables used to create a new lesson
     * 		**HARD CODED HERE FOR NOW, EVENTUALLY THEY WILL BE STORED SOMEWHERE ELSE AND ONLY READ IN
     * 		  WHEN THE PROGRAM HAS A SPECIFIC LESSON CALLED
     */
   // private double [] nTimes = {3.00, 6.00, 9.00, 12.00, 15.00, 18.00, 21.00, 14.00, 16.00};			//times to play notes at
    private double [] nTimes = {1.00, 5.00, 10.00, 12.00, 15.00, 20.00, 25.00};			//times to play notes at
    private String [] nNotes = {"A", "B", "C","D", "E", "F", "Z"};					//value of notes 
    private int [] nOcts = {1, 2, 2, 2, 1, 2, 3};										//octaves of notes
    private boolean [] nRing = {true, false, true, false, true, false,false};
    private double [] nGracePeriod = {0.5, 0.4, 0.3, 0.5, 0.4, 0.3, 1};
    private FretLesson lessonOne;															//Lesson object to store operate using data

    
    
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
            if (startRecording == false) {
                startRecording = true;
                userPrompt = false;
                upNext.resetLesson();
                upNext.setNotes();
                upNext.repaint();
            } else {
                System.out.println("RECORDING ALREADY IN PROGRESS");
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
            if (startRecording == true) {
                startRecording = false;
                userPrompt = false;
                startTime = -1;
                upNext.resetLesson();
                upNext.setNotes();
                upNext.repaint();
                
            } else {
                System.out.println("NO RECORDING IN PROGRESS TO STOP");
            }
        }
    };

    /*
     * ActionListener to signal tunner page
     * 		>add 
     * 			the tunerPanel to the frame
     * 		>remove
     * 			jP panel, the lesson page with scrollbox, buttons, and fretboard
     * 			oPanel, the graph showing real time sound wave
     * 		>set tunerP variable to true, so handlePitch may work properly
     */
    private ActionListener tunerPressed = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            frame.add(tunerPanel);
            frame.remove(lPanel);
            frame.remove(bottomP);
            //frame.remove(upNext);
            frame.repaint();
            tunerP = true;
        }
    };

    /*
     * ActionListener to signal lesson page, from the tuner page
     * 		>remove
     * 			the tunerPanel to the frame
     * 		>add
     * 			jP panel, the lesson page with scrollbox, buttons, and fretboard
     * 			oPanel, the graph showing real time sound wave
     * 		>set tunerP variable to false, so handlePitch may work properly
     */
    private ActionListener backPressed = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            frame.remove(tunerPanel);
            frame.add(lPanel);
            frame.add(bottomP);
           // frame.add(upNext);
            frame.repaint();
            tunerP = false;
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
        		buttonsPanel.repaint();
        		frame.repaint();
        	}
        	else if(allNotesOn == false)
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
    
    //*************END ACTION LISTENERS*****************//


    /*
     * Similar code to pitchDetectionExample from TarsoDSP library, modified to work with my program
     * Creates the GUI, sets algorithm, and calls necessary functions to start input
     */
    public ArtificialInstructor() {
    	
    	/*
    	 * set properties of the frame
    	 */
        this.setLayout(new GridLayout(2, 1));
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


        /*
         * try to call performTasks method in order to establish audio input/output
         * if there is an error print the stack trace
         */
        try {
            performTasks();
        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

     
        oPanel = new OscilloscopePanel();	// Init the panel to be used to show the soundwave of input
        upNext = new UpNextPanel(nNotes, nRing, nTimes, nOcts, nGracePeriod);
        
        
        bottomP = new JPanel();
        bottomP.setLayout(new GridLayout(1, 2));
        bottomP.add(oPanel);
        bottomP.add(upNext);
      
        
        /*
         * 1) Init the TextArea object and dont allow users to edit 
         * 		Create custom font in order to have a larger font size 
         * 2) Init the ScrollPane object which will allow the user to scroll 
         * 		through the output in textarea
         */
        lessonTextArea = new JTextArea();						//init textArea1
        lessonTextArea.setEditable(false);						//dont allow user to edit this
        Font font1 = lessonTextArea.getFont();					//get font, manipulate it, and set new font
        float fSize = font1.getSize() + 10.0f ;
        lessonTextArea.setFont(font1.deriveFont(fSize));
        sp = new JScrollPane(lessonTextArea);					//init sp, using textArea1 just created
        sp.setPreferredSize(new Dimension(200, 100));			//set the size of the scroll pane 


        /*
         * Init the button to be used to trigger single note mode
         * Add the action listener that starts the input and tracks correct variables
         * set a prefered size to match other buttons
         */
        start = new JButton("Start");
        start.addActionListener(startRec);
        start.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        start.setBackground(Color.green);

        /*
         * Init the button to be used to trigger stop button for input
         * Add the action listener that stops and makes correct variable changes
         * set a prefered size to match other buttons
         */
        stop = new JButton("Stop");
        stop.addActionListener(stopRec);
        stop.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        stop.setBackground(Color.red);
        
        /*
         * Init the button to be used to trigger the tunerPanel
         * Add the action listener that change the panels and resets frame
         * set a prefered size to match other buttons
         */
        tuner = new JButton("Start Tuner");
        tuner.addActionListener(tunerPressed);
        tuner.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        
        /*
         * Init the button to be used to trigger all note mode
         * Add the action listener that changes mode boolean 
         * set a prefered size to match other buttons
         */
        allNotes = new JButton("Show All Notes");
        allNotes.addActionListener(allNotesPressed);
        allNotes.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        allNotes.setBackground(Color.red);
       
        /*
         * Init the button to be used to trigger single note mode
         * Add the action listener that changes mode boolean 
         * set a prefered size to match other buttons
         * set Visible and add to the buttons panel 
         */
        singleNote = new JButton("Show Single Note");
        singleNote.addActionListener(allNotesPressed);
        singleNote.setPreferredSize(new Dimension(150, 50));
        singleNote.setBackground(Color.red);
        singleNote.setVisible(false);
        
        /*
         * Init the JPanel to hold all of the buttons and set visible
         * Add all necessary buttons
         */
        buttonsPanel = new JPanel();						
        buttonsPanel.setVisible(true);
        buttonsPanel.add(start);
        buttonsPanel.add(stop);
        buttonsPanel.add(tuner);
        buttonsPanel.add(allNotes);
        buttonsPanel.add(singleNote);
        
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
        lPanel.setVisible(true);


        /*
         * Init the textArea to display the notes read by the tuner
         * 		-change size of font in order to have notes appear really big 
         * 		-set size of the box in order to give FretBoardPanel used by the tuner has enough space
         */
        tunerTextArea = new JTextArea();
        Font fontTuner = tunerTextArea.getFont();
        tunerTextArea.setDisabledTextColor(Color.RED);
        float fTunerSize = fontTuner.getSize() + 100.0f;
        tunerTextArea.setFont(font1.deriveFont(fTunerSize));
        tunerTextArea.setPreferredSize(new Dimension(200, 200));
        tunerTextArea.setVisible(true);

        /*
         * Initialize back button to be used by the tunerPanel
         * Add the actionlistener to the button
         * Add the button to the buttonsPanel
         */
        back = new JButton("Leave Tuner");
        back.addActionListener(backPressed);
        buttonsPanel.add(back);

        /*
         * Initialize the FretBoardPanel to be used by the tuner, set first note to nothing to display
         */
        fretBoardTuner = new FretBoardPanel();
        fretBoardTuner.setNoteVal("");

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
        tunerPanel.add(fretBoardTuner, BorderLayout.CENTER);
        tunerPanel.add(tunerTextArea, BorderLayout.BEFORE_LINE_BEGINS);
        tunerPanel.add(back, BorderLayout.PAGE_END);
        tunerPanel.setVisible(true);

        /*
         * add panels to frame
         */
        add(lPanel);			//button panel and textarea
        add(bottomP);		//panel to display sound wave
        //add(oPanel);
       
        
        //add(fretBoardPlayer);
    }


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
            TargetDataLine line;
            line = (TargetDataLine) mixer.getLine(dataLineInfo);
            final int numberOfSamples = bufferSize;


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
            
            System.out.println("here");
        } //end performTasks


    //Main method taken from TarsoDSP pitchDetectionExample program and modified to fit my program
    public static void main(String...strings) throws InterruptedException,
        InvocationTargetException {

            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    frame = new ArtificialInstructor();			//initilize frame to have new object
                    frame.pack();								//pack the frame
                    frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);	//set the default size of the frame
                    frame.setResizable(false);
                    frame.setVisible(true);						//make visible to the user
                }
            });
            
           
        }


    //Modified from TarsoDSP pitchDetectionExample from TarsoDSP example by adding conditionals and output msg
    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
    	
    	/*
    	 * If variables meet the requirements
    	 * 		initialize startTime variable to avoid this being called before lesson is over, or stop button pressed
    	 * 		initialize lessonOne using data
    	 * 		set stopPressed equal to true
    	 */
    	if(startTime == -1 && startRecording == true) {
        	startTime = audioEvent.getTimeStamp();
            lessonOne = new FretLesson(startTime, nTimes, nNotes, nOcts, nRing, nGracePeriod);
            fretBoardPlayer.setNoteDisplayMode(allNotesOn);
            
            upNext.setCounter(lessonOne.getLesCounter());
    		upNext.setDotColor(lessonOne.getNoteColor());
    		upNext.repaint();
            //stopPressed = true;
        }
    	
    	if(startRecording == true){
    		lessonOne.incrementCnt(audioEvent.getTimeStamp());
    		fretBoardPlayer.setNoteDisplayMode(allNotesOn);
    		fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
    		fretBoardPlayer.setLesOctave(lessonOne.getNoteOct());
    		fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
    		fretBoardPlayer.repaint(); 
    		
    		upNext.setCounter(lessonOne.getLesCounter());
    		upNext.setDotColor(lessonOne.getNoteColor());
    		upNext.repaint();
    	}
    	
    	/*
    	 * If the value read in by pitchDetectionResult is not -1
    	 * 		>a usable value has been read in from the user by the dispatcher
    	 * 		>step through a set of conditionals to properly update the correct GUI
    	 */
    	if (pitchDetectionResult.getPitch() != -1) {

            float pitch = pitchDetectionResult.getPitch();		//set pitch value to the value of the value of the parameter read in 
            String note = getNoteValue(pitch, 0);				//user pitch value to call getNoteValue in order to set corresponding note letter
           	
            
            /*
             * if tunerP is note pressed then we are on the main screen for lesson
             * 		>step through other conditionals to properly update the correct
             * 			>FretBoardPanel 
             * 			>type of display
             * 			>textareas
             */
            if (tunerP == false) 
            {
            	
            	if(lessonOne != null){
            		if(lessonOne.getLessonPlace(audioEvent.getTimeStamp()) == true )
            		{
            			startRecording = false;
            			startTime = -1;
            			lessonOne = null;
            			
            			upNext.lessonFinished();
            		}
            	}
            	
            	/*
            	 * If startRecording is true then process data properly
            	 */
                if (startRecording == true) 
                {
                		/*
                		 * Update the scrollpane and textArea1 to show the input
                		 * 		>show note value and the pitch value
                		 * 		>set the caret position of textArea1 to the bottom so user sees the latest readings
                		 */
                		lessonTextArea.append("SCORE " + lessonOne.getScore() + ")\n");
                        lessonTextArea.setCaretPosition(lessonTextArea.getDocument().getLength());
                        
                       
                        
                        /*
                         * if boolean returned by getLessonPlace is true
                         * 		>lesson has been completed 
                         * 		>set startRecording to false so 
                         * 		>set startTime to -1 so new object can be initialized when start pressed again
                         * 		>reset lessonPlace to be be able to be used when new object is created
                         */
                       
                        
                        /*
                         * If allNotesOn is false, update fretboard using only single note display for note
                         * Else, update fretboard using all possible note displays for that note
                         */
                        if(allNotesOn == false)
                        {
	                        /*
                        	 * Update the FretBoardPanel to include the lesson notes
                        	 * 		>increment the counter with the lesson to get correct note for time
                        	 * 		>set the value to note expected in lesson, at current time
                        	 *		>set the value to octave of the note expected in lesson, at current time
                        	 * 		>repaint the frame
                        	 */
	                    	lessonOne.incrementCnt(audioEvent.getTimeStamp());
	                    	fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
	                    	fretBoardPlayer.setLesOctave(lessonOne.getNoteOct());
	                    	fretBoardPlayer.setLesRing(lessonOne.getNoteRing());
	                    	fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
	                    	fretBoardPlayer.repaint(); 
	                    	
	                    	upNext.setCounter(lessonOne.getLesCounter());
	                    	upNext.setDotColor(lessonOne.getNoteColor());
	                		upNext.repaint();
	                        
                        	/*
                        	 * Update the FretBoardPanel to include the user notes
                        	 * 		>set the display mode 
                        	 * 		>set the value to note read in
                        	 * 		>set the corresponding octave to display proper single note
                        	 * 		>repaint the panel with updated info
                        	 */
                        	fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                        	fretBoardPlayer.setNoteVal(note);
                        	fretBoardPlayer.setOctave(octave);
	                        fretBoardPlayer.repaint();
	                        
	                        
	                        //Repaint to show the lesson note on top
	                        fretBoardPlayer.repaint(); 
	                        
	                        lessonOne.checkAccuracy(audioEvent.getTimeStamp(), note, octave);
	                        
                        }
                        else
                        {
                        	/*
                        	 * Update the FretBoardPanel to include the lesson notes
                        	 * 		>increment the counter with the lesson to get correct note for time
                        	 * 		>set the value to note expected in lesson, at current time
                        	 * 		>repaint the frame
                        	 */
                            lessonOne.incrementCnt(audioEvent.getTimeStamp());
                            fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
	                    	fretBoardPlayer.setLesRing(lessonOne.getNoteRing());
	                    	fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
                            fretBoardPlayer.repaint(); 
                            
                            upNext.setCounter(lessonOne.getLesCounter());
                            upNext.setDotColor(lessonOne.getNoteColor());
                    		upNext.repaint();
                            
                        	/*
                        	 * Update the FretBoardPanel to include the user notes
                        	 * 		>set the display mode 
                        	 * 		>set the value to note read in
                        	 * 		>repaint panel
                        	 */
                        	fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                        	fretBoardPlayer.setNoteVal(note);
                        	fretBoardPlayer.repaint();
                        	
                        	
                        	fretBoardPlayer.repaint();
                        	
                        	lessonOne.checkAccuracy(audioEvent.getTimeStamp(), note, octave);
                        }
                    } 
                    else 
                    {
                    	
                    	/*
                    	 * If userPrompt is equal to false then recording button has not been pressed
                    	 * Or may have been pressed, but the last button pressed was the stop button
                    	 * 		>Prompt the user and set userPrompt to true in order to only display message once
                    	 */
                        if (userPrompt == false) 
                        {
                        	lessonTextArea.append("RECORDING NOT IN PROGRESSS, PRESS START BUTTON\n");
                            userPrompt = true;
                        }
                    }
                
            } 
            else if (tunerP == true) 
            {
            	
            	/*
            	 * If tunerP is pressed then the tuner page is active, 
            	 * 		>update the tunerText area to display nothing and go down a space (FOR LOOKS)
            	 * 		>add the currently read in note to the textArea so user can see what they are playing
            	 * 		>set FretBoardPanel being used by tuner to display the users input
            	 * 		>repaint the FretBoardPanel
            	 */
                if (note != "couldnt be found") 
                {
                    tunerTextArea.setText("");
                    tunerTextArea.setText("\n");
                    tunerTextArea.append(note + octave);
                    fretBoardTuner.setOctave(octave);
                    fretBoardTuner.setNoteVal(note);
                    fretBoardTuner.repaint();
                    
                    fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                	fretBoardPlayer.setNoteVal(note);
                	fretBoardPlayer.setOctave(octave);
                    fretBoardPlayer.repaint();

                }
            }
        } 
        else 
        {
        	/*
        	 * Even if dispatcher is not reading in a pitch from user, update the fretboard to show lesson note
        	 * 		If recording is true then 
        	 * 			>increment lesson count
        	 * 			>set lesson variables using methods for note and octave
        	 * 			>repaint the FretBoardPanel used by lesson page
        	 * 		
        	 */
        	if(startRecording == true)
        	{
        		lessonOne.incrementCnt(audioEvent.getTimeStamp());
        		fretBoardPlayer.setLesNoteVal(lessonOne.getNoteValue());
        		fretBoardPlayer.setLesOctave(lessonOne.getNoteOct());
                fretBoardPlayer.setLesRing(lessonOne.getNoteRing());
                fretBoardPlayer.setLesColor(lessonOne.getNoteColor());
        		fretBoardPlayer.repaint(); 
        		
        		fretBoardPlayer.setNoteVal("Rest");
        		fretBoardPlayer.setOctave(-100);		//-100 is the octave for rest
        		fretBoardPlayer.repaint();
        		
        		
        		upNext.setCounter(lessonOne.getLesCounter());
        		upNext.setDotColor(lessonOne.getNoteColor());
        		upNext.repaint();
        		
        	}
        }
        
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
    public String getNoteValue(float inputPitch, float offsetTuning) {

            String note = null;
            float pitch = inputPitch + offsetTuning; //offsetTuning set during tuning process to allow for proper pitch detection. 

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

            } else {
                note = "Note Could Note Be Recognized";
            } //end outer-conditionals

            return note;

        } //end getNoteValue()

}
