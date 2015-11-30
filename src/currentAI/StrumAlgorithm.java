package currentAI;

import java.io.ByteArrayOutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class StrumAlgorithm {
	private static boolean newNote = false;
	private static boolean sameNote = false;
	private static byte val1;
	private static byte val2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		AudioFormat playBackFormat = new AudioFormat(1024, 16, 1, true, true);
        ByteArrayOutputStream out;				//input
    	int numBytesRead;						//input
    	int bytesRead;							//input
    	byte[] data;							//input
    	TargetDataLine instrument = null;		//input
    	DataLine.Info info1;					//input
    	
		
    	while(true){
    		try {
    			instrument = AudioSystem.getTargetDataLine(playBackFormat);

    			info1 = new DataLine.Info(TargetDataLine.class, playBackFormat);
    			instrument = (TargetDataLine) AudioSystem.getLine(info1);
    			instrument.open(playBackFormat);
    			instrument.start();
            
    			out = new ByteArrayOutputStream();
    			data = new byte[instrument.getBufferSize()];
            
    			bytesRead = 0;

    			numBytesRead = instrument.read(data, 0, 1024);
    			bytesRead = bytesRead + numBytesRead;
    			//System.out.println(bytesRead);
    			out.write(data, 0, numBytesRead);
    			
    			for(int i = 0; i < data.length-2; i++){
    				
    				val1 = data[i];
    				val2 = data[i+1];
    				
    				if(val1 < val2){
    					sameNote = true;
    					//System.out.println("SAME Note" + data[i]);
    				}
    				
    				if(val1 > val2 & sameNote == true){
    					newNote = true;
    					sameNote = false;
    					//System.out.println("New Note 111" + data[i]);
    				}
    				
    				if(newNote == true){
    					System.out.println("New Note" + data[i]);
    				}
    			}
               
    		}
    		catch (LineUnavailableException e) {
    			e.printStackTrace();
    		}
    	}
	}
}
