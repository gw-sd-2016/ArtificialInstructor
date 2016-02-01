package currentAI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import be.tarsos.dsp.AudioEvent;

//Taken from TarsoDSP oscilloscopeExample program, no changes were made
class OscilloscopePanel extends JPanel{

	
	private static final long serialVersionUID = 1L;
	float data[];
	
	public OscilloscopePanel(){
		setMaximumSize(new Dimension(1,1)); 
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g); //paint background
        g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.WHITE);
		if(data != null){
			float width = getWidth();
			float height = getHeight();
			float halfHeight = height / 2;
			for(int i=0; i < data.length ; i+=4){
				 g.drawLine((int)(data[i]* width),(int)( halfHeight - data[i+1]* height),(int)( data[i+2]*width),(int)( halfHeight - data[i+3]*height));
			}
		}
    }
	
	public void paint(float[] data, AudioEvent event){
		this.data = data;
	}
}
