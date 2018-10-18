package com.shushan.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.codec.Charsets;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.codehaus.jackson.map.ObjectMapper;

public class APIUtils {
	public static final String WEBSITE = "https://www.jiandaoyun.com";
	private static boolean retryIfRateLimited = true;
    private String urlGetWidgets;
    private String urlGetFormData;
    private String urlRetrieveData;
    private String urlUpdateData;
    private static String urlCreateData;
    private String urlDeleteData;
    private static String apiKey;
    
    /**
     * @param appId - 应用id
     * @param entryId - 表单id
     * @param apiKey - apiKey
     */
    public APIUtils (String appId, String entryId, String apiKey) {
        this.apiKey = apiKey;
        this.initUrl(appId, entryId);
    }

    private void initUrl (String appId, String entryId) {
        urlGetWidgets = WEBSITE + "/api/v1/app/" + appId + "/entry/" + entryId + "/widgets";
        urlGetFormData = WEBSITE + "/api/v1/app/" + appId + "/entry/" + entryId + "/data";
        urlRetrieveData = WEBSITE + "/api/v1/app/" + appId + "/entry/" + entryId + "/data_retrieve";
        urlUpdateData = WEBSITE + "/api/v1/app/" + appId + "/entry/" + entryId + "/data_update";
        urlCreateData = WEBSITE + "/api/v1/app/" + appId + "/entry/" + entryId + "/data_create";
        urlDeleteData = WEBSITE + "/api/v1/app/" + appId + "/entry/" + entryId + "/data_delete";
    }
    public static HttpClient getSSLHttpClient () throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            //信任所有
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

    /**
     * 获取请求头信息
     * @return
     */
    public static Header[] getHttpHeaders () {
        List<Header> headerList = new ArrayList<Header>();
        headerList.add(new BasicHeader("Authorization", "Bearer " + apiKey));
        headerList.add(new BasicHeader("Content-Type", "application/json;charset=utf-8"));
        return headerList.toArray(new Header[headerList.size()]);
    }
    
    /**
     * 发送HTTP请求
     * @param method - HTTP动词 { GET|POST }
     * @param url - 请求路径
     * @param data - 请求的数据
     * @throws Exception
     */
    public static Object sendRequest (String method, String url, Map<String, Object> data) throws Exception {
        HttpClient client = getSSLHttpClient();
        Header[] headers = getHttpHeaders();
        HttpRequestBase request;
        method = method.toUpperCase();
        if ("GET".equals(method)) {
            // GET请求
            URIBuilder uriBuilder = new URIBuilder(url);
            if (data != null) {
                // 添加请求参数
                for(Map.Entry<String, Object> entry : data.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), (String) entry.getValue());
                }
            }
            request = new HttpGet(uriBuilder.build());
        } else if ("POST".equals(method)) {
            // POST请求
            request = new HttpPost(url);
            ObjectMapper mapper = new ObjectMapper();
            HttpEntity entity = new StringEntity(mapper.writeValueAsString(data), Charsets.UTF_8);
            ((HttpPost) request).setEntity(entity);
        } else {
            throw new RuntimeException("不支持的HTTP动词");
        }
        // 设置请求头
        request.setHeaders(headers);
        // 发送请求并获取返回结果
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> result = (Map<String, Object>) mapper.readValue(response.getEntity().getContent(), Object.class);
        if (statusCode >= 400) {
            // 请求错误
            if ((Integer) result.get("code") == 8303 && retryIfRateLimited) {
                // 频率超限，5s后重试
                Thread.sleep(5000);
                return sendRequest(method, url, data);
            } else {
                throw new RuntimeException("请求错误，Error Code: " + result.get("code") + ", Error Msg: " + result.get("msg"));
            }
        } else {
            // 处理返回结果
            return result;
        }
    }

    /**
     * 创建单条数据
     * @param rawData - 创建数据内容
     * @return 更新后的数据
     */
    public  Map<String, Object> createData (Map<String, Object> rawData) {
        Map<String, Object> data = null;
        try {
            Map<String, Object> requestData = new HashMap<String, Object>();
            requestData.put("data", rawData);
            Map<String, Object> result = (Map<String, Object>)sendRequest("POST",urlCreateData, requestData);
            data = (Map<String, Object>) result.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  data;
    }
    /**
     * 获取表单字段
     * @return 表单字段
     */
    public List<Map<String, Object>> getFormWidgets () {
        List<Map<String, Object>> widgets = null;
        try {
            Map<String, Object> result = (Map<String, Object>) this.sendRequest("POST", urlGetWidgets, new HashMap<String, Object>());
            widgets = (List<Map<String, Object>>) result.get("widgets");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return widgets;
    }

    /**
     * 按条件获取表单数据
     * @param limit - 数据条数
     * @param fields - 显示的字段
     * @param filter - 过滤条件
     * @param dataId - 上次取数的最后一个数据id
     * @return - 返回的数据
     */
    public List<Map<String, Object>> getFormData (final int limit, final String[] fields, final Map<String, Object> filter, String dataId) {
        List<Map<String, Object>> data = null;
        try {
            // 构造请求数据
            Map<String, Object> requestData = new HashMap<String, Object>() {
                {
                    put("limit", limit);
                    put("fields", fields);
                    put("filter", filter);
                }
            };
            if (dataId != null) {
                requestData.put("data_id", dataId);
            }
            Map<String, Object> result = (Map<String, Object>) this.sendRequest("POST", urlGetFormData, requestData);
            data = (List<Map<String, Object>>) result.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 按条件获取全部表单数据
     * @return 表单数据
     */
    public List<Map<String, Object>> getAllFormData (String[] fields, Map<String, Object> filter) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        String offset = null;
        do {
            List<Map<String, Object>> data = this.getFormData(100, fields, filter, offset);
            // 获取返回的数据
            if (data == null || data.isEmpty()) {
                // 已经获取全部的数据
                offset = null;
            } else {
                // 获取最后一条数据的id
                offset = (String) data.get(data.size() - 1).get("_id");
                dataList.addAll(data);
            }
        } while (offset != null);
        return dataList;
    }

    /**
     * 搜索单条数据
     * @param dataId - 要查询的数据id
     * @return 表单数据
     */
    public Map<String, Object> retrieveData (String dataId) {
        Map<String, Object> data = null;
        try {
            Map<String, Object> requestData = new HashMap<String, Object>();
            requestData.put("data_id", dataId);
            Map<String, Object> result = (Map<String, Object>) this.sendRequest("POST", urlRetrieveData, requestData);
            data = (Map<String, Object>) result.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    /**
     * 更新单条数据
     * @return 更新结果
     */
    public Map<String, Object> updateData (String dataId, Map<String, Object> update) {
        Map<String, Object> data = null;
        try {
            Map<String, Object> requestData = new HashMap<String, Object>();
            requestData.put("data_id", dataId);
            requestData.put("data", update);
            Map<String, Object> result = (Map<String, Object>) this.sendRequest("POST", urlUpdateData, requestData);
            data = (Map<String, Object>) result.get("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 删除单条数据
     * @return 删除结果
     */
    public Map<String, String> deleteData (String dataId) {
        Map<String, String> result = null;
        try {
            Map<String, Object> requestData = new HashMap<String, Object>();
            requestData.put("data_id", dataId);
            result = (Map<String, String>) this.sendRequest("POST", urlDeleteData, requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
