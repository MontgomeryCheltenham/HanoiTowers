import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;



public class HTMain extends JPanel {
	
	static JFrame frm = new JFrame("Hanoi Towers");
	static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	static int W = dim.width; static int H = dim.height;
	static JPanel p = new JPanel();
	JPanel pnlMenu = new JPanel(); 
	JPanel visual = new Visual(); JPanel buttons = new JPanel();
	JButton btnStart = new JButton("start");
	JButton btnQuit = new JButton("quit");
	JButton btnAgain = new JButton("play again");
	JButton btnMenu = new JButton("menu");
	JRadioButton take1 = new JRadioButton(); JRadioButton put1 = new JRadioButton();
	JRadioButton take2 = new JRadioButton(); JRadioButton put2 = new JRadioButton();
	JRadioButton take3 = new JRadioButton(); JRadioButton put3 = new JRadioButton();
	JLabel takeLbl = new JLabel("take"); JLabel putLbl = new JLabel("put");
	JLabel warningsLbl = new JLabel();
	JSlider slider;
	GridBagConstraints gbc = new GridBagConstraints();
	static ArrayList<Blocks> blocks0, blocks1, blocks2; //arrays of blocks on each stick
	static int nofBlocks, nofBlocksMin, nofBlocksMax; 
	
	public static void main(String[] args) {
		new HTMain();
	}
	public HTMain(){
		p.setPreferredSize(new Dimension(550, 450));
		p.setLayout(new GridBagLayout());
		blocks0 = new ArrayList<Blocks>();
		blocks1 = new ArrayList<Blocks>();
		blocks2 = new ArrayList<Blocks>();
		nofBlocksMin = 3; nofBlocksMax = 9;
		slider = new JSlider(JSlider.HORIZONTAL, nofBlocksMin, nofBlocksMax, 6);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true); slider.setPaintLabels(true); 
		
