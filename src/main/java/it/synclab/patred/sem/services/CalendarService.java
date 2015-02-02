package it.synclab.patred.sem.services;

import java.util.Calendar;
import java.util.Date;

import com.google.inject.Singleton;

@Singleton
public class CalendarService {
	
	public Calendar getCurrentCalendar() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getCurrentDate());
		return calendar;
	}
	
	public long getCurrentTime() {
		return getCurrentDate().getTime();
	}
	
	public Date getCurrentDate() {
		return new Date();
	}
	
	/**
	 * ritorna la Data corrente più dateadd giorni
	 * @param dateadd
	 * @return
	 */
	public Date getCurrentDate(int dateadd) {
		return getCurrentCalendar(dateadd).getTime();
	}
	
	/**
	 * ritorna il Calendar corrente più dateadd giorni
	 * @param dateadd
	 * @return
	 */

	public Calendar getCurrentCalendar(int dateadd) {
		Calendar cal = getCurrentCalendar();
		cal.add(Calendar.DATE, dateadd);
		return cal;
	}
	public Date getNextMonthDate() {
		Calendar cal = getCurrentCalendar();
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}
	
	public Date getNextMonthDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 1);
		return cal.getTime();
	}
	
	public boolean isToday(Calendar date) {
		
		Calendar today = getCurrentCalendar();
		if (date.get(Calendar.YEAR) == today.get(Calendar.YEAR) && date.get(Calendar.MONTH) == today.get(Calendar.MONTH) && date.get(Calendar.DATE) == today.get(Calendar.DATE))
			return true;
		
		return false;
	}
	
}
