package com.s7design.menutablet.views;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.Item;
import com.s7design.menutablet.dataclasses.OrderItem;

public class OrderItemView extends LinearLayout {

	private static final String TAG = OrderItemView.class.getSimpleName();

	private TextView textViewTableNumber;
	private TextView textViewName;
	private TextView textViewTime;
	private LinearLayout layoutRowContainer;
	private ImageButton imageButton;

	private int rowNumber = 0;

	public OrderItemView(Context context, int rowNumber) {
		super(context);

		this.rowNumber = rowNumber;

		init();
	}

	private void init() {

		// Log.w(TAG, "init");

		inflate(getContext(), R.layout.item_order, this);

		textViewTableNumber = (TextView) findViewById(R.id.textViewTableNumber);
		textViewName = (TextView) findViewById(R.id.textViewName);
		textViewTime = (TextView) findViewById(R.id.textViewTime);
		layoutRowContainer = (LinearLayout) findViewById(R.id.layoutRowContainer);
		imageButton = (ImageButton) findViewById(R.id.imageButton);

		for (int i = 0; i < rowNumber; ++i) {
			OrderItemRow row = new OrderItemRow(getContext());
			layoutRowContainer.addView(row);
			if (i > 0) {
				row.showDivider();
				row.setVisibility(View.GONE);
			}
		}
	}

	public void setActionButtonResource(int res) {
		imageButton.setImageResource(res);
	}

	public void setData(OrderItem order) {

		textViewTableNumber.setText(order.tablenumber);
		textViewName.setText(order.name + "\n" + order.name);
		textViewTime.setText(order.time);

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
				if (item.getVisibility() != View.VISIBLE) {
					item.setVisibility(View.VISIBLE);
					// Log.w(TAG, "set visible");
				}

			} else {
				if (item.getVisibility() != View.GONE) {
					item.setVisibility(View.GONE);
					// Log.w(TAG, "set gone");
				}
			}
		}
	}

	public void setButtonOnClickListener(int position, OnClickListener listener) {
		imageButton.setTag(position);
		imageButton.setOnClickListener(listener);
	}

}
