package it.synclab.patred.sem.persistence.entities.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateConverter extends XmlAdapter<String, Date> {
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	public String marshal(Date date) throws Exception {
		if (date == null)
			return null;
		return sdf.format(date);
	}
	
	@Override
	public Date unmarshal(String date) throws Exception {
		return sdf.parse(date);
	}
	
}