		final ButtonGroup takeGroup = new ButtonGroup();
		takeGroup.add(take1); takeGroup.add(take2); takeGroup.add(take3);
		final ButtonGroup putGroup = new ButtonGroup();
		putGroup.add(put1); putGroup.add(put2); putGroup.add(put3);
		buttons.setLayout(new GridBagLayout());
		gbc.gridx=0; gbc.gridy=1; buttons.add(takeLbl, gbc);
		gbc.gridx=1; gbc.gridy=1; buttons.add(take1, gbc);
		gbc.gridx=2; gbc.gridy=1; buttons.add(take2, gbc);
		gbc.gridx=3; gbc.gridy=1; buttons.add(take3, gbc);
		gbc.gridx=0; gbc.gridy=2; buttons.add(putLbl, gbc);
		gbc.gridx=1; gbc.gridy=2; buttons.add(put1, gbc);
		gbc.gridx=2; gbc.gridy=2; buttons.add(put2, gbc);
		gbc.gridx=3; gbc.gridy=2; buttons.add(put3, gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 3;
		gbc.gridx=0; gbc.gridy=3; buttons.add(warningsLbl, gbc);
		
		pnlMenu.add(slider);
		pnlMenu.add(btnStart); pnlMenu.add(btnQuit); 
		gbc.gridx=0; gbc.gridy=0; p.add(pnlMenu, gbc);
		frm.add(p); frm.pack();
		frm.getRootPane().setDefaultButton(btnStart);
		btnStart.requestFocus();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setLocation(W/8, H/8);
		frm.setResizable(false); frm.setFocusable(true); frm.requestFocus();
		frm.setSize(700, 500);
		frm.setVisible(true);
		
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		//populating arraylist of blocks on first stick
				nofBlocks = slider.getValue();
				for(int i=1; i<nofBlocks+1; i++) {
					blocks0.add(new Blocks(i, 0));
					blocks0.get(i-1).setColor(new Color((int)(Math.random()*255)+1,(int)(Math.random()*255)+1,(int)(Math.random()*255)+1));
				}
				p.remove(pnlMenu);
				gbc.gridx=0; gbc.gridy=0; p.add(visual, gbc); 
				gbc.gridx=0; gbc.gridy=1; p.add(buttons, gbc);
				gbc.gridwidth = 1;
				gbc.gridx=2; gbc.gridy=4; buttons.add(btnAgain, gbc);
				gbc.gridx=3; gbc.gridy=4; buttons.add(btnMenu, gbc);
				enableGroups(takeGroup, true); enableGroups(putGroup, true); //necessary as gamefinish disables radio btns
				p.validate(); p.repaint();
			}
		});
		btnQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		btnAgain.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
		//remove all blocks, populate blocks0 on 1st stick
				clearSticks();
				nofBlocks = slider.getValue();
				for(int i=1; i<nofBlocks+1; i++) {
					blocks0.add(new Blocks(i, 0));
					blocks0.get(i-1).setColor(new Color((int)(Math.random()*255)+1,(int)(Math.random()*255)+1,(int)(Math.random()*255)+1));
				}
				warningsLbl.setText("");
				enableGroups(takeGroup, true); enableGroups(putGroup, true);
				p.validate(); p.repaint();
			}
		});
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearSticks();
				warningsLbl.setText("");
				p.remove(btnAgain); p.remove(btnMenu); 
				p.remove(visual); p.remove(buttons);
				p.add(pnlMenu);
				p.validate(); p.repaint();
			}
		});
		take1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(blocks0.isEmpty()) { 
					takeGroup.clearSelection();
					warningsLbl.setText("Can't take from empty stick.");
				}
			}
		});
		take2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(blocks1.isEmpty()) { 
					takeGroup.clearSelection();
					warningsLbl.setText("Can't take from empty stick.");
				}
			}
		});
		take3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(blocks2.isEmpty()) { 
					takeGroup.clearSelection();
					warningsLbl.setText("Can't take from empty stick.");
				}
			}
		});
		put1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(take1.isSelected()) {
					warningsLbl.setText("pls put on another stick"); putGroup.clearSelection();
				}
				else if(take2.isSelected()) {
					theAction(blocks1, blocks0, 1, 0);
					takeGroup.clearSelection(); putGroup.clearSelection();  
					visual.repaint();
				}
				else if(take3.isSelected()) {
					theAction(blocks2, blocks0, 2, 0);
					takeGroup.clearSelection(); putGroup.clearSelection();  
					visual.repaint();
				}
				else {putGroup.clearSelection();}
			}
		});
		put2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(take1.isSelected()) { //no need to check if stick emtpy, cant select 'take' btn in such case
					theAction(blocks0, blocks1, 0, 1);
					takeGroup.clearSelection(); putGroup.clearSelection();  
					visual.repaint();
				}
				else if(take2.isSelected()) {
					warningsLbl.setText("pls put on another stick"); putGroup.clearSelection();
				}
				else if(take3.isSelected()) {
					theAction(blocks2, blocks1, 2, 1);
					takeGroup.clearSelection(); putGroup.clearSelection();  
					visual.repaint();
				}
				else {putGroup.clearSelection(); 
				}  // condition to finish game
				if(blocks1.size()==nofBlocks || blocks2.size()==nofBlocks) {
					warningsLbl.setText("Congrats! You've accomplished the goal.");
					enableGroups(takeGroup, false); enableGroups(putGroup, false);
				}
			}
		});
		put3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(take1.isSelected()) {
					theAction(blocks0, blocks2, 0, 2);
					takeGroup.clearSelection(); putGroup.clearSelection();  
					visual.repaint();
				}
				else if(take2.isSelected()) {
					theAction(blocks1, blocks2, 1, 2);
					takeGroup.clearSelection(); putGroup.clearSelection();  
					visual.repaint();
				}
				else if(take3.isSelected()) {
					warningsLbl.setText("pls put on another stick"); putGroup.clearSelection();
				}
				else {putGroup.clearSelection();
				} // condition to finish game
				if(blocks1.size()==nofBlocks || blocks2.size()==nofBlocks) {
					warningsLbl.setText("Congrats! You've accomplished the goal.");
					enableGroups(takeGroup, false); enableGroups(putGroup, false);
				}
			}
		});
	}
	public void theAction(ArrayList<Blocks> take, ArrayList<Blocks> put, int posT, int posP) {
		int di = take.get(take.size()-1).getDia();
		Color clr = take.get(take.size()-1).getColor();

		take.remove(take.size()-1);
		put.add(new Blocks(di, posP));
		put.get(put.size()-1).setColor(clr);
		warningsLbl.setText("");
		if(put.size()>1 && put.get(put.size()-1).getDia()<put.get(put.size()-2).getDia()) {
			warningsLbl.setText("can't put biggr on smaller");
			Color c = put.get(put.size()-1).getColor();
			put.remove(put.size()-1);
			take.add(new Blocks(di, posT));
			take.get(take.size()-1).setColor(c);
			visual.repaint();
		}
	}
	public void clearSticks() {
		Iterator<Blocks> it0 = blocks0.iterator();
		while(it0.hasNext()) { Blocks b = it0.next(); it0.remove(); }
		Iterator<Blocks> it1 = blocks1.iterator();
		while(it1.hasNext()) { Blocks b = it1.next(); it1.remove(); }
		Iterator<Blocks> it2 = blocks2.iterator();
		while(it2.hasNext()) { Blocks b = it2.next(); it2.remove(); }
	}
	public void enableGroups(ButtonGroup btnGroup, boolean enable) {
		Enumeration<AbstractButton> enumeration = btnGroup.getElements();
		while (enumeration.hasMoreElements()) {
		    enumeration.nextElement().setEnabled(enable);
		}
	}
}
