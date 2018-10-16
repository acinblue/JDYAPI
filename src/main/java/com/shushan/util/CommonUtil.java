package com.shushan.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CommonUtil {
	private static final String SECRET = "test-secret";
	
	// 生成签名信息
	public static String getSignature(String nonce, String payload, String secret, String timestamp) {
        return DigestUtils.sha1Hex(nonce + ":" + payload + ":" + secret + ":" + timestamp);
    }

    // 获取GET请求中的参数
    public static Map<String, String> parseParameter(String query) {
        Map<String, String> paramMap = new HashMap<String, String>();
        String[] params = query.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            paramMap.put(keyValue[0], keyValue[1]);
        }
        return paramMap;
    }
    
    public HttpClient getSSLHttpClient () throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
    


    
 // 处理array类型
    public static String handleArray (JSONArray array) {
        return array.toJSONString();
    }

    // 处理地址类型
    public static String handleAddress (JSONObject address) {
        return address.toJSONString();
    }

    // 处理子表单类型
    public static String handleSubform (JSONArray subform) {
        return subform.toJSONString();
    }
    
    // 处理JSONObject类型
    public static String handleJSONObject(JSONObject obj) {
    	return obj.toString();
    }

	public static String getSecret() {
		return SECRET;
	}

	
}
