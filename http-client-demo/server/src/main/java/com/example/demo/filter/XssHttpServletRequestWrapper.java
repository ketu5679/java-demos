package com.example.demo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Author: zjh
 * @Date: 2020/12/23 18:48
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    Logger logger = LoggerFactory.getLogger(XssHttpServletRequestWrapper.class);

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        return super.getHeader(name);
    }

    @Override
    public String getQueryString() {
        String queryString = super.getQueryString();
        logger.info("getQueryString:{}", queryString);
        return queryString;
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        logger.info("getParameter:name={}, val={}",name,  parameter);
        return parameter;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if(values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for(int i = 0; i < length; i++){
                escapseValues[i] = values[i].replace('1','2');
                logger.info("getParameterValues name={}, value={}", name, values[i].replace("1","2"));
            }
            return escapseValues;
        }
        return values;
    }

//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        String str=getRequestBody(super.getInputStream());
//        Map<String,Object> map= JSON.parseObject(str,Map.class);
//        Map<String,Object> resultMap=new HashMap<>(map.size());
//        for(String key:map.keySet()){
//            Object val=map.get(key);
//            if(map.get(key) instanceof String){
//                resultMap.put(key,StringEscapeUtils.escapeHtml4(val.toString()));
//            }else{
//                resultMap.put(key,val);
//            }
//        }
//        str=JSON.toJSONString(resultMap);
//        final ByteArrayInputStream bais = new ByteArrayInputStream(str.getBytes());
//        return new ServletInputStream() {
//            @Override
//            public int read() throws IOException {
//                return bais.read();
//            }
//            @Override
//            public boolean isFinished() {
//                return false;
//            }
//            @Override
//            public boolean isReady() {
//                return false;
//            }
//            @Override
//            public void setReadListener(ReadListener listener) {
//            }
//        };
//    }

    private String getRequestBody(ServletInputStream stream) {
        String line = "";
        StringBuilder body = new StringBuilder();
        int counter = 0;

        // 读取POST提交的数据内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        try {
            while ((line = reader.readLine()) != null) {
                body.append(line);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body.toString();
    }
}
