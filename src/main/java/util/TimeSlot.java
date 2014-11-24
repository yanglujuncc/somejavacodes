package util;

import java.util.Arrays;
import java.util.TimeZone;



public class TimeSlot {

	final int TimeSlot=24;
	
	public static int timeSlot(long time, long zoneOffset) {
		long hours = (time + zoneOffset) / 3600000L;

		return (int) (hours % 24);
	}

	public static void main(String[] args) {

		long time = System.currentTimeMillis();
		long zoneOffset = TimeZone.getTimeZone("GMT+8").getRawOffset();
		System.out.println("time:" + time);
		System.out.println("zoneOffset:" + zoneOffset);
		int timeSlot = timeSlot(time, zoneOffset);
		System.out.println("timeSlot:" + timeSlot);


	}
}
