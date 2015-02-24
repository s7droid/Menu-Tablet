package com.s7design.menutablet.views;

import android.content.Context;
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
	private TextView textViewDesc1;
	private TextView textViewDesc2;
	private TextView textViewDesc3;
	private TextView textViewQuantity1;
	private TextView textViewQuantity2;
	private TextView textViewQuantity3;
	private LinearLayout layout2;
	private LinearLayout layout3;

	private ImageLoader imageLoader;

	public OrderItemRow(Context context) {
		super(context);

		init();
	}

	private void init() {

		inflate(getContext(), R.layout.row_item_order, this);

		imageView1 = (NetworkImageView) findViewById(R.id.imageView1);
		imageView2 = (NetworkImageView) findViewById(R.id.imageView2);
		imageView3 = (NetworkImageView) findViewById(R.id.imageView3);
		textViewTitle1 = (TextView) findViewById(R.id.textViewTitle1);
		textViewTitle2 = (TextView) findViewById(R.id.textViewTitle2);
		textViewTitle3 = (TextView) findViewById(R.id.textViewTitle3);
		textViewDesc1 = (TextView) findViewById(R.id.textViewDesc1);
		textViewDesc2 = (TextView) findViewById(R.id.textViewDesc2);
		textViewDesc3 = (TextView) findViewById(R.id.textViewDesc3);
		textViewQuantity1 = (TextView) findViewById(R.id.textViewQuantity1);
		textViewQuantity2 = (TextView) findViewById(R.id.textViewQuantity2);
		textViewQuantity3 = (TextView) findViewById(R.id.textViewQuantity3);
		layout2 = (LinearLayout) findViewById(R.id.layout2);
		layout3 = (LinearLayout) findViewById(R.id.layout3);

		imageLoader = VolleySingleton.getInstance(getContext()).getImageLoader();
	}

	public void setData(Item item1, Item item2, Item item3) {

		imageView1.setImageUrl(item1.imagesrc, imageLoader);
		textViewTitle1.setText(item1.itemname);
		textViewQuantity1.setText(item1.amount + " " + item1.label);
		if (item1.label.equals("LARGE"))
			textViewQuantity1.setTextColor(getResources().getColor(R.color.menu_main_orange));
		else
			textViewQuantity1.setTextColor(getResources().getColor(R.color.menu_main_gray));

		if (item2 != null) {
			layout2.setVisibility(View.VISIBLE);
			imageView2.setImageUrl(item2.imagesrc, imageLoader);
			textViewTitle2.setText(item2.itemname);
			textViewQuantity2.setText(item2.amount + " " + item2.label);
			if (item2.label.equals("LARGE"))
				textViewQuantity2.setTextColor(getResources().getColor(R.color.menu_main_orange));
			else
				textViewQuantity2.setTextColor(getResources().getColor(R.color.menu_main_gray));
		} else {
			layout2.setVisibility(View.INVISIBLE);
		}

		if (item3 != null) {
			layout3.setVisibility(View.VISIBLE);
			imageView3.setImageUrl(item3.imagesrc, imageLoader);
			textViewTitle3.setText(item3.itemname);
			textViewQuantity3.setText(item3.amount + " " + item3.label);
			if (item3.label.equals("LARGE"))
				textViewQuantity3.setTextColor(getResources().getColor(R.color.menu_main_orange));
			else
				textViewQuantity3.setTextColor(getResources().getColor(R.color.menu_main_gray));
		} else {
			layout3.setVisibility(View.INVISIBLE);
		}
	}

}
