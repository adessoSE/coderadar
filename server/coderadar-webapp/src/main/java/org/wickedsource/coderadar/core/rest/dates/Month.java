package org.wickedsource.coderadar.core.rest.dates;

public class Month {

  private final int year;

  private final int month;

  public Month(int year, int month) {
    this.year = year;
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public int getMonth() {
    return month;
  }
}
