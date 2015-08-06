package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OutsidePanel {
	private JPanel panel;
	public JButton[] upButtons = new JButton[20];
	public JButton[] downButtons = new JButton[20];
	public ElePanelController[] epsc;
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void reachOfOP(int reachFloor , int  fromDirection) {
		if(fromDirection>0)
			upButtons[reachFloor].setBackground(new Color(180, 180, 255, 255));
		else
			downButtons[reachFloor].setBackground(new Color(180, 180, 255, 255));
		upButtons[19].setBackground(panel.getBackground());
		downButtons[0].setBackground(panel.getBackground());
	}
	
	public OutsidePanel(){
		
		panel=new JPanel(new GridLayout(21, 3, 5, 5));
		panel.setPreferredSize(new Dimension(180, 730));
		panel.add(new JLabel("     楼层"));
		panel.add(new JLabel("        上"));
		panel.add(new JLabel("        下"));
		
		for(int i=0;i<20;i++){//第一个按钮是20楼
			panel.add(new JLabel("      "+(20-i)+"F"));
			
			JButton up=new JButton("↑");
			up.setBackground(new Color(180, 180, 255, 255));
			upButtons[19-i]=up;
			//add listener in another function
			panel.add(up);
			
			JButton down=new JButton("↓");
			down.setBackground(new Color(180, 180, 255, 255));
			downButtons[19-i]=down;
			//add listener in another function
			panel.add(down);
		}
		
		upButtons[19].setText("");
		upButtons[19].setEnabled(false);
		upButtons[19].setBorderPainted(false);
		upButtons[19].setBackground(panel.getBackground());
		
		downButtons[0].setText("");
		downButtons[0].setEnabled(false);
		downButtons[0].setBorderPainted(false);
		downButtons[0].setBackground(panel.getBackground());
		
	}
	
	public void addListener() {
		for(int i=0;i<20;i++){
			
			final JButton upJButton=upButtons[i];
			upJButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int j=0;j<20;j++){
						if(upButtons[j].equals(upJButton)){
							ElePanelController uptmp=nearestElevator(j, 1);
							uptmp.status.cros=1;
							if(upJButton.getBackground().equals(new Color(255, 160, 160)))
								continue;
							//if pressed , no more request
							
							upJButton.setBackground(new Color(255, 160, 160));
							//tell status
//							选择的函数还没写！
							uptmp.status.outsidePressed(j, 1);
						}
					}
				}
			});
			
			
			final JButton downJButton=downButtons[i];
			downJButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int j=0;j<20;j++){
						if(downButtons[j].equals(downJButton)){
							ElePanelController downtmp=nearestElevator(j, -1);
							downtmp.status.cros=1;
							if(downJButton.getBackground().equals(new Color(255, 160, 160)))
								continue;
							//if pressed , no more request
							downJButton.setBackground(new Color(255, 160, 160));
							//tell status
							downtmp.status.outsidePressed(j, -1);
						}
					}
				}
			});
		}
	}
	
	//the function can pick the nearest elevator for the outside request
	
	public ElePanelController nearestElevator(int floor,int direction){
		ElePanelController target=null;
		int length=40;
		for (ElePanelController tmp : epsc) {	//stop is the best
			int i=0;
			if(tmp.status.cros==0&&(i=Math.abs(floor-tmp.status.getFloor()))<length){
				length=i;
//				System.out.println(length);
				target=tmp;
			}
		}
		if(target!=null){
			System.out.println(""+target.index);
			return target;
		}
			
		
		length=40;
		for (ElePanelController tmp : epsc) {	//best condition 10
//			something
			int i=0;
			if(tmp.status.getDirection()==direction&&
					(i=direction*(floor-tmp.status.getFloor()))>=0&&
					i<length){
				length=i;
				target=tmp;
			}
		}
		if(target!=null)
			return target;
		
		length=-1;
		for (ElePanelController tmp : epsc) {   //normal condition 11
//			something
			int i=0;
			if(tmp.status.getDirection()!=direction&&
					(i=direction*(floor-tmp.status.getFloor()))>=0&&
					i>length){
				length=i;
				target=tmp;
			}
		}
		if(target!=null)
			return target;
		
		length=40;
		for (ElePanelController tmp : epsc) {	//worst condition 01
//			something
			int i=0;
			if(tmp.status.getDirection()!=direction&&
					(i=-direction*(floor-tmp.status.getFloor()))>=0&&
					i<length){
				length=i;
				target=tmp;
			}
		}
		if(target!=null)
			return target;
		
		length=-1;
		for (ElePanelController tmp : epsc) { 	//normal condition 00
//			something
			int i=0;
			if(tmp.status.getDirection()==direction&&
					(i=-direction*(floor-tmp.status.getFloor()))>=0&&
					i>length){
				length=i;
				target=tmp;
			}
		}
	
			return target;
	}
}
