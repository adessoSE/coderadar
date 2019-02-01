package org.wickedsource.coderadar.core.rest.dates;

public class Day implements Comparable<Day> {

	private final int year;

	private final int month;

	private final int dayOfMonth;

	public Day(int year, int month, int dayOfMonth) {
		this.year = year;
		this.month = month;
		this.dayOfMonth = dayOfMonth;
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDayOfMonth() {
		return dayOfMonth;
	}

	@Override
	public int compareTo(Day o) {
		if (this.year < o.year) {
			return -1;
		}
		if (this.year > o.year) {
			return 1;
		}
		if (this.month < o.month) {
			return -1;
		}
		if (this.month > o.month) {
			return 1;
		}
		if (this.dayOfMonth < o.dayOfMonth) {
			return -1;
		}
		if (this.dayOfMonth > o.dayOfMonth) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Day day = (Day) o;

		return year == day.year && month == day.month && dayOfMonth == day.dayOfMonth;
	}

	@Override
	public int hashCode() {
		int result = year;
		result = 31 * result + month;
		result = 31 * result + dayOfMonth;
		return result;
	}

	@Override
	public String toString() {
		return "Day[" + "year=" + year + ", month=" + month + ", dayOfMonth=" + dayOfMonth + ']';
	}
}
