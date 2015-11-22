package currentAI;


/*
 * example program was used, from:
 * http://stackoverflow.com/questions/2064066/does-java-have-built-in-libraries-for-audio-synthesis/2065693#2065693
 */
import currentAI.Lesson;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class lessonTest {

	    public static void main(String[] args) throws LineUnavailableException {
	     
			String [] notes = {"C", "C#", "D", "Eb", "E", "F", "F#"};
			double [] noteF = {16.835, 17.35, 18.9, 20.025, 21.215, 22.475, 23.81};
			double [] times = {1.00, 2.00, 3.00, 4.00, 5.00, 6.00, 7.00};
			
			Lesson test = new Lesson(1, "Test", 1, times, notes, noteF);
			
	    	final AudioFormat af = new AudioFormat(Note.SAMPLE_RATE, 8, 1, true, true);
	        SourceDataLine line = AudioSystem.getSourceDataLine(af);
	        line.open(af, Note.SAMPLE_RATE);
	        line.start();
	        for  (Note n : Note.values()) {
	            play(line, n, 500);
	            play(line, Note.REST, 10);
	        }
	        line.drain();
	        line.close();
	    }

	    private static void play(SourceDataLine line, Note note, int ms) {
	        ms = Math.min(ms, Note.SECONDS * 1000);
	        int length = Note.SAMPLE_RATE * ms / 1000;
	        int count = line.write(note.data(), 0, length);
	    }
	}

	enum Note {

	    REST, A0, A0$, B0, C0, C0$, D0, D0$, E0, F0, F0$, G0, G0$, A1, A1$, B1, C1, C$1, D1, D$1, E1, F1, F$1, G1, G$1;
	    public static final int SAMPLE_RATE = 1 * 1024; // ~16KHz
	    public static final int SECONDS = 2;
	    private byte[] sin = new byte[SECONDS * SAMPLE_RATE];

	    int [] x = {14, 15, 16, 17};
	    Note() {
	    	for(int m = 0; m < x.length - 1; m++){
	        int n = x[m];
	        if (n > 0) {
	            double exp = ((double) n - 1) / 12d;
	            double f = 440d * Math.pow(2d, exp);
	            for (int i = 0; i < sin.length; i++) {
	                double period = (double)SAMPLE_RATE / f;
	                double angle = 2.0 * Math.PI * i / period;
	                sin[i] = (byte)(Math.sin(angle) * 127f);
	            }
	        }
	    }
	    }

	    public byte[] data() {
	        return sin;
	    }
	}

