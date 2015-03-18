package com.s7design.menutablet.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menutablet.volley.responses.CompleteOrderResponse;

public class CompleteOrderRequest extends GsonRequest<CompleteOrderResponse> {

	public CompleteOrderRequest(Activity context, Map<String, String> params, Listener<CompleteOrderResponse> listener, ErrorListener errorListener) {
		super(context, Request.Method.POST, "ordercomplete", params, CompleteOrderResponse.class, listener, errorListener);
	}

}
