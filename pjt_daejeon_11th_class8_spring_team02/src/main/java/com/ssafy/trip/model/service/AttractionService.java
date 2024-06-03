package com.ssafy.trip.model.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import com.ssafy.trip.model.dao.AttractionDAO;
import com.ssafy.trip.model.dto.AttractionDto;
import com.ssafy.trip.model.dto.GunguDto;
import com.ssafy.trip.model.dto.SearchInfoDto;
import com.ssafy.trip.model.dto.SidoDto;

@Service
public class AttractionService {

	AttractionDAO adao;

	public AttractionService(AttractionDAO adao) {
		super();
		this.adao = adao;
	}

	public List<SidoDto> sidoList() {
		return adao.getSidoList();
	}

	public List<GunguDto> gunguList(int sido) {
		return adao.getGunguList(sido);
	}
	
	public boolean insertAttraction(AttractionDto attractionDto) {
		System.out.println(attractionDto.toString());
		if(adao.insert(attractionDto) == 1) {
			if(adao.insertOverview(attractionDto) == 1) {
				return true;
			}
			return true;
		}
		return false;
	}
	
	public int selectAttraction(String title, String addr) {
		String contentId = adao.searchAttractionContentId(title, addr);
		System.out.println(contentId);
		if(contentId != null) {
			return Integer.parseInt(contentId);
		}
		return adao.getAttractionContentId();
	}

	public List<AttractionDto> getAttractionListBySidoGungu(SearchInfoDto attr) {
		List<AttractionDto> result;
		if (attr.getContentTypeId() == 0) {
			result = adao.getAttractionListBySidoGungu(attr.getSidoCode(), attr.getGunguCode());
		} else {
			result = adao.getAttractionListBySidoGunguTypeId(attr.getSidoCode(), attr.getGunguCode(),
					attr.getContentTypeId());
		}
		return result;
	}

	public List<AttractionDto> getAttractionListByLocation(SearchInfoDto attr) {
		
		float rate = 0.0045f;
		
		switch (attr.getLevel()){
		case 1:{
			rate = 0.0018f;
			break;
		}
		case 2:{
			rate = 0.0027f;
			break;
		}
		case 3: {
			rate = 0.0045f;
			break;
		}
		case 4: {
			rate = 0.009f;
			break;
		}
		case 5:{
			rate = 0.0225f;
			break;
		}
		case 6:{
			rate = 0.0225f;
			break;
		}
		case 7:{
			rate = 0.03f;
			break;
		}
		case 8:{
			rate = 0.03f;
			break;
		}
		default:
			rate = 0.0225f;
			break;
		}

		BigDecimal minLatitue = attr.getLatitude().subtract(BigDecimal.valueOf(rate));
		BigDecimal maxLatitue = attr.getLatitude().add(BigDecimal.valueOf(rate));
		BigDecimal minLongitue = attr.getLongitude().subtract(BigDecimal.valueOf(rate));
		BigDecimal maxLongitue = attr.getLongitude().add(BigDecimal.valueOf(rate));

		List<AttractionDto> result;

		if (attr.getContentTypeId() == 0) {
			result = adao.getAttractionListByLocation(minLongitue, maxLongitue, minLatitue, maxLatitue);
		} else {
			result = adao.getAttractionListByLocationTypeId(minLongitue, maxLongitue, minLatitue, maxLatitue,
					attr.getContentTypeId());
		}

		return result;
	}
	
	public List<AttractionDto> getAttractionListByInfluencer(int ano) {
		return adao.getAttractionListByInfluencer(ano);
	}

	public AttractionDto getAttraction(int contentId) {
		AttractionDto result = adao.getAttraction(contentId);
		return result;
	}
	
	public AttractionDto getRand()
	{
		AttractionDto result = adao.getRand();
		return result;
	}
}
