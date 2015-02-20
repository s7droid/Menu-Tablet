package com.s7design.menutablet.callbacks;

import com.android.volley.VolleyError;
import com.s7design.menutablet.volley.responses.GsonResponse;

public interface OnVolleyErrorCallback {

	public void onResponseError(GsonResponse response);
	public void onVolleyError(VolleyError volleyError);

}
