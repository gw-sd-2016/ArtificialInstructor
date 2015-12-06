package currentAI;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class FretBoardPanel extends JPanel {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String noteVal = "";

    private final int CORNERFRETBOARD = 50;
    private final int WIDTHFRETBOARD = 900;
    private final int HEIGHTFRETBOARD = 250;

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

    private final int D_LABELWIDTH0 = DOTWIDTH0 + 12;
    private final int D_LABELWIDTH1 = DOTWIDTH1 + 12;
    private final int D_LABELWIDTH2 = DOTWIDTH2 + 12;
    private final int D_LABELWIDTH3 = DOTWIDTH3 + 12;
    private final int D_LABELWIDTH4 = DOTWIDTH4 + 12;
    private final int D_LABELWIDTH5 = DOTWIDTH5 + 12;
    private final int D_LABELWIDTH6 = DOTWIDTH6 + 12;
    private final int D_LABELWIDTH7 = DOTWIDTH7 + 12;
    private final int D_LABELWIDTH8 = DOTWIDTH8 + 12;
    private final int D_LABELWIDTH9 = DOTWIDTH9 + 12;
    private final int D_LABELWIDTH10 = DOTWIDTH10 + 12;
    private final int D_LABELWIDTH11 = DOTWIDTH11 + 12;

    private final int DOTHEIGHT0 = 85;
    private final int DOTHEIGHT1 = 135;
    private final int DOTHEIGHT2 = 185;
    private final int DOTHEIGHT3 = 235;

    private final int D_LABELHEIGHT0 = 112;
    private final int D_LABELHEIGHT1 = 162;
    private final int D_LABELHEIGHT2 = 212;
    private final int D_LABELHEIGHT3 = 262;

    private final int DOT_RADIUS = 40;

    private boolean displayAllNotes = true;
    private int currentOctave = 0;
    
    public FretBoardPanel() {

    }

    public void setNoteVal(String note) {
        System.out.println("setNote");
        noteVal = note;
    }

    public void setNoteDisplayMode(boolean mode) {
        displayAllNotes = mode;
    }

    public void setOctave(int oct)
    {
    	currentOctave = oct;
    }
    
    protected void paintComponent(Graphics g) {
        System.out.println("AT PAINTCOMP");
        //super.paintComponent(g);       

        g.setFont(new Font("Times New Roman Bold", 25, 25));

        //Fill rectange for FretBoard
        g.setColor(Color.WHITE);
        g.fillRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

        //Draw rectange for FretBoard
        g.setColor(Color.BLACK);
        g.drawRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

        //draw frets
        g.drawLine(SX_FRET0, CORNERFRETBOARD, SX_FRET0, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET1, CORNERFRETBOARD, SX_FRET1, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET2, CORNERFRETBOARD, SX_FRET2, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET3, CORNERFRETBOARD, SX_FRET3, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET4, CORNERFRETBOARD, SX_FRET4, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET5, CORNERFRETBOARD, SX_FRET5, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET6, CORNERFRETBOARD, SX_FRET6, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET7, CORNERFRETBOARD, SX_FRET7, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET8, CORNERFRETBOARD, SX_FRET8, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET9, CORNERFRETBOARD, SX_FRET9, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET10, CORNERFRETBOARD, SX_FRET10, HEIGHTFRETBOARD + CORNERFRETBOARD);
        g.drawLine(SX_FRET11, CORNERFRETBOARD, SX_FRET11, HEIGHTFRETBOARD + CORNERFRETBOARD);

        //label frets
        g.drawString("Open", 50, 35);
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

        paintNotes(g);

    }

    public void paintNotes(Graphics g) {

            if (displayAllNotes == true) {

                if (noteVal == "A") {
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
                } else if (noteVal == "Bb") {
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
                } else if (noteVal == "B") {
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
                } else if (noteVal == "C") {
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

                } else if (noteVal == "C#") {
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

                } else if (noteVal == "D") {
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

                } else if (noteVal == "Eb") {
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

                } else if (noteVal == "E") {
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
                } else if (noteVal == "F") {
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

                } else if (noteVal == "F#") {
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

                } else if (noteVal == "G") {
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

            }
            else if(displayAllNotes == false)
            {
            	
            }
        } //end paintNotes

} //end class