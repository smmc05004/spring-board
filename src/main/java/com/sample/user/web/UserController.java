package com.sample.user.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sample.user.service.UserService;
import com.sample.user.vo.User;
import com.sample.user.web.form.UserForm;

@Controller
@RequestMapping("/user")
// 클래스에 @RequestMapping으로 적어준 경로는 메소드의 @RequestMapping에서는 빼고 적어도 됨 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/form.do")
	public String form(Model model) {
		model.addAttribute("userForm", new UserForm());
		// return에는 prefix, suffix사이에 들어가는 경로
		return "user/form";
	}
	
	@RequestMapping("/add.do")
	// @Valid -> UserForm에 있는 유효성 체크 위해 붙임
	// BindingResult -> 유효성 체크에 위배되면 변수에 위배된 필드명과 메세지가 담김
	public String register(@Valid UserForm userForm, BindingResult errors) {
		
		if(userService.getUserDetail(userForm.getId()) != null) {
			// 에러 메세지를 errors 안에 새로 추가
			// "id" -> input의 path
			errors.rejectValue("id", null, "이미 사용중인 아이디 입니다.");
			return "user/form";
		}
		// hasErrors -> 에러가 발생 했는지(에러 객체가 들어있는지) 유무반환
		if (errors.hasErrors()) {
			// 에러로 인한 가입이 불가해졌을 때
			// 1. 메인 페이지로 이동시킴
			// 2. 다시 등록페이지로 재이동시킴
			// if 2번 선택한 경우, 어디서 에러가 발생했는지 알려주기 위해 form.jsp를 다음과 같이 만들어야 함
			// form library 사용해서 form태그를 이용한 form생성  // ex) name 속성을 -> path로 변경
			return "user/form";
		}
		User user = new User();
		BeanUtils.copyProperties(userForm, user);
		// 비빌번호 암호화
		String encryptPassword = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(encryptPassword);
		userService.addNewUser(user);
		
		return "redirect:/home.do";
	}
	@RequestMapping(value="/login.do", method = RequestMethod.GET)
	//login form으로 들어가는 건 get방식
	public String loginform() {
		return "user/loginform";
		
	}
	@RequestMapping(value="/login.do", method = RequestMethod.POST )
	// 들어가서 처리하는 건 post방식
	// if 파라미터로 받아야 하는 id와 password가 
	// loginform의 name 속성으로 지정했다면 ex) String userid, String userpwd,
	// @RequestParam불필요
	// @RequestParam에는 loginform의 name 속성 기입
	public String loginprocess(@RequestParam("userid") String id,
								@RequestParam("userpwd") String password,
								HttpSession session) {
		
		User user = userService.getUserDetail(id);
		if (user == null) {
			return "redirect:/user/login.do?error=fail";
		}
		String encryptPassword = DigestUtils.md5DigestAsHex(password.getBytes());
		if (!user.getPassword().equals(encryptPassword)) {
			return "redirect:/user/login.do?error=fail";
		}
		// 로그인에 성공하면,
		// navibar에 로그인 정보를 LOGIN_USER이름으로 전달
		session.setAttribute("LOGIN_USER", user);
		
		return "redirect:/home.do";
	}
	@RequestMapping("/logout.do")
	public String logout(HttpSession session) {
		session.invalidate();
		
		return "redirect:/home.do";
	}
}
