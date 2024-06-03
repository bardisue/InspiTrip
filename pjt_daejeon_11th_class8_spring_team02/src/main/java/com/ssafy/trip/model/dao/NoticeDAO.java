package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.model.dto.NoticeDTO;

public interface NoticeDAO {
	int insert(NoticeDTO noticeDTO);
	public int selectTotalCount();
	List<NoticeDTO> selectList(@Param("sr") int startRow,@Param("cc") int count);
	NoticeDTO selectOne(int bno);
	int update(NoticeDTO noticeDTO);
	int readcountUp(int no);
	List<NoticeDTO> selectListThree();
	
	public int delete(int no);
}
