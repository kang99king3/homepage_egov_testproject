package egovframework.example.sample.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.LoginVO;
import egovframework.example.sample.service.MemberService;
import egovframework.example.sample.service.SampleVO;

@Controller
public class EgovMemberController {
	Logger logger=LoggerFactory.getLogger(EgovMemberController.class);
	
	@Resource(name = "memberService")
	private MemberService memberService;
	
	@PostMapping("/member/login")
	public String login(@Valid @ModelAttribute LoginVO loginVO, BindingResult bindingResult,
			HttpServletRequest request) 
			throws Exception {
		
		logger.info("로그인하기");
		
		if (bindingResult.hasErrors()) {
			return "index";
		}
		
		LoginVO resultloginVO= memberService.loginMember(loginVO);

		
		if(resultloginVO!=null) {
			HttpSession session=request.getSession();
			session.setMaxInactiveInterval(60*10);//10분
			session.setAttribute("loginVO", resultloginVO);
		}
		
		return "redirect:/sample/list";
	}
}




