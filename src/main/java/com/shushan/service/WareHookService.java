package com.shushan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shushan.dao.WareHookDao;
@Service
public class WareHookService {
	@Autowired
	private WareHookDao wareDao;
	public void add(JSONObject data) {
		// TODO Auto-generated method stub
		wareDao.add(data);
	}

	public void update(JSONObject data) {
		// TODO Auto-generated method stub
		wareDao.update(data);
	}

	public void delete(JSONObject data) {
		// TODO Auto-generated method stub
		wareDao.delete(data);
	}

	public WareHookDao getWareDao() {
		return wareDao;
	}

	public void setWareDao(WareHookDao wareDao) {
		this.wareDao = wareDao;
	}
	

}
