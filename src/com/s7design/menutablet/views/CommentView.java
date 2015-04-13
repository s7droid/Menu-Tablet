package com.s7design.menutablet.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.s7design.menutablet.R;

public class CommentView extends RelativeLayout {

	private Context context;
	private LinearLayout linearLayoutContainer;
	private TextView textViewContent;
	private Button buttonDismiss;
	private boolean isDialogVisible;

	public CommentView(Context context) {
		super(context);
		this.context = context;
		initViews();
	}

	public CommentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initViews();
	}

	public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews();
	}

	private void initViews() {
		View convertView = inflate(getContext(), R.layout.dialog_comments, this);

		linearLayoutContainer = (LinearLayout) convertView.findViewById(R.id.linearLayoutCommentsContainer);
		textViewContent = (TextView) convertView.findViewById(R.id.textViewComment);
		buttonDismiss = (Button) convertView.findViewById(R.id.buttonDismissDialog);

		buttonDismiss.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				linearLayoutContainer.setVisibility(View.GONE);
			}
		});

	}

	public void showDialog() {
		linearLayoutContainer.setVisibility(View.VISIBLE);
	}

}
