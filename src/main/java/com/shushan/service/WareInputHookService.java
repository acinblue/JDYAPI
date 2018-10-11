package com.shushan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shushan.dao.WareInputHookDao;
@Service
public class WareInputHookService {
	@Autowired
	private WareInputHookDao wareInputDao;
	public void add(JSONObject data) {
		// TODO Auto-generated method stub
		wareInputDao.add(data);
	}

	public void update(JSONObject data) {
		// TODO Auto-generated method stub
		wareInputDao.update(data);
	}

	public void delete(JSONObject data) {
		// TODO Auto-generated method stub
		wareInputDao.delete(data);
	}

	public WareInputHookDao getWareInputDao() {
		return wareInputDao;
	}

	public void setWareInputDao(WareInputHookDao wareInputDao) {
		this.wareInputDao = wareInputDao;
	}
	

}
