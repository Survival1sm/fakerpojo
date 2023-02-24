package io.github.survival1sm.fakerpojo.domain;

import io.github.survival1sm.fakerpojo.annotations.FakerField;
import java.time.temporal.ChronoUnit;

/** A class implementation of the {@link FakerField} annotation. */
public class FakerFieldProps {

  private String type;
  private int length;
  private int decimals;
  private int min;
  private int max;
  private int records;
  private int maxDepth;
  private String defaultValueExpression;
  private String from;
  private String to;
  private ChronoUnit chronoUnit;

  public String getDefaultValueExpression() {
    return defaultValueExpression;
  }

  public void setDefaultValueExpression(String defaultValueExpression) {
    this.defaultValueExpression = defaultValueExpression;
  }

  public int getMaxDepth() {
    return maxDepth;
  }

  public void setMaxDepth(int maxDepth) {
    this.maxDepth = maxDepth;
  }

  public ChronoUnit getChronoUnit() {
    return chronoUnit;
  }

  public void setChronoUnit(ChronoUnit chronoUnit) {
    this.chronoUnit = chronoUnit;
  }

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
