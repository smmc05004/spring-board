package com.sample.board.web.form;

import org.springframework.web.multipart.MultipartFile;

public class BoardForm {

	private String title;
	private String contents;
	// MultipartFile -> 첨부파일 처리 type / 파일을 넣어주지 않으면 null이 아니고 객체는 존재하되 비어있음
	// 내가 올린 파일에 대한 정보를 담고 있다.
	// 첨부파일은 임시 디렉토리에 저장이 되고 그걸 복사해서 다른 곳에 저장 
	// 화면에 표시되어야 하는 파일은 resource파일 안에 폴더를 만들어 저장
	// board의 filename과 변수명이 달라야 함
	private MultipartFile attachedfile;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public MultipartFile getAttachedfile() {
		return attachedfile;
	}
	public void setAttachedfile(MultipartFile attachedfile) {
		this.attachedfile = attachedfile;
	}
}
