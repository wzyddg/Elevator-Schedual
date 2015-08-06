package controller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.EleStatus;

public class ElePanelController {
	private JPanel panel;
	public JButton[] insideButtons = new JButton[20];
	public JButton[] eleImagines = new JButton[20];
	public EleStatus status;
	public JLabel annoJLabel;
	public int prevFloor=0;
	//bitmap references are in status
	
	public final Integer index;
	
	public JPanel getPanel() {
		return panel;
	}
	
	private String directionToString(int direction){
		if(direction>0)
			return "↑";
		else 
			return "↓";
		
	}
	
	public void setOpen() {
		eleImagines[status.getFloor()].setBackground(new Color(255, 255, 150, 255));
		eleImagines[status.getFloor()].setText("开");
	}
	
	public void setClose(){
		eleImagines[status.getFloor()].setBackground(new Color(255, 120, 120, 255));
		eleImagines[status.getFloor()].setText("关");
	}
	
	public void setRunning(){
		eleImagines[status.getFloor()].setBackground(new Color(180, 255, 180, 255));
		eleImagines[status.getFloor()].setText("");
	}
	
	public void changeFloor(int from , int to){
//		if(status.getFloor()-status.getDirection()>19||status.getFloor()-status.getDirection()<0)
//			return;
		eleImagines[from].setBackground(new Color(200, 200, 200, 255));
		eleImagines[to].setBackground(new Color(180, 255, 180, 255));
	}
	
	public void insideReach(Integer reachFloor){
		insideButtons[reachFloor].setBackground(new Color(180, 180, 255, 255));
	}
	
	public void reachOfEPC(Integer reachFloor,Integer fromDirection){
		System.out.println("EPC "+index+" reach "+reachFloor+" from direction "+fromDirection);
		changeFloor(prevFloor, reachFloor);
		prevFloor=reachFloor;
		annoJLabel.setText("     "+(reachFloor+1)+ "F   "+directionToString(fromDirection));
	}
	
	public ElePanelController(int index,OutsidePanel op) {
		this.index=index;
		status=new EleStatus(this,op);
		panel=new JPanel(new GridLayout(21, 2, 5, 5));
		annoJLabel=new JLabel("     1F");
		panel.setPreferredSize(new Dimension(126, 730));
		
		panel.add(new JLabel(" 内部按钮"));
		panel.add(annoJLabel);
		for(int i=0;i<20;i++){//第一个按钮是20楼
			final JButton button=new JButton(""+(20-i)+"F");
			button.setBackground(new Color(180, 180, 255, 255));
			insideButtons[19-i]=button;
			//add listener
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					button.setBackground(new Color(255, 160, 160));
					for(int k=0;k<20;k++){
						if(insideButtons[k].equals(button)){
							status.insidePressed(k);
							break;
						}
					}
				}
			});
			//add done
			panel.add(button);
			
			JButton picture=new JButton();
			picture.setBackground(new Color(200, 200, 200, 255));
			picture.setForeground(new Color(200, 200, 200, 255));
			picture.setBorderPainted(false);
			picture.setEnabled(false);
			eleImagines[19-i]=picture;
			panel.add(picture);
		}
		eleImagines[0].setBackground(new Color(180, 255, 180, 255));
		eleImagines[0].setForeground(new Color(180, 255, 180, 255));
	}
}