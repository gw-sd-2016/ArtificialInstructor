package currentAI;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import currentAI.playBackThread;

class threadTesting {

    public static void main(String[] args) {
    	
    	/*
    	 * 1500 -- clear but slow with a minor delay
    	 * 2000 -- most clear with very small delay that is barely noticable
    	 * 8000 -- choppy, but no delay
    	 * 44100 -- no delay but cannot make sense of actual sound
    	 */
    	AudioFormat playBackFormat = new AudioFormat(2000.0f, 16, 2, true, true);
    	//AudioFormat format = new AudioFormat(44100.0f, 16, 1, true, true);
        
    	int CHUNK_SIZE = 1024;			//format
    	
        ByteArrayOutputStream out;		//input & output
        
    	int numBytesRead;				//input
    	int bytesRead;					//input
    	byte[] data;					//input
    	TargetDataLine microphone;		//input
    	DataLine.Info info1;			//input
    	
    	byte audioData[];				//output 
        SourceDataLine sourceDataLine;	//output
        AudioInputStream ais;			//output
        DataLine.Info dataLineInfo;		//output
        
    	try {
            microphone = AudioSystem.getTargetDataLine(playBackFormat);

            info1 = new DataLine.Info(TargetDataLine.class, playBackFormat);
            microphone = (TargetDataLine) AudioSystem.getLine(info1);
            microphone.open(playBackFormat);
            microphone.start();
            
            out = new ByteArrayOutputStream();
            data = new byte[microphone.getBufferSize()/2];
            
            bytesRead = 0;

            while (true) { 
                try {
            		
                    numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                    bytesRead = bytesRead + numBytesRead;
                    System.out.println(bytesRead);
                    out.write(data, 0, numBytesRead);
                    
                    audioData = out.toByteArray();
                    out.reset();
                    
                    InputStream byteArrayInputStream = new ByteArrayInputStream(
                            audioData);
                    ais = new AudioInputStream(byteArrayInputStream, playBackFormat, audioData.length / playBackFormat.getFrameSize());
                    dataLineInfo = new DataLine.Info(SourceDataLine.class, playBackFormat);
                    sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(playBackFormat);
 
                    Runnable threadJob = new playBackThread(sourceDataLine, ais);
                	Thread t1 = new Thread(threadJob);
                	
                	t1.start();
                   
            	} catch (Exception e) {
            		e.printStackTrace();
            	} 
            }
            
    	} catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
  