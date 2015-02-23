package com.s7design.menutablet.activities;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.Response.Listener;
import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.OrderItem;
import com.s7design.menutablet.utils.Settings;
import com.s7design.menutablet.views.OrderItemView;
import com.s7design.menutablet.volley.VolleySingleton;
import com.s7design.menutablet.volley.requests.GetOrdersRequest;
import com.s7design.menutablet.volley.responses.GetOrdersResponse;

public class OrderActivity extends BaseActivity {

	private LinearLayout layoutContainer;

	private OrderItem[] orders;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		showProgressDialogLoading();

		layoutContainer = (LinearLayout) findViewById(R.id.layoutContainer);

		Map<String, String> params = new HashMap<String, String>();
		params.put("accesstoken", Settings.getAccessToken(this));

		GetOrdersRequest getOrdersRequest = new GetOrdersRequest(OrderActivity.this, params, new Listener<GetOrdersResponse>() {

			@Override
			public void onResponse(GetOrdersResponse getOrdersResponse) {

				if (getOrdersResponse.response != null && getOrdersResponse.response.equals("success")) {

					orders = getOrdersResponse.orders;
					setViews();
				} else {

				}

				dismissProgressDialog();
			}
		});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(getOrdersRequest);

	}

	private void setViews() {

		for (OrderItem order : orders) {
			OrderItemView view = new OrderItemView(this, order);
			layoutContainer.addView(view);
		}
	}

}
