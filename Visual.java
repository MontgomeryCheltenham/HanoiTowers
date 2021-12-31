import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Visual extends JPanel{
	static int stickX, stickY, stickW, stickH, stDist;
	
	public Visual() {
		setPreferredSize(new Dimension(480, 240));
		stickX = 100; stickY = 40; stickW = 10; stickH = 180; stDist = 150;
	}
	public void paint(Graphics g){
		super.paintComponent(g);
		for(int i=0; i<3; i++) {
			g.setColor(Color.BLACK);
			g.fillRect(stickX+stDist*i, stickY, stickW, stickH);
		}
		for(int i=0; i<HTMain.blocks0.size(); i++) {
			HTMain.blocks0.get(i).paint(g);
		}
		for(int i=0; i<HTMain.blocks1.size(); i++) {
			HTMain.blocks1.get(i).paint(g);
		}
		for(int i=0; i<HTMain.blocks2.size(); i++) {
			HTMain.blocks2.get(i).paint(g);
		}
	}
}
