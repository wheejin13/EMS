package com.haemilsoft.ems.network.asynctask;

import android.os.AsyncTask;

import com.haemilsoft.ems.network.NetworkUtil;
import com.haemilsoft.ems.utils.LOG;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpAsyncTask extends AsyncTask<String, String, ResponseData> {

    private HttpRequestListener mHttpRequestListener = null;
    private String mMethodType;
    private JSONObject mHeader;
    private JSONObject mBody;

    public HttpAsyncTask(String methodType, JSONObject header, JSONObject body, HttpRequestListener httpRequestListener){
        mMethodType = methodType;
        mHeader = header;
        mBody = body;
        mHttpRequestListener = httpRequestListener;
    }

    @Override
    protected ResponseData doInBackground(String... address) {
        ResponseData responseData = null;
        HttpURLConnection connection = null;

        if(address != null && address.length > 0)
            connection = getConnection(address[0]);

        if(connection == null)
            return new ResponseData(ResultCode.CONNECTION_FAILED);

        InputStream is = null;
        ByteArrayOutputStream baos = null;

        try {
            LOG.i("URL : " + connection.getURL());

            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = connection.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[1024];

                int nLength;
                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }

                responseData = new ResponseData(new String(baos.toByteArray()));
            }
            else
                LOG.e("Response code(Error) : " + connection.getResponseCode());

        } catch (IOException e) {
            LOG.e(e.getMessage());
            e.printStackTrace();
            return new ResponseData(ResultCode.FAIL);
        } finally {
            try {
                if (baos != null)   baos.close();
                if (is != null)     is.close();
                if (connection != null) connection.disconnect();
            } catch (Exception e) {
                LOG.e(e.getMessage());
                e.printStackTrace();
            }
        }

        return responseData;
    }

    @Override
    protected void onPostExecute(ResponseData responseData) {
        if(mHttpRequestListener != null)
            mHttpRequestListener.httpResponse(responseData);
        mHttpRequestListener = null;
        mMethodType = null;
        mHeader = null;
        mBody = null;
    }


    //connection 객체 생성하기
    private HttpURLConnection getConnection(String address){
        if(address == null)
            return null;
        else if(address.length() <= 5)
            return null;

        HttpURLConnection connection;

        try {
            URL url = new URL(address);

            //SSL 사용
            if (address.substring(0, 5).equalsIgnoreCase("https")) {
                trustAllHosts();

                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });
                connection = httpsURLConnection;
            } else //Non-SSL
                connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(mMethodType);
            connection.setDoInput(true);
            connection.setReadTimeout(NetworkUtil.HTTP_READ_TIMEOUT);
            connection.setConnectTimeout(NetworkUtil.HTTP_CONNECTION_TIMEOUT);

            //헤더 영역 처리
            if (mHeader != null) {
                Iterator headers = mHeader.keys();

                while (headers.hasNext()) {
                    String key = (String) headers.next();
                    connection.setRequestProperty(key, mHeader.getString(key));
                }
            }

            //바디 영역 처리
            OutputStream os = null;

            if (mBody != null) {
                connection.setDoOutput(true);
                os = connection.getOutputStream();
                os.write(mBody.toString().getBytes());
                os.flush();
            } else
                connection.setDoOutput(false);

            try{
                if(os != null)
                    os.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }catch(IOException e){
            LOG.e("e : " + e.getMessage());
            e.printStackTrace();
            return null;
        }catch(Exception e) {
            LOG.e(e.getMessage());
            e.printStackTrace();
            return null;
        }

        return connection;
    }

    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }

            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {

            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType)
                    throws java.security.cert.CertificateException {

            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
