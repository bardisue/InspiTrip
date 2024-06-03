package com.ssafy.trip.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dao.AlarmDAO;
import com.ssafy.trip.model.dto.AlarmDTO;
import com.ssafy.trip.model.dto.MemberDTO;
import com.ssafy.trip.model.service.MemberService;
import com.ssafy.trip.model.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/member")
@Tag(name = "유저 컨트롤러", description = "유저의 회원가입, 정보수정, 탈퇴, 로그인 등을 관리합니다.")
@CrossOrigin("*")
public class MemberController {
	@Autowired
	private MemberService mservice;
	@Autowired
	private UserService userService;

	// 로그인
	@PostMapping("/login")
	@Operation(summary = "로그인", description = "입력정보를 통해 로그인을 진행합니다.")
	public String login(@RequestBody MemberDTO memberDTO, HttpSession session) {
		MemberDTO memberInfo = mservice.login(memberDTO.getId(), memberDTO.getPw());
		if (memberInfo != null) {
			session.setAttribute("memberInfo", memberInfo); // 회원 정보 세션에 넘겨줌
			return userService.login(memberDTO.getId(), memberDTO.getPw()); // 성공
		}
		return "fail"; // 실패
	}

	// 로그아웃
	@GetMapping("/logout")
	@Operation(summary = "로그아웃", description = "로그인 정보를 만료시키고 로그아웃합니다.")
	public String logout(HttpSession session) {
		session.invalidate();
		return "ok";
	}

	// 회원 가입
	@PostMapping("/regist")
	@Operation(summary = "회원가입", description = "입력정보를 통해 회원가입을 진행합니다.")
	public String regist(@RequestBody MemberDTO memberDTO) {
		System.out.println(memberDTO.toString());
		if (mservice.memberInsert(memberDTO)) {
			return "ok";
		}
		return "fail";
	}

	// 회원 정보 업데이트
	@PostMapping("/update")
	@Operation(summary = "회원 정보 수정", description = "입력정보를 통해 회원의 정보를 수정합니다.")
	public String update(@RequestBody MemberDTO memberDTO) {
		System.out.println(memberDTO.toString());
		if (mservice.memberUpdate(memberDTO)) { // 성공
			MemberDTO memberInfo = mservice.login(memberDTO.getId(), memberDTO.getPw()); // 업데이트된 회원 정보 가져오기
			if (memberInfo != null) {
				return "ok";
			}
		}
		return "fail"; // 실패
	}

	// 회원 정보 삭제
	@DeleteMapping("/delete")
	@Operation(summary = "회원 삭제", description = "회원 정보를 삭제합니다.")
	public String delete(@RequestBody MemberDTO memberDTO) {
		System.out.println(memberDTO.toString());
		if (mservice.memberDelete(memberDTO)) { // 성공
			return "ok";
		}
		return "fail"; // 실패
	}

	// 아이디 중복 체크
	@GetMapping("/check/{memberId}")
	@Operation(summary = "아이디 중복 체크", description = "해당 아이디를 가진 유저가 이미 있는지 체크합니다.")
	public String delete(@PathVariable("memberId") String memberId) {
		if (mservice.idCheck(memberId)) {
			return "ok"; // 아이디 사용가능
		}
		return "fail"; // 아이디 중복됨
	}

	@PostMapping("/follow/{memberId}/{influencerNo}")
	@Operation(summary = "팔로우기능", description = "유저가 해당 인플루언서를 팔로우합니다.")
	public String follow(@PathVariable("memberId") String memberId, @PathVariable("influencerNo") int influencerNo) {
		String result = mservice.follow(memberId, influencerNo);
		return result;
	}

	@PostMapping("/info/{memberId}")
	@Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
	public MemberDTO info(@PathVariable("memberId") String memberId) {
		return mservice.getInfo(memberId);
	}
	
	@GetMapping("/alarms/{no}")
	@Operation(summary = "알람 리스트 확인", description = "읽지 않은 알람을 확인합니다")
	public List<AlarmDTO> alarms(@PathVariable("no") String memberId) {
		return mservice.selectAlarms(memberId);
	}
	
	@DeleteMapping("/alarms/{no}")
	@Operation(summary = "알람 삭제", description = "알람을 삭제 합니다")
	public String deleteAlarm(@PathVariable("no") int no) {
		if(mservice.deleteAlarm(no) == 0) {
			return "false";
		}
		return "ok";
	}
	
	@PostMapping("/alarms/{no}")
	@Operation(summary = "알람 체크", description = "알람을 읽음 표시 합니다")
	public String readAlarm(@PathVariable("no") int no) {
		if(mservice.readAlarm(no) == 0) {
			return "false";
		}
		return "ok";
	}
}
