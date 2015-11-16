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

@inproceedings{six2014tarsosdsp,
	  author      = {Joren Six and Olmo Cornelis and Marc Leman},
	  title       = {{TarsosDSP, a Real-Time Audio Processing Framework in Java}},
	  booktitle   = {{Proceedings of the 53rd AES Conference (AES 53rd)}}, 
	  year        =  2014
	}
	
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
import currentAI.playBackThread;

/*
 * Additional Imports required
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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

/*
 *  ArtificalInstructor currently:
 *  	-Gets audioInput from a line-in
 *  	-graphs realtime input using TarsoDSP oscilloscope program
 *  	-maps pitch of input to note using getNoteValue() method and TarsoDSP pitchDetectionHandler 
 *  	-outputs sound input using playBack() method and class playThread
 */

public class ArtificialInstructor extends JFrame implements PitchDetectionHandler, OscilloscopeEventHandler {
	
	private static final long serialVersionUID = 1L;
	
	//variables for pitch detection and oscilloscope 
	private AudioDispatcher dispatcher;
	private PitchEstimationAlgorithm algo;	
	
	//variables for computing 
	private boolean startRecording = false;
	
	//variables for GUI
	private final GaphPanel panel;
	private final JPanel jP;
	private final JScrollPane sp;
	private JButton start;
	private JButton stop;
	private JTextArea textArea1;
	private boolean userPrompt = false; 
	
	
	//*************BEING ACTION LISTENERS*****************//
	//Actionlistener for START button
	private ActionListener startRec = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent e){
			if(startRecording == false){
				startRecording = true;
				userPrompt = false;
			}else{
				System.out.println("RECORDING ALREADY IN PROGRESS");	
			}
		
		}
	};
	
	//Actionlistener for STOP button
	private ActionListener stopRec = new ActionListener(){
		@Override
		public void actionPerformed(final ActionEvent e){
			if(startRecording == true){
				startRecording = false;
				userPrompt = false;
			}else{
				System.out.println("NO RECORDING IN PROGRESS TO STOP");
			}
		}
	};
	//*************END ACTION LISTENERS*****************//
	
	
	/*
	 * Similar code to pitchDetectionExample from TarsoDSP library, modified to work with my program
	 * Creates the GUI, sets algorithm, and calls necessary functions to start input
	 */
	public ArtificialInstructor() {
		this.setLayout(new GridLayout(2,1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Artifical Instructor");
		
		algo = PitchEstimationAlgorithm.FFT_YIN;
		//algo = PitchEstimationAlgorithm.DYNAMIC_WAVELET;
		//algo = PitchEstimationAlgorithm.FFT_PITCH;
		//algo = PitchEstimationAlgorithm.YIN;
		
		try {
			performTasks();
		} catch (LineUnavailableException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		//initialize panel
		panel = new GaphPanel();
		
		//initialize textarea and scrollpane
		textArea1 = new JTextArea();
		textArea1.setEditable(false);
		sp = new JScrollPane(textArea1);
		
		//create a panel for the start and stop buttons to go in
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setMaximumSize(new Dimension(50, 50));
		buttonsPanel.setVisible(true);
		
		//create start button and assign the actionlistener
		start = new JButton("Start");
		start.addActionListener(startRec);
		start.setPreferredSize(new Dimension(400, 100));
		start.setBackground(Color.green);
		buttonsPanel.add(start);
		
		//create a stop button and assign the actionlistener
		stop = new JButton("Stop");
		stop.addActionListener(stopRec);
		stop.setPreferredSize(new Dimension(400,100));
		stop.setBackground(Color.red);
		buttonsPanel.add(stop);
		
		//initialize the components to go in, and add components
		jP = new JPanel();
		jP.setVisible(true);
		jP.setLayout(new BorderLayout());
		jP.add(sp, BorderLayout.CENTER);
		jP.add(buttonsPanel, BorderLayout.PAGE_END);
		
		//add the graph(panel) and output(buttons and textarea) to frame
		this.add(panel);
		this.add(jP);
		
	}

	
	/*
	 * 	Runs real time playback while the user is playing
	 * 	Uses a different format than analysis in order to output the correct sound
	 */
	public void playBack(){
    	AudioFormat playBackFormat = new AudioFormat(1024, 16, 2, true, true);
        ByteArrayOutputStream out;				//input
    	int numBytesRead;						//input
    	int bytesRead;							//input
    	byte[] data;							//input
    	TargetDataLine instrument = null;		//input
    	DataLine.Info info1;					//input
    	
    	byte audioData[];						//output 
        SourceDataLine sourceDataLine = null;	//output
        AudioInputStream ais;					//output
        DataLine.Info dataLineInfo1;			//output
		
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
            instrument = AudioSystem.getTargetDataLine(playBackFormat);

            info1 = new DataLine.Info(TargetDataLine.class, playBackFormat);
            instrument = (TargetDataLine) AudioSystem.getLine(info1);
            instrument.open(playBackFormat);
            instrument.start();
            
            out = new ByteArrayOutputStream();
            data = new byte[instrument.getBufferSize()];
            
            bytesRead = 0;

                try {
                    numBytesRead = instrument.read(data, 0, 1024);
                    bytesRead = bytesRead + numBytesRead;
                    System.out.println(bytesRead);
                    out.write(data, 0, numBytesRead);
                    
                    audioData = out.toByteArray();
                    out.reset();
                    
                    
                    InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
                    ais = new AudioInputStream(byteArrayInputStream, playBackFormat, audioData.length / playBackFormat.getFrameSize());
                    dataLineInfo1 = new DataLine.Info(SourceDataLine.class, playBackFormat);
                    sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
                    sourceDataLine.open(playBackFormat, 1024);
 
                    Runnable threadJob = new playBackThread(sourceDataLine, ais);
                	Thread t1 = new Thread(threadJob);
                	
                	t1.start();
                   
            	} catch (Exception e) {
            		e.printStackTrace();
            	} 
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        
	}//end playBack()
	
	
	//Modified method from pitchDetectionExample from TarsoDSP library
	private void performTasks() throws LineUnavailableException,
			UnsupportedAudioFileException {
		
		Mixer.Info [] availMixers = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(availMixers[1]);
		
		if(dispatcher!= null){
			dispatcher.stop();
		}
		
		//float sampleRate = 8192;  
		float sampleRate = 4096;	//best sample size
		//float sampleRate = 1024;
		int bufferSize = 1024;
		int overlap = 0;
		
		AudioFormat format = new AudioFormat(sampleRate, 16, 2, true, true);
		//final AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
		
		final DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, format);
		TargetDataLine line;
		line = (TargetDataLine) mixer.getLine(dataLineInfo);
		final int numberOfSamples = bufferSize;
		
		
		line.open(format, numberOfSamples);
		line.start();
		AudioInputStream stream = new AudioInputStream(line);
		JVMAudioInputStream audioStream = new JVMAudioInputStream(stream);
		
	
		// create a new dispatcher and add the pitchprocessor and oscilloscope to it
		dispatcher = new AudioDispatcher(audioStream, bufferSize, overlap);
		dispatcher.addAudioProcessor(new PitchProcessor(algo, sampleRate, bufferSize, this));
		dispatcher.addAudioProcessor(new Oscilloscope(this));
		
		//start thread for dispatcher
		new Thread(dispatcher,"Audio dispatching").start();
		
	}//end performTasks
	

	//Taken from TarsoDSP oscilloscopeExample program, no changes were made
	private static class GaphPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 4969781241442094359L;
		
		float data[];
		
		public GaphPanel(){
			setMinimumSize(new Dimension(80,60)); 
		}
		
		public void paintComponent(Graphics g) {
	        super.paintComponent(g); //paint background
	        g.setColor(Color.BLACK);
			g.fillRect(0, 0,getWidth(), getHeight());
			g.setColor(Color.WHITE);
			if(data != null){
				float width = getWidth();
				float height = getHeight();
				float halfHeight = height / 2;
				for(int i=0; i < data.length ; i+=4){
					 g.drawLine((int)(data[i]* width),(int)( halfHeight - data[i+1]* height),(int)( data[i+2]*width),(int)( halfHeight - data[i+3]*height));
				}
			}
	    }
		
		public void paint(float[] data, AudioEvent event){
			this.data = data;
		}
	}
	
	//Main method taken from TarsoDSP pitchDetectionExample program and modified to fit my program
	public static void main(String... strings) throws InterruptedException,
			InvocationTargetException {
		
		
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
			
				JFrame frame = new ArtificialInstructor();
				frame.pack();
				frame.setSize(1000,800);
				
				frame.setVisible(true);
			}
		});
	}

	
	//Modified from TarsoDSP pitchDetectionExample from TarsoDSP example by adding conditionals and output msg
	@Override
	public void handlePitch(PitchDetectionResult pitchDetectionResult,AudioEvent audioEvent) {
		if(pitchDetectionResult.getPitch() != -1){
			float pitch = pitchDetectionResult.getPitch();
			
			String note = getNoteValue(pitch, 0);
			
			if(startRecording == true){
				textArea1.append("Note: "+ note + ", pitch: " + pitch + ")\n");
				textArea1.setCaretPosition(textArea1.getDocument().getLength());
				
			}else{
				if(userPrompt == false){
					textArea1.append("RECORDING NOT IN PROGRESSS, PRESS START BUTTON\n");
					userPrompt = true;
				}
			}
		}
	}
	
	//Modified from TarsoDSP oscilloscopeExample program by adding thread creation to run playback
	@Override
	public void handleEvent(float[] data, AudioEvent event) {
		if(startRecording == true){
			panel.paint(data,event);
			panel.repaint();
			
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
	public String getNoteValue(float inputPitch, float offsetTuning){
		
		String note = "";
		float pitch = inputPitch + offsetTuning;	//offsetTuning set during tuning process to allow for proper pitch detection. 
		
		/*
		 * Set of conditionals to get pitch.
		 * --Outer conditionals determine which octave the input pitch belongs to
		 * --Inner conditionals determine which note in that octive the pitch is
		 */
		//Octave 0
		if(pitch >= 0 && pitch <= 31.785){
			
			if(pitch >= 0 && pitch <= 16.835)
			{
				note = "C0";
			}
			else if(pitch >= 16.836 && pitch <= 17.835)
			{
				note = "C#0";
			}
			else if(pitch >= 17.836 && pitch <= 18.9)
			{
				note = "D0";
			}
			else if(pitch >= 18.91 && pitch <= 20.025)
			{
				note = "Eb0";
			}
			else if(pitch >= 20.026 && pitch <= 21.215)
			{
				note = "E0";
			}
			else if(pitch >= 21.216 && pitch <= 22.475)
			{
				note = "F0";
			}
			else if(pitch >= 22.476 && pitch <= 23.81)
			{
				note = "F#0";
			}
			else if(pitch >= 23.82 && pitch <= 25.23)
			{
				note = "G0";
			}
			else if(pitch >= 25.24 && pitch <= 26.73)
			{
				note = "G#0";
			}
			else if(pitch >= 26.74 && pitch <= 28.32)
			{
				note = "A0";
			}
			else if(pitch >= 28.33 && pitch <= 30.005)
			{
				note = "Bb0";
			}
			else if(pitch >= 30.006 && pitch <= 31.785)
			{
				note = "B0";
			}//end inner-conditionals
		}	
		//Octave 1
		else if(pitch >= 31.786 && pitch <= 63.575)
		{
			if(pitch >= 31.786 && pitch <= 33.675)
			{
				note = "C1";
			}
			else if(pitch >= 33.676 && pitch <= 35.68)
			{
				note = "C#1";
			}
			else if(pitch >= 35.69 && pitch <= 37.8)
			{
				note = "D1";
			}
			else if(pitch >= 37.9 && pitch <= 40.045)
			{
				note = "Eb1";
			}
			else if(pitch >= 40.046 && pitch <= 42.425)
			{
				note = "E1";
			}
			else if(pitch >= 42.426 && pitch <= 44.95)
			{
				note = "F1";
			}
			else if(pitch >= 44.96 && pitch <= 47.625)
			{
				note = "F#1";
			}
			else if(pitch >= 47.626 && pitch <= 50.455)
			{
				note = "G1";
			}
			else if(pitch >= 50.456 && pitch <= 53.455)
			{
				note = "G#1";
			}
			else if(pitch >= 53.456 && pitch <= 56.635)
			{
				note = "A1";
			}
			else if(pitch >= 56.636 && pitch <= 60.005)
			{
				note = "Bb1";
			}
			else if(pitch >= 60.006 && pitch <= 63.575)
			{
				note = "B1";
			}//end inner-conditionals
		}
		//Octave 2
		else if(pitch >= 63.576 && pitch <= 127.15){
			if(pitch >= 63.576 && pitch <= 67.355)
			{
				note = "C2";
			}
			else if(pitch >= 67.356 && pitch <= 71.36)
			{
				note = "C#2";
			}
			else if(pitch >= 71.37 && pitch <= 75.6)
			{
				note = "D2";
			}
			else if(pitch >= 75.7 && pitch <= 80.095)
			{
				note = "Eb2";
			}
			else if(pitch >= 80.096 && pitch <= 84.86)
			{
				note = "E2";
			}
			else if(pitch >= 84.87 && pitch <= 89.905)
			{
				note = "F2";
			}
			else if(pitch >= 89.906 && pitch <= 95.25)
			{
				note = "F#2";
			}
			else if(pitch >= 96.26 && pitch <= 100.9)
			{
				note = "G2";
			}
			else if(pitch >= 101 && pitch <= 106.9)
			{
				note = "G#2";
			}
			else if(pitch >= 107 && pitch <= 113.25)
			{
				note = "A2";
			}
			else if(pitch >= 113.26 && pitch <= 120)
			{
				note = "Bb2";
			}
			else if(pitch >= 120.5 && pitch <= 127.15)
			{
				note = "B2";
			}//end inner-conditionals
			
		}
		//Octave 3
		else if(pitch >= 127.16 && pitch <= 254.25){
			if(pitch >= 127.16 && pitch <= 134.7)
			{
				note = "C3";
			}
			else if(pitch >= 134.8 && pitch <= 142.7)
			{
				note = "C#3";
			}
			else if(pitch >= 142.8 && pitch <= 151.2)
			{
				note = "D3";
			}
			else if(pitch >= 151.3 && pitch <= 160.2)
			{
				note = "Eb3";
			}
			else if(pitch >= 160.3 && pitch <= 169.7)
			{
				note = "E3";
			}
			else if(pitch >= 169.8 && pitch <= 179.8)
			{
				note = "F3";
			}
			else if(pitch >= 179.9 && pitch <= 190.5)
			{
				note = "F#3";
			}
			else if(pitch >= 190.6 && pitch <= 201.85)
			{
				note = "G3";
			}
			else if(pitch >= 201.86 && pitch <= 213.85)
			{
				note = "G#3";
			}
			else if(pitch >= 213.86 && pitch <= 226.55)
			{
				note = "A3";
			}
			else if(pitch >= 226.56 && pitch <= 240)
			{
				note = "Bb3";
			}
			else if(pitch >= 240.1 && pitch <= 254.25)
			{
				note = "B3";
				
			}//end inner-conditionals
		}
		//Octave 4
		else if(pitch >= 254.26 && pitch <= 508.6){
			if(pitch >= 254.25 && pitch <= 269.4)
			{
				note = "C4";
			}
			else if(pitch >= 269.5 && pitch <= 285.45)
			{
				note = "C#4";
			}
			else if(pitch >= 285.46 && pitch <= 302.4)
			{
				note = "D4";
			}
			else if(pitch >= 302.5 && pitch <= 320.35)
			{
				note = "Eb4";
			}
			else if(pitch >= 320.36 && pitch <= 339.4)
			{
				note = "E4";
			}
			else if(pitch >= 339.5 && pitch <= 359.6)
			{
				note = "F4";
			}
			else if(pitch >= 359.7 && pitch <= 381)
			{
				note = "F#4";
			}
			else if(pitch >= 381.1 && pitch <= 403.65)
			{
				note = "G4";
			}
			else if(pitch >= 403.66 && pitch <= 427.65)
			{
				note = "G#4";
			}
			else if(pitch >= 427.66 && pitch <= 453.1)
			{
				note = "A4";
			}
			else if(pitch >= 453.2 && pitch <= 480.05)
			{
				note = "Bb4";
			}
			else if(pitch >= 480.06 && pitch <= 508.6)
			{
				note = "B4";
				
			}//end inner-conditionals
			
		}else{
			note = "couldnt be found";
		}//end outer-conditionals
		
		return note;
		
	}//end getNoteValue()
	
}//end Artifical Instructor

