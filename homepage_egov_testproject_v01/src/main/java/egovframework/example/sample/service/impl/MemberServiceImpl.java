package egovframework.example.sample.service.impl;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.LoginVO;
import egovframework.example.sample.service.MemberService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.egovframe.rte.fdl.idgnr.EgovIdGnrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("memberService")
public class MemberServiceImpl extends EgovAbstractServiceImpl implements MemberService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MemberServiceImpl.class);

	/** MemberDAO */
	@Resource(name="memberMapper")
	private MemberMapper memberDAO;
//
//	/** ID Generation */
//	@Resource(name = "egovIdGnrService")
//	private EgovIdGnrService egovIdGnrService;

	@Override
	public LoginVO loginMember(LoginVO vo) throws Exception {
		LOGGER.info("로그인하기");
		return memberDAO.loginMember(vo);
	}

	

}
