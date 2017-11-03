package com.sample.web.advice;

import java.util.Date;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import com.sample.exception.HTAException;
import com.sample.web.editor.DateEditor;

@ControllerAdvice
// @ControllerAdvice -> 자동 스캔되어 모든 컨트롤러에 적용됨
// 거의 대부분 날짜 형식 변형 메소드만 정의
public class CommonControllerAdvice {

	// @InitBinder -> 요청핸들러 매개변수에 타입에 맞게 변형 후 입력시킴
	// 한 컨트롤러에서 사용하면 다른 컨트롤러에서는 사용불가
	@InitBinder
	public void binder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionExceptionHandler(Exception e) {
		e.printStackTrace();
		return "error/unknown_error";
	}
	
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionHandler(DataAccessException e) {
		e.printStackTrace();
		return "error/db_error";
	}
	
	@ExceptionHandler(HTAException.class)
	public String htaExceptionHandler(HTAException e) {
		return "error/hta_error";
	}
}
