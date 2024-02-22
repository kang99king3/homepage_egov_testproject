package egovframework.example.sample.service;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "사용자 정보 VO")
@Getter
@Setter
public class LoginVO implements Serializable{

	private static final long serialVersionUID = -7634364538509548841L;
	
	@Schema(description = "회원번호")
	private int mber_no;
	
	@Schema(description = "아이디")
	private String mber_id;
	
	@Schema(description = "비밀번호")
	private String password;
	
	@Schema(description = "이름")
	private String name;
	
	@Schema(description = "회원상태")
	private String mber_sttus;
	
	@Schema(description = "가입일자")
	private String sbscrb_de;
	
	@Schema(description = "회원등급")
	private String role;

}
