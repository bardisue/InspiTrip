package com.ssafy.trip.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.model.dto.InfluencerDTO;
import com.ssafy.trip.model.dto.InfluencerTempDTO;
import com.ssafy.trip.model.dto.VisitDTO;

@Mapper
public interface InfluencerDAO {
	
	public InfluencerDTO selectOne(int no);
	public InfluencerDTO selectOneName(String no);
	public InfluencerDTO selectTempOne(int no);
	public int addTemp(InfluencerDTO influencer);
	public int acceptInfluencer(InfluencerDTO influencer);
	public int deleteTemp(int no);
	
	public int addVisit(VisitDTO visit);
	public List<VisitDTO> selectList(int ano);
	public List<InfluencerDTO> selectAll();
	public List<InfluencerDTO> selectByName(String word);
	public List<InfluencerDTO> selectByFollow(String id);
	
	public List<InfluencerTempDTO> tempSelectAll();
	
}
