package currentAI;

import java.security.Timestamp;

public class FretLesson {
	
	private double startTime; 
	private double [] nTimes;
	private String [] notes;
	private double [] nGracePeriod;
	private int [] nOct;
	private String [] nType;
	private int cnt = 0;
	private boolean finished = false;
	private boolean locked = false;
	private double score = 0;
	private boolean ringChecked = false;
	private boolean nColor = false;
	private double BPM;
	
	public FretLesson(double initTime, double [] noteT, String [] nVals, int [] octs, String [] type, double [] graceP, double bpm)
	{
		startTime = initTime;
		nTimes = noteT;
		notes = nVals;
		nOct = octs;
		nType = type;
		nGracePeriod = graceP;
		cnt = 0;
		score = 0;
		BPM = bpm;
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
	public String getNoteType()
	{
		return nType[cnt];
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
				( ( (inputT - startTime) - nTimes[cnt] ) >= -nGracePeriod[cnt]) ){
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
		
		
		
		if(notes[cnt].equals("Z") || cnt == notes.length-1){
			isfinished = true;
			finished = true;
		}
		else{
			isfinished = false;
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
		 * note is not meant to be left to ring out, check if played within correct GracePeriod
		 */
		if( ( ( (inputT - startTime) - nTimes[cnt]) <= nGracePeriod[cnt]) && 
				( (  (inputT - startTime) - nTimes[cnt] )  >= -nGracePeriod[cnt]) ){
				
			//if not equals check for the correct octave after incrementing score, else decrement
			if( notes[cnt].equals(uNote) == true)
			{
				incrementScore(1);
					
				if( uOct == nOct[cnt] && uNote.equals("REST") != true)
				{
						incrementScore(.5);
				}
				else
				{
					decrementScore(.5);
				}
			}
			else
			{
				decrementScore(1);
			}
		}
		else
		{
			if(uNote.equals("REST") == true){
				decrementScore(.5);
			}
			else{
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

	
	public void produceFeedback(String [] attemptNote, int [] attemptOct, double [] attemptTime){
		int [] correctAttemptTimeIndex = new int[nTimes.length];
		int [] incorrectAttemptTimeIndex = new int[attemptTime.length];
		int [] correctLessonTimeIndex = new int[nTimes.length];
		int iATCnt = 0;		//incorrect Attempt Time counter
		int cATCnt = 0;		//correct Attempt Time counter
		int cLTCnt = 0;		//correct Lesson Time counter
		
		int [] correctNoteAttemptIndex = new int[nTimes.length];
		int [] incorrectNoteAttemptIndex = new int[attemptTime.length];
		int [] correctNoteLessonIndex = new int[nTimes.length];
		int iANCnt = 0;		//incorrect Attempt Note counter
		int cANCnt = 0;		//correct Attempt Note counter
		int cLNCnt = 0;		//correct Lesson Note counter
		
		int [] correctOctAttemptIndex = new int[nTimes.length];
		int [] incorrectOctAttemptIndex = new int[attemptTime.length];
		int [] correctOctLessonIndex = new int[nTimes.length];
		int iAOCnt = 0;		//incorrect Attempt Octave counter
		int cAOCnt = 0;		//correct Attempt Octave counter
		int cLOCnt = 0;		//correct lesson Octave counter
		
		int [] correctTypeAttemptIndex = new int[nTimes.length];
		int [] incorrectTypeAttemptIndex = new int[attemptTime.length];
		int [] correctTypeLessonIndex = new int[nTimes.length];
		int iATyCnt = 0;		//incorrect Attempt Type counter
		int cATyCnt = 0;		//correct Attempt Type counter
		int cLTyCnt = 0;		//correct lesson Type counter
		
		String[] attemptType = new String[attemptTime.length];
		
		
		//Nested for loops to determine the correct times strummed and store indices found
		for(int j = 0; j < nTimes.length; j++)
		{
			for(int i = 0; i < attemptTime.length - 1; i++)
			{	
				//if attempt times are greater than rest of times in 
				if(attemptTime[i] > nTimes[j])
				{
					break;
				}
				
				
				
				//if attempt time is within correct range add i value to correct array
				//else add i to incorrect array
				if( ( ( (attemptTime[i] - startTime) - nTimes[j]) <= 0.03) && 
						( (  (attemptTime[i] - startTime) - nTimes[j] )  >= -0.03) )
				{
					//Store values into index arrays
					correctAttemptTimeIndex[cATCnt] = i;
					correctLessonTimeIndex[cLTCnt] = j;
					
					//increment counters 
					cATCnt++;
					cLTCnt++;
					
					
				}
				else
				{
					//store values into index arrays and increment counters
					incorrectAttemptTimeIndex[iATCnt] = i;
					
				}
			}//end inner
		}//end outer
		
		//Note comparison
		for(int k = 0; k < correctAttemptTimeIndex.length; k++){
			for( int l = 0; l < correctLessonTimeIndex.length; l++)
			{
				if(nTimes[l] < attemptTime[k])
				{
					break;
				}
				
				
				//if note at correct time is equal to lesson
				if(attemptNote[correctAttemptTimeIndex[k]].equals(notes[correctLessonTimeIndex[l]]) == true)
				{
					//store index values
					correctNoteAttemptIndex[cANCnt] = k;
					incorrectNoteAttemptIndex[cLNCnt] = l;
					
					//increment counters
					cANCnt++;
					cLNCnt++;
					
				}
				else
				{
					incorrectNoteAttemptIndex[iANCnt] = k;
					iANCnt++;
					
				}
				
			}//end inner for
		}//end outer for
		
		
		//Octave comparison
		for(int m = 0; m < correctAttemptTimeIndex.length; m++){
			for( int n = 0; n < correctLessonTimeIndex.length; n++)
			{
				if(nTimes[n] < attemptTime[m])
				{
					break;
				}
				
				
				//if note at correct time is equal to lesson
				if(attemptOct[correctNoteAttemptIndex[m]] == nOct[correctNoteLessonIndex[n]] )
				{
					//store index values
					correctOctAttemptIndex[cAOCnt] = m;
					incorrectOctAttemptIndex[cLOCnt] = n;
							
					//increment counters
					cAOCnt++;
					cLOCnt++;
					
				}
				else
				{
					incorrectNoteAttemptIndex[iAOCnt] = m;
					iAOCnt++;
					
				}
						
			}//end inner for
		}//end outer for
		
	
		/*
		 * Use arrays with the correct Times/Notes to determine if octave was correct
		 * Calculate distance between attemptTime[XX] and attemptTime[XX + 1] to assign a type 
		 */
		//Type comparison for notes 
		
		/*
		 * Convert the times into string values of types to compare
		 */
		for(int q = 0; q < attemptType.length; q++)
		{
			double diff;
			
			if(q + 1 != attemptType.length)
			{
				diff = attemptTime[correctNoteAttemptIndex[1+1]] - attemptTime[correctNoteAttemptIndex[1]];
				attemptType[q] = calculateType(diff);
			}
			else
			{
				attemptType[q] = "Whole";
			}
		}//end calculate loop for types
		
		/*
		 * Nested loops to go through and for correct type, only on the correct notes
		 */
		for(int o = 0; o < correctAttemptTimeIndex.length; o++){
			for( int  p= 0; p < correctLessonTimeIndex.length; p++)
			{
				if(nTimes[p] < attemptTime[o])
				{
					break;
				}
				
				
				//if note at correct time is equal to lesson
				if(attemptType[correctNoteAttemptIndex[o]].equals(nType[correctNoteLessonIndex[p]] ))
				{
					//store index values
					correctTypeAttemptIndex[cATyCnt] = o;
					incorrectOctAttemptIndex[cLTyCnt] = p;
									
					//increment counters
					cATyCnt++;
					cLTyCnt++;
					
				}
				else
				{
					incorrectNoteAttemptIndex[iATyCnt] = o;
					iATyCnt++;
				}
								
			}//end inner for
		}//end outer for
		
		System.out.println("Correct Type count: " +cATyCnt + " / " + iATyCnt);
		System.out.println("Correct Note count: " +cANCnt+ " / " + iANCnt);
		System.out.println("Correct Time count: " +cATCnt+ " / " + iATCnt);
		System.out.println("Correct Octave count: " + cAOCnt+ " / " + iAOCnt);
		
		
		
	}//end giveFeedback
	
	/*
	 * Given the input, of the difference between two times, this method will
	 * Calculate the "type" of note based on its length. 
	 */
	public String calculateType(double dist){
		String retVal = "";
		
		if( (dist > 0) && (dist <= (1/16 * (BPM/60) ) ) )
		{
			//sixteenth note
			retVal = "Sixteenth";
		}
		else if ( (dist > (1/16 * (BPM/60) ) ) && (dist <= (1/8 * (BPM/60) ) ) )
		{
			//eighth note
			retVal = "Eighth";
		}
		else if ( (dist > (1/8 * (BPM/60) ) ) && (dist <= (1/4 * (BPM/60) ) ) )
		{
			//quarter note
			retVal = "quarter";
		}
		else if ( (dist > (1/4 * (BPM/60) ) ) && (dist <= (1/2 * (BPM/60) ) ) )
		{
			//half note
			retVal = "Half";
		}
		else if ( (dist > (1/2 * (BPM/60) ) ) && (dist <= (1 * (BPM/60) ) ) )
		{
			//whole note 
			retVal = "Whole";
		}
		else
		{
			//else
			retVal = "ERROR";
		}//end if/else
		
		return retVal;
	}
}//end class
