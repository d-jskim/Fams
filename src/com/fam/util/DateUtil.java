package com.fam.util;



import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 날짜를 제어하는 헬퍼 함수들
 */
public class DateUtil {

	/** 변환에 사용되는 날짜 패턴이다. 원하는 대로 바꿔도 좋다. */
	//private static final String DATE_PATTERN = "dd.MM.yyyy";
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	/** 날짜 변환기 */
	private static final DateTimeFormatter DATE_FORMATTER =
			DateTimeFormatter.ofPattern(DATE_PATTERN);

	/**
     * 주어진 날짜를 String 타입으로 반환한다. 위에서 정의한
     * {@link DateUtil#DATE_PATTERN}이 사용된다.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }

    /**
     * String을 {@link DateUtil#DATE_PATTERN}에 정의한 대로
     * {@link LocalDate} 객체로 변환한다.
     *
     * String이 변환되지 않으면 null을 반환한다.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString) {
        try {
        	return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * 유효한 날짜인지 검사한다.
     *
     * @param dateString
     * @return true if the String is a valid date
     */
    public static boolean validDate(String dateString) {
    	// Try to parse the String.
    	return DateUtil.parse(dateString) != null;
    }
}
