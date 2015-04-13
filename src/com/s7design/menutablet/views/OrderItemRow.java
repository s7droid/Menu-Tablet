package com.s7design.menutablet.views;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.Item;
import com.s7design.menutablet.volley.VolleySingleton;

public class OrderItemRow extends LinearLayout {

	private static final String TAG = OrderItemRow.class.getSimpleName();

	private RelativeLayout relativeLayoutCommentContainer1;
	private RelativeLayout relativeLayoutCommentContainer2;
	private RelativeLayout relativeLayoutCommentContainer3;
	private TextView textViewComment1;
	private TextView textViewComment2;
	private TextView textViewComment3;
	private TextView buttonDismissDialog1;
	private TextView buttonDismissDialog2;
	private TextView buttonDismissDialog3;
	private NetworkImageView imageView1;
	private NetworkImageView imageView2;
	private NetworkImageView imageView3;
	private ImageButton imageButtonItem1;
	private ImageButton imageButtonItem2;
	private ImageButton imageButtonItem3;
	private TextView textViewTitle1;
	private TextView textViewTitle2;
	private TextView textViewTitle3;
	private TextView textViewQuantity1;
	private TextView textViewQuantity2;
	private TextView textViewQuantity3;
	private View viewDivider;

	private ImageLoader imageLoader;

	private Context context;

	public OrderItemRow(Context context) {
		super(context);

		this.context = context;

		init();
	}

	private void init() {

		inflate(getContext(), R.layout.row_item_order, this);

		relativeLayoutCommentContainer1 = (RelativeLayout) findViewById(R.id.linearLayoutCommentsContainer1);
		relativeLayoutCommentContainer2 = (RelativeLayout) findViewById(R.id.linearLayoutCommentsContainer2);
		relativeLayoutCommentContainer3 = (RelativeLayout) findViewById(R.id.linearLayoutCommentsContainer3);
		textViewComment1 = (TextView) findViewById(R.id.textViewComment1);
		textViewComment2 = (TextView) findViewById(R.id.textViewComment2);
		textViewComment3 = (TextView) findViewById(R.id.textViewComment3);
		buttonDismissDialog1 = (TextView) findViewById(R.id.buttonDismissDialog1);
		buttonDismissDialog2 = (TextView) findViewById(R.id.buttonDismissDialog2);
		buttonDismissDialog3 = (TextView) findViewById(R.id.buttonDismissDialog3);

		imageView1 = (NetworkImageView) findViewById(R.id.imageView1);
		imageView2 = (NetworkImageView) findViewById(R.id.imageView2);
		imageView3 = (NetworkImageView) findViewById(R.id.imageView3);
		imageButtonItem1 = (ImageButton) findViewById(R.id.imagebuttonItem1);
		imageButtonItem2 = (ImageButton) findViewById(R.id.imagebuttonItem2);
		imageButtonItem3 = (ImageButton) findViewById(R.id.imagebuttonItem3);
		textViewTitle1 = (TextView) findViewById(R.id.textViewTitle1);
		textViewTitle2 = (TextView) findViewById(R.id.textViewTitle2);
		textViewTitle3 = (TextView) findViewById(R.id.textViewTitle3);
		textViewQuantity1 = (TextView) findViewById(R.id.textViewQuantity1);
		textViewQuantity2 = (TextView) findViewById(R.id.textViewQuantity2);
		textViewQuantity3 = (TextView) findViewById(R.id.textViewQuantity3);

		viewDivider = (View) findViewById(R.id.viewDivider);

		imageLoader = VolleySingleton.getInstance(getContext())
				.getImageLoader();
		
	}

	public void showDivider() {
		viewDivider.setVisibility(View.VISIBLE);
	}

