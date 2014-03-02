import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


import java.text.DecimalFormat;

import javax.swing.JComponent;



public class Swing extends JComponent{

	
	private int[] zones;

	public Swing(int[] zones){
        this.zones = zones;   
	}

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int h = height(zones[0]);
        g.setColor(Color.blue);
        g.fillRect(50, 550-h, 100, h);
        g.drawString(stringFormat(0), 50, 500-h);
        
        h= height(zones[1]);
        g.setColor(Color.GREEN);
        g.fillRect(150, 550-h, 100, h);
        g.drawString(stringFormat(1), 150, 500-h);

        
        h= height(zones[2]);
        g.setColor(Color.RED);
        g.fillRect(250, 550-h, 100, h);
        g.drawString(stringFormat(2), 250, 500-h);

        
        h= height(zones[3]);
        g.setColor(Color.ORANGE);
        g.fillRect(350, 550-h, 100, h);
        g.drawString(stringFormat(3), 350, 500-h);

        
        h= height(zones[4]);
        g.setColor(Color.BLACK);
        g.fillRect(450, 550-h, 100, h);
        g.drawString(stringFormat(4), 450, 500-h);

    }
    
    public int height(int zone){
    	int max = 0;
    	for(int i : zones){
    		if(i > max)
    			max = i;
    	}
    	double prop = (double)zone/max;
    	return (int) (prop*500);
    }
    
    private String stringFormat(int zone){
		double total = 0;
        DecimalFormat df = new DecimalFormat("##");
		for(int i : zones){
			total = total + i;
		}
			int min = zones[zone]/60;
			int sec = zones[zone] - (min*60);
			return ("Zone "+(zone+1)+": "+ min+"m, "+sec+"s. \n"+df.format((zones[zone]/total)*100)+"%");
    }
	
}
