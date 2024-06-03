package com.ssafy.trip.model.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.trip.model.dto.AttractionDto;
import com.ssafy.trip.model.dto.GunguDto;
import com.ssafy.trip.model.dto.MemberDTO;
import com.ssafy.trip.model.dto.SidoDto;

@Mapper
public interface AttractionDAO {
	public List<SidoDto> getSidoList();

	public List<GunguDto> getGunguList(int sido);
	
	public int insert(AttractionDto attractionDto);
	
	public int insertOverview(AttractionDto attractionDto);
	
	public String searchAttractionContentId(@Param("title")String title, @Param("addr")String addr);
	
	public int getAttractionContentId();

	public List<AttractionDto> getAttractionListBySidoGungu(@Param("sido") int sido, @Param("gungu") int gungu);

	public List<AttractionDto> getAttractionListBySidoGunguTypeId(@Param("sido") int sido, @Param("gungu") int gungu,
			@Param("contentTypeId") int contentTypeId);

	public List<AttractionDto> getAttractionListByLocation(@Param("min_longitude") BigDecimal min_longitude,
			@Param("max_longitude") BigDecimal max_longitude, @Param("min_latitude") BigDecimal min_latitude,
			@Param("max_latitude") BigDecimal max_latitude);

	public List<AttractionDto> getAttractionListByLocationTypeId(@Param("min_longitude") BigDecimal min_longitude,
			@Param("max_longitude") BigDecimal max_longitude, @Param("min_latitude") BigDecimal min_latitude,
			@Param("max_latitude") BigDecimal max_latitude, @Param("contentTypeId") int contentTypeId);

	public AttractionDto getAttraction(int contentId);

	public List<AttractionDto> getAttractionListByInfluencer(int ino);
	
	public AttractionDto getRand();
}
