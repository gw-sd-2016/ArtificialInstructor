package currentAI;


import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class UpNextPanel extends JPanel {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /*
     * Default sizing variables 
     */
    private final int CORNERFRETBOARD = 25;
    private final int WIDTHFRETBOARD = 540;
    private final int HEIGHTFRETBOARD = 250;
    
    /*
     * Constants for height of panel to set the label of dot to start at
     * 		0 = HIGHEST STRING
     * 		1 = 2nd from top
     * 		2 = 3rd from top
     * 		4 = LOWEST STRING
     */
    private final int D_LABELHEIGHT0 = 112 - 20;
    private final int D_LABELHEIGHT1 = 162- 20;
    private final int D_LABELHEIGHT2 = 212-20;
    private final int D_LABELHEIGHT3 = 262-20;
    
    
    private final int NEXT_LINES_1 = CORNERFRETBOARD + 180 ;
    private final int NEXT_LINES_2 = CORNERFRETBOARD + 180*2 ;
    private final int NEXT_LINES_3 = CORNERFRETBOARD + 180*3 ;
    //private final int NEXT_LINES_6 = 950;
    
    
    private final int DOTWIDTH0 = 130 - 25;
    private final int DOTWIDTH1 = 310 - 25;
    private final int DOTWIDTH2 = 490 - 25;
    
    private final int D_LABELWIDTH0 = DOTWIDTH0 + 7;
    private final int D_LABELWIDTH1 = DOTWIDTH1 + 7;
    private final int D_LABELWIDTH2 = DOTWIDTH2 + 7;
    
    private final int DOTHEIGHT0 = 85 - 25;
    private final int DOTHEIGHT1 = 135 - 25;
    private final int DOTHEIGHT2 = 185 - 25;
    private final int DOTHEIGHT3 = 235 - 25;
    
    private final int DOT_RADIUS = 40; 
    
    /*
     * Variables 
     */
    private int currentCnt;
    private String [] nVals;
    private String secondNote, thirdNote, fourthNote;
    private int secondOct, thirdOct, fourthOct;
    private String [] nType;
    private double [] nTimes;
    private int [] nOcts;
    private double [] nGracePeriod;
    private Color dotColor = Color.BLUE;
    private boolean lessonFinished = false;
    double timeUntil1, timeUntil2, timeUntil3;
    
    
    //Class constructor
    public UpNextPanel( String [] vals, String [] types, double [] times, int [] octs, double [] graceP) {
    	nVals = vals;
    	nType = types;
    	nTimes = times;
    	nOcts = octs;
    	nGracePeriod = graceP;
    	currentCnt = 0;
    	secondNote = nVals[0];
    	secondOct = nOcts[0];
    	
    	if(nVals.length > 1){
    		thirdNote = nVals[1];
    		thirdOct = nOcts[1];
    		
    		if(nVals.length > 2){
    	    	fourthNote = nVals[2];
    	    	fourthOct = nOcts[2];
    		}
    	}//end if
    	
    }//end constructor

    public void assignNotes(String [] notes)
    {
    	nVals = notes;
    }
    
    public void assignTimes(double [] times)
    {
    	nTimes = times;
    }
    
    public void assignType(String [] rings)
    {
    	nType = rings;
    }
    
    public void assignOcts(int [] octs)
    {
    	nOcts = octs;
    }
    
    public void assignGP(double [] gracePeriod)
    {
    	nGracePeriod = gracePeriod;
    }
    
    //Setter method for global counter
    public void setCounter(int cnt){
    	currentCnt = cnt;
    	
    	setNotes();
    	
    }
    
    
    public void lessonFinished(){
    	lessonFinished = true;
    }
    
    public void resetLesson(){
    	lessonFinished = false;
    	currentCnt = 0;
    	
    	setNotes();
    }
    
    public void setNotes(){
    	
    	if( currentCnt >= 0){
    		
    		if(currentCnt < nVals.length - 2){
    			secondNote = nVals[currentCnt];
    			thirdNote = nVals[currentCnt + 1];
    			fourthNote = nVals[currentCnt + 2];
    	
    			secondOct = nOcts[currentCnt];
    			thirdOct = nOcts[currentCnt + 1];
    			fourthOct = nOcts[currentCnt + 2];
    		}
    		else if(currentCnt < nVals.length - 1){
    			secondNote = nVals[currentCnt];
    			thirdNote = nVals[currentCnt + 1];
    			fourthNote = "";
        		
    			secondOct = nOcts[currentCnt];
    			thirdOct = nOcts[currentCnt + 1];
    			fourthOct = -1;
    		}
    		else if(currentCnt < nVals.length){
    			secondNote = nVals[currentCnt];
    			thirdNote = "";
        		fourthNote = "";
        		
        		secondOct = nOcts[currentCnt];
        		thirdOct = -1;
        		fourthOct = -1;
    		}
    		
    	}
    }
    
    //Getter method for global counter
    public int getCounter(){
    	return currentCnt;
    	
    }
    
    //Paint Components to be called with GUI is updated, or initialized
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);       
    	 g.setFont(new Font("Times New Roman Bold", 25, 25));
    	 g.setFont(g.getFont().deriveFont(Font.BOLD, g.getFont().getSize()+7));
    	 
         //Fill rectangle for FretBoard
         g.setColor(Color.WHITE);
         g.fillRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

         //Draw rectangle for FretBoard
         g.setColor(Color.BLACK);
         g.drawRect(CORNERFRETBOARD, CORNERFRETBOARD, WIDTHFRETBOARD, HEIGHTFRETBOARD);

         
         //Draw strings on fretboard 
         g.setColor(Color.GRAY);
         g.drawLine(CORNERFRETBOARD - 15, 100- 25, WIDTHFRETBOARD + CORNERFRETBOARD + 15, 100- 25);
         g.drawLine(CORNERFRETBOARD - 15, 150- 25, WIDTHFRETBOARD + CORNERFRETBOARD + 15, 150- 25);
         g.drawLine(CORNERFRETBOARD - 15, 200- 25, WIDTHFRETBOARD + CORNERFRETBOARD + 15, 200- 25);
         g.drawLine(CORNERFRETBOARD - 15, 250- 25, WIDTHFRETBOARD + CORNERFRETBOARD + 15, 250- 25);

         //Label Strings
         g.setColor(Color.BLACK);
         g.drawString("G", 5, D_LABELHEIGHT0);
         g.drawString("D", 5, D_LABELHEIGHT1);
         g.drawString("A", 5, D_LABELHEIGHT2);
         g.drawString("E", 5, D_LABELHEIGHT3);
         
         
         g.drawLine(NEXT_LINES_1, 50- 25, NEXT_LINES_1, 340- 25);
         g.drawLine(NEXT_LINES_2, 50- 25, NEXT_LINES_2, 340- 25);
         g.drawLine(NEXT_LINES_3, 50- 25, NEXT_LINES_3, 340- 25);
         

         
         g.drawString("Current", 100 - 55, 350 - 45);
         g.drawString("Next", 280 - 35, 350 - 45);
         g.drawString("2 Away", 460 - 35, 350 - 45);
         
         //Draw the times until the next notes
         if(timeUntil1 != -1)
         {
        	 if(timeUntil1 <= nGracePeriod[currentCnt]){
        		 g.setColor(Color.GREEN);
        		 g.drawString(Double.toString(timeUntil1) , 100 - 25, 350 - 15);
        		 g.setColor(Color.BLACK);
        	 }
        	 else
        	 {
        		 g.drawString(Double.toString(timeUntil1) , 100 - 25, 350 - 15);
        	 }
         } 
         if(timeUntil2 != -1)
         {
        	 g.drawString(Double.toString(timeUntil2), 280 - 25, 350 - 15);
         }
         if(timeUntil3 != -1)
         {
        	 g.drawString(Double.toString(timeUntil3), 460 - 25, 350 - 15);
         }
         
         
         if(lessonFinished == false)
         {
        	 int i = 0;
             int DOT_HEIGHT = -1;
             int [] heights1 = getDotHeight(secondOct, secondNote);
             
        	 /*
        	  * Loop to add the notes for the CURRENT column
        	  */
        	 for( i = 0; i < heights1.length; i++)
        	 {
        	 
        		 DOT_HEIGHT = assignDotHeight(heights1[i]);
        		 dotColor = getDotColor();
        	 
        		 if( heights1[i] != -1)
        		 {
        			 g.setColor(dotColor);
        			 g.fillOval(DOTWIDTH0, DOT_HEIGHT, DOT_RADIUS, DOT_RADIUS);
        	 
        			 g.setColor(Color.BLACK);
        			 g.drawString(secondNote, D_LABELWIDTH0, DOT_HEIGHT + 27);
        			 
        			 
        		 }
        		 DOT_HEIGHT = -1;
        	 
        	 }//end for
         
        	 i = 0;
        	 int [] heights2 = getDotHeight(thirdOct, thirdNote);
         
        	 /*
        	  * Loop to add the notes for the NEXT column
        	  */
        	 for( i = 0; i < heights2.length; i++)
        	 {
        		 DOT_HEIGHT = assignDotHeight(heights2[i]);
        		 dotColor = getDotColor();
        	 
        		 if( heights2[i] != -1 )
        		 {
        			 g.setColor(Color.BLUE);
        			 g.fillOval(DOTWIDTH1, DOT_HEIGHT, DOT_RADIUS, DOT_RADIUS);
        	 
        			 g.setColor(Color.BLACK);
        			 g.drawString(thirdNote, D_LABELWIDTH1, DOT_HEIGHT + 27);
        		 }
        		 DOT_HEIGHT = -1;
        	 }//end for
        	 
        	 i = 0;
        	 int [] heights3 = getDotHeight(fourthOct, fourthNote);
         
        	 /*
        	  * Loop to add the notes for the 2 AWAY column
        	  */
        	 for( i = 0; i < heights3.length; i++)
        	 {
        		 DOT_HEIGHT = assignDotHeight(heights3[i]);
        		 dotColor = getDotColor();
        	 
        		 if( heights3[i] != -1)
        		 {
        			 g.setColor(Color.BLUE);
        			 g.fillOval(DOTWIDTH2, DOT_HEIGHT, DOT_RADIUS, DOT_RADIUS);
        	 
        			 g.setColor(Color.BLACK);
        			 g.drawString(fourthNote, D_LABELWIDTH2, DOT_HEIGHT + 27);
        		 }
        		 DOT_HEIGHT = -1;
        	 }//end for
        	 
         }//end if
        
         
    }//end PaintComponents

    public int assignDotHeight(int num){
    	int retVal = 0;
    	
    	if(num == 0)
    	{
    		retVal = DOTHEIGHT0;
    	}
    	else if( num == 1)
    	{
    		retVal = DOTHEIGHT1;
    	}
    	else if( num == 2)
    	{
    		retVal = DOTHEIGHT2;
    	}
    	else if( num == 3)
    	{
    		retVal = DOTHEIGHT3;
    	}
    	else if( num == -1)
    	{
    		retVal = -1;
    	}
    	
    	return retVal;
    }
    
    
	public int [] getDotHeight(int octave, String note){
    	int retVal[] = new int[3];
    	
    	if(octave == 1 )
    	{
    		if(note.equals("A") || note.equals("A#") || note.equals("B"))
    		{
    			retVal[0] = 3;
    			retVal[1] = 2;
    			retVal[2] = -1;
    		}
    		else if(note.equals("C") || note.equals("C#") || note.equals("D") || note.equals("D#") || note.equals("E") || 
    					note.equals("F") || note.equals("F#") || note.equals("G") || note.equals("G#"))
    		{
    			retVal[0] = 3;
    			retVal[1] = -1;
    			retVal[2] = -1;
    		}
    		else
    		{
    			retVal[0] = -1;
    			retVal[1] = -1;
    			retVal[2] = -1;
    		}
    	}
    	else if( octave == 2)
    	{
    		if(note.equals("C") || note.equals("C#"))
    		{
    			retVal[0] = 3;
    			retVal[1] = 2;
    			retVal[2] = -1;
    		}
    		else if(note.equals("A") || note.equals("A#") || note.equals("B"))
    		{
    			retVal[0] = 1;
    			retVal[1] = 0;
    			retVal[2] = -1;
    		}
    		else if(note.equals("D") || note.equals("D#"))
    		{
    			retVal[0] = 3;
    			retVal[1] = 2;
    			retVal[2] = 1;
    		}
    		else if(note.equals("G") || note.equals("G#"))
    		{
    			retVal[0] = 2;
    			retVal[1] = 1;
    			retVal[2] = 0;
    		}
    		else if(note.equals("E") || note.equals("F") || note.equals("F#"))
    		{
    			retVal[0] = 2;
    			retVal[1] = 1;
    			retVal[2] = -1;
    		}
    		else
    		{
    			retVal[0] = -1;
    			retVal[1] = -1;
    			retVal[2] = -1;
    		}
    	}
    	else if( octave == 3)
    	{
    		if(note.equals("C") || note.equals("C#"))
    		{
    			retVal[0] = 1;
    			retVal[1] = 0;
    			retVal[2] = -1;
    		}
    		else if(note.equals("D") || note.equals("D#") || note.equals("E") || note.equals("F") || note.equals("F#"))
    		{
    			retVal[0] = 1;
    			retVal[1] = -1;
    			retVal[2] = -1;
    		}
    		else
    		{
    			retVal[0] = -1;
    			retVal[1] = -1;
    			retVal[2] = -1;
    		}
    	}
    	else if( octave == -1 || note.equals(""))
    	{
    		
    		retVal[0] = -1;
    		retVal[1] = -1;
			retVal[2] = -1;
    	}
    	
    	return retVal;
    }
    
	public void setDotColor(boolean lColor){
    	
    	if(lColor == true){
    		dotColor = Color.GREEN;
    	}
    	else
    	{
    		dotColor = Color.BLUE;
    	}
    	
    }
    
    public Color getDotColor(){
    	return dotColor;
    }
   
    public void setTimeUntil(double currentTime, double startTime){
    	if( startTime != -1)
    	{
    		if(currentCnt < nTimes.length - 1)
    		{
    			timeUntil1 = nTimes[currentCnt] - (currentTime - startTime);
    		
    			if(currentCnt + 1 < nTimes.length -1)
    			{
    				timeUntil2 = nTimes[currentCnt + 1] - (currentTime - startTime);
    				
    				if(currentCnt + 2 < nTimes.length-1)
    				{
    					timeUntil3 = nTimes[currentCnt + 2] - (currentTime - startTime);
    				}
    				else
    				{
    					timeUntil3 = -1;
    				}//end fourth layer conditional
    			}
    			else
    			{
    				timeUntil2 = -1;
    				timeUntil3 = -1;
    			}//end third layer conditional
    			
    		}//end second layer conditional
    	}
    	else
    	{
    		timeUntil1 = -1;
    		timeUntil2 = -1;
    		timeUntil3 = -1;
    	}//end outer conditional
    }
    
    
} //end class



