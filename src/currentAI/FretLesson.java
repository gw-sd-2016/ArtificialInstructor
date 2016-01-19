package currentAI;

public class FretLesson {
	
	private double startTime; 
	private double [] nTimes;
	private String [] notes;
	private int [] nOct;
	private boolean [] nRing;
	private int cnt = 0;
	private boolean finished = false;
	private boolean locked = false;
	private int score = 0;
	private boolean ringChecked = false;
	private boolean nColor = false;
	
	public FretLesson(double initTime, double [] noteT, String [] nVals, int [] octs, boolean [] ring)
	{
		startTime = initTime;
		nTimes = noteT;
		notes = nVals;
		nOct = octs;
		nRing = ring;
		cnt = 0;
		
	}
	
	public void setStartTime(double inputT)
	{
		startTime = inputT;
	}
	
	public boolean getLessonPlace(){
		return finished;
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
	
	/*
	 * FIX FIX FIX FIX FIX FIX FIX FIX FIX FIX FIX
	 */
	public void checkAccuracy(double inputT, String uNote, int uOct){
		
		if(nRing[cnt] == true){
			if(ringChecked == false){
			
			}
		}
		else{
			if( ( (inputT  - (nTimes[cnt]) ) >= -.10) ||  
					( (inputT  + (nTimes[cnt]) ) >= .10) ||
						( (nTimes[cnt]) - inputT <= -.10 ) ||
							( (nTimes[cnt]) + inputT <= .10 )) {
				
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
		
		if( ( ( (inputT - startTime) - nTimes[cnt]) <= .50) && 
				( ( (inputT - startTime) - nTimes[cnt] ) >= -.50) 
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
