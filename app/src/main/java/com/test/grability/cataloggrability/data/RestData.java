package com.test.grability.cataloggrability.data;

import android.util.Log;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RestData {

    public interface OnRequestCompleteListener {
        void OnRequestComplete(int statusCode, String result);
    }

    private static final String TAG = "RestData";

    private static HashMap<String, String> staticHeaders = new HashMap<>();
    private boolean useStaticHeaders = true;

    private RestMethod mRequestMethod = RestMethod.GET;
    private OnRequestCompleteListener mOnRequestCompleteListener = null;
    private String url = "";
    private HashMap<String, String> params = new HashMap<>();
    private HashMap<String, String> paramsUrl = new HashMap<>();
    private HashMap<String, String> headers = new HashMap<>();

    private boolean inProcess = false;
    private boolean isCancel = false;

    private RestTask mRestTask;

    public static void setStaticHeaders(HashMap<String, String> headers) {
        staticHeaders = headers;
    }

    public void setUseStaticHeaders(boolean useStaticHeaders) {
        this.useStaticHeaders = useStaticHeaders;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public void request(RestMethod mRequestMethod, String url, HashMap<String, String> params, OnRequestCompleteListener onRequestCompleteListener) {
        this.mRequestMethod = mRequestMethod;
        this.url = url;
        this.params = params;
        this.mOnRequestCompleteListener = onRequestCompleteListener;
        request();
    }

    public void request(RestMethod mRequestMethod, String url, HashMap<String, String> params, HashMap<String, String> paramsUrl, OnRequestCompleteListener onRequestCompleteListener) {
        this.mRequestMethod = mRequestMethod;
        this.url = url;
        this.params = params;
        this.paramsUrl = paramsUrl;
        this.mOnRequestCompleteListener = onRequestCompleteListener;
        request();
    }

    private void request() {
        inProcess = true;

        mRestTask = new RestTask(new RestTask.OnTaskCompleteListener() {
            @Override
            public void OnTaskComplete(int statusCode, String result) {
                inProcess = false;
                if (mOnRequestCompleteListener != null && !isCancel) {
                    mOnRequestCompleteListener.OnRequestComplete(statusCode, result);
                }
            }
        });

        if(useStaticHeaders) {
            headers.putAll(staticHeaders);
        }
        setUpParamsUrl();

        switch (mRequestMethod) {
            case GET:
                get();
                break;
            case POST:
                post();
                break;
        }
    }

    private void get() {
        try {
            Log.i(TAG, "REQUEST URL: " + url);
            HttpGet mHttpGet = new HttpGet(url);
            setUpHeaders(mHttpGet);

            mRestTask.execute(mHttpGet);
        } catch (Exception e) {
            e.printStackTrace();

            inProcess = false;
            if (mOnRequestCompleteListener != null) {
                mOnRequestCompleteListener.OnRequestComplete(404, "");
            }
        }
    }

    private void post() {
        try {
            Log.i(TAG, "REQUEST URL: " + url);
            HttpPost mHttpPost = new HttpPost(url);

            List<NameValuePair> paramsRequest = new ArrayList<>();
            if(params != null && params.size() > 0) {
                Log.i(TAG, "REQUEST PARAMS: " + params.toString());
                for (String key : params.keySet()) {
                    paramsRequest.add(new BasicNameValuePair(key, params.get(key)));
                }
            }

            setUpHeaders(mHttpPost);

            MultipartEntityBuilder mMultipartEntityBuilder = MultipartEntityBuilder.create();
            mMultipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            for (int i = 0; i < paramsRequest.size(); i++) {
                if (paramsRequest.get(i).getName().equalsIgnoreCase("file1") ||
                        paramsRequest.get(i).getName().equalsIgnoreCase("file2") ||
                        paramsRequest.get(i).getName().equalsIgnoreCase("file3") ||
                        paramsRequest.get(i).getName().equalsIgnoreCase("file4")) {

                    mMultipartEntityBuilder.addPart(paramsRequest.get(i).getName(),
                            new FileBody(new File(paramsRequest.get(i).getValue())));
                } else {
                    mMultipartEntityBuilder.addPart(paramsRequest.get(i).getName(),
                            new StringBody(paramsRequest.get(i).getValue(), Consts.UTF_8));
                }
            }

            mHttpPost.setEntity(mMultipartEntityBuilder.build());
            mRestTask.execute(mHttpPost);

        } catch (Exception e) {
            e.printStackTrace();

            inProcess = false;
            if (mOnRequestCompleteListener != null) {
                mOnRequestCompleteListener.OnRequestComplete(404, "");
            }
        }
    }

    private void setUpParamsUrl() {
        if(mRequestMethod.equals(RestMethod.GET)) {
            paramsUrl = params;
        }

        if (paramsUrl != null && paramsUrl.size() > 0) {
            for (String key : paramsUrl.keySet()) {
                if(!url.contains("?")) {
                    url += "?";
                } else {
                    url += "&";
                }
                url += key + "=" + paramsUrl.get(key);
            }
        }
    }

    private void setUpHeaders(HttpGet mHttpGet) {
        if(headers.size() > 0) {
            Log.i(TAG, "REQUEST HEADERS: " + headers.toString());
            for (String key : headers.keySet()) {
                mHttpGet.addHeader(key, headers.get(key));
            }
        }
    }

    private void setUpHeaders(HttpPost mHttpPost) {
        if(headers.size() > 0) {
            Log.i(TAG, "REQUEST HEADERS: " + headers.toString());
            for (String key : headers.keySet()) {
                mHttpPost.addHeader(key, headers.get(key));
            }
        }
    }

    public boolean isInProcess() {
        return inProcess;
    }

    public void cancel() {
        this.isCancel = true;
    }
}
