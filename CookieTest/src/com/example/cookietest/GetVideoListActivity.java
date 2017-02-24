package com.example.cookietest;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GetVideoListActivity extends Activity implements OnClickListener{
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
		new AsyncTask<Void, Void, String>() {

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
			
		}.execute();
		
	}
	
}
