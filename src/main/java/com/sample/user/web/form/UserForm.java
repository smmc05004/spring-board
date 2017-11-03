package com.sample.user.web.form;

import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

// 입력 폼에 기입한 내용들이 들어오게 만든 vo
public class UserForm {

	@NotEmpty(message="이름은 필수 입력 값입니다.")
	@Length(min=2, message="이름은 2글자 이상 입력해야 합니다.")
	@Pattern(regexp="/^[가-힣]{2,}$/", message="한글")
	// 두 글자 이상이고 비어있으면 안 된다.
	private String fullname;
	
	@NotEmpty(message="사용자 아이디는 필수 입력 값입니다.")
	@Length(min=4, max=20, message="아이디는 4글자 이상 20글자 이하로 입력해야 합니다.")
	private String id;
	
	@NotEmpty
	@Length(min=4, max=20)
	private String password;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Past	// 오늘보다 과거의 날짜만 입력 가능
	private Date birth;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	@Pattern(regexp="^\\d{3}-\\d{3,4}-\\d{4}$")
	private String phone;
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
