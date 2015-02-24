package com.s7design.menutablet.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.Response.Listener;
import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.OrderItem;
import com.s7design.menutablet.utils.Settings;
import com.s7design.menutablet.views.OrderItemView;
import com.s7design.menutablet.volley.VolleySingleton;
import com.s7design.menutablet.volley.requests.GetOrdersRequest;
import com.s7design.menutablet.volley.responses.GetOrdersResponse;

public class OrderActivity extends BaseActivity {

	private static final String TAG = OrderActivity.class.getSimpleName();

	private ListView listView;

	private ArrayList<OrderItem> orders;

	private Adapter adapter;

	private int rowNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		showProgressDialogLoading();

		listView = (ListView) findViewById(R.id.listView);

		Map<String, String> params = new HashMap<String, String>();
		params.put("accesstoken", Settings.getAccessToken(this));

		GetOrdersRequest getOrdersRequest = new GetOrdersRequest(OrderActivity.this, params, new Listener<GetOrdersResponse>() {

			@Override
			public void onResponse(GetOrdersResponse getOrdersResponse) {

				if (getOrdersResponse.response != null && getOrdersResponse.response.equals("success")) {

					orders = new ArrayList<OrderItem>(Arrays.asList(getOrdersResponse.orders));

					int maxElNum = 0;

					for (OrderItem order : orders) {
						if (order.items.length > maxElNum)
							maxElNum = order.items.length;
					}

					rowNumber = maxElNum / 3 + (maxElNum % 3 > 0 ? 1 : 0);

					adapter = new Adapter(OrderActivity.this, orders);
					listView.setAdapter(adapter);
				} else {

				}

				dismissProgressDialog();
			}
		});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(getOrdersRequest);

	}

	class Adapter extends BaseAdapter {

		private Context context;
		private ArrayList<OrderItem> orders;

		public Adapter(Context context, ArrayList<OrderItem> orders) {
			this.context = context;
			this.orders = orders;
		}

		@Override
		public int getCount() {
			return orders.size();
		}

		@Override
		public OrderItem getItem(int position) {
			return orders.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = new OrderItemView(context, rowNumber);
			}

			((OrderItemView) convertView).setData(getItem(position));

			return convertView;
		}
	}
}
