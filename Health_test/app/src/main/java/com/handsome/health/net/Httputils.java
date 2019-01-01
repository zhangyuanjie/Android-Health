package com.handsome.health.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;


/**
 * Created by zyj on 2018/12/26.
 */
public class Httputils {
    private final static String HOST = "http://119.29.60.170/";
    //private final static String HOST = "http://192.168.1.101:8080/health/";

    public static String doPost(String method , HashMap<String,Object> params){
        try {
            URL url = new URL(HOST+method);
            //�������ӣ���ȡ���Ӷ���
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //��������ʽ
            conn.setRequestMethod("POST");
            //�������ӳ�ʱ
            conn.setConnectTimeout(5000);
            //���ö�ȡ��ʱ
            conn.setReadTimeout(5000);

            if(params.entrySet().size()>0){
                //�в���
                //���������
                conn.setDoOutput(true);
                //��ȡ���������
                OutputStream os = conn.getOutputStream();
                //ƴ�����ݸ�ʽ
                String reqData = param(params);
                //д������
                os.write(reqData.getBytes("utf-8"));
            }

            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                //��Ӧ�ɹ�
                //��ȡ������
                InputStream is = conn.getInputStream();
                //---��ȡ������---
                int len;//���ڽ���ÿ�ζ�ȡ�ĳ���
                byte[] b = new byte[1024];//ÿ�ζ�ȡ���ֽ���
                StringBuffer sb = new StringBuffer();
                //ѭ����ȡ���ݣ���ʱ�洢��byte�����У���ȡ������Ϊ-1Ϊֹ������ȡ����
                while ((len = is.read(b))!= -1){
                    sb.append(new String(b,0,len,"utf-8"));
                }
                //�ر�������
                is.close();
                return sb.toString();
            }else {
                //��Ӧʧ��--
                return "����url";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "�쳣";
        }
    }

    private static String param(HashMap<String,Object> params){
        StringBuffer sb = new StringBuffer();
        for (HashMap.Entry entry:params.entrySet()){
            sb.append(("&"+entry.getKey()+"="+entry.getValue()));
        }

        if(sb.length()>0){
            //ɾ����һ���ַ����±�Ϊ0���ַ���
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

}
