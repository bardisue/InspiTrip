package com.ssafy.trip.model.dao;

import java.util.List;

import com.ssafy.trip.model.dto.AlarmDTO;

public interface AlarmDAO {
	public int insertAlarm(AlarmDTO alarm);
	public List<AlarmDTO> selectAlarmsByMemberId(String mid);
	public int readAlarm(int no);
	public int deleteAlarm(int no);
}
