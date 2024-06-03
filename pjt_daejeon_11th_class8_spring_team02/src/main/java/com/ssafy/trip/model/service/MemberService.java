package com.ssafy.trip.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.trip.model.dao.AlarmDAO;
import com.ssafy.trip.model.dao.MemberDAO;
import com.ssafy.trip.model.dto.AlarmDTO;
import com.ssafy.trip.model.dto.MemberDTO;

@Service
public class MemberService {
	@Autowired
	private MemberDAO mdao;
	@Autowired
	private AlarmDAO adao;

	// 회원 가입
	public boolean memberInsert(MemberDTO memberDTO) {
		if (mdao.insert(memberDTO) == 1) {
			return true;
		}
		return false;
	}

	// 회원 정보 수정
	public boolean memberUpdate(MemberDTO memberDTO) {
		if (mdao.update(memberDTO) == 1) {
			return true;
		}
		return false;
	}

	// 회원 탙퇴
	public boolean memberDelete(MemberDTO memberDTO) {
		if (mdao.delete(memberDTO) == 1) {
			return true;
		}
		return false;
	}

	// 회원 로그인
	public MemberDTO login(String memberId, String memberPw) {
		if (mdao.login(memberId, memberPw) == null)
			return null;
		return getInfo(memberId);
	}

	// 아이디 중복 확인
	public boolean idCheck(String memberId) {
		if (mdao.idCheck(memberId) != null) {
			return false; // 아이디 사용할 수 없음
		}
		return true; // 아이디 사용 가능
	}

	public MemberDTO getInfo(String id) {
		return mdao.selectOne(id);
	}
	
	public String follow(String memberId, int influencerNo) {
		int result = mdao.checkAlready(memberId, influencerNo);
		if(result == 1) {
			return "already";
		}
		else {
			mdao.follow(memberId, influencerNo);
			return "success";
		}
	}
	
	public List<AlarmDTO> selectAlarms(String mid){
		return adao.selectAlarmsByMemberId(mid);
	}
	
	public int readAlarm(int no) {
		return adao.readAlarm(no);
	}
	public int deleteAlarm(int no) {
		return adao.deleteAlarm(no);
	}
}
