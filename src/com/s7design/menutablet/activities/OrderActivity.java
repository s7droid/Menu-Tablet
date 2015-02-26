package com.s7design.menutablet.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
	private TextView textViewActive;
	private TextView textViewFinished;

	private ArrayList<OrderItem> ordersActive;
	private ArrayList<OrderItem> ordersFinished;

	private Adapter adapterActive;
	private Adapter adapterFinished;

	private int rowNumber = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		showProgressDialogLoading();

		listView = (ListView) findViewById(R.id.listView);
		textViewActive = (TextView) findViewById(R.id.textViewActive);
		textViewFinished = (TextView) findViewById(R.id.textViewFinished);

		textViewActive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				textViewActive.setTextColor(getResources().getColor(R.color.menu_main_orange));
				textViewFinished.setTextColor(getResources().getColor(R.color.menu_main_gray_light));

				listView.setAdapter(adapterActive);
			}
		});

		textViewFinished.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				textViewActive.setTextColor(getResources().getColor(R.color.menu_main_gray_light));
				textViewFinished.setTextColor(getResources().getColor(R.color.menu_main_orange));

				listView.setAdapter(adapterFinished);
			}
		});

		Map<String, String> params = new HashMap<String, String>();
		params.put("accesstoken", Settings.getAccessToken(this));

		GetOrdersRequest getOrdersRequest = new GetOrdersRequest(OrderActivity.this, params, new Listener<GetOrdersResponse>() {

			@Override
			public void onResponse(final GetOrdersResponse getOrdersResponse) {

				if (getOrdersResponse.response != null && getOrdersResponse.response.equals("success")) {

					new AsyncTask<Void, Void, Void>() {

						@Override
						protected Void doInBackground(Void... params) {

							ordersActive = new ArrayList<OrderItem>();
							ordersFinished = new ArrayList<OrderItem>();

							int maxElNum = 0;

							for (OrderItem item : getOrdersResponse.orders) {
								if (item.status.equals("active")) {
									ordersActive.add(item);
								} else {
									ordersFinished.add(item);
								}

								if (item.items.length > maxElNum)
									maxElNum = item.items.length;
							}

							rowNumber = maxElNum / 3 + (maxElNum % 3 > 0 ? 1 : 0);

							adapterActive = new Adapter(OrderActivity.this, ordersActive);
							adapterFinished = new Adapter(OrderActivity.this, ordersFinished);

							return null;
						}

						protected void onPostExecute(Void result) {

							listView.setAdapter(adapterActive);
							dismissProgressDialog();
						};
					}.execute();

				} else {
					dismissProgressDialog();
				}

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
