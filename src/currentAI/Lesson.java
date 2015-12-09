package currentAI;

public class Lesson {
	
	int lessonNum;
	String lessonTitle;
	int lessonLvl;
	int highScore;
	double [] timeNotes;
	String [] noteValues;
	double [] notesFreq;
	
	public Lesson(int lessonNumber, String lessonName, int lvl, double [] timing, String [] notes, double[] noteF){
		lessonNum = lessonNumber;
		lessonTitle = lessonName;
		lessonLvl = lvl;
		timeNotes = timing;
		noteValues = notes;
		highScore = 0;
		notesFreq = noteF;
	}
	
	public void setLessonNumber(int lessonNumber){
		lessonNum = lessonNumber;
	}
	
	public int getLessonNumber(){
		return lessonNum;
	}
	
	public void setLessonTitle(String lessonName){
		lessonTitle = lessonName;
	}
	
	public String getLessonTitle(){
		return lessonTitle;
	}
	
	public void setLessonLevel(int lvl){
		lessonLvl = lvl;
	}
	
	public int getLessonLevel(){
		return lessonLvl;
	}
	
	public void setLessonNotes(String [] notes){
		noteValues = notes;
	}
	
	public String[] getLessonNotes(){
		return noteValues;
	}
	
	public void setLessonTiming(double [] timing){
		timeNotes = timing;
	}
	
	public double [] getLessonTiming(){
		return timeNotes;
	}
	
	public void setHighScore(int newScore){
		highScore = newScore;
	}
	
	public int getHighScore(){
		return highScore;
	}
	
	public void setNoteFrequencies(double [] noteF){
		notesFreq = noteF;
	}
	
	public double [] getNoteFrequencies(){
		return notesFreq;
	}
}
