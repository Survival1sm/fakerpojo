package io.github.survival1sm.fakerpojo.domain;

import java.util.List;

/**
 * The list of default types used to resolve into Java types or {@link io.github.survival1sm.fakerpojo.generators.FieldValueGenerator}'s
 */
public class Type {

	public static final String FULL_NAME = "FULL_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String INTEGER = "INTEGER";
	public static final String BOOLEAN = "BOOLEAN";
	public static final String DOUBLE = "DOUBLE";
	public static final String STRING = "STRING";
	public static final String DATE = "DATE";
	public static final String LOCALDATE = "LOCALDATE";
	public static final String LOCALDATETIME = "LOCALDATETIME";
	public static final String PARAGRAPH = "PARAGRAPH";
	public static final String CLASS = "CLASS";
	public static final String LIST = "LIST";
	public static final String MAP = "MAP";
	public static final String FLOAT = "FLOAT";
	public static final String LONG = "LONG";
	public static final String URL = "URL";
	public static final String INSTANT = "INSTANT";
	public static final String ENUM = "ENUM";
	public static final String PHONE_NUMBER = "PHONE_NUMBER";
	public static final String EMAIL = "EMAIL";
	public static final String UUID = "UUID";
	public static final String SET = "SET";
	public static final String DURATION = "DURATION";
	public static final String CITY = "CITY";
	public static final String STATE = "STATE";
	public static final String OBJECT = "OBJECT";

	public static final List<String> defaultTypeList = List.of(
			FULL_NAME,
			LAST_NAME,
			FIRST_NAME,
			INTEGER,
			BOOLEAN,
			DOUBLE,
			STRING,
			DATE,
			LOCALDATE,
			LOCALDATETIME,
			OBJECT,
			PARAGRAPH,
			CLASS,
			LIST,
			MAP,
			FLOAT,
			LONG,
			URL,
			INSTANT,
			ENUM,
			PHONE_NUMBER,
			EMAIL,
			UUID,
			SET,
			DURATION,
			CITY,
			STATE
	);
}
