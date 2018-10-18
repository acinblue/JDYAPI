package com.shushan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shushan.dao.WareDao;

@Service
public class WareService {
	@Autowired
	private WareDao wareDao;
	public String add(String warehouse, int warehouseno, String warehousetext) {
		
		return wareDao.add(warehouse, warehouseno, warehousetext)?"成功":"失败";
	}
	public void updateIDJDY(String warehouse, String idJDY) {
		wareDao.updateIDJDY(warehouse, idJDY);
		
	}

}
