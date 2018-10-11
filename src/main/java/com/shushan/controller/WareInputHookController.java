package com.shushan.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.shushan.service.WareHookService;
import com.shushan.service.WareInputHookService;
import com.shushan.util.CommonUtil;

@Controller
@RequestMapping("/warehook_input")
public class WareInputHookController {
	
	@Autowired
	private WareInputHookService wareInputService;
	private static final String DATA_CREATE = "data_create";
	private static final String DATA_UPDATE = "data_update";
	private static final String DATA_DELETE = "data_delete";
	@RequestMapping(value= {"/ware_input_manifest"}, method= {org.springframework.web.bind.annotation.RequestMethod.POST}, produces = { "text/plain;charset=UTF-8" })
	public void wareMani(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String method = request.getMethod();
		if ("POST".equalsIgnoreCase(method)) {
			BufferedReader br;
			try {
				br = request.getReader();
				String str, payload = "";
				while((str = br.readLine()) != null){
					payload += str;
				}
				String jdy = request.getHeader("x-jdy-signature");
				Map<String, String[]> pramMap = request.getParameterMap();
				String nonce = pramMap.get("nonce")[0];
				String timestamp =  pramMap.get("timestamp")[0];
				String signature = CommonUtil.getSignature(nonce, payload, CommonUtil.getSecret(), timestamp);
				if (!signature.equals(jdy)) {
					response.setStatus(401);
					response.getWriter().write("fail");
					return;
				}
				response.setStatus(200);
				response.getWriter().write("success");
				handlerData(payload);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			
		}
	}
	/**
	 * 处理推送来的数据
	 * @param payload-推送来的数据
	 */
	private void handlerData(String payload) {
			
			//解析为JSON字符串
			JSONObject payloadJSON = JSONObject.parseObject(payload);
			String op = (String) payloadJSON.get("op");
			JSONObject data = (JSONObject) payloadJSON.get("data");
			if (DATA_CREATE.equals(op)) {
				wareInputService.add(data);
			}else if (DATA_UPDATE.equals(op)) {
				wareInputService.update(data);
			}else {
				wareInputService.delete(data);
			}
		}
}
