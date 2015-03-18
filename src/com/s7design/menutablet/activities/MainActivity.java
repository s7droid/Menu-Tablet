package com.s7design.menutablet.activities;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.s7design.menutablet.R;
import com.s7design.menutablet.utils.Settings;
import com.s7design.menutablet.volley.VolleySingleton;
import com.s7design.menutablet.volley.requests.LoginRequest;
import com.s7design.menutablet.volley.responses.LoginResponse;

public class MainActivity extends BaseActivity {

	private static final String TAG = MainActivity.class.getSimpleName();

	private Button buttonSignIn;
	private EditText editTextEmail;
	private EditText editTextPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
		editTextEmail = (EditText) findViewById(R.id.editTextEmail);
		editTextPassword = (EditText) findViewById(R.id.editTextPassword);

		if (Settings.getAccessToken(this).length() > 0) {
			startActivity(new Intent(MainActivity.this, OrderActivity.class));
			finish();
		}

		buttonSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showProgressDialogLoading();

				Map<String, String> params = new HashMap<String, String>();
				params.put("username", editTextEmail.getText().toString());
				params.put("password", editTextPassword.getText().toString());

				LoginRequest loginRequest = new LoginRequest(MainActivity.this, params, new Listener<LoginResponse>() {

					@Override
					public void onResponse(LoginResponse loginResponse) {

						Log.e(TAG, "response");

						if (loginResponse.response != null && loginResponse.response.equals("success")) {

							Settings.setAccessToken(MainActivity.this, loginResponse.accesstoken);

							startActivity(new Intent(MainActivity.this, OrderActivity.class));
							finish();
						} else {

						}

						dismissProgressDialog();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

						dismissProgressDialog();
						showAlertDialog(R.string.dialog_title_error, R.string.dialog_body_network_problem);
					}
				});

				VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(loginRequest);

			}
		});
	}

}
