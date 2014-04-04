package com.lichsword.codeoffamilywang.app.ftp;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by wangyue.wy on 14-3-7.
 */
public class HttpServer {

    private final String LOCAL_HOST = "127.0.0.1";

    private final int DEFAULT_PORT = 8080;

    public HttpServer(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpHost host = new HttpHost(LOCAL_HOST, DEFAULT_PORT);
        HttpRequest request = new MarxRequest();
        request.
        try {
            httpclient.execute(host, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
