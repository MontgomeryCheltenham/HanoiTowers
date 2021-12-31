import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Blocks extends JPanel {
	int dia, position; // diameter order, position 0,1,2 - sticks
	//dia - smaller dia, longer block, factor in blockW
	int blockX, blockY, blockW, blockH, nofBlocks, pos;
	int stickX = Visual.stickX; int stickY = Visual.stickY;
	int stickW = Visual.stickW; int stickH = Visual.stickH; int stDist = Visual.stDist;
	Color clr;
	
	public Blocks(int dia, int pos) {
		this.dia = dia; this.position = pos; nofBlocks = HTMain.nofBlocks;
		blockW = 128-dia*12; blockH = 20;
		blockX = stickX+stickW/2 - blockW/2 + stDist*pos; 
		if(pos==0) { blockY = stickY+stickH-HTMain.blocks0.size()*blockH-blockH; }
		if(pos==1) { blockY = stickY+stickH-HTMain.blocks1.size()*blockH-blockH; }
		if(pos==2) { blockY = stickY+stickH-HTMain.blocks2.size()*blockH-blockH; }
	}
	public int getDia() { return dia; }
	public Color getColor() { return clr; }
	public void setColor(Color clr) { this.clr = clr; }
	
	public void paint(Graphics g){
		super.paintComponent(g);
		g.setColor(clr);
		g.fillRect(blockX, blockY, blockW, blockH);
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(dia), blockX + blockW/2-2, blockY+blockH/2+4);
	}
}
