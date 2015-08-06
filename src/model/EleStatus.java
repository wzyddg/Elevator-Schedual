package model;

import controller.ElePanelController;
import controller.OutsidePanel;

public class EleStatus {
	private Integer floor;
	private Integer direction;
	public Boolean[] upSequence;
	public Boolean[] downSequence;
	public ElePanelController epc;
	public OutsidePanel op;
	public EleClock clock;
	public int cros=0; //0=stop 1=running 2=open 3=close
	
	public Boolean[] getSequence(Integer direction) {
		if(direction>0)
			return upSequence;
		else {
			return downSequence;
		}
	}
	
	public void insidePressed(int pressedFloor) {
		System.out.println("this is ES insidePressed ,get message from EPC\nfloor : "
				+pressedFloor+"  index : "+epc.index);
//		turn the inside number into outside number with direction
		if(direction*(pressedFloor-floor)>0)
			outsidePressed(pressedFloor, direction);
		else 
			outsidePressed(pressedFloor, -direction);
	}
	
	public void outsidePressed(int pressedFloor,int direction){
		System.out.println("this is ES outsidePressed , get message \nfloor : "
				+pressedFloor+"  direction : "+direction+"  index:"+epc.index);
		getSequence(direction)[pressedFloor]=true;
	}
	
	public EleStatus(ElePanelController epc,OutsidePanel op) {
		cros=0;
		this.epc=epc;
		floor=0;
		direction=1;
		this.op=op;
		upSequence=new Boolean[20];
		downSequence=new Boolean[20];
		for(int i=0;i<20;i++){
			upSequence[i]=false;
			downSequence[i]=false;
		}
		clock=new EleClock(this);
	}
	
	public Integer getDirection() {
		return direction;
	}
	
	public Integer getFloor() {
		return floor;
	}
	
	public void setDirection(Integer direction) {
		if(direction>0)
			this.direction = 1;
		else
			this.direction = -1;
	}
	
	public void setFloor(Integer floor) {
		if(floor>=0&&floor<20)
			this.floor = floor;
	}
}