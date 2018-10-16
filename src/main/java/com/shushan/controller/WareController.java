package com.shushan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shushan.entity.Warehouse;
import com.shushan.service.WareService;
import com.shushan.util.APIUtils;


@Controller
@RequestMapping(value="/ware")
public class WareController {
	@Autowired
	private WareService wareService;
	private String warehouse;
	private int warehouseno;
	private String warehousetext;
	private String appId = "5bab4895cf5afe42606dbdeb";
	private String entryId = "5bab48a69d2c75425a8f2f7e";
	private String apiKey = "4L9Gfjl6idxcfWvjGQBUCun0K16WZOfF";
	private APIUtils api;
	
	@RequestMapping(value= {"ware_add"}, method= {org.springframework.web.bind.annotation.RequestMethod.POST}, produces = { "text/plain;charset=UTF-8" })
	public void wareAdd(HttpServletResponse response) {
		String message = wareService.add(warehouse,warehouseno,warehousetext);
		try {
			Map<String, Object> resultMap = wareAddToJDY(warehouse, warehouseno, warehousetext);
			response.getWriter().write(resultMap.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Map<String, Object> wareAddToJDY(String warehouseJDY, int warehousenoJDY,String warehousetextJDY) {
		Map<String, Object> create = new HashMap<String, Object>();
		create.put("warehouse", new HashMap<String, Object>(){
			{
				put("value", warehouseJDY);
			}
		});
		create.put("warehouseno", new HashMap<String, Object>(){
			{
				put("value", warehousenoJDY);
			}
		});
		create.put("warehousetext", new HashMap<String, Object>(){
			{
				put("value", warehousetextJDY);
			}
		});
		Map<String, Object> createData = api.createData(create);
		return createData;
	}

	public WareService getWareService() {
		return wareService;
	}

	public void setWareService(WareService wareService) {
		this.wareService = wareService;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public int getWarehouseno() {
		return warehouseno;
	}

	public void setWarehouseno(int warehouseno) {
		this.warehouseno = warehouseno;
	}

	public String getWarehousetext() {
		return warehousetext;
	}

	public void setWarehousetext(String warehousetext) {
		this.warehousetext = warehousetext;
	}
	
}
