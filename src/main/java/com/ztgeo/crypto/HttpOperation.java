package com.ztgeo.crypto;

import com.ztgeo.common.ZtgeoBizRuntimeException;
import com.ztgeo.msg.CodeMsg;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

/**
 * 处理http请求操作
 *
 * @author zoupeidong
 * @version 2018-12-13
 */
public class HttpOperation {


    /**
     * 发送http请求
     * get方式
     *
     * @param url 待发送数据
     * @return 返回的数据
     */
    public static String sendGetHttp(String url) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder().get().url(url).build();//请求的url
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZtgeoBizRuntimeException(CodeMsg.SDK_INTER_ERROR);
        }
    }

    /**
     * 发送http请求
     * content-type:application/json
     *
     * @param url  请求url
     * @param data 待发送数据
     * @return 返回的数据
     */
    public static String sendJsonHttp(String url, String data) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                    , data);
            Request request = new Request.Builder()
                    .url(url)//请求的url
                    .post(requestBody)
                    .build();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZtgeoBizRuntimeException(CodeMsg.SDK_INTER_ERROR);
        }
    }

}
