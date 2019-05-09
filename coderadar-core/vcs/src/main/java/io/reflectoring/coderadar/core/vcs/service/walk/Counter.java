package io.reflectoring.coderadar.core.vcs.service.walk;

public class Counter {

  private int count;

  public Counter(int startValue) {
    this.count = startValue;
  }

  public void increment() {
    this.count++;
  }

  public void decrement() {
    this.count--;
  }

  public int getValue() {
    return this.count;
  }
}
