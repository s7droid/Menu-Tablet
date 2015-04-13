package com.s7design.menutablet.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s7design.menutablet.R;
import com.s7design.menutablet.utils.Utils;

public class CommentsDialog extends Dialog {

	private String message;

	public CommentsDialog(Context context, int xPosition, int yPosition,
			LinearLayout layout, String message) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_comments);

		LayoutParams params = getWindow().getAttributes();
		params.x = xPosition;
		
		int screenHeight = Utils.getScreenHeight(context) - Utils.getStatusBarHeight(context);
		int screenWidth = Utils.getScreenWidth(context);
		
		Log.e("TAG", "Screen height= " + screenHeight);
		
		if(yPosition < (int)screenHeight/2){
			params.y = - (int) yPosition/2;
		}else{
			params.y = (int) yPosition;
		}
		params.gravity = Gravity.LEFT;
		params.width = (int) Utils.convertDpToPixel(280, context);

		Log.e("Some TAG", "Dialog width= " + layout.getWidth());

		Log.i("TAG", "X position = " + xPosition);
		Log.i("TAG", "Y position= " + yPosition);

		getWindow().setAttributes(
				(android.view.WindowManager.LayoutParams) params);

		this.message = message;

		Log.i("TAG", "Window X= " + getWindow().getAttributes().x);
		Log.i("TAG", "Window Y= " + getWindow().getAttributes().y);
		
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		setCanceledOnTouchOutside(true);
		setCancelable(true);

		initViews();

		show();

	}

	private void initViews() {

		TextView content = (TextView) findViewById(R.id.textViewComment);
		Button dismiss = (Button) findViewById(R.id.buttonDismissDialog);

		content.setText("Mesaage very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very very long");

		dismiss.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

	}

}
