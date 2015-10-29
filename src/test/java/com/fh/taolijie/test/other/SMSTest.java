package com.fh.taolijie.test.other;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by whf on 9/25/15.
 */
public class SMSTest {
    @Test
    public void sendSMS() throws IOException {
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpPost post = new HttpPost("http://api.weimi.cc/2/sms/send.html");

        List<NameValuePair> parmList = new ArrayList<>();
        parmList.add(new BasicNameValuePair("uid", "Ctpc2fjQKMq3"));
        parmList.add(new BasicNameValuePair("pas", "7yaz2zeb"));
        parmList.add(new BasicNameValuePair("mob", "18369905318,13287096323,15653325871,18369905893,15689093197"));
        parmList.add(new BasicNameValuePair("cid", "vJWD1eXBm0AM"));
        parmList.add(new BasicNameValuePair("p1", "wanghongfei"));
        parmList.add(new BasicNameValuePair("type", "json"));

        post.setEntity(new UrlEncodedFormEntity(parmList, HTTP.UTF_8));

        HttpResponse resp = client.execute(post);
        InputStream in = resp.getEntity().getContent();

        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inReader);

        String line = null;
        while ( (line = reader.readLine()) != null ) {
            System.out.println(line);
        }


    }

    @Test
    public void testAlipay() throws Exception{
        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();
        HttpPost post = new HttpPost("https://mapi.alipay.com/gateway.do");

        List<NameValuePair> parmList = new ArrayList<>();
        parmList.add(new BasicNameValuePair("payment_type", "1"));
        parmList.add(new BasicNameValuePair("subject", "test"));
        parmList.add(new BasicNameValuePair("total_fee", "1.00"));
        parmList.add(new BasicNameValuePair("body", "test"));
        parmList.add(new BasicNameValuePair("service", "mobile.securitypay.pay"));
        parmList.add(new BasicNameValuePair("partner", "2088021861615600"));
        parmList.add(new BasicNameValuePair("_input_charset", "utf-8"));
        parmList.add(new BasicNameValuePair("sign_type", "RSA"));
        parmList.add(new BasicNameValuePair("sign", "gZwmb+gCMF7ejlTvOYMjAY3yJL3wR34I977vurffGKAm9BL3VNgzpjyq410qsVMcPTYeNdJ8HCCY1IidtwvK8pxl79rT3tQjInBmYm7d5rtHsAI7Tsv4FdDyXUSt0kC7SxgGYLgI2b+QW2FuuydLMP98/FisZGWsI/TO9+1VgIo="));
        parmList.add(new BasicNameValuePair("notify_url", "http://120.24.218.56"));
        parmList.add(new BasicNameValuePair("seller_id", "taolijie@vip.sina.com"));
        parmList.add(new BasicNameValuePair("out_trade_no", "44"));

        post.setEntity(new UrlEncodedFormEntity(parmList, HTTP.UTF_8));

        HttpResponse resp = client.execute(post);
        InputStream in = resp.getEntity().getContent();

        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inReader);

        String line = null;
        while ( (line = reader.readLine()) != null ) {
            System.out.println(line);
        }

    }
}
