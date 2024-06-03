package com.ssafy.trip.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.model.dao.AlarmDAO;
import com.ssafy.trip.model.dao.InfluencerDAO;
import com.ssafy.trip.model.dao.MemberDAO;
import com.ssafy.trip.model.dto.AlarmDTO;
import com.ssafy.trip.model.dto.InfluencerDTO;
import com.ssafy.trip.model.dto.InfluencerTempDTO;
import com.ssafy.trip.model.dto.VisitDTO;

@Service
public class InfluencerService {
	InfluencerDAO idao;
	@Autowired
	MemberDAO mdao;
	@Autowired
	AlarmDAO adao;
	@Autowired
	ReadCountService rservice;
	
	public InfluencerService(InfluencerDAO idao) {
		super();
		this.idao = idao;
	}
	
    public static String extractChannelId(String url) {
        try {
            URL youtubeURL = new URL(url);
            String path = youtubeURL.getPath();
            if (path.startsWith("/channel/")) {
                return path.substring("/channel/".length());
            } else {
                throw new IllegalArgumentException("URL does not contain a channel ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

	public InfluencerDTO find(int no) {
		return idao.selectOne(no);
	}
	
	public InfluencerDTO findName(String no) {
		return idao.selectOneName(no);
	}

	public int insert(InfluencerDTO influencer) {
		return idao.addTemp(influencer);
	}

	@Transactional
	public int accept(int no) {
		InfluencerDTO tmp = idao.selectTempOne(no);
		String youtubeURL = tmp.getUrl();
        String[] parts = youtubeURL.split("/");
		tmp.setYoutubeId(parts[parts.length-1]);
		System.out.println(tmp.getYoutubeId());
		idao.deleteTemp(no);
		int result = idao.acceptInfluencer(tmp);
		return result;
	}

	
	@Transactional
	public int insertVisit(VisitDTO visit) {
		int result = idao.addVisit(visit);
		int visitNo = visit.getNo();
	 	List<String> members = mdao.getFollowersByInfluencerId(visit.getIno());
		for(String member : members) {
			AlarmDTO aDTO = new AlarmDTO(0, visitNo, member, false, null,0,0,null,null,null);
			adao.insertAlarm(aDTO);
		}
		return result;
	}
	
	public List<VisitDTO> selectVisits(int no){
		return idao.selectList(no);
	}

	public List<InfluencerDTO> selectAll(){
		return idao.selectAll();
	}
	
	public List<InfluencerDTO> selectByName(String word){
		return idao.selectByName(word);
	}
	
	public List<InfluencerDTO> selectByFollow(String userId){
		return idao.selectByFollow(userId);
	}
	
    public int rankUp(int id) {
        if (id != 0) {
            rservice.incrementReadCount(id);
            int readCount = rservice.getReadCount(id);
            rservice.addToReadCountRank(id, readCount);
        }
        return 1;
    }
    
    public List<InfluencerDTO> rank(){
    	Set<String> _set = rservice.getReadCountRank();
    	List<InfluencerDTO> result = new ArrayList<>();
    	for(String str : _set) {
    		result.add(idao.selectOne(Integer.parseInt(str)));
    	}
    	return result;
    }
    
    public List<InfluencerTempDTO> tempList(){
    	return idao.tempSelectAll();
    }
    
	public boolean refuse(int no) {
		if(idao.deleteTemp(no) > 0) {
			return true;
		}
		return false;
	}
    
    
}
