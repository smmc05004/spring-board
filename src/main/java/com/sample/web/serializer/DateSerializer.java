package com.sample.web.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateSerializer extends JsonSerializer<Date> {

	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// Date객체는 직렬화시 숫자로 변환되어 내려가도록 default값이 설정되어 있다.
		// 그 중에서도 객체를 xml로 변환하는 것 -> 마샬링
		// 입력한 날짜를 value로 받음
		String strDate = formatter.format(value);
		gen.writeString(strDate);
				
	}
}