	public void setData(Item item1, Item item2, Item item3) {

		imageView1.setImageUrl(item1.imagesrc, imageLoader);
		imageView1.setDefaultImageResId(R.drawable.no_image);
		imageView1.setErrorImageResId(R.drawable.no_image);
		textViewTitle1.setText(item1.itemname);

		String amount1 = String.valueOf(item1.amount);
		SpannableString ss1 = new SpannableString(amount1 + " " + item1.label);
		ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, amount1.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		textViewQuantity1.setText(ss1);

		if (item1.label.equals("LARGE") || item1.label.equals("BOTTLE"))
			textViewQuantity1.setTextColor(getResources().getColor(
					R.color.menu_main_orange));
		else
			textViewQuantity1.setTextColor(getResources().getColor(
					R.color.menu_main_gray));

		if (item1.comment != null && !item1.comment.isEmpty()) {
			imageButtonItem1.setVisibility(View.VISIBLE);
			buttonDismissDialog1.setVisibility(View.VISIBLE);
			textViewComment1.setText(item1.comment);
		} else {
			imageButtonItem1.setVisibility(View.GONE);
		}

		if (item2 != null) {
			imageView2.setVisibility(View.VISIBLE);
			imageView2.setImageUrl(item2.imagesrc, imageLoader);
			imageView2.setDefaultImageResId(R.drawable.no_image);
			imageView2.setErrorImageResId(R.drawable.no_image);
			textViewTitle2.setText(item2.itemname);

			String amount2 = String.valueOf(item2.amount);
			SpannableString ss2 = new SpannableString(amount2 + " "
					+ item2.label);
			ss2.setSpan(new StyleSpan(Typeface.BOLD), 0, amount2.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			textViewQuantity2.setText(ss2);
			if (item2.label.equals("LARGE") || item2.label.equals("BOTTLE"))
				textViewQuantity2.setTextColor(getResources().getColor(
						R.color.menu_main_orange));
			else
				textViewQuantity2.setTextColor(getResources().getColor(
						R.color.menu_main_gray));

			if (item2.comment != null && !item2.comment.isEmpty()) {
				imageButtonItem2.setVisibility(View.VISIBLE);
				buttonDismissDialog2.setVisibility(View.VISIBLE);
				textViewComment2.setText(item2.comment);
			} else {
				imageButtonItem2.setVisibility(View.GONE);
			}

		} else {
			imageView2.setVisibility(View.INVISIBLE);
			textViewTitle2.setText("");
			textViewQuantity2.setText("");
		}

		if (item3 != null) {
			imageView3.setVisibility(View.VISIBLE);
			imageView3.setImageUrl(item3.imagesrc, imageLoader);
			imageView3.setDefaultImageResId(R.drawable.no_image);
			imageView3.setErrorImageResId(R.drawable.no_image);
			textViewTitle3.setText(item3.itemname);

			String amount3 = String.valueOf(item3.amount);
			SpannableString ss3 = new SpannableString(amount3 + " "
					+ item3.label);
			ss3.setSpan(new StyleSpan(Typeface.BOLD), 0, amount3.length(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			textViewQuantity3.setText(ss3);
			if (item3.label.equals("LARGE") || item3.label.equals("BOTTLE"))
				textViewQuantity3.setTextColor(getResources().getColor(
						R.color.menu_main_orange));
			else
				textViewQuantity3.setTextColor(getResources().getColor(
						R.color.menu_main_gray));

			if (item1.comment != null && !item3.comment.isEmpty()) {
				imageButtonItem3.setVisibility(View.VISIBLE);
				buttonDismissDialog3.setVisibility(View.VISIBLE);
				textViewComment3.setText(item3.comment);
			} else {
				imageButtonItem3.setVisibility(View.GONE);
			}
		} else {
			imageView3.setVisibility(View.INVISIBLE);
			textViewTitle3.setText("");
			textViewQuantity3.setText("");
		}

//		Utils.handleDismissDialog(relativeLayoutCommentContainer1, getContext());
//		Utils.handleDismissDialog(relativeLayoutCommentContainer2, getContext());
//		Utils.handleDismissDialog(relativeLayoutCommentContainer3, getContext());

		buttonDismissDialog1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				relativeLayoutCommentContainer1.setVisibility(View.GONE);
			}
		});

		buttonDismissDialog2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				relativeLayoutCommentContainer2.setVisibility(View.GONE);
			}
		});

		buttonDismissDialog3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				relativeLayoutCommentContainer3.setVisibility(View.GONE);
			}
		});

		imageButtonItem1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				relativeLayoutCommentContainer1.setVisibility(View.VISIBLE);
				buttonDismissDialog1.setVisibility(View.VISIBLE);
			}
		});

		imageButtonItem2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				relativeLayoutCommentContainer2.setVisibility(View.VISIBLE);
				buttonDismissDialog2.setVisibility(View.VISIBLE);
			}
		});

		imageButtonItem3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				relativeLayoutCommentContainer3.setVisibility(View.VISIBLE);
				buttonDismissDialog3.setVisibility(View.VISIBLE);
			}
		});
	}
	// private int getRelativeLeft(View myView) {
	// if (myView.getParent() == myView.getRootView())
	// return (int) myView.getX();
	// else
	// return (int) myView.getX()
	// + getRelativeLeft((View) myView.getParent());
	// }
	//
	// private int getRelativeTop(View myView) {
	// if (myView.getParent() == myView.getRootView())
	// return (int) myView.getY();
	// else
	// return (int) myView.getTop()
	// + getRelativeTop((View) myView.getParent());
	// }

}
