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
import be.tarsos.dsp.beatroot.Peaks;
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
import currentAI.PlayBackThread;
import currentAI.OscilloscopePanel;



/*
 *  ArtificalInstructor currently:
 *  	-Gets audioInput from a line-in
 *  	-graphs realtime input using TarsoDSP oscilloscope program
 *  	-maps pitch of input to note using getNoteValue() method and TarsoDSP pitchDetectionHandler 
 *  	-outputs sound input using playBack() method and class playThread
 */

public class ArtificialInstructor extends JFrame implements PitchDetectionHandler, OscilloscopeEventHandler {

    

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int MIXERVAL = 1;

    //variables for pitch detection and oscilloscope 
    private AudioDispatcher dispatcher;
    private PitchEstimationAlgorithm algo;

    //variables for computing 
    private boolean startRecording = false;

    //variables for GUI
    private JPanel buttonsPanel;
    private final OscilloscopePanel oPanel;
    private final JPanel jP, tunerPanel;
    private final JScrollPane sp;
    private JButton start;
    private JButton stop;
    private JTextArea textArea1;
    private boolean userPrompt = false;
    private boolean tunerP = false;
    private boolean backP = false;
    private JButton tuner;
    private JButton back;
    private JTextArea tunerTextArea;
    public static JFrame frame;
    private boolean newNote = false;
    
    private JButton allNotes;
    private JButton singleNote;
    private boolean allNotesOn = false;
    
    
    private String[] ACCEPTABLE_NOTES = {
        "A",
        "Bb",
        "B",
        "C",
        "C#",
        "D",
        "Db",
        "E",
        "F",
        "F#",
        "G",
        "G#"
    };
    private FretBoardPanel fretBoardTuner;
    private FretBoardPanel fretBoardPlayer;
    private int octave = 0;

