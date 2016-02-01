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
	private int score = 0;
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
	}
	
	
	
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
	}
	
	public int getScore(){
		return score;
	}
	
	public void setLessonPlace(){
		cnt = 0;
	}
	
	public void incrementScore(){
		score++;
	}
	
	public void decrementScore(){
		score--;
	}
	
	public int getLesCounter(){
		return cnt;
	}
	
	/*
	 * FIX FIX FIX FIX FIX FIX FIX FIX FIX FIX FIX
	 */
	public void checkAccuracy(double inputT, String uNote, int uOct){
		
		if(finished == false){
			
		
			if(nRing[cnt] == true){
				
				
					if( uNote == notes[cnt] ){
						score++;
						System.out.println("Correct Note");
					
						if( uOct == nOct[cnt]){
							score++;
							System.out.println("Correct Octave");
						}
						else
						{
							score--;
							System.out.println("Incorrect Octave");
						}
					}
					else
					{
						score--;
						System.out.println("Incorrect Note");
					}
				
			
			
			}
			else{
				if( ( ( (inputT - startTime) - nTimes[cnt]) <= .50) && 
						( (  (inputT - startTime) - nTimes[cnt] )  >= -.50) ){
					score++;
					System.out.println("Correct Note");
				
					if( uOct == nOct[cnt]){
						score++;
						System.out.println("Correct Octave");
					}
					else
					{
						score--;
						System.out.println("Incorrect Octave");
					}
				}
				else
				{
					score--;
					System.out.println("Incorrect Octave");
				}
			}
		}
	}
	
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
			}
			
			ringChecked = false;
			
		}//end if
		
		setNoteColor(inputT);
		
		if(finished == false){
			if( notes[cnt].equals("Z")  ){
				finished = true;
			}
			else{
				finished = false;
				
			}
		}
		
	}
	
	//returns the note 
	public String getNoteValue()
	{
		return notes[cnt];
	}
	
	public int getNoteOct()
	{
		return nOct[cnt];
	}
	
	public boolean getNoteRing()
	{
		return nRing[cnt];
	}
	
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
	}
	
	public boolean getNoteColor(){
		return nColor;
	}
	
	
}

