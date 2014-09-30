package uta.mav.appoint.beans;

import java.io.Serializable;

public class Appointment implements Serializable{

	/**
	 * JavaBean for Appointments db table
	 */
	private static final long serialVersionUID = -3734663824525723817L;
	String pname;
	String advisingDate;
	String advisingStartTime;
	String advisingEndTime;
	String appointmentType;
	String advisorEmail;
	int appointmentId;
	
	/**
	 * @return the pname
	 */
	public String getPname() {
		return pname;
	}
	/**
	 * @param pname the pname to set
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}
	/**
	 * @return the advisingDate
	 */
	public String getAdvisingDate() {
		return advisingDate;
	}
	/**
	 * @param advisingDate the advisingDate to set
	 */
	public void setAdvisingDate(String advisingDate) {
		this.advisingDate = advisingDate;
	}
	/**
	 * @return the advisingStartTime
	 */
	public String getAdvisingStartTime() {
		return advisingStartTime;
	}
	/**
	 * @param advisingStartTime the advisingStartTime to set
	 */
	public void setAdvisingStartTime(String advisingStartTime) {
		this.advisingStartTime = advisingStartTime;
	}
	/**
	 * @return the advisingEndTime
	 */
	public String getAdvisingEndTime() {
		return advisingEndTime;
	}
	/**
	 * @param advisingEndTime the advisingEndTime to set
	 */
	public void setAdvisingEndTime(String advisingEndTime) {
		this.advisingEndTime = advisingEndTime;
	}
	/**
	 * @return the appointmentType
	 */
	public String getAppointmentType() {
		return appointmentType;
	}
	/**
	 * @param appointmentType the appointmentType to set
	 */
	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}
	/**
	 * @return the advisorEmail
	 */
	public String getAdvisorEmail() {
		return advisorEmail;
	}
	/**
	 * @param advisorEmail the advisorEmail to set
	 */
	public void setAdvisorEmail(String advisorEmail) {
		this.advisorEmail = advisorEmail;
	}
	/**
	 * @return the appointmentId
	 */
	public int getAppointmentId() {
		return appointmentId;
	}
	/**
	 * @param appointmentId the appointmentId to set
	 */
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	
	@Override
	public String toString(){
		return	"<b>Advisor: </b>" + this.getPname()
			+	"<b> Advising Date: </b>" + this.getAdvisingDate()
			+	"<b> Advising Start: </b>" + this.getAdvisingStartTime()
			+	"<b> Advising End: </b>" + this.getAdvisingEndTime()
			+	"<b> Appointment Type: </b>" + this.getAppointmentType()
			+	"<b> Advisor Email: </b>" + this.getAdvisorEmail();
	}
}