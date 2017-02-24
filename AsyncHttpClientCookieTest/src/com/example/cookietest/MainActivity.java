package com.example.cookietest;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookietest.LoginManager.ICallBack;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
//参考文章：http://blog.csdn.net/jdsjlzx/article/details/44632451

public class MainActivity extends Activity implements OnClickListener {
	private final String TAG = "MainActivity";
	//用户名
	EditText etUserName;
	// 密码
	EditText etPwd;
	// 登录按钮
	Button btnLogin;
	// 取消按钮
	Button btnCancel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		App app = (App) this.getApplication();
		app.mContext = getApplicationContext();
		
	}

	private void init() {
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPwd = (EditText) findViewById(R.id.etPwd);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnLogin.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btnLogin:
			/*new LoginManager().login(etUserName.getText().toString(), etPwd.getText().toString(), new ICallBack() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Toast.makeText(App.mContext, "登录成功，cookie=" + Util.getPreference("cookie"), Toast.LENGTH_SHORT).show();
					startActivity(new Intent(MainActivity.this, GetVideoListActivity.class));
				}
				
				@Override
				public void onFailed(String error) {
					// TODO Auto-generated method stub
					Toast.makeText(App.mContext, error, Toast.LENGTH_SHORT).show();
				}
			});*/
			login(etUserName.getText().toString(), etPwd.getText().toString());
			break;

		case R.id.btnCancel:
			this.finish();
			break;
		default:
			break;
		}
	}
	
	private void login(String username, String password){
		String url = URLContainer.getLoginUrl(
				username, password);
		final AsyncHttpClient client = new AsyncHttpClient();
		
		//保存cookie，自动保存到了shareprefercece
		PersistentCookieStore myCookieStore = new PersistentCookieStore(MainActivity.this);  
        client.setCookieStore(myCookieStore);
        
		/*RequestParams params = new RequestParams();
		params.put("username", "15001066722");
		params.put("pwd", "123456");*/
        /*CookieStore cookies = (CookieStore) client.getHttpContext().getAttribute(ClientContext.COOKIE_STORE);//获取AsyncHttpClient中的CookieStore  
        if(cookies!=null){  
            for(Cookie c:cookies.getCookies()){  
            	Log.d(TAG,"main before ~~"+c.getName() +"  "+c.getValue());  
            }  
        }else{  
        	Log.d(TAG, "main  before~~   cookies is null");  
        }*/  
        /*CookieStore newcookies = (CookieStore) client.getHttpContext().getAttribute(ClientContext.COOKIE_STORE);  
        if(newcookies!=null){  
            for(Cookie c:newcookies.getCookies()){  
                Log.d(TAG, "main after ~~"+c.getName()+" -- "+c.getValue());  
            }  
        }else{  
            Log.d(TAG, "main  after~~ cookies is null");  
        }*/
		client.post(url, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] errorResponse, Throwable e) {
				Log.e(TAG, "获取数据异常 ", e);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] response) {
				String json = new String(response);
				Log.d(TAG, "onSuccess json = " + json);
				
				/*PersistentCookieStore myCookieStore = new PersistentCookieStore(MainActivity.this);  
		        client.setCookieStore(myCookieStore);*/  
		        /*CookieStore newcookies = (CookieStore) client.getHttpContext().getAttribute(ClientContext.COOKIE_STORE);  
		        if(newcookies!=null){  
		            for(Cookie c:newcookies.getCookies()){  
		                Log.d(TAG, "main after ~~"+c.getName()+" -- "+c.getValue());  
		            }  
		        }else{  
		            Log.d(TAG, "main  after~~ cookies is null");  
		        }*/
		        
		        /*List<Cookie> cookies = myCookieStore.getCookies();
		        if(cookies!=null){  
		            for(Cookie c:cookies){  
		                Log.d(TAG, "222 main after ~~"+c.getName()+" -- "+c.getValue());  
		            }  
		        }else{  
		            Log.d(TAG, "222 main  after~~ cookies is null");  
		        }*/
				//测试获得已经保存的cookie
				Toast.makeText(App.mContext, "登录成功，cookie=" + getCookieText(), Toast.LENGTH_SHORT).show();
				startActivity(new Intent(MainActivity.this, GetVideoListActivity.class));
			}
			
		});
		
	}
	
	
	/**
	 * 获取标准 Cookie  
	 */
	private String getCookieText() {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(MainActivity.this);
		List<Cookie> cookies = myCookieStore.getCookies();
		Log.d(TAG, "cookies.size() = " + cookies.size());
		Util.setCookies(cookies);
		for (Cookie cookie : cookies) {
			Log.d(TAG, cookie.getName() + " = " + cookie.getValue());
		}
		 StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cookies.size(); i++) {
			 Cookie cookie = cookies.get(i);
			 String cookieName = cookie.getName();
			 String cookieValue = cookie.getValue();
			if (!TextUtils.isEmpty(cookieName)
					&& !TextUtils.isEmpty(cookieValue)) {
				sb.append(cookieName + "=");
				sb.append(cookieValue + ";");
			}
		}
		Log.e("cookie", sb.toString());
		return sb.toString();
	}
}
