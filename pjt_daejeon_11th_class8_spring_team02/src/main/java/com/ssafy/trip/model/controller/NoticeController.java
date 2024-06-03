package com.ssafy.trip.model.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.model.dto.NoticeDTO;
import com.ssafy.trip.model.dto.PageDTO;
import com.ssafy.trip.model.service.NoticeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/notice")
@Tag(name = "공지사항", description = "공지사항적는 게시판")
@CrossOrigin("*")
public class NoticeController {
	@Autowired
	private NoticeService nservice;

	@GetMapping("/list/{page}")
	@Operation(summary = "페이지 정보 리턴", description = "페이지 인덱스에 따라 페이지 정보를 가져옵니다. 시작 게시글 번호 끝 번호, 게시글 개수를 포함합니다.")
	public PageDTO getMethodName(@PathVariable("page") int page) {
		PageDTO pageInfo = nservice.makePage(page);
		return pageInfo;
	}

	@PostMapping("/write")
	@Operation(summary = "게시글 작성", description = "공지사항을 작성합니다.")
	public String write(@RequestBody NoticeDTO board) throws Exception {
		try {
			nservice.write(board);
			//HttpHeaders headers = new HttpHeaders();
			//headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return "ok";
		} catch (Exception e) {
			return "fail";
		}

	}

	@PostMapping("/update")
	@Operation(summary = "게시글 수정", description = "공지사항을 수정합니다.")
	public ResponseEntity<?> update(@RequestBody NoticeDTO board) throws Exception {
		System.out.println("update");
		if (board.getContent().equals("") || board.getTitle().equals("")) {
			throw new Exception();
		}
		try {
			nservice.update(board);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body("ok");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@DeleteMapping("/delete/{no}")
	@Operation(summary = "게시글 삭제", description = "공지사항을 삭제합니다.")
	public ResponseEntity<?> delete(@PathVariable("no") int no) {
		try {
			nservice.delete(no);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
			return ResponseEntity.ok().headers(headers).body("ok");
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@GetMapping("/read/{no}")
	@Operation(summary = "게시글 조회", description = "특정 게시글에 대한 정보를 모두 조회합니다.")
	public NoticeDTO read(@PathVariable("no") int no) {
		NoticeDTO result = nservice.selectOne(no);
		return result;
	}
	
	@PutMapping("/readcount/{no}")
	@Operation(summary = "게시글 조회수 증가", description = "특정 게시글에 대한 조회수를 증가시킵니다.")
	public String readcount(@PathVariable("no") int no) {
		System.out.println(no);
		if(nservice.readcountUp(no) > 0) {
			return "ok";
		}
		return "fail";
	}
	
	@GetMapping("/list/three")
	@Operation(summary = "게시글 3개 조회", description = "최신 게시글을 3개 조회합니다.")
	public List<NoticeDTO> listThree() {
		return nservice.listThree();
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
//		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error : " + e.getMessage());
	}

}
