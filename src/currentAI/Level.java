package currentAI;

import currentAI.Lesson;

public class Level {

	int levelNum;
	String levelTitle;
	int scoreRequired;
	int currentScore;
	Lesson [] lessons;
	int numLessons;
	
	public Level(int lvlNum, String lvlName, int scoreReq){
		levelNum = lvlNum;
		levelTitle = lvlName;
		scoreRequired = scoreReq;
		currentScore = 0;
		numLessons = 0;
		lessons = null;
		
	}
	
	public void setLevelNumber(int num){
		levelNum = num;
	}
	
	public int getLevelNumber(){
		return levelNum;
	}
	
	public void setLevelTitle(String name){
		levelTitle = name;
	}
	
	public String getLevelTitle(){
		return levelTitle;
	}
	
	public void setScoreRequired(int score){
		scoreRequired = score;
	}
	
	public int getScoreRequired(){
		return scoreRequired;
	}
	
	public void setCurrentScore(Lesson [] l){
		int currentS = 0;
		
		for(int i = 0; i < l.length - 1; i++)
		{
			currentS = currentS + l[i].getHighScore();
		}
		
		currentScore = currentS;
	}
	
	public int getCurrentScore(){
		return currentScore;
	}
	
	public void setNumberLessons(Lesson [] l){
		numLessons = l.length;
	}
	
	public int getNumberLessons(){
		return numLessons;
	}
	
	public void addLesson(Lesson newL){
		lessons[lessons.length] = newL;
	}
	
	public void removeLesson(int lessonNumber){
		lessons[lessonNumber] = null;
		
		for(int i = lessonNumber; i < lessons.length -1; i++)
		{
			lessons[i] = lessons[i+1];
			
			if(i == lessons.length)
			{
				lessons[i] = null;
			}
		}
	}//end removelesson
	
	public Lesson getLesson(int lessonNumber){
		return lessons[lessonNumber];
	}//end getLesson(num)
	
	public Lesson getLesson(String lessonTitle){
		Lesson retL = null;
		
		for(int i = 0; i < lessons.length - 1; i++)
		{
			if(lessons[i].getLessonTitle() == lessonTitle)
			{
				retL = lessons[i];
				break;
			}
		}
		
		return retL;
	}//end getlesson(name)
	
}//end Level
