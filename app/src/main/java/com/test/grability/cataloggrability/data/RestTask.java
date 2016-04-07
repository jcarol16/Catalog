package com.test.grability.cataloggrability.data;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

public class RestTask extends AsyncTask<HttpUriRequest, Void, Boolean> {

    private static final String TAG = "RestTask";
    private int statusCode = 404;
    private String result = "";

    public interface OnTaskCompleteListener {
        void OnTaskComplete(int statusCode, String result);
    }

    private OnTaskCompleteListener mOnTaskCompleteListener = null;
    private HttpClient mClient;

    public RestTask(OnTaskCompleteListener mOnTaskCompleteListener) {
        this.mOnTaskCompleteListener = mOnTaskCompleteListener;
        this.mClient = RestUtils.getHttpClient();
    }

    @Override
    protected Boolean doInBackground(HttpUriRequest... params) {
        try {
            HttpUriRequest mHttpUriRequest = params[0];
            HttpResponse mHttpResponse = mClient.execute(mHttpUriRequest);
            HttpEntity mHttpEntity = mHttpResponse.getEntity();
            statusCode = mHttpResponse.getStatusLine().getStatusCode();
            result = EntityUtils.toString(mHttpEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean complete) {
        Log.i(TAG, "STATUS: " + statusCode);
        Log.i(TAG, "RESULT = " + result);
        
        if (mOnTaskCompleteListener != null) {
            mOnTaskCompleteListener.OnTaskComplete(statusCode, result);
        }
    }
}