package com.ssafy.trip.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.MemberReviewDTO;
import com.ssafy.trip.model.dto.ReviewImageDTO;
import com.ssafy.trip.model.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/review")
@Tag(name = "리뷰", description = "관광지에 작성하는 리뷰")
@CrossOrigin("*")
public class ReviewController {
	@Autowired
	private ReviewService rservice;
	
	@PostMapping("/add")
	@Operation(summary="리뷰 추가", description = "관광지에 리뷰를 추가합니다.")
	public String postReviewName(@RequestBody MemberReviewDTO review) {
		System.out.println(review);
		return rservice.insert(review)+"";
	}
	
	@PostMapping("/image/add")
	@Operation(summary="리뷰 추가", description = "관광지에 리뷰를 추가합니다.")
	public String postReviewImageName(@RequestBody ReviewImageDTO dto) {
		if(rservice.insertImage(dto)) {
			return "ok";
		}
		return "fail";
	}
	
	@GetMapping("/list/{ano}")
	@Operation(summary = "관광지 리뷰 정보 리턴", description = "관광지의 ano에 작성된 모든 리뷰 정보를 불러옵니다.")
	public List<MemberReviewDTO> getReviewName(@PathVariable("ano") int ano) {
		System.out.println("/list/{ano} >> " + ano);
		List<MemberReviewDTO> memberReviews = rservice.selectAll(ano);
		for(MemberReviewDTO dto : memberReviews) {
			System.out.println(dto.toString());
		}
		System.out.println("/list/{ano} >> " + memberReviews.size());
		return memberReviews;
	}
	
	@GetMapping("/image/{rno}")
	@Operation(summary = "관광지 리뷰 이미지 정보 리턴", description = "관광지의 ano에 작성된 리뷰의 이미지 정보를 불러옵니다.")
	public List<ReviewImageDTO> getImageName(@PathVariable("rno") int rno) {
		System.out.println("/image/{rno} >> " + rno);
		List<ReviewImageDTO> imageList = rservice.getImage(rno);
		System.out.println("/image/{rno} >> " + imageList.size());
		return imageList;
	}
	
	@GetMapping("/avg/{ano}")
	@Operation(summary = "관광지 평점", description = "유저 리뷰들로부터 평점의 평균을 받아옵니다")
	public double getAvg(@PathVariable("ano") int ano) {
		return rservice.getRating(ano);
	}
	
}
