package com.example.cookietest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetVideoListActivity extends Activity implements OnClickListener{
	private final String TAG = "GetVideoListActivity";
    //请求获取视频列表
	Button btnGetList;
	
	TextView tvList; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_list);
		btnGetList = (Button)findViewById(R.id.btnGetList);
		btnGetList.setOnClickListener(this);
		
		tvList = (TextView)findViewById(R.id.tvList);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		/*new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(URLContainer.getPlayHistoryInCloud());
				httpGet.setHeader("Cookie", Util.getPreference("cookie"));
				HttpResponse httpResponse;
				try {
					httpResponse = httpClient.execute(httpGet);
					InputStream is = httpResponse.getEntity().getContent();
					String jsonString = Util.convertStreamToString(is);
					return jsonString;
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
			}
			
			protected void onPostExecute(String result) {
				tvList.setText(result);
			};
			
		}.execute();*/
		String url = URLContainer.getPlayHistoryInCloud();
		final AsyncHttpClient client = new AsyncHttpClient();
		if (Util.getCookies() != null) {
			Log.d(TAG, "Util.getCookies() 不是空的 ");
			BasicCookieStore bcs = new BasicCookieStore();
			bcs.addCookies(Util.getCookies().toArray(
					new Cookie[Util.getCookies().size()]));
			client.setCookieStore(bcs);
		}
		
		client.get(url, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				Log.e(TAG, "获取数据异常 ", e);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
				String json = new String(response);
				Log.d(TAG, "onSuccess json = " + json);
				tvList.setText(json);
			}
			
		});
		
	}
	
}
