package com.ssafy.trip.model.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.InfluencerDTO;
import com.ssafy.trip.model.dto.InfluencerTempDTO;
import com.ssafy.trip.model.dto.VisitDTO;
import com.ssafy.trip.model.service.InfluencerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("influencer")
@Tag(name = "인플러언서 컨트롤러", description = "인플러언서 등록, 허가, 삭제, 조회, 방문지 추가 기능을 구현 합니다.")
@CrossOrigin("*")
public class InfluencerController {

	InfluencerService iservice;

	public InfluencerController(InfluencerService iservice) {
		super();
		this.iservice = iservice;
	}

	@GetMapping("/find/{no}")
	@Operation(summary = "인플루언서 정보 조회", description = "인플루언서 정보를 입력받은 번호를 통하여 반환합니다.")
	public InfluencerDTO getInfluencer(@PathVariable("no") int no) {
		InfluencerDTO result = iservice.find(no);
		return result;
	}
	
	@GetMapping("/findname/{name}")
	@Operation(summary = "인플루언서 정보 조회", description = "인플루언서 정보를 입력받은 번호를 통하여 반환합니다.")
	public InfluencerDTO getInfluencerName(@PathVariable("name") String name) {
		InfluencerDTO result = iservice.findName(name);
		return result;
	}

	@PostMapping("/add")
	@Operation(summary = "인플루언서 추가", description = "인플루언서에 대한 추가 요청을 보냅니다.")
	public ResponseEntity<?> delete(@RequestBody InfluencerDTO influencer) {
		try {
			iservice.insert(influencer);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body("ok");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@GetMapping("/accept/list")
	@Operation(summary = "인플루언서 요청 목록", description = "사용자가 요청한 인플루언서 요청들에 대한 목록을 보여줍니다.")
	public List<InfluencerTempDTO> acceptList() {
		return iservice.tempList();
	}

	@PostMapping("/accept/{no}")
	@Operation(summary = "인플루언서 요청 승락", description = "인플루언서 추가 요청을 승락합니다.")
	public ResponseEntity<?> accept(@PathVariable("no") int no) {
		try {
			iservice.accept(no);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body("ok");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@DeleteMapping("/refuse/{no}")
	@Operation(summary = "인플루언서 요청 거절", description = "인플루언서 추가 요청을 거절합니다.")
	public String refuse(@PathVariable("no") int no) {
		if(iservice.refuse(no)) {
			return "ok";
		}
		return "fail";
	}

	@PostMapping("/visit")
	@Operation(summary = "방문 정보 추가", description = "인플루언서가 특정 관광지에 방문한 기록을 추가합니다. 방문한 동영상 url등을 추가해야합니다.")
	public ResponseEntity<?> insertVisit(@RequestBody VisitDTO visit) {
		try {
			iservice.insertVisit(visit);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body("ok");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	@PostMapping("/visit/{no}")
	@Operation(summary = "방문 정보 조회", description = "특정 관광지에 어떤 인플루언서들이 방문했었는지 조회합니다. 방문 url을 포함합니다.")
	public List<VisitDTO> searchVisits(@PathVariable("no") int no) {
		return iservice.selectVisits(no);
	}
	
	@GetMapping("/list")
	@Operation(summary = "인플루언서 전체 조회", description = "모든 인플루언서들에 대한 정보를 조회합니다.")
	public List<InfluencerDTO> influencers(){
		List<InfluencerDTO> list = iservice.selectAll();
		return list;
	}
	
	@PostMapping("/list/word")
	@Operation(summary = "인플루언서 키워드 검색", description = "키워드로 인플루언서들에 대한 정보를 조회합니다.")
	public List<InfluencerDTO> influencerByWord(@RequestBody InfluencerDTO inf){
		List<InfluencerDTO> list = iservice.selectByName(inf.getName());
		return list;
	}
	
	@GetMapping("/list/{userId}")
	@Operation(summary = "인플루언서 팔로우 조회", description = "특정 유저가 팔로우한 인플루언서들에 대한 정보를 조회합니다.")
	public List<InfluencerDTO> influencers(@PathVariable("userId") String userId){
		List<InfluencerDTO> list = iservice.selectByFollow(userId);
		return list;
	}
	
	@PostMapping("/rankup/{no}")
	@Operation(summary = "랭크업", description = "인플루언서가 랭크업됩니다.")
	public String rankUp(@PathVariable("no") int no){
		int result = iservice.rankUp(no);
		if(result ==1) {
			return "ok";
		}
		else {
			return "fail";
		}
	}
	
	@GetMapping("/rank")
	@Operation(summary = "랭크", description = "인플루언서가 랭크를 가져옵니다.")
	public List<InfluencerDTO> rank(){
		return iservice.rank();
	}
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}
}
