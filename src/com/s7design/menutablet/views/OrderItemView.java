package com.s7design.menutablet.views;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.Item;
import com.s7design.menutablet.dataclasses.OrderItem;

public class OrderItemView extends LinearLayout {

	private static final String TAG = OrderItemView.class.getSimpleName();

	private TextView textViewTableNumber;
	private TextView textViewDesc1;
	private TextView textViewDesc2;
	private TextView textViewTime;
	private LinearLayout layoutRowContainer;

	private int rowNumber = 0;

	public OrderItemView(Context context, int rowNumber) {
		super(context);

		this.rowNumber = rowNumber;

		init();
	}

	private void init() {

		inflate(getContext(), R.layout.item_order, this);

		textViewTableNumber = (TextView) findViewById(R.id.textViewTableNumber);
		textViewDesc1 = (TextView) findViewById(R.id.textViewDesc1);
		textViewDesc2 = (TextView) findViewById(R.id.textViewDesc2);
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		layoutRowContainer = (LinearLayout) findViewById(R.id.layoutRowContainer);

		for (int i = 0; i < rowNumber; ++i) {
			OrderItemRow row = new OrderItemRow(getContext());
			layoutRowContainer.addView(row);
			if (i > 0) {
				row.showDivider();
				row.setVisibility(View.GONE);
			}
		}
	}

	public void setData(OrderItem order) {

		int res = order.items.length % 3;
		int length = order.items.length / 3 + (res > 0 ? 1 : 0);

		for (int i = 0; i < rowNumber; ++i) {

			View item = layoutRowContainer.getChildAt(i);

			if (i < length) {
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

				((OrderItemRow) item).setData(i1, i2, i3);
				item.setVisibility(View.VISIBLE);
			} else {
				item.setVisibility(View.GONE);
			}
		}

	}

}
