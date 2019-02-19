package org.wickedsource.coderadar.core.rest.dates;

import lombok.Value;

@Value
public class Week implements Comparable<Week> {

  private final int year;

  private final int weekOfYear;

  @Override
  public int compareTo(Week o) {
    if (this.year < o.year) {
      return -1;
    }
    if (this.year > o.year) {
      return 1;
    }
    return Integer.compare(this.weekOfYear, o.weekOfYear);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Week week = (Week) o;

    if (year != week.year) {
      return false;
    }
    return weekOfYear == week.weekOfYear;
  }

  @Override
  public int hashCode() {
    int result = year;
    result = 31 * result + weekOfYear;
    return result;
  }

  @Override
  public String toString() {
    return "Week[" + "year=" + year + ", weekOfYear=" + weekOfYear + ']';
  }
}
