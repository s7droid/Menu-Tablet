package com.s7design.menutablet.volley.requests;

import java.util.Map;

import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menutablet.volley.responses.LoginResponse;

public class LoginRequest extends GsonRequest<LoginResponse> {

	public LoginRequest(Activity context, Map<String, String> params, Listener<LoginResponse> listener, ErrorListener errorListener) {
		super(context, Request.Method.POST, "login", params, LoginResponse.class, listener, errorListener);
	}

}
