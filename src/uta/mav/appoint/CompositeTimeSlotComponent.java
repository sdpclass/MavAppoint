package uta.mav.appoint;

import java.util.Collection;

public class CompositeTimeSlotComponent extends TimeSlotComponent{
	Collection children;
	private String startTime;
	private String endTime;
	
	@Override
	public void add(TimeSlotComponent ts){
		children.add(ts);
	}
	
	@Override
	public void remove(TimeSlotComponent ts){
		children.remove(ts);
	}
	
	@Override
	public TimeSlotComponent get(TimeSlotComponent ts){
		return ts;
	}
	
	@Override
	public String getStartTime(){
		return null;
	}
	
	@Override
	public String getEndTime(){
		return null;
	}
	
	
}
