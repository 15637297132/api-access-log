package com.edu.api.log.v2.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-09-30 15:28
 **/
public class Ip2Address {

    private static final Logger LOGGER = LoggerFactory.getLogger(Ip2Address.class);

    private static final String api = "https://api.clife.cn/v1/web/env/location/get?";

    /**
     * @param ip       请求的参数 格式为：name=xxx&pwd=xxx
     * @param encoding 服务器端请求编码。如GBK,UTF-8等
     * @return
     */
    private static String getResult(String ip, String encoding) {
        URL url = null;
        HttpURLConnection connection = null;
        try {
            url = new URL(api);
            // 新建连接实例
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间，单位毫秒
            connection.setConnectTimeout(2000);
            // 设置读取数据超时时间，单位毫秒
            connection.setReadTimeout(2000);
            // 是否打开输出流 true|false
            connection.setDoOutput(true);
            // 是否打开输入流true|false
            connection.setDoInput(true);
            // 提交方法POST|GET
            connection.setRequestMethod("POST");
            // 是否缓存true|false
            connection.setUseCaches(false);
            // 打开连接端口
            connection.connect();
            // 打开输出流往对端服务器写数据
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            // 写数据,也就是提交你的表单 name=xxx&pwd=xxx
            out.writeBytes("city=" + ip);
            out.flush();
            out.close();
            // 往对端写完数据对端服务器返回数据
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            // ,以BufferedReader流来读取
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        } catch (Exception e) {
            LOGGER.error("request failed , error is {}", e);
        } finally {
            if (connection != null) {
                connection.disconnect();// 关闭连接
            }
        }
        return null;
    }


    public static String getAddress(String ip) {
        StringBuilder address = new StringBuilder();
        try {
            String jsonResult = Ip2Address.getResult(ip, "utf-8");
            Map<String, Object> map = JSONObject.parseObject(jsonResult, Map.class);
            JSONObject result = (JSONObject) map.get("data");
            Object province = result.get("province");
            if (province != null) {
                address.append(province).append("省");
            }
            if (result.get("cityId") != null) {
                address.append(result.get("city")).append("市");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return address.toString();
        }
    }

    public static void main(String[] args) {
        String ip = "10.8.4.98";
        String address = getAddress(ip);
        System.out.println(address);
    }

}
