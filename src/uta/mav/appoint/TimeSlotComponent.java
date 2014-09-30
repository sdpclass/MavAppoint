package uta.mav.appoint;

public abstract class TimeSlotComponent {
	public void add(TimeSlotComponent ts){}
	public void remove(TimeSlotComponent ts){}
	public TimeSlotComponent get(TimeSlotComponent ts){return null;}
	public abstract String getStartTime();
	public abstract String getEndTime();
}
