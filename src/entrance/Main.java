package entrance;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Timer;

import javax.swing.JFrame;

import controller.ElePanelController;
import controller.OutsidePanel;

public class Main {
	JFrame frame = new JFrame("OS Project : Elevator Scheduale   By 1352878 Õı‘Û”Ó Shepard ");
	ElePanelController[] epsc=new ElePanelController[5];
	
	public Main() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new java.awt.FlowLayout(FlowLayout.LEFT,10,10));
        OutsidePanel op=new OutsidePanel();
        
        frame.getContentPane().add(op.getPanel());
        
        for(int i=0;i<5;i++){
        	ElePanelController elePanel=new ElePanelController(i,op);
        	epsc[i]=elePanel;
        	frame.getContentPane().add(elePanel.getPanel());
        }
        
        op.epsc=epsc;
        
        op.addListener();
        
        frame.setSize(890,780);	
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.lightGray);
		frame.setResizable(false);
		
		Timer[] timers=new Timer[5];
		for(int i=0;i<5;i++){
			timers[i]=new Timer();
		}
		
		for(int i=0;i<5;i++){
			timers[i].schedule(epsc[i].status.clock, 1000, 500);
		}
	}
	
	public static void main(String[] args) {
//		
		Main m=new Main();
		m.frame.setVisible(true);
	}
}