    //*************BEING ACTION LISTENERS*****************//
    //Actionlistener for START button
    private ActionListener startRec = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            if (startRecording == false) {
                startRecording = true;
                userPrompt = false;
            } else {
                System.out.println("RECORDING ALREADY IN PROGRESS");
            }

        }
    };

    //Actionlistener for STOP button
    private ActionListener stopRec = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            if (startRecording == true) {
                startRecording = false;
                userPrompt = false;
            } else {
                System.out.println("NO RECORDING IN PROGRESS TO STOP");
            }
        }
    };

    private ActionListener tunerPressed = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            frame.add(tunerPanel);
            frame.remove(jP);
            frame.remove(oPanel);
            frame.repaint();
            tunerP = true;
        }
    };

    private ActionListener backPressed = new ActionListener() {
        @Override
        public void actionPerformed(final ActionEvent e) {
            frame.remove(tunerPanel);
            frame.add(jP);
            frame.add(oPanel);
            frame.repaint();
            tunerP = false;
        }
    };
    
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
        this.setLayout(new GridLayout(2, 1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Artifical Instructor");

        algo = PitchEstimationAlgorithm.FFT_YIN;
        //algo = PitchEstimationAlgorithm.DYNAMIC_WAVELET;
        //algo = PitchEstimationAlgorithm.MPM;
        //algo = PitchEstimationAlgorithm.YIN;


        try {
            performTasks();
        } catch (LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        //initialize panel
        oPanel = new OscilloscopePanel();
        //initialize textarea and scrollpane
        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        Font font1 = textArea1.getFont();
        float fSize = font1.getSize() + 10.0f ;
        textArea1.setFont(font1.deriveFont(fSize));
        sp = new JScrollPane(textArea1);
        sp.setPreferredSize(new Dimension(200, 100));

        //create a panel for the start and stop buttons to go in
        buttonsPanel = new JPanel();
        //buttonsPanel.setLayout(new GridLayout(3, 1));
        buttonsPanel.setMaximumSize(new Dimension(50, 50));
        buttonsPanel.setVisible(true);

        //create start button and assign the actionlistener
        start = new JButton("Start");
        start.addActionListener(startRec);
        start.setPreferredSize(new Dimension(150, 50));
        start.setBackground(Color.green);
        buttonsPanel.add(start);

        //create a stop button and assign the actionlistener
        stop = new JButton("Stop");
        stop.addActionListener(stopRec);
        stop.setPreferredSize(new Dimension(150, 50));
        stop.setBackground(Color.red);
        buttonsPanel.add(stop);
        
        tuner = new JButton("Start Tuner");
        tuner.addActionListener(tunerPressed);
        tuner.setPreferredSize(new Dimension(150, 50));
        buttonsPanel.add(tuner);
        
        allNotes = new JButton("Show All Notes");
        allNotes.addActionListener(allNotesPressed);
        allNotes.setPreferredSize(new Dimension(150, 50));
        allNotes.setBackground(Color.red);
        buttonsPanel.add(allNotes);
        
        singleNote = new JButton("Show Single Note");
        singleNote.addActionListener(allNotesPressed);
        singleNote.setPreferredSize(new Dimension(150, 50));
        singleNote.setBackground(Color.red);
        buttonsPanel.add(singleNote);
        singleNote.setVisible(false);

        fretBoardPlayer = new FretBoardPanel();
        fretBoardPlayer.setNoteVal("");

        //initialize the components to go in, and add components
        jP = new JPanel();
        jP.setLayout(new BorderLayout());
        jP.add(sp, BorderLayout.BEFORE_LINE_BEGINS);
        jP.add(buttonsPanel, BorderLayout.PAGE_START);
        jP.add(fretBoardPlayer, BorderLayout.CENTER);
        jP.repaint();
        jP.setVisible(true);

        //Tuner panel creation and additions
        tunerPanel = new JPanel();
        tunerPanel.setLayout(new BorderLayout());
        tunerTextArea = new JTextArea();
        Font fontTuner = tunerTextArea.getFont();
        tunerTextArea.setDisabledTextColor(Color.RED);
        float fTunerSize = fontTuner.getSize() + 100.0f;
        tunerTextArea.setFont(font1.deriveFont(fTunerSize));
        tunerTextArea.setPreferredSize(new Dimension(200, 200));
        tunerTextArea.setVisible(true);

        back = new JButton("Leave Tuner");
        back.addActionListener(backPressed);
        buttonsPanel.add(back);
        //buttonsPanel.setPreferredSize(new Dimension(50,50));

        fretBoardTuner = new FretBoardPanel();
        fretBoardTuner.setNoteVal("");

        tunerPanel.add(fretBoardTuner, BorderLayout.CENTER);
        tunerPanel.add(tunerTextArea, BorderLayout.BEFORE_LINE_BEGINS);
        tunerPanel.add(back, BorderLayout.PAGE_END);
        tunerPanel.setVisible(true);

        //add the graph(panel) and output(buttons and textarea) to frame
        add(jP);
        add(oPanel);



    }


    /*
     * 	Runs real time playback while the user is playing
     * 	Uses a different format than analysis in order to output the correct sound
     */
    public void playBack() {
            AudioFormat playBackFormat = new AudioFormat(1024, 16, 1, true, true);
            ByteArrayOutputStream out; //input
            int numBytesRead; //input
            int bytesRead; //input
            byte[] data; //input
            TargetDataLine instrument = null; //input
            DataLine.Info info1; //input

            byte audioData[]; //output 
            SourceDataLine sourceDataLine = null; //output
            AudioInputStream ais; //output
            DataLine.Info dataLineInfo1; //output

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

                    Runnable threadJob = new PlayBackThread(sourceDataLine, ais);
                    Thread t1 = new Thread(threadJob);

                    t1.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();
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

        } //end performTasks


    //Main method taken from TarsoDSP pitchDetectionExample program and modified to fit my program
    public static void main(String...strings) throws InterruptedException,
        InvocationTargetException {


            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {

                    frame = new ArtificialInstructor();
                    frame.pack();
                    frame.setSize(1200, 800);

                    frame.setVisible(true);
                }
            });
        }


    //Modified from TarsoDSP pitchDetectionExample from TarsoDSP example by adding conditionals and output msg
    @Override
    public void handlePitch(PitchDetectionResult pitchDetectionResult, AudioEvent audioEvent) {
        if (pitchDetectionResult.getPitch() != -1) {

            float pitch = pitchDetectionResult.getPitch();
            String note = getNoteValue(pitch, 0);

            if (tunerP == false) 
            {
                if (newNote == false) 
                {
                    if (startRecording == true) 
                    {
                        textArea1.append(note + " (" + pitch + ")\n");
                        textArea1.setCaretPosition(textArea1.getDocument().getLength());
                        if(allNotesOn == false)
                        {
                        	fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                        	fretBoardPlayer.setNoteVal(note);
                        	fretBoardPlayer.setOctave(octave);
	                        fretBoardPlayer.repaint();
                        }
                        else
                        {
                        	fretBoardPlayer.setNoteDisplayMode(allNotesOn);
                        	fretBoardPlayer.setNoteVal(note);
                        	fretBoardPlayer.repaint();
                        }
                    } 
                    else 
                    {
                        if (userPrompt == false) 
                        {
                            textArea1.append("RECORDING NOT IN PROGRESSS, PRESS START BUTTON\n");
                            userPrompt = true;
                        }
                    }
                } 
                else 
                {
                    newNote = true;
                }
            } 
            else if (tunerP == true) 
            {
                if (note != "couldnt be found") 
                {
                    tunerTextArea.setText("");
                    tunerTextArea.setText("\n");
                    tunerTextArea.append(note);
                    fretBoardTuner.setNoteVal(note);
                    fretBoardTuner.repaint();

                }
            }
        } 
        else 
        {
            newNote = false;
        }
        
    }//end handlePitch

    //Modified from TarsoDSP oscilloscopeExample program by adding thread creation to run playback
    @Override
    public void handleEvent(float[] data, AudioEvent event) {
        if (startRecording == true) {
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i] * 5;
            }
            oPanel.paint(data, event);
            oPanel.repaint();

            /*
            new Thread(new Runnable(){
            	@Override
            	public void run(){
            		playBack();
            	}
            }).start();
            */
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
                note = "couldnt be found";
            } //end outer-conditionals

            return note;

        } //end getNoteValue()

} //end Artifical Instructor

