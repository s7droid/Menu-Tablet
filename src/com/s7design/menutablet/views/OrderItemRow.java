package com.s7design.menutablet.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.Item;
import com.s7design.menutablet.volley.VolleySingleton;

public class OrderItemRow extends LinearLayout {

	private static final String TAG = OrderItemRow.class.getSimpleName();

	private NetworkImageView imageView1;
	private NetworkImageView imageView2;
	private NetworkImageView imageView3;
	private TextView textViewTitle1;
	private TextView textViewTitle2;
	private TextView textViewTitle3;
	private TextView textViewQuantity1;
	private TextView textViewQuantity2;
	private TextView textViewQuantity3;
	private View viewDivider;

	private ImageLoader imageLoader;

	public OrderItemRow(Context context) {
		super(context);

		init();
	}

	private void init() {

		Log.e(TAG, "init");

		inflate(getContext(), R.layout.row_item_order, this);

		imageView1 = (NetworkImageView) findViewById(R.id.imageView1);
		imageView2 = (NetworkImageView) findViewById(R.id.imageView2);
		imageView3 = (NetworkImageView) findViewById(R.id.imageView3);
		textViewTitle1 = (TextView) findViewById(R.id.textViewTitle1);
		textViewTitle2 = (TextView) findViewById(R.id.textViewTitle2);
		textViewTitle3 = (TextView) findViewById(R.id.textViewTitle3);
		textViewQuantity1 = (TextView) findViewById(R.id.textViewQuantity1);
		textViewQuantity2 = (TextView) findViewById(R.id.textViewQuantity2);
		textViewQuantity3 = (TextView) findViewById(R.id.textViewQuantity3);
		viewDivider = (View) findViewById(R.id.viewDivider);

		imageLoader = VolleySingleton.getInstance(getContext()).getImageLoader();

		Log.d(TAG, "init done");
	}

	public void showDivider() {
		viewDivider.setVisibility(View.VISIBLE);
	}

	public void setData(Item item1, Item item2, Item item3) {

		imageView1.setImageUrl(item1.imagesrc, imageLoader);
		textViewTitle1.setText(item1.itemname);
		textViewQuantity1.setText(item1.amount + " " + item1.label);
		if (item1.label.equals("LARGE")||item1.label.equals("BOTTLE"))
			textViewQuantity1.setTextColor(getResources().getColor(R.color.menu_main_orange));
		else
			textViewQuantity1.setTextColor(getResources().getColor(R.color.menu_main_gray));

		if (item2 != null) {
			imageView2.setVisibility(View.VISIBLE);
			imageView2.setImageUrl(item2.imagesrc, imageLoader);
			textViewTitle2.setText(item2.itemname);
			textViewQuantity2.setText(item2.amount + " " + item2.label);
			if (item2.label.equals("LARGE")||item2.label.equals("BOTTLE"))
				textViewQuantity2.setTextColor(getResources().getColor(R.color.menu_main_orange));
			else
				textViewQuantity2.setTextColor(getResources().getColor(R.color.menu_main_gray));
		} else {
			imageView2.setVisibility(View.INVISIBLE);
			textViewTitle2.setText("");
			textViewQuantity2.setText("");
		}

		if (item3 != null) {
			imageView3.setVisibility(View.VISIBLE);
			imageView3.setImageUrl(item3.imagesrc, imageLoader);
			textViewTitle3.setText(item3.itemname);
			textViewQuantity3.setText(item3.amount + " " + item3.label);
			if (item3.label.equals("LARGE")||item3.label.equals("BOTTLE"))
				textViewQuantity3.setTextColor(getResources().getColor(R.color.menu_main_orange));
			else
				textViewQuantity3.setTextColor(getResources().getColor(R.color.menu_main_gray));
		} else {
			imageView3.setVisibility(View.INVISIBLE);
			textViewTitle3.setText("");
			textViewQuantity3.setText("");
		}
	}

}
