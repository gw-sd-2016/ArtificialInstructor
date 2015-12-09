package currentAI;

/*
 * BassGuitar Class used to:
 * 	1) Create new instrument objects
 *  2) Access those object's data
 *  3) Set/Modify those object's data
 */
public class BassGuitar {
	String nickname;
	String manufacturer;
	String model;
	int numStrings;
	String [] defaultTuning;
	
	public BassGuitar(String name, int strings){
		/*
		 * nickname and numStrings required during instrument creation, these are set to input values
		 * defaultTuning, manufacturer, and model not required but initialized
		 */
		nickname = name;
		numStrings = strings;
		
		//defaultTuning set to E,A,D,G for 4 strings and B,E,A,D,G for 5 string
		if(numStrings == 4)
		{
			defaultTuning[0] = "E";
			defaultTuning[1] = "A";
			defaultTuning[2] = "D";
			defaultTuning[3] = "G";
		}
		else if(numStrings == 5)
		{
			defaultTuning[0] = "B";
			defaultTuning[1] = "E";
			defaultTuning[2] = "A";
			defaultTuning[3] = "D";
			defaultTuning[4] = "G";
		}
		
		manufacturer = null;
		model = null;
		
	}
	
	public BassGuitar(String name, int strings, String [] tuning){
		/*
		 * nickname numStrings, and defaultTuning required during instrument creation, these are set to input values
		 * manufacturer, and model not required but initialized
		 */
		nickname = name;
		numStrings = strings;
		defaultTuning = tuning;
		manufacturer = null;
		model = null;
	}
	
	public BassGuitar(String name, int strings, String [] tuning, String manufacturerInput){
		/*
		 * nickname numStrings, defaultTuning, and manufacturer required during instrument creation, these are set to input values
		 * model not required but initialized
		 */
		nickname = name;
		numStrings = strings;
		defaultTuning = tuning;
		manufacturer = manufacturerInput;
		model = null;
	}
	
	public BassGuitar(String name, int strings, String [] tuning, String manufacturerInput, String modelInput){
		/*
		 * All instance variables are required during instrument creation, 
		 * these are set to input values
		 */
		nickname = name;
		numStrings = strings;
		defaultTuning = tuning;
		manufacturer = manufacturerInput;
		model = modelInput;
	}
	
	
	public void setManufacturer(String manufacturerInput){
		manufacturer = manufacturerInput;
	}
	
	public String getManufacturer(){
		return manufacturer;
	}
	
	public void setModel(String modelInput){
		model = modelInput;
	}
	
	public String getModel(){
		return model;
	}
	
	public void setName(String name){
		nickname = name;
	}
	
	public String getName(){
		return nickname;
	}
	
	public void setStrings(int strings){
		numStrings = strings;
	}
	
	public int getStrings(){
		return numStrings;
	}
	
	public void setTuning(String [] tuning){
		defaultTuning = tuning;
	}
	
	public String[] getTuning(){
		return defaultTuning;
	}
	
}
