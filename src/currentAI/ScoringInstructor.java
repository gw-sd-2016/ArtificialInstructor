package currentAI;

public class ScoringInstructor {

	private String uVal, lVal;
	private int uOct, lOct;
	private double uTime, lTime, lGracePeriod;
	private boolean lRing, ringEnd, ringCheck;
	
	public ScoringInstructor()
	{
		ringEnd = false;
		ringCheck = false;
	}
	
	//USER NOTE METHODS
	public void setUserNote(String note){
		uVal = note;
	}
	
	public String getUserNote(){
		return uVal;
	}
	
	//LESSON NOTE METHODS
	public void setLesNote(String note){
		lVal = note;
	}
	
	public String getLesNote(){
		return lVal;
	}
	
	//USER OCTAVE METHODS
	public void setUserOct(int oct){
		uOct = oct;
	}
	
	public int getUserOct(){
		return uOct;
	}
	
	//LESSON OCTAVE METHODS
	public void setLesOct(int oct){
		lOct = oct;
	}
	
	public int getLesOct(){
		return lOct;
	}
	
	//USER TIME METHODS
	public void setUserTime(double time){
		uTime = time;
	}
	
	public double getUserTime(){
		return uTime;
	}
	
	//LESSON TIME METHODS
	public void setLesTime(double time){
		lTime = time;
	}
	
	public double getLesTime(){
		return lTime;
	}
	
	//LESSON GRACE PERIOD METHODS
	public void setGracePeriod(double time){
		lGracePeriod = time;
	}
	
	public double getGracePeriod(){
		return lGracePeriod;
	}
	
	//LESSON RING METHODS
	public void setLesRing(boolean ring){
		lRing = ring;
	}
	
	public boolean getLesRing(){
		return lRing;
	}
	
	//SCORING RING END METHODS
	public void setRingEnd(boolean ring){
		ringEnd = ring;
	}
	
	public boolean getRingEnd(){
		return ringEnd;
	}
	
	//SCORING RING CHECK METHODS
	public void resetRingCheck(boolean ring){
		ringCheck = ring;
	}
	
	public boolean getRingCheck(){
		return ringCheck;
	}
	
}
