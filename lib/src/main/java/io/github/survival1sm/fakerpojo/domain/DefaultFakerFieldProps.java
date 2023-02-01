package io.github.survival1sm.fakerpojo.domain;

import java.time.temporal.ChronoUnit;

/**
 * Default implementation of {@link FakerFieldProps} for when a {@link io.github.survival1sm.fakerpojo.annotations.FakerField} annotation is not present on the field
 */
public class DefaultFakerFieldProps extends FakerFieldProps {

	private String type = Type.STRING;
	private int length = 10;
	private int decimals = 2;
	private int min = 0;
	private int max = 1000000;
	private int records = 1;
	private String from = "2000-01-01 00:00:00";
	private String to = "2100-01-01 00:00:00";
	private ChronoUnit chronoUnit = ChronoUnit.MINUTES;

	public DefaultFakerFieldProps withType(String type) {
		this.type = type;
		return this;
	}

	public void setChronoUnit(ChronoUnit chronoUnit) {
		this.chronoUnit = chronoUnit;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setRecords(int records) {
		this.records = records;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getType() {
		return type;
	}

	public ChronoUnit getChronoUnit() {
		return chronoUnit;
	}

	public int getLength() {
		return length;
	}

	public int getDecimals() {
		return decimals;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	public int getRecords() {
		return records;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
}
