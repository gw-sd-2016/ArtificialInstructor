package currentAI;

public class FretLesson {
	
	private double startTime; 
	private double [] nTimes;
	private String [] notes;
	private double [] nGracePeriod;
	private int [] nOct;
	private boolean [] nRing;
	private int cnt = 0;
	private boolean finished = false;
	private boolean locked = false;
	private double score = 0;
	private boolean ringChecked = false;
	private boolean nColor = false;
	
	public FretLesson(double initTime, double [] noteT, String [] nVals, int [] octs, boolean [] ring, double [] graceP)
	{
		startTime = initTime;
		nTimes = noteT;
		notes = nVals;
		nOct = octs;
		nRing = ring;
		nGracePeriod = graceP;
		cnt = 0;
		score = 0;
	}
	
	public void setStartTime(double inputT)
	{
		startTime = inputT;
	}//end setStartTime()
	
	/*
	 * 	Using the global counter this returns the current note value
	 */
	public String getNoteValue()
	{
		return notes[cnt];
	}//end getNoteValue()
	
	
	/*
	 *	 Using the global counter this returns the current octave value
	 */
	public int getNoteOct()
	{
		return nOct[cnt];
	}//end getNoteOct()
	
	
	/*
	 * Using the global counter this returns the current ring value
	 */
	public boolean getNoteRing()
	{
		return nRing[cnt];
	}//end getNoteRing()
	
	
	/*
	 * This method takes in the input time and determiens if the current note is 
	 * 		within the GracePeriod
	 * 
	 * 		If it is within the GracePeriod then COLOR is GREEN
	 * 		ELSE the COLOR is BLUE
	 */
	public void setNoteColor(double inputT){
		
		if( ( ( (inputT - startTime) - nTimes[cnt]) <= nGracePeriod[cnt]) && 
				( ( (inputT - startTime) - nTimes[cnt] ) >= -nGracePeriod[cnt]) 
					|| nRing[cnt] == true ){
			nColor = true;
		}
		else
		{
			nColor = false;
		}
	}//end setNoteColor()
	
	/*
	 * Returns the color value that is currently set
	 */
	public boolean getNoteColor(){
		return nColor;
	}//end getNoteColor()
	
	
	/*
	 * Taking in the current time in the lesson, it determines if the lesson has been completed
	 * 
	 * 	If the current note value is equal to "Z" lesson has been completed
	 * 		set global variable to track progress to true
	 * 
	 * Returns the current state of the lesson
	 */
	public boolean getLessonPlace(double inputT){
		boolean isfinished;
		
		if(finished == false){
			
			incrementCnt(inputT);
			
			if( notes[cnt].equals("Z")  ){
				finished = true;
				isfinished = true;
			}
			else{
				isfinished = false;
				
			}
		}else
		{
			isfinished = true;
		}
		
		return isfinished;
		
	}//end getLessonPlace()
	
	
	/*
	 * Returns the value of the global variable score
	 */
	public double getScore(){
		return score;
	}//end getScore()
	
	
	/*
	 * Increments the global variable score by number passed in
	 */
	public void incrementScore(double num){
		score += num;
	}//end incrementScore()
	
	
	/*
	 * Decrements the global variable score by number passed in
	 */
	public void decrementScore(double num){
		score -= num;
	}//end decrementScore()
	
	
	/*
	 * Returns the global variable cnt
	 */
	public int getLesCounter(){
		return cnt;
	}//end getLesCounter()
	
	/*
	 * Takes in the current time, the note value of user input, and the octave value of user input
	 * 
	 * Determines a score based off of a series of conditionals
	 */
	public void checkNoteAccuracy(double inputT, String uNote, int uOct){
		
		/*
		 * If the note is supposed to be strummed and left to be rung out
		 */
		if(nRing[cnt] == true){
				
			/*
			 * IF the program has not checked for input of a "RINGED NOTE" and it is within the grace period
			 */
			if(ringChecked == false && (inputT-startTime) >= nGracePeriod[cnt] && (inputT-startTime) <= nGracePeriod[cnt])
			{
				
				if( uNote == notes[cnt] ){
					incrementScore(1);
					
					if( uNote.equals("NO INPUT") == false )
					{
						if( uOct == nOct[cnt]){
							incrementScore(1);
						}
						else
						{
							decrementScore(1);
						}
					}
				}
				else
				{
					decrementScore(1);
				}
				
				ringChecked = true;
			}
			else
			{
				if( uNote == notes[cnt] ){
					incrementScore(0.5);
					
					if( uNote.equals("NO INPUT") == false )
					{
						if( uOct == nOct[cnt]){
							incrementScore(0.5);
						}
						else
						{
							decrementScore(1);
						}
					}
				}
				else
				{
					decrementScore(1);
				}
			}
			
		}
		else{
			/*
			 * Else note is not meant to be left to ring out, check if played within correct GracePeriod
			 */
			if( ( ( (inputT - startTime) - nTimes[cnt]) <= nGracePeriod[cnt]) && 
						( (  (inputT - startTime) - nTimes[cnt] )  >= -nGracePeriod[cnt]) ){
				incrementScore(1);
				
				if( uNote.equals("NO INPUT") == false )
				{
					if( uOct == nOct[cnt]){
						incrementScore(2);
					}
					else
					{
						decrementScore(1);
					}
				}
			}
			else
			{
				decrementScore(1);
			}
		}
	}//end checkNoteAcurracy()	
	
	
	public void checkRhythmAccuracy(double inputT, double distacnce){
		
	}//end checkRhythmAccuracy()
	
	/*
	 * Increments the global variable cnt based on the current time
	 * 
	 * looks at the lesson times to determine where in the lesson the user is
	 */
	public void incrementCnt(double inputT)
	{
		
		double currentT = inputT - startTime;
		
		if(currentT > nTimes[cnt]){
			
			if(cnt != nTimes.length - 1)
			{
				cnt++;
				incrementCnt(inputT);
			}
			else{
				cnt = 0;
			}//end check for end
			
			ringChecked = false;
			
		}//end if greater
		
		setNoteColor(inputT);
		
		if(finished == false){
			if( notes[cnt].equals("Z")  ){
				finished = true;
			}
			else{
				finished = false;
			}
		}//end check for completion
		
	}//end incrementCnt()

	
	
}//end class


