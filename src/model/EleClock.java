package model;

import java.awt.Color;
import java.util.TimerTask;

public class EleClock extends TimerTask{
	public EleStatus host;
	
	public EleClock(EleStatus host){
		this.host=host;
	}
	
	@Override
	public void run() {	//redo this part
		
		if(host.getSequence(host.getDirection())[host.getFloor()]){
			host.op.reachOfOP(host.getFloor(), host.getDirection());
			host.epc.reachOfEPC(host.getFloor(), host.getDirection());
			host.epc.insideReach(host.getFloor());
			if(host.cros==1){
				host.epc.setOpen();
				host.cros=2;
				return;
			}
			if(host.cros==2){
				host.epc.setClose();
				host.cros=3;
				return;
			}
			if(host.cros==3){
				host.epc.setRunning();
				host.cros=1;
			}
			host.getSequence(host.getDirection())[host.getFloor()]=false;
		} else{
//			host.op.reachOfOP(host.getFloor(), host.getDirection());
			host.epc.reachOfEPC(host.getFloor(), host.getDirection());
			if(host.epc.insideButtons[host.getFloor()].getBackground().equals(new Color(255, 160, 160))){
				if(host.cros==1){
					host.epc.setOpen();
					host.cros=2;
					return;
				}
				if(host.cros==2){
					host.epc.setClose();
					host.cros=3;
					return;
				}
				if(host.cros==3){
					host.epc.setRunning();
					host.cros=1;
				}
				host.epc.insideButtons[host.getFloor()].setBackground(new Color(180, 180, 255, 255));
				return;
			}
			if(hasRequestTowards(host.getDirection())){
				host.setFloor(host.getFloor()+host.getDirection());
				host.cros=1;
			}else {
				if(!hasRequestTowards(-host.getDirection())){
					if(host.getSequence(-host.getDirection())[host.getFloor()])
						host.setDirection(-host.getDirection());
					else
						host.cros=0;
				}	
				else {
					host.setDirection(-host.getDirection());
					host.cros=1;
				}
			}
		}
	}

	public Boolean hasRequestTowards(int direction) {
		
		if(direction>0){		//up
			if(host.getFloor()==19)
				return false;
			Boolean hasRequest=false;
			for(int i=host.getFloor()+1;i<20;i++){
				hasRequest=hasRequest||host.upSequence[i]||host.downSequence[i];
			}
			return hasRequest;
		}else {					//down
			if(host.getFloor()==0)
				return false;
			Boolean hasRequest=false;
			for(int i=host.getFloor()-1;i>-1;i--){
				hasRequest=hasRequest||host.upSequence[i]||host.downSequence[i];
			}
			return hasRequest;
		}
	}
}
