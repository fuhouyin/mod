package utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RestTemplateUtils {

    private Logger logger = LoggerFactory.getLogger(RestTemplateUtils.class);

    private static RestTemplate restTemplate = new RestTemplate();

    /**bodyEntity*/
    private HttpEntity httpEntity;

    /**header*/
    private HttpHeaders httpHeaders;

    /**contentType*/
    private String contentType;

    /**requestParams*/
    private HashMap<String, Object> requestParams;

    /**body Params*/
    private HashMap<String, Object> bodyParams;

    private RestTemplateUtils() {}

    /**
     * 构造函数已经私有化，只能通过create方法构造HttpContent
     * @return HttpContent
     */
    public static RestTemplateUtils create() {
        return new RestTemplateUtils();
    }

    /**
     * get请求返回模板对象
     */
    public <T> T getForObj(String url, Class<T> clazz) {
        Long startTime = System.currentTimeMillis();
        T t = RestTemplateUtils.restTemplate.getForObject(this.buildGetUrl(url), clazz);
        logger.info("耗时: {} ms", System.currentTimeMillis() - startTime);
        return t;
    }

    /**
     * 构建url
     */
    private String buildGetUrl(String url) {
        StringBuffer builder = new StringBuffer();
        builder.append(url);
        if (!CollectionUtils.isEmpty(this.requestParams)) {
            if (!url.endsWith("?")) {
                builder.append("?");
            }
            for (String key : this.requestParams.keySet()) {
                if (builder.toString().endsWith("?")) {
                    builder.append(key).append("=").append(this.requestParams.get(key).toString());
                } else {
                    builder.append("&").append(key).append("=").append(this.requestParams.get(key).toString());
                }
            }
        }
        logger.info("https get url: {}", builder.toString());
        return builder.toString();
    }

    /**
     * post请求返回模板对象
     */
    public <T> T postForObj(String url, Class<T> clazz) {
        this.buildBody();
        logger.info("https post url: {}", url);
        Long startTime = System.currentTimeMillis();
        T t = restTemplate.postForObject(url, this.httpEntity, clazz);
        logger.info("耗时: {} ms", System.currentTimeMillis() - startTime);
        return t;
    }


    /**
     * 添加Header内容
     */
    public RestTemplateUtils addHeader(String headerName, String headerValue) {
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.add(headerName, headerValue);
        return this;
    }

    /**
     * 设置请求体
     */
    public RestTemplateUtils addRequestParams(String key, Object value) {
        if (Objects.isNull(requestParams)) {
            requestParams = new HashMap<>();
        }
        requestParams.put(key, value);
        return this;
    }

    /**
     * 设置请求体
     */
    public RestTemplateUtils addRequestParamsMap(Map<String, Object> paramsMap) {
        if (Objects.isNull(requestParams)) {
            requestParams = new HashMap<>();
        }
        for (String key : paramsMap.keySet()) {
            requestParams.put(key, paramsMap.get(key));
        }
        return this;
    }

    /**
     * 设置请求体
     */
    public RestTemplateUtils addBody(String key, Object value) {
        if (Objects.isNull(bodyParams)) {
            bodyParams = new HashMap<>();
        }
        bodyParams.put(key, value);
        return this;
    }

    /**
     * 设置请求体
     */
    public RestTemplateUtils addBodyMap(Map<String, Object> paramsMap) {
        if (Objects.isNull(bodyParams)) {
            bodyParams = new HashMap<>();
        }
        for (String key : paramsMap.keySet()) {
            bodyParams.put(key, paramsMap.get(key));
        }
        return this;
    }

    /**
     * 设置content-Type
     */
    public RestTemplateUtils setContentType(String contentType) {
        if (StringUtils.isEmpty(contentType)) {
            throw new RuntimeException("content-Type can not be empty,please check your mediaType and try again ~");
        }
        this.contentType = contentType;
        if (Objects.isNull(httpHeaders)) {
            httpHeaders = new HttpHeaders();
        }
        httpHeaders.setContentType(MediaType.parseMediaType(contentType));
        return this;
    }

    /**
     * 构建HttpEntity对象
     * 不设置Content-Type默认application/json
     */
    private void buildBody() {
        if (Objects.isNull(this.bodyParams)) {
            throw new RuntimeException("body must be not empty,please add body and try again ~");
        }
        if (Objects.isNull(contentType) || ContentType.APPLICATION_JSON.getMimeType().equals(contentType)) {
            this.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            JSONObject jsonObject = new JSONObject();
            for (String s : bodyParams.keySet()) {
                try {
                    jsonObject.put(s, bodyParams.get(s));
                } catch (JSONException e) {
                    logger.error("error:", e);
                }
            }
            String body = jsonObject.toString();
            this.httpEntity = new HttpEntity<>(body, httpHeaders);
        } else if (ContentType.APPLICATION_FORM_URLENCODED.getMimeType().equals(contentType)) {
            MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            for (String s : bodyParams.keySet()) {
                formData.add(s, bodyParams.get(s));
            }
            this.httpEntity = new HttpEntity<>(formData, httpHeaders);
        } else {
            throw new RuntimeException("sorry this tool only support content-Type include {application/json,multipart/form-data}");
        }
    }

    public static void main(String[] args) {
        JSONObject json = RestTemplateUtils
                .create() //创建一个实例
                .setContentType("xxx") //设置Content-Type,不设置默认为：application/json,多次设置，以最后一次为准
                .addHeader("headerKey","headerVal") //添加header，可以多次添加
                .addBody("bodyKey","bodyVal") //添加body，可以多次添加
                .postForObj("url",JSONObject.class); //发送post请求
    }
}
