package currentAI;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FretBoardPanel extends JPanel {


    /**
	 * 
	 */
	private static final long serialVersionUID = 4139798146217404624L;
	/**
     * 
     */
   

    /*
     * Default sizing variables 
     */
    private final int CORNERFRETBOARD = 50;
    private final int WIDTHFRETBOARD = 900;
    private final int HEIGHTFRETBOARD = 250;

    /*
     * starting X value of the point of the FretLine
     *     # at the end of the variable corresponds to the value, where 
     * 			0 = open fret
     * 			1-11 = that fret number
     */
    private final int SX_FRET0 = 125;
    private final int SX_FRET1 = 200;
    private final int SX_FRET2 = 275;
    private final int SX_FRET3 = 350;
    private final int SX_FRET4 = 425;
    private final int SX_FRET5 = 500;
    private final int SX_FRET6 = 575;
    private final int SX_FRET7 = 650;
    private final int SX_FRET8 = 725;
    private final int SX_FRET9 = 800;
    private final int SX_FRET10 = 875;
    private final int SX_FRET11 = 950;

    /*
     * Constants for width of panel to set the dot starting at
     *		# at the end of the variable corresponds to the value, where 
     * 			0 = open fret
     * 			1-11 = that fret number
     */
    private final int DOTWIDTH0 = 65;
    private final int DOTWIDTH1 = 140;
    private final int DOTWIDTH2 = 215;
    private final int DOTWIDTH3 = 290;
    private final int DOTWIDTH4 = 365;
    private final int DOTWIDTH5 = 440;
    private final int DOTWIDTH6 = 515;
    private final int DOTWIDTH7 = 590;
    private final int DOTWIDTH8 = 665;
    private final int DOTWIDTH9 = 740;
    private final int DOTWIDTH10 = 815;
    private final int DOTWIDTH11 = 890;

    /*
     * Constants for width of panel to set the label of dot to start at
     * 		# at the end of the variable corresponds to the value, where 
     * 			0 = open fret
     * 			1-11 = that fret number
     */
    private final int D_LABELWIDTH0 = DOTWIDTH0 + 10;
    private final int D_LABELWIDTH1 = DOTWIDTH1 + 10;
    private final int D_LABELWIDTH2 = DOTWIDTH2 + 10;
    private final int D_LABELWIDTH3 = DOTWIDTH3 + 10;
    private final int D_LABELWIDTH4 = DOTWIDTH4 + 10;
    private final int D_LABELWIDTH5 = DOTWIDTH5 + 10;
    private final int D_LABELWIDTH6 = DOTWIDTH6 + 10;
    private final int D_LABELWIDTH7 = DOTWIDTH7 + 10;
    private final int D_LABELWIDTH8 = DOTWIDTH8 + 10;
    private final int D_LABELWIDTH9 = DOTWIDTH9 + 10;
    private final int D_LABELWIDTH10 = DOTWIDTH10 + 10;
    private final int D_LABELWIDTH11 = DOTWIDTH11 + 10;

    /*
     * Constants for height of panel to set the dot to start at
     *      0 = HIGHEST STRING
     * 		1 = 2nd from top
     * 		2 = 3rd from top
     * 		4 = LOWEST STRING
     */
    private final int DOTHEIGHT0 = 75;
    private final int DOTHEIGHT1 = 125;
    private final int DOTHEIGHT2 = 175;
    private final int DOTHEIGHT3 = 225;

    /*
     * Constants for height of panel to set the label of dot to start at
     * 		0 = HIGHEST STRING
     * 		1 = 2nd from top
     * 		2 = 3rd from top
     * 		4 = LOWEST STRING
     */
    private final int D_LABELHEIGHT0 = 110;
    private final int D_LABELHEIGHT1 = 160;
    private final int D_LABELHEIGHT2 = 210;
    private final int D_LABELHEIGHT3 = 260;

    /*
     * Radius of all dots
     */
    private final int DOT_RADIUS = 50;

    /*
     * Variables to be change values of fretboard GUI that may be updated
     */
    private boolean displayAllNotes = false;			//mode to draw notes(true = show all notes, false = show single notes)
    private String noteVal = "";					//note value store from input of player
    private int currentOctave = 0;					//octave value stored from input of player
    private String lNoteVal;						//note value stored from input of the lesson
    private int lOct;								//octave value stored from input of the lesson
    private String lType;
    private Color lesColor;
    
    private String lastNote = "";
    
    public FretBoardPanel() {
    		noteVal = "";
    		currentOctave = 0;
    		lNoteVal = "";
    		lOct = 0;
    		displayAllNotes = false;
    }

    public void setNoteVal(String note) {
        
        lastNote = noteVal;
    	noteVal = note;
        
    }

    public void setNoteDisplayMode(boolean mode) {
        displayAllNotes = mode;
    }

    public void setOctave(int oct)
    {
    	currentOctave = oct;
    }
    
    public void resetFretBoard(Graphics g)
    {
    	g.setFont(new Font("Times New Roman Bold", 25, 25));

        g.setFont(g.getFont().deriveFont(Font.BOLD, g.getFont().getSize()+6));
        
        //Fill rectangle for FretBoard
        g.setColor(Color.WHITE);
        g.fillRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(CORNERFRETBOARD, CORNERFRETBOARD, SX_FRET0 - CORNERFRETBOARD, HEIGHTFRETBOARD);
        
      //Draw rectangle for FretBoard
        g.setColor(Color.BLACK);
        g.drawRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

        g.setColor(Color.BLACK);
        //draw frets
        g.drawLine(SX_FRET0, CORNERFRETBOARD - 30, SX_FRET0, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET1, CORNERFRETBOARD - 30, SX_FRET1, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET2, CORNERFRETBOARD - 30, SX_FRET2, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET3, CORNERFRETBOARD - 30, SX_FRET3, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET4, CORNERFRETBOARD - 30, SX_FRET4, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET5, CORNERFRETBOARD - 30, SX_FRET5, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET6, CORNERFRETBOARD - 30, SX_FRET6, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET7, CORNERFRETBOARD - 30, SX_FRET7, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET8, CORNERFRETBOARD - 30, SX_FRET8, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET9, CORNERFRETBOARD - 30, SX_FRET9, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET10, CORNERFRETBOARD - 30, SX_FRET10, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET11, CORNERFRETBOARD - 30, SX_FRET11, HEIGHTFRETBOARD + CORNERFRETBOARD);

        //label frets
        g.drawString("Open", 40, 35);
        g.drawString("1", 150, 35);
        g.drawString("2", 225, 35);
        g.drawString("3", 300, 35);
        g.drawString("4", 375, 35);
        g.drawString("5", 450, 35);
        g.drawString("6", 525, 35);
        g.drawString("7", 600, 35);
        g.drawString("8", 675, 35);
        g.drawString("9", 750, 35);
        g.drawString("10", 825, 35);
        g.drawString("11", 900, 35);

        
        //Draw strings on fretboard 
        g.drawLine(35, 100, 965, 100);
        g.drawLine(35, 150, 965, 150);
        g.drawLine(35, 200, 965, 200);
        g.drawLine(35, 250, 965, 250);

        //Label Strings
        g.drawString("G", 15, D_LABELHEIGHT0);
        g.drawString("D", 15, D_LABELHEIGHT1);
        g.drawString("A", 15, D_LABELHEIGHT2);
        g.drawString("E", 15, D_LABELHEIGHT3);

        //draw fret markers
        g.setColor(Color.GRAY);
        g.fillOval( (SX_FRET0 + SX_FRET1)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.fillOval( (SX_FRET2 + SX_FRET3)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.fillOval( (SX_FRET4 + SX_FRET5)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.fillOval( (SX_FRET6 + SX_FRET7)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        g.fillOval( (SX_FRET8 + SX_FRET9)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        
        //add outline to fret markers
        g.setColor(Color.CYAN);
        g.drawOval( (SX_FRET0 + SX_FRET1)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.drawOval( (SX_FRET2 + SX_FRET3)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.drawOval( (SX_FRET4 + SX_FRET5)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.drawOval( (SX_FRET6 + SX_FRET7)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        g.drawOval( (SX_FRET8 + SX_FRET9)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        
        g.drawOval( (SX_FRET0 + SX_FRET1)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET2 + SX_FRET3)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET4 + SX_FRET5)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET6 + SX_FRET7)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET8 + SX_FRET9)/2-3, D_LABELHEIGHT1, 15, 25);
        
        g.drawOval( (SX_FRET0 + SX_FRET1)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET2 + SX_FRET3)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET4 + SX_FRET5)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET6 + SX_FRET7)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET8 + SX_FRET9)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        
        g.setColor(Color.BLACK);
        
    }
    
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);       

        g.setFont(new Font("Times New Roman Bold", 25, 25));

        g.setFont(g.getFont().deriveFont(Font.BOLD, g.getFont().getSize()+6));
        
        //Fill rectangle for FretBoard
        g.setColor(Color.WHITE);
        g.fillRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(CORNERFRETBOARD, CORNERFRETBOARD, SX_FRET0 - CORNERFRETBOARD, HEIGHTFRETBOARD);
        
      //Draw rectangle for FretBoard
        g.setColor(Color.BLACK);
        g.drawRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

        g.setColor(Color.BLACK);
        //draw frets
        g.drawLine(SX_FRET0, CORNERFRETBOARD - 30, SX_FRET0, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET1, CORNERFRETBOARD - 30, SX_FRET1, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET2, CORNERFRETBOARD - 30, SX_FRET2, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET3, CORNERFRETBOARD - 30, SX_FRET3, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET4, CORNERFRETBOARD - 30, SX_FRET4, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET5, CORNERFRETBOARD - 30, SX_FRET5, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET6, CORNERFRETBOARD - 30, SX_FRET6, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET7, CORNERFRETBOARD - 30, SX_FRET7, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET8, CORNERFRETBOARD - 30, SX_FRET8, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET9, CORNERFRETBOARD - 30, SX_FRET9, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET10, CORNERFRETBOARD - 30, SX_FRET10, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET11, CORNERFRETBOARD - 30, SX_FRET11, HEIGHTFRETBOARD + CORNERFRETBOARD);

        //label frets
        g.drawString("Open", 40, 35);
        g.drawString("1", 150, 35);
        g.drawString("2", 225, 35);
        g.drawString("3", 300, 35);
        g.drawString("4", 375, 35);
        g.drawString("5", 450, 35);
        g.drawString("6", 525, 35);
        g.drawString("7", 600, 35);
        g.drawString("8", 675, 35);
        g.drawString("9", 750, 35);
        g.drawString("10", 825, 35);
        g.drawString("11", 900, 35);

        
        //Draw strings on fretboard 
        g.drawLine(35, 100, 965, 100);
        g.drawLine(35, 150, 965, 150);
        g.drawLine(35, 200, 965, 200);
        g.drawLine(35, 250, 965, 250);

        //Label Strings
        g.drawString("G", 15, D_LABELHEIGHT0);
        g.drawString("D", 15, D_LABELHEIGHT1);
        g.drawString("A", 15, D_LABELHEIGHT2);
        g.drawString("E", 15, D_LABELHEIGHT3);

        //draw fret markers
        g.setColor(Color.GRAY);
        g.fillOval( (SX_FRET0 + SX_FRET1)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.fillOval( (SX_FRET2 + SX_FRET3)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.fillOval( (SX_FRET4 + SX_FRET5)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.fillOval( (SX_FRET6 + SX_FRET7)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        g.fillOval( (SX_FRET8 + SX_FRET9)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        
        //add outline to fret markers
        g.setColor(Color.CYAN);
        g.drawOval( (SX_FRET0 + SX_FRET1)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.drawOval( (SX_FRET2 + SX_FRET3)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.drawOval( (SX_FRET4 + SX_FRET5)/2 - 8, D_LABELHEIGHT1 -5, 25, 35);
        g.drawOval( (SX_FRET6 + SX_FRET7)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        g.drawOval( (SX_FRET8 + SX_FRET9)/2 - 8, D_LABELHEIGHT1 - 5, 25, 35);
        
        g.drawOval( (SX_FRET0 + SX_FRET1)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET2 + SX_FRET3)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET4 + SX_FRET5)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET6 + SX_FRET7)/2-3, D_LABELHEIGHT1, 15, 25);
        g.drawOval( (SX_FRET8 + SX_FRET9)/2-3, D_LABELHEIGHT1, 15, 25);
        
        g.drawOval( (SX_FRET0 + SX_FRET1)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET2 + SX_FRET3)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET4 + SX_FRET5)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET6 + SX_FRET7)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        g.drawOval( (SX_FRET8 + SX_FRET9)/2+2, D_LABELHEIGHT1 + 5, 5, 15);
        
        g.setColor(Color.BLACK);
        
        
       
        paintNotes(g);
        paintLessonNotes(g);

    }

    public void paintNotes(Graphics g) {

            if (displayAllNotes == true && noteVal.equals(null) == false) {

                if (noteVal.equals("A")) {
                    //add dot to mark note
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("A", D_LABELWIDTH5, D_LABELHEIGHT3);
                    g.drawString("A", D_LABELWIDTH0, D_LABELHEIGHT2);
                    g.drawString("A", D_LABELWIDTH7, D_LABELHEIGHT1);
                    g.drawString("A", D_LABELWIDTH2, D_LABELHEIGHT0);
                } else if (noteVal.equals("Bb")) {
                    //add dot to mark note
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("Bb", D_LABELWIDTH6, D_LABELHEIGHT3);
                    g.drawString("Bb", D_LABELWIDTH1, D_LABELHEIGHT2);
                    g.drawString("Bb", D_LABELWIDTH8, D_LABELHEIGHT1);
                    g.drawString("Bb", D_LABELWIDTH3, D_LABELHEIGHT0);
                } else if (noteVal.equals("B")) {
                    //add dot to mark note
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("B", D_LABELWIDTH7, D_LABELHEIGHT3);
                    g.drawString("B", D_LABELWIDTH2, D_LABELHEIGHT2);
                    g.drawString("B", D_LABELWIDTH9, D_LABELHEIGHT1);
                    g.drawString("B", D_LABELWIDTH4, D_LABELHEIGHT0);
                } else if (noteVal.equals("C")) {
                    //mark notes with dot
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);
                    g.drawString("C", D_LABELWIDTH3, D_LABELHEIGHT2);
                    g.drawString("C", D_LABELWIDTH10, D_LABELHEIGHT1);
                    g.drawString("C", D_LABELWIDTH5, D_LABELHEIGHT0);

                } else if (noteVal.equals("C#")) {
                    //mark notes with dot
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);
                    g.drawString("C#", D_LABELWIDTH4, D_LABELHEIGHT2);
                    g.drawString("C#", D_LABELWIDTH11, D_LABELHEIGHT1);
                    g.drawString("C#", D_LABELWIDTH6, D_LABELHEIGHT0);

                } else if (noteVal.equals("D")) {
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
                    g.drawString("D", D_LABELWIDTH5, D_LABELHEIGHT2);
                    g.drawString("D", D_LABELWIDTH0, D_LABELHEIGHT1);
                    g.drawString("D", D_LABELWIDTH7, D_LABELHEIGHT0);

                } else if (noteVal.equals("Eb")) {
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
                    g.drawString("Eb", D_LABELWIDTH6, D_LABELHEIGHT2);
                    g.drawString("Eb", D_LABELWIDTH1, D_LABELHEIGHT1);
                    g.drawString("Eb", D_LABELWIDTH8, D_LABELHEIGHT0);

                } else if (noteVal.equals("E")) {
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("E", D_LABELWIDTH0, D_LABELHEIGHT3);
                    g.drawString("E", D_LABELWIDTH7, D_LABELHEIGHT2);
                    g.drawString("E", D_LABELWIDTH2, D_LABELHEIGHT1);
                    g.drawString("E", D_LABELWIDTH9, D_LABELHEIGHT0);
                } else if (noteVal.equals("F") ){
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F", D_LABELWIDTH1, D_LABELHEIGHT3);
                    g.drawString("F", D_LABELWIDTH8, D_LABELHEIGHT2);
                    g.drawString("F", D_LABELWIDTH3, D_LABELHEIGHT1);
                    g.drawString("F", D_LABELWIDTH10, D_LABELHEIGHT0);

                } else if (noteVal.equals("F#")) {
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F#", D_LABELWIDTH2, D_LABELHEIGHT3);
                    g.drawString("F#", D_LABELWIDTH9, D_LABELHEIGHT2);
                    g.drawString("F#", D_LABELWIDTH4, D_LABELHEIGHT1);
                    g.drawString("F#", D_LABELWIDTH11, D_LABELHEIGHT0);

                } else if (noteVal.equals("G")) {
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G", D_LABELWIDTH3, D_LABELHEIGHT3);
                    g.drawString("G", D_LABELWIDTH10, D_LABELHEIGHT2);
                    g.drawString("G", D_LABELWIDTH5, D_LABELHEIGHT1);
                    g.drawString("G", D_LABELWIDTH0, D_LABELHEIGHT0);
                }
                else if (noteVal.equals("G#") ){
                    //add dot to mark notes
                    g.setColor(Color.RED);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G#", D_LABELWIDTH4, D_LABELHEIGHT3);
                    g.drawString("G#", D_LABELWIDTH11, D_LABELHEIGHT2);
                    g.drawString("G#", D_LABELWIDTH6, D_LABELHEIGHT1);
                    g.drawString("G#", D_LABELWIDTH1, D_LABELHEIGHT0);
                }

            }
            else if(displayAllNotes == false)
            {
            	/*
            	 * CURRENTLY NO NEED FOR OCTAVE 0 KEEPING IT FOR FUTURE USE IF NECESSARY
            	if(currentOctave == 0)
            	{
                    if (noteVal == "A") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("A", D_LABELWIDTH5, D_LABELHEIGHT3);
                        
                    } else if (noteVal == "Bb") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("Bb", D_LABELWIDTH6, D_LABELHEIGHT3);
                        
                    } else if (noteVal == "B") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("B", D_LABELWIDTH7, D_LABELHEIGHT3);
                        
                    } else if (noteVal == "C") {
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);
                    } else if (noteVal == "C#") {
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);
                    } else if (noteVal == "D") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
                    } else if (noteVal == "Eb") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
                    } else if (noteVal == "E") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH0, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("E", D_LABELWIDTH0, D_LABELHEIGHT3);
                        
                    } else if (noteVal == "F") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH1, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F", D_LABELWIDTH1, D_LABELHEIGHT3);
                    } else if (noteVal == "F#") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH2, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F#", D_LABELWIDTH2, D_LABELHEIGHT3);
                    } else if (noteVal == "G") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH3, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G", D_LABELWIDTH3, D_LABELHEIGHT3);
                    }
                    else if (noteVal == "G#") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH4, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G#", D_LABELWIDTH4, D_LABELHEIGHT3);
                    }
            	}
            	*/
            	if(currentOctave == 1)
            	{
                    if (noteVal.equals("A")) {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH0, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);

                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("A", D_LABELWIDTH5, D_LABELHEIGHT3);
                        g.drawString("A", D_LABELWIDTH0, D_LABELHEIGHT2);
                        
                    } else if (noteVal.equals("Bb") ){
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH1, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);

                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("Bb", D_LABELWIDTH6, D_LABELHEIGHT3);
                        g.drawString("Bb", D_LABELWIDTH1, D_LABELHEIGHT2);

                    } else if (noteVal.equals("B")) {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH2, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);

                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("B", D_LABELWIDTH7, D_LABELHEIGHT3);
                        g.drawString("B", D_LABELWIDTH2, D_LABELHEIGHT2);
                        
                    } else if (noteVal.equals("C")) {
                    	/*
                         * CURRENTLY NOTE BEING USED AT THIS TUNING 
                    	//mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);
						*/
                    } else if (noteVal.equals("C#") ){
                    	/*
                         * CURRENTLY NOTE BEING USED AT THIS TUNING
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);
						*/
                    } else if (noteVal.equals("D") ){
                    	/*
                         * CURRENTLY NOTE BEING USED AT THIS TUNING
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
						*/
                    } else if (noteVal.equals("Eb") ){
                    	/*
                         * CURRENTLY NOTE BEING USED AT THIS TUNING
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
						*/
                    } else if (noteVal.equals("E") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH0, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("E", D_LABELWIDTH0, D_LABELHEIGHT3);
                        
                    } else if (noteVal.equals("F") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH1, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F", D_LABELWIDTH1, D_LABELHEIGHT3);

                    } else if (noteVal.equals("F#") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH2, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F#", D_LABELWIDTH2, D_LABELHEIGHT3);

                    } else if (noteVal.equals("G") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH3, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G", D_LABELWIDTH3, D_LABELHEIGHT3);
                    }
                    else if (noteVal.equals("G#") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH4, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G#", D_LABELWIDTH4, D_LABELHEIGHT3);
                    }
            	}
            	else if(currentOctave == 2)
            	{
            		 if (noteVal.equals("A") ) {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH2, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("A", D_LABELWIDTH7, D_LABELHEIGHT1);
                        g.drawString("A", D_LABELWIDTH2, D_LABELHEIGHT0);
                    } else if (noteVal.equals("Bb") ){
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH3, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("Bb", D_LABELWIDTH8, D_LABELHEIGHT1);
                        g.drawString("Bb", D_LABELWIDTH3, D_LABELHEIGHT0);
                    } else if (noteVal.equals("B") ){
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH4, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("B", D_LABELWIDTH9, D_LABELHEIGHT1);
                        g.drawString("B", D_LABELWIDTH4, D_LABELHEIGHT0);
                    } else if (noteVal.equals("C") ){
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH3, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C", D_LABELWIDTH3, D_LABELHEIGHT2);
                        g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);

                    } else if (noteVal.equals("C#") ){
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH4, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C#", D_LABELWIDTH4, D_LABELHEIGHT2);
                        g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);

                    } else if (noteVal.equals("D") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH0, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                        
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("D", D_LABELWIDTH0, D_LABELHEIGHT1);
                        g.drawString("D", D_LABELWIDTH5, D_LABELHEIGHT2);
                        g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
                    } else if (noteVal.equals("Eb") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("Eb", D_LABELWIDTH6, D_LABELHEIGHT2);
                        g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
                    } else if (noteVal.equals("E") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH2, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("E", D_LABELWIDTH7, D_LABELHEIGHT2);
                        g.drawString("E", D_LABELWIDTH2, D_LABELHEIGHT1);
                    } else if (noteVal.equals("F") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH3, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F", D_LABELWIDTH8, D_LABELHEIGHT2);
                        g.drawString("F", D_LABELWIDTH3, D_LABELHEIGHT1);

                    } else if (noteVal.equals("F#") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH4, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F#", D_LABELWIDTH9, D_LABELHEIGHT2);
                        g.drawString("F#", D_LABELWIDTH4, D_LABELHEIGHT1);

                    } else if (noteVal.equals("G") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH0, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G", D_LABELWIDTH10, D_LABELHEIGHT2);
                        g.drawString("G", D_LABELWIDTH5, D_LABELHEIGHT1);
                        g.drawString("G", D_LABELWIDTH0, D_LABELHEIGHT0);
                    }
                    else if (noteVal.equals("G#") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH1, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G#", D_LABELWIDTH11, D_LABELHEIGHT2);
                        g.drawString("G#", D_LABELWIDTH6, D_LABELHEIGHT1);
                        g.drawString("G#", D_LABELWIDTH1, D_LABELHEIGHT0);
                    }
            	}
            	else if(currentOctave == 3)
            	{
            		/*
            		 * NOT CURRENTLY BEING USED BY TUNING EXPECTED, KEEPING IF NECESSARY IN THE FUTURE
            		 
                    if (noteVal == "A") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("A", D_LABELWIDTH7, D_LABELHEIGHT1);
                    } else if (noteVal == "Bb") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("Bb", D_LABELWIDTH8, D_LABELHEIGHT1);
                    } else if (noteVal == "B") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("B", D_LABELWIDTH9, D_LABELHEIGHT1);
                    } 
                    */
                    if (noteVal.equals("C") ){
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C", D_LABELWIDTH10, D_LABELHEIGHT1);
                        g.drawString("C", D_LABELWIDTH5, D_LABELHEIGHT0);

                    } else if (noteVal.equals("C#") ){
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C#", D_LABELWIDTH11, D_LABELHEIGHT1);
                        g.drawString("C#", D_LABELWIDTH6, D_LABELHEIGHT0);

                    } else if (noteVal.equals("D") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("D", D_LABELWIDTH7, D_LABELHEIGHT0);

                    } else if (noteVal.equals("Eb") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("Eb", D_LABELWIDTH8, D_LABELHEIGHT0);

                    } else if (noteVal.equals("E") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("E", D_LABELWIDTH9, D_LABELHEIGHT0);
                    } else if (noteVal.equals("F") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F", D_LABELWIDTH10, D_LABELHEIGHT0);

                    } else if (noteVal.equals("F#") ){
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F#", D_LABELWIDTH11, D_LABELHEIGHT0);

                    } 
                    /*
            		 * NOT CURRENTLY BEING USED BY TUNING EXPECTED, KEEPING IF NECESSARY IN THE FUTURE
            		 
            		 else if (noteVal == "G") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G", D_LABELWIDTH5, D_LABELHEIGHT1);
                    }
                    else if (noteVal == "G#") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G#", D_LABELWIDTH6, D_LABELHEIGHT1);
                    }
                    */
            	}
            	/* 
            	 * 
            	 * CURRENTLY THERE IS NO NEED FOR OCTAVE 4 KEEPING IN CODE INCASE I OPEN POSSIBILITY FOR GUITARS
            	 
            	else if(currentOctave == 4)
            	{
                    if (noteVal == "A") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH2, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("A", D_LABELWIDTH2, D_LABELHEIGHT0);
                    } else if (noteVal == "Bb") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH3, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("Bb", D_LABELWIDTH3, D_LABELHEIGHT0);
                    } else if (noteVal == "B") {
                        //add dot to mark note
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH4, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label
                        g.setColor(Color.BLACK);
                        g.drawString("B", D_LABELWIDTH4, D_LABELHEIGHT0);
                    } else if (noteVal == "C") {
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH5, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C", D_LABELWIDTH5, D_LABELHEIGHT0);
                    } else if (noteVal == "C#") {
                        //mark notes with dot
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH6, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("C#", D_LABELWIDTH6, D_LABELHEIGHT0);
                    } else if (noteVal == "D") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH7, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("D", D_LABELWIDTH7, D_LABELHEIGHT0);
                    } else if (noteVal == "Eb") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH8, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //label dot
                        g.setColor(Color.BLACK);
                        g.drawString("Eb", D_LABELWIDTH8, D_LABELHEIGHT0);
                    } else if (noteVal == "E") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH9, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("E", D_LABELWIDTH9, D_LABELHEIGHT0);
                    } else if (noteVal == "F") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH10, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F", D_LABELWIDTH10, D_LABELHEIGHT0);
                    } else if (noteVal == "F#") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH11, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("F#", D_LABELWIDTH11, D_LABELHEIGHT0);
                    } else if (noteVal == "G") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH0, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G", D_LABELWIDTH0, D_LABELHEIGHT0);
                    }
                    else if (noteVal == "G#") {
                        //add dot to mark notes
                        g.setColor(Color.RED);
                        g.fillOval(DOTWIDTH1, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                        //add label to dot
                        g.setColor(Color.BLACK);
                        g.drawString("G#", D_LABELWIDTH1, D_LABELHEIGHT0);
                    }
            	}
            	*/
            	else if(noteVal.equals("REST") == true)
            	{
            		g.setColor(Color.RED);
            		g.fillOval(0, 0, 0, 0);
            		
            		g.setColor(Color.BLACK);
            		g.drawString("", 0, 0);
            	}
            }//end outer conditional
            
        } //end paintNotes
    
    
    
    public void setLesNoteVal(String noteV) {
        lNoteVal = noteV;
    }

    public void setLesOctave(int oct)
    {
    	lOct = oct;
    }
    
    public void setLesType(String type){
    	lType = type;
    }
    
    public void setLesColor(boolean lColor){
    	
    	if(lColor == true){
    		lesColor = Color.GREEN;
    	}
    	else
    	{
    		lesColor = Color.BLUE;
    	}
    	
    }
    
    public Color getLessonColor(){
    	return lesColor;
    }
    
    public void paintLessonNotes(Graphics g) {

    	Color lessonColor;
    	
    	lessonColor = getLessonColor();
    	//lessonColor = lesColor;
    	
        if (displayAllNotes == true) {
        	
            if (lNoteVal.equals("A") ){
                //add dot to mark note
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH5, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH0, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH7, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH2, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label
                g.setColor(Color.BLACK);
                g.drawString("A", D_LABELWIDTH5, D_LABELHEIGHT3);
                g.drawString("A", D_LABELWIDTH0, D_LABELHEIGHT2);
                g.drawString("A", D_LABELWIDTH7, D_LABELHEIGHT1);
                g.drawString("A", D_LABELWIDTH2, D_LABELHEIGHT0);
            } else if (lNoteVal.equals("Bb") ){
                //add dot to mark note
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH6, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH1, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH8, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH3, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label
                g.setColor(Color.BLACK);
                g.drawString("Bb", D_LABELWIDTH6, D_LABELHEIGHT3);
                g.drawString("Bb", D_LABELWIDTH1, D_LABELHEIGHT2);
                g.drawString("Bb", D_LABELWIDTH8, D_LABELHEIGHT1);
                g.drawString("Bb", D_LABELWIDTH3, D_LABELHEIGHT0);
            } else if (lNoteVal.equals("B") ){
                //add dot to mark note
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH7, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH2, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH9, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH4, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label
                g.setColor(Color.BLACK);
                g.drawString("B", D_LABELWIDTH7, D_LABELHEIGHT3);
                g.drawString("B", D_LABELWIDTH2, D_LABELHEIGHT2);
                g.drawString("B", D_LABELWIDTH9, D_LABELHEIGHT1);
                g.drawString("B", D_LABELWIDTH4, D_LABELHEIGHT0);
            } else if (lNoteVal.equals("C") ){
                //mark notes with dot
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH3, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH10, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH5, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);
                g.drawString("C", D_LABELWIDTH3, D_LABELHEIGHT2);
                g.drawString("C", D_LABELWIDTH10, D_LABELHEIGHT1);
                g.drawString("C", D_LABELWIDTH5, D_LABELHEIGHT0);

            } else if (lNoteVal.equals("C#") ){
                //mark notes with dot
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH4, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH11, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH6, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);
                g.drawString("C#", D_LABELWIDTH4, D_LABELHEIGHT2);
                g.drawString("C#", D_LABELWIDTH11, D_LABELHEIGHT1);
                g.drawString("C#", D_LABELWIDTH6, D_LABELHEIGHT0);

            } else if (lNoteVal.equals("D") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH5, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH0, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH7, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //label dot
                g.setColor(Color.BLACK);
                g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
                g.drawString("D", D_LABELWIDTH5, D_LABELHEIGHT2);
                g.drawString("D", D_LABELWIDTH0, D_LABELHEIGHT1);
                g.drawString("D", D_LABELWIDTH7, D_LABELHEIGHT0);

            } else if (lNoteVal.equals("Eb") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH6, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH1, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH8, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //label dot
                g.setColor(Color.BLACK);
                g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
                g.drawString("Eb", D_LABELWIDTH6, D_LABELHEIGHT2);
                g.drawString("Eb", D_LABELWIDTH1, D_LABELHEIGHT1);
                g.drawString("Eb", D_LABELWIDTH8, D_LABELHEIGHT0);

            } else if (lNoteVal.equals("E") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH0, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH7, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH2, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH9, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("E", D_LABELWIDTH0, D_LABELHEIGHT3);
                g.drawString("E", D_LABELWIDTH7, D_LABELHEIGHT2);
                g.drawString("E", D_LABELWIDTH2, D_LABELHEIGHT1);
                g.drawString("E", D_LABELWIDTH9, D_LABELHEIGHT0);
            } else if (lNoteVal.equals("F") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH1, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH8, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH3, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH10, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("F", D_LABELWIDTH1, D_LABELHEIGHT3);
                g.drawString("F", D_LABELWIDTH8, D_LABELHEIGHT2);
                g.drawString("F", D_LABELWIDTH3, D_LABELHEIGHT1);
                g.drawString("F", D_LABELWIDTH10, D_LABELHEIGHT0);

            } else if (lNoteVal.equals("F#") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH2, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH9, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH4, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH11, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("F#", D_LABELWIDTH2, D_LABELHEIGHT3);
                g.drawString("F#", D_LABELWIDTH9, D_LABELHEIGHT2);
                g.drawString("F#", D_LABELWIDTH4, D_LABELHEIGHT1);
                g.drawString("F#", D_LABELWIDTH11, D_LABELHEIGHT0);

            } else if (lNoteVal.equals("G") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH3, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH10, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH5, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH0, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("G", D_LABELWIDTH3, D_LABELHEIGHT3);
                g.drawString("G", D_LABELWIDTH10, D_LABELHEIGHT2);
                g.drawString("G", D_LABELWIDTH5, D_LABELHEIGHT1);
                g.drawString("G", D_LABELWIDTH0, D_LABELHEIGHT0);
            }
            else if (lNoteVal.equals("G#") ){
                //add dot to mark notes
                g.setColor(lessonColor);
                g.fillOval(DOTWIDTH4, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH11, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH6, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                g.fillOval(DOTWIDTH1, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                //add label to dot
                g.setColor(Color.BLACK);
                g.drawString("G#", D_LABELWIDTH4, D_LABELHEIGHT3);
                g.drawString("G#", D_LABELWIDTH11, D_LABELHEIGHT2);
                g.drawString("G#", D_LABELWIDTH6, D_LABELHEIGHT1);
                g.drawString("G#", D_LABELWIDTH1, D_LABELHEIGHT0);
            }

        }
        else if(displayAllNotes == false)
        {
        	/*
        	 * CURRENTLY NO NEED FOR OCTAVE 0 KEEPING IT IN CODE FOR LATER USE IF NECESSARY
        	
        	if(lOct == 0)
        	{
                if (lNoteVal == "A") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("A", D_LABELWIDTH5, D_LABELHEIGHT3);
                    
                } else if (lNoteVal == "Bb") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("Bb", D_LABELWIDTH6, D_LABELHEIGHT3);
                    
                } else if (lNoteVal == "B") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("B", D_LABELWIDTH7, D_LABELHEIGHT3);
                    
                } else if (lNoteVal == "C") {
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);
                } else if (lNoteVal == "C#") {
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);
                } else if (lNoteVal == "D") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
                } else if (lNoteVal == "Eb") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
                } else if (lNoteVal == "E") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("E", D_LABELWIDTH0, D_LABELHEIGHT3);
                    
                } else if (lNoteVal == "F") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F", D_LABELWIDTH1, D_LABELHEIGHT3);
                } else if (lNoteVal == "F#") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F#", D_LABELWIDTH2, D_LABELHEIGHT3);
                } else if (lNoteVal == "G") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G", D_LABELWIDTH3, D_LABELHEIGHT3);
                }
                else if (lNoteVal == "G#") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G#", D_LABELWIDTH4, D_LABELHEIGHT3);
                }
        	}
        	 */
        	if(lOct == 1)
        	{
                if (lNoteVal.equals("A") ){
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("A", D_LABELWIDTH5, D_LABELHEIGHT3);
                    g.drawString("A", D_LABELWIDTH0, D_LABELHEIGHT2);
                    
                } else if (lNoteVal.equals("Bb") ){
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("Bb", D_LABELWIDTH6, D_LABELHEIGHT3);
                    g.drawString("Bb", D_LABELWIDTH1, D_LABELHEIGHT2);

                } else if (lNoteVal.equals("B") ){
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("B", D_LABELWIDTH7, D_LABELHEIGHT3);
                    g.drawString("B", D_LABELWIDTH2, D_LABELHEIGHT2);
                    
                } else if (lNoteVal.equals("C") ){
                	/*
                     * CURRENTLY NOTE BEING USED AT THIS TUNING 
                	//mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);
					*/
                } else if (lNoteVal.equals("C#") ){
                	/*
                     * CURRENTLY NOTE BEING USED AT THIS TUNING
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);
					*/
                } else if (lNoteVal.equals("D") ){
                	/*
                     * CURRENTLY NOTE BEING USED AT THIS TUNING
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
					*/
                } else if (lNoteVal.equals("Eb") ){
                	/*
                     * CURRENTLY NOTE BEING USED AT THIS TUNING
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
					*/
                } else if (lNoteVal.equals("E") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("E", D_LABELWIDTH0, D_LABELHEIGHT3);
                    
                } else if (lNoteVal.equals("F") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F", D_LABELWIDTH1, D_LABELHEIGHT3);

                } else if (lNoteVal.equals("F#") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F#", D_LABELWIDTH2, D_LABELHEIGHT3);

                } else if (lNoteVal.equals("G") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G", D_LABELWIDTH3, D_LABELHEIGHT3);
                }
                else if (lNoteVal.equals("G#") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G#", D_LABELWIDTH4, D_LABELHEIGHT3);
                }
        	}
        	else if(lOct == 2)
        	{
                if (lNoteVal.equals("A") ){
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("A", D_LABELWIDTH7, D_LABELHEIGHT1);
                    g.drawString("A", D_LABELWIDTH2, D_LABELHEIGHT0);
                } else if (lNoteVal.equals("Bb") ){
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("Bb", D_LABELWIDTH8, D_LABELHEIGHT1);
                    g.drawString("Bb", D_LABELWIDTH3, D_LABELHEIGHT0);
                } else if (lNoteVal.equals("B") ){
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("B", D_LABELWIDTH9, D_LABELHEIGHT1);
                    g.drawString("B", D_LABELWIDTH4, D_LABELHEIGHT0);
                } else if (lNoteVal.equals("C") ){
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C", D_LABELWIDTH3, D_LABELHEIGHT2);
                    g.drawString("C", D_LABELWIDTH8, D_LABELHEIGHT3);

                } else if (lNoteVal.equals("C#") ){
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C#", D_LABELWIDTH4, D_LABELHEIGHT2);
                    g.drawString("C#", D_LABELWIDTH9, D_LABELHEIGHT3);

                } else if (lNoteVal.equals("D") ){
                	//add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);
                    
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("D", D_LABELWIDTH0, D_LABELHEIGHT1);
                    g.drawString("D", D_LABELWIDTH5, D_LABELHEIGHT2);
                    g.drawString("D", D_LABELWIDTH10, D_LABELHEIGHT3);
                } else if (lNoteVal.equals("Eb") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT3, DOT_RADIUS, DOT_RADIUS);

                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("Eb", D_LABELWIDTH6, D_LABELHEIGHT2);
                    g.drawString("Eb", D_LABELWIDTH11, D_LABELHEIGHT3);
                } else if (lNoteVal.equals("E") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("E", D_LABELWIDTH7, D_LABELHEIGHT2);
                    g.drawString("E", D_LABELWIDTH2, D_LABELHEIGHT1);
                } else if (lNoteVal.equals("F") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F", D_LABELWIDTH8, D_LABELHEIGHT2);
                    g.drawString("F", D_LABELWIDTH3, D_LABELHEIGHT1);

                } else if (lNoteVal.equals("F#") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F#", D_LABELWIDTH9, D_LABELHEIGHT2);
                    g.drawString("F#", D_LABELWIDTH4, D_LABELHEIGHT1);

                } else if (lNoteVal.equals("G") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G", D_LABELWIDTH10, D_LABELHEIGHT2);
                    g.drawString("G", D_LABELWIDTH5, D_LABELHEIGHT1);
                    g.drawString("G", D_LABELWIDTH0, D_LABELHEIGHT0);
                }
                else if (lNoteVal.equals("G#") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT2, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G#", D_LABELWIDTH11, D_LABELHEIGHT2);
                    g.drawString("G#", D_LABELWIDTH6, D_LABELHEIGHT1);
                    g.drawString("G#", D_LABELWIDTH1, D_LABELHEIGHT0);
                }
        	}
        	else if(lOct == 3)
        	{
        		/*
        		 * NOT CURRENTLY BEING USED BY TUNING EXPECTED, KEEPING IF NECESSARY IN THE FUTURE
        		 
                if (lNoteVal == "A") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("A", D_LABELWIDTH7, D_LABELHEIGHT1);
                } else if (lNoteVal == "Bb") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("Bb", D_LABELWIDTH8, D_LABELHEIGHT1);
                } else if (lNoteVal == "B") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("B", D_LABELWIDTH9, D_LABELHEIGHT1);
                } 
                */
                if (lNoteVal.equals("C") ){
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C", D_LABELWIDTH10, D_LABELHEIGHT1);
                    g.drawString("C", D_LABELWIDTH5, D_LABELHEIGHT0);

                } else if (lNoteVal.equals("C#") ){
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C#", D_LABELWIDTH11, D_LABELHEIGHT1);
                    g.drawString("C#", D_LABELWIDTH6, D_LABELHEIGHT0);

                } else if (lNoteVal.equals("D") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("D", D_LABELWIDTH7, D_LABELHEIGHT0);

                } else if (lNoteVal.equals("Eb") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("Eb", D_LABELWIDTH8, D_LABELHEIGHT0);

                } else if (lNoteVal.equals("E") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("E", D_LABELWIDTH9, D_LABELHEIGHT0);
                } else if (lNoteVal.equals("F") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F", D_LABELWIDTH10, D_LABELHEIGHT0);

                } else if (lNoteVal.equals("F#") ){
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);

                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F#", D_LABELWIDTH11, D_LABELHEIGHT0);

                } 
                /*
        		 * NOT CURRENTLY BEING USED BY TUNING EXPECTED, KEEPING IF NECESSARY IN THE FUTURE
        		 
        		 else if (lNoteVal == "G") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G", D_LABELWIDTH5, D_LABELHEIGHT1);
                }
                else if (lNoteVal == "G#") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT1, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G#", D_LABELWIDTH6, D_LABELHEIGHT1);
                }
                */
        	}
        	
        	/*
        	 *   CURRENTLY THERE IS NO NEED FOR OCTAVE 4 KEEPING IT IN CODE FOR FUTURE USE (POSSIBLY)
        	else if(lOct == 4)
        	{
                if (lNoteVal == "A") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH2, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("A", D_LABELWIDTH2, D_LABELHEIGHT0);
                } else if (lNoteVal == "Bb") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH3, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("Bb", D_LABELWIDTH3, D_LABELHEIGHT0);
                } else if (lNoteVal == "B") {
                    //add dot to mark note
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH4, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label
                    g.setColor(Color.BLACK);
                    g.drawString("B", D_LABELWIDTH4, D_LABELHEIGHT0);
                } else if (lNoteVal == "C") {
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH5, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C", D_LABELWIDTH5, D_LABELHEIGHT0);
                } else if (lNoteVal == "C#") {
                    //mark notes with dot
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH6, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("C#", D_LABELWIDTH6, D_LABELHEIGHT0);
                } else if (lNoteVal == "D") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH7, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("D", D_LABELWIDTH7, D_LABELHEIGHT0);
                } else if (lNoteVal == "Eb") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH8, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //label dot
                    g.setColor(Color.BLACK);
                    g.drawString("Eb", D_LABELWIDTH8, D_LABELHEIGHT0);
                } else if (lNoteVal == "E") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH9, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("E", D_LABELWIDTH9, D_LABELHEIGHT0);
                } else if (lNoteVal == "F") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH10, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F", D_LABELWIDTH10, D_LABELHEIGHT0);
                } else if (lNoteVal == "F#") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH11, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("F#", D_LABELWIDTH11, D_LABELHEIGHT0);
                } else if (lNoteVal == "G") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH0, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G", D_LABELWIDTH0, D_LABELHEIGHT0);
                }
                else if (lNoteVal == "G#") {
                    //add dot to mark notes
                    g.setColor(lessonColor);
                    g.fillOval(DOTWIDTH1, DOTHEIGHT0, DOT_RADIUS, DOT_RADIUS);
                    //add label to dot
                    g.setColor(Color.BLACK);
                    g.drawString("G#", D_LABELWIDTH1, D_LABELHEIGHT0);
                }
        	}
        	*/
        	else if(lNoteVal.equals("REST") == true)
        	{
        		g.setColor(lessonColor);
        		g.fillOval(0, 0, 0, 0);
        		
        		g.setColor(Color.BLACK);
        		g.drawString("", 0, 0);
        	}
        }//end outer conditional
        
    } //end paintNotesForLesson

} //end class
