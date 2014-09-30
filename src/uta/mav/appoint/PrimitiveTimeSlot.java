package uta.mav.appoint;

public class PrimitiveTimeSlot extends TimeSlotComponent{
	private String name;
	private String date;
	private String starttime;
	private String endtime;
	private int uniqueid;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the starttime
	 */
	@Override
	public String getStartTime() {
		return starttime;
	}
	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	/**
	 * @return the endtime
	 */
	@Override
	public String getEndTime() {
		return endtime;
	}
	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	/**
	 * @return the uniqueid
	 */
	public int getUniqueid() {
		return uniqueid;
	}
	/**
	 * @param uniqueid the uniqueid to set
	 */
	public void setUniqueid(int uniqueid) {
		this.uniqueid = uniqueid;
	}
	
	
}
