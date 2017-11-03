package com.sample.user.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sample.user.service.UserService;
import com.sample.user.vo.CheckResult;
import com.sample.user.vo.User;

@Controller
public class UserAjaxController {

	@Autowired
	UserService userService;
	
	@RequestMapping("/user/idcheck.do")
	// @ResponseBody -> html의 구성요소 중 하나인 응답 바디에 
	// CheckResult객체가 가진 컨텐츠를 xml이나 json으로 변환시켜 내려보냄
	public @ResponseBody CheckResult idcheck(@RequestParam("id") String userid) {
		
		CheckResult result = new CheckResult();
		result.setUserid(userid);
		
		User user = userService.getUserDetail(userid);
		
		if (user != null) {
			result.setUsed(true);
		}
		return result;
	}
	
}
