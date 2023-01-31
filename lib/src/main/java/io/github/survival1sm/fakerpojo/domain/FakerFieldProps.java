package io.github.survival1sm.fakerpojo.domain;

import java.time.temporal.ChronoUnit;

public class FakerFieldProps {

  private String type;
  private int length;
  private int decimals;
  private int min;
  private int max;
  private int records;
  private String from;
  private String to;

  public ChronoUnit getChronoUnit() {
    return chronoUnit;
  }

  public void setChronoUnit(ChronoUnit chronoUnit) {
    this.chronoUnit = chronoUnit;
  }

  private ChronoUnit chronoUnit;

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public int getDecimals() {
    return decimals;
  }

  public void setDecimals(int decimals) {
    this.decimals = decimals;
  }

  public int getMin() {
    return min;
  }

  public void setMin(int min) {
    this.min = min;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public int getRecords() {
    return records;
  }

  public void setRecords(int records) {
    this.records = records;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

}
