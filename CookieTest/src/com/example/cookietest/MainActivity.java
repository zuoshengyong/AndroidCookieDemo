package com.example.cookietest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cookietest.LoginManager.ICallBack;

public class MainActivity extends Activity implements OnClickListener {
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
			new LoginManager().login(etUserName.getText().toString(), etPwd.getText().toString(), new ICallBack() {
				
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
			});
			break;

		case R.id.btnCancel:
			this.finish();
			break;
		default:
			break;
		}
	}
}
