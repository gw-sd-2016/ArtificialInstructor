package currentAI;

/*
 * Custom playback thread for the ArtificalInstructor application
 */

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

public class playBackThread implements Runnable {
	private SourceDataLine sdl;
	private AudioInputStream aIStream;
	
	public playBackThread(SourceDataLine sourceDL, AudioInputStream audIS) throws IOException{
		sdl = sourceDL;
		aIStream = audIS;
	}
	
	public void run(){
		sdl.start();
        
        int len = 0;
       	byte [] buff = new byte[(sdl.getBufferSize())];
        
        try {
			len = aIStream.read(buff, 0, buff.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        sdl.write(buff, 0, len);
          
        sdl.drain();
        //sdl.close();
	}
	
	
}
