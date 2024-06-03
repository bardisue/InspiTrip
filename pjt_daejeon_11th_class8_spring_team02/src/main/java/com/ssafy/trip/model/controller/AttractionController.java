package com.ssafy.trip.model.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.AttractionDto;
import com.ssafy.trip.model.dto.GunguDto;
import com.ssafy.trip.model.dto.MemberDTO;
import com.ssafy.trip.model.dto.SearchInfoDto;
import com.ssafy.trip.model.dto.SidoDto;
import com.ssafy.trip.model.service.AttractionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/attr")
@Tag(name = "관광지 컨트롤러", description = "시도, 구군 정보와 관광지 정보를 불러오는 컨트롤러")
@CrossOrigin("*")
public class AttractionController {

	AttractionService aservice;

	public AttractionController(AttractionService aservice) {
		super();
		this.aservice = aservice;
	}

	@GetMapping("/sido")
	@Operation(summary = "시도목록", description = "시도에 대한 코드와 이름 리스트를 반환합니다.")
	public List<SidoDto> sidoList() {
		List<SidoDto> result = aservice.sidoList();
		return result;
	}

	@GetMapping("/gungu/{sido}")
	@Operation(summary = "구군목록", description = "시도에 따라 소속한 구군에 대한 코드와 이름 리스트를 반환합니다.")
	public List<GunguDto> gunguList(@PathVariable("sido") int sido) {
		List<GunguDto> result = aservice.gunguList(sido);
		return result;
	}
	
	// 음식점 추가
	@PostMapping("/insert")
	@Operation(summary = "음식점 추가", description = "입력된 정보를 통해 음식점을 DB에 추가합니다.")
	public String regist(@RequestBody AttractionDto attractionDto) {
		int contentId = aservice.selectAttraction(attractionDto.getTitle(), attractionDto.getAddress());
		attractionDto.setContentId(contentId);
		if (aservice.insertAttraction(attractionDto)) {
			return attractionDto.getContentId()+"";
		}
		return "fail";
	}

	@PostMapping("/list/{type}")
	@Operation(summary = "관광지 리스트", description = "설정한 검색 필터링에 따라서 리스트를 리턴해줍니다. sidoGungu, location 두가지 type을 가집니다.")
	public List<AttractionDto> list(@PathVariable("type") String type, @RequestBody SearchInfoDto info)
			throws Exception {
		List<AttractionDto> _list;
		if (type.equals("sidoGungu")) {
			_list = aservice.getAttractionListBySidoGungu(info);
		} else if (type.equals("location")) {
			_list = aservice.getAttractionListByLocation(info);
		} else {
			throw new Exception();
		}
		return _list;
	}

	@GetMapping("/visit/{no}")
	@Operation(summary = "인플루언서 별 관광지 리스트", description = "인플루언서가 방문한적이 있는 관광지를 출력합니다.")
	public List<AttractionDto> influencerList(@PathVariable("no") int no) throws Exception {
		List<AttractionDto> _list = aservice.getAttractionListByInfluencer(no);
		return _list;
	}

	@GetMapping("/attraction/{content_id}")
	@Operation(summary = "관광지 조회", description = "Id를 통하여 원하는 관광지를 하나 조회합니다.")
	public AttractionDto attraction(@PathVariable("content_id") int contentId) {
		return aservice.getAttraction(contentId);
	}
	
	@GetMapping("/attraction/rand")
	@Operation(summary = "랜덤조회", description = "랜덤 관광지를 하나 조회합니다.")
	public AttractionDto attractionRand() {
		return aservice.getRand();
	}

}
