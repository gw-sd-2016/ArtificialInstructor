package currentAI;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class Strum {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		AudioFormat playBackFormat = new AudioFormat(1024, 16, 1, true, true);
        ByteArrayOutputStream out;				//input
    	int numBytesRead;						//input
    	int bytesRead;							//input
    	byte[] data;							//input
    	TargetDataLine instrument = null;		//input
    	DataLine.Info info1;					//input
    	byte val1;
    	byte val2;	
    	byte val3;
    	byte maxPeak = 0;
    	byte minPeak = 0;
    	int count = 0;
    	
    		try {
    			instrument = AudioSystem.getTargetDataLine(playBackFormat);

    			info1 = new DataLine.Info(TargetDataLine.class, playBackFormat);
    			instrument = (TargetDataLine) AudioSystem.getLine(info1);
    			instrument.open(playBackFormat);
    			instrument.start();
            
    			out = new ByteArrayOutputStream();
    		//	BufferedInputStream in = new BufferedInputStream(new FileInputStream("/Users/Shawn/Desktop/1.wav"));
    			
    			//data = new byte[in.available()/9];
    			data = new byte[instrument.getBufferSize()/4];
    			int read;
    			
    			while((read = instrument.read(data, 0, data.length)) > 0){
    				
    				out.write(data, 0, read);
    				
        			for(int i = 0; i < data.length-2; i++){
        				
        				val1 = data[i];
        				val2 = data[i+1];
        				val3 = data[i+2];
        				
        				//values are increasing
        				if(val1 < val2 && val2 < val3){
        					if(val2 > maxPeak){
        						maxPeak = val2;
        					}	
        				}
        				
        				//values are decreasing
        				if(val1 > val2 && val2 > val3){
        					if(val2 > maxPeak){
        						minPeak = val2;
        					}
        				}

        			}//end for
        			
        			if(maxPeak != 0)
        			{
        				System.out.println("Max: " + maxPeak);
        				maxPeak = 0;
        				count++;
        			}
        			if(minPeak != 0)
        			{
        				System.out.println("Min: "+ minPeak);
        				minPeak = 0;
        				count++;
        			}
        			
    			}
    			System.out.println(count);
    			

               
    		}
    		catch (LineUnavailableException e) {
    			e.printStackTrace();
    		}
    	}
	
}
