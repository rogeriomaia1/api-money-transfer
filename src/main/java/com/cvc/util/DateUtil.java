package com.cvc.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public final class DateUtil {
	
	public static long diffDates(String dateFinal, String pattner) {

		LocalDate dBefore = LocalDate.now();
		LocalDate dAfter = null;
		        
		switch (pattner) {
			case Constants.DD_MM_YYYY -> {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattner);
				dAfter = LocalDate.parse(dateFinal, formatter);
			}
		
			//Futuras implementações de novos pattners
			//case "dd/MM/yyyy" -> {}
		}
		
		if (dAfter.isBefore(dBefore) && !dBefore.equals(dAfter)) {
			return -1;
		}
		
		long diff = ChronoUnit.DAYS.between(dBefore, dAfter);

		return diff;
	}

}
