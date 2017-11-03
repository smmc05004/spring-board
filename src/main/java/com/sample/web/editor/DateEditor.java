package com.sample.web.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

// text를 변환시켜서 값 주입하는 메소드
public class DateEditor extends PropertyEditorSupport {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	// 폼 입력값을 변수에 담을 때 실행 됨
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		try {
			setValue(sdf.parse(text));
		} catch(ParseException e) {
			setValue(null);
		}
	}

	// 변수에 들어있는 값을 꺼낼 때 실행됨
	@Override
	public String getAsText() {
		String text = "";
		if(getValue() != null) {
			text = sdf.format(getValue());
		}
			return text;
	}
}
