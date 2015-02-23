package com.s7design.menutablet.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.s7design.menutablet.volley.responses.GetOrdersResponse;

public class GetOrdersRequest extends GsonRequest<GetOrdersResponse> {

	public GetOrdersRequest(Activity context, Map<String, String> params, Listener<GetOrdersResponse> listener) {
		super(context, Request.Method.POST, "orders", params, GetOrdersResponse.class, listener, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {

			}
		});
	}

}
