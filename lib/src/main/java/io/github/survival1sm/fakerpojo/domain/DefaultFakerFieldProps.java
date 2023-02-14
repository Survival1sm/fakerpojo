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
	private int maxDepth = 2;
	private String from = "2000-01-01 00:00:00";
	private String to = "2100-01-01 00:00:00";
	private ChronoUnit chronoUnit = ChronoUnit.MINUTES;

	public DefaultFakerFieldProps withType(String type) {
		this.type = type;
		return this;
	}

	@Override
	public int getMaxDepth() {
		return maxDepth;
	}

	@Override
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public void setChronoUnit(ChronoUnit chronoUnit) {
		this.chronoUnit = chronoUnit;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setLength(int length) {
		this.length = length;
	}

	@Override
	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	@Override
	public void setMin(int min) {
		this.min = min;
	}

	@Override
	public void setMax(int max) {
		this.max = max;
	}

	@Override
	public void setRecords(int records) {
		this.records = records;
	}

	@Override
	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public void setTo(String to) {
		this.to = to;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public ChronoUnit getChronoUnit() {
		return chronoUnit;
	}

	@Override
	public int getLength() {
		return length;
	}

	@Override
	public int getDecimals() {
		return decimals;
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public int getMax() {
		return max;
	}

	@Override
	public int getRecords() {
		return records;
	}

	@Override
	public String getFrom() {
		return from;
	}

	@Override
	public String getTo() {
		return to;
	}
}
