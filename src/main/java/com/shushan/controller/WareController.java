package com.shushan.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	private APIUtils api = new APIUtils(appId, entryId,apiKey);
	
	@RequestMapping(value= {"ware_add"}, method= {org.springframework.web.bind.annotation.RequestMethod.POST})
	public void wareAdd(@RequestBody Warehouse warehouse,HttpServletResponse response) {
		String message = wareService.add(warehouse.getWarehouse(),warehouse.getWarehouseno(),warehouse.getWarehousetext());
		try {
			Map<String, Object> resultMap = wareAddToJDY(warehouse.getWarehouse(),warehouse.getWarehouseno(),warehouse.getWarehousetext());
			
			response.getWriter().write(resultMap.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 将新建的数据推送到简道云
	 * @param warehouseJDY
	 * @param warehousenoJDY
	 * @param warehousetextJDY
	 * @return
	 */
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
		String id_JDY = (String) createData.get("_id");
		wareService.updateIDJDY(warehouseJDY, id_JDY);
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
