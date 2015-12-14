package it.synclab.patred.sem.fake;

import it.synclab.patred.sem.services.CalendarService;

import java.util.Date;

public class CalendarServiceMOCK extends CalendarService {

	private static Date sdate;

	public Date getCurrentDate() {
		return sdate;
	}

	public static void setFixedCurrentDate(Date date) {
		sdate = date;
	}

}
