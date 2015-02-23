package com.s7design.menutablet.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.Item;
import com.s7design.menutablet.dataclasses.OrderItem;

public class OrderItemView extends LinearLayout {

	private OrderItem order;

	private TextView textViewTableNumber;
	private TextView textViewDesc1;
	private TextView textViewDesc2;
	private TextView textViewTime;
	private LinearLayout layoutRowContainer;

	public OrderItemView(Context context, OrderItem order) {
		super(context);

		this.order = order;

		init();
		setData();
	}

	private void init() {

		inflate(getContext(), R.layout.item_order, this);

		textViewTableNumber = (TextView) findViewById(R.id.textViewTableNumber);
		textViewDesc1 = (TextView) findViewById(R.id.textViewDesc1);
		textViewDesc2 = (TextView) findViewById(R.id.textViewDesc2);
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		layoutRowContainer = (LinearLayout) findViewById(R.id.layoutRowContainer);
	}

	private void setData() {

		int res = order.items.length % 3;
		int length = order.items.length / 3 + (res > 0 ? 1 : 0);

		for (int i = 0; i < length; ++i) {
			Item i1 = order.items[i];
			Item i2 = null;
			Item i3 = null;
			if (i < length - 1) {
				i2 = order.items[i + 1];
				i3 = order.items[i + 2];
			} else {
				if (res == 2)
					i2 = order.items[i + 1];
				if (res == 0)
					i3 = order.items[i + 2];
			}
			OrderItemRow item = new OrderItemRow(getContext(), i1, i2, i3);
			layoutRowContainer.addView(item);
		}

	}

}
