package com.s7design.menutablet.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.s7design.menutablet.R;

public class MainActivity extends Activity {

	private Button buttonSignIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonSignIn = (Button) findViewById(R.id.buttonSignIn);

		buttonSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivity(new Intent(MainActivity.this, OrderActivity.class));
			}
		});
	}

}
