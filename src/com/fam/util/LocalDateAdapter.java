package com.fam.util;


import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * LocalDate와 ISO 8601 간 변환을 하는 JAXB 어댑터
 * String은 '2012-12-03' 같은 날짜를 나타낸다.
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}