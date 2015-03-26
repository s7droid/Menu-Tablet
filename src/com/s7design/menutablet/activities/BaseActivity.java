package com.s7design.menutablet.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;

import com.android.volley.VolleyError;
import com.s7design.menutablet.R;
import com.s7design.menutablet.app.MenuTablet;
import com.s7design.menutablet.callbacks.OnVolleyErrorCallback;
import com.s7design.menutablet.dialogs.AlertDialogFragment;
import com.s7design.menutablet.dialogs.ProgressDialogFragment;
import com.s7design.menutablet.volley.responses.GsonResponse;

public class BaseActivity extends Activity implements OnVolleyErrorCallback {

	private AlertDialogFragment alertDialog;
	private ProgressDialogFragment progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		alertDialog = new AlertDialogFragment();
		alertDialog.setFragmentManager(getFragmentManager(), this);

		progressDialog = new ProgressDialogFragment();
		progressDialog.setFragmentManager(getFragmentManager(), this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		MenuTablet.getInstance().registerOnVolleyErrorCallback(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		MenuTablet.getInstance().unregisterOnVolleyErrorCallback();
	}

	public void showAlertDialog(int title, int body) {
		alertDialog.showDialog(getString(title), getString(body), null);
	}

	public void showAlertDialog(String title, String body) {
		alertDialog.showDialog(title, body, null);
	}

	public void showAlertDialog(int title, int body, OnClickListener onClickListener) {
		alertDialog.showDialog(getString(title), getString(body), onClickListener);
	}

	public void showAlertDialog(String title, String body, OnClickListener onClickListener) {
		alertDialog.showDialog(title, body, onClickListener);
	}

	public void showProgressDialog(int body) {
		progressDialog.showDialog(getString(body));
	}

	public void showProgressDialog(String body) {
		progressDialog.showDialog(body);
	}

	public void showProgressDialogLoading() {
		if (!progressDialog.isVisible) {
			progressDialog.show(getFragmentManager(), BaseActivity.class.getSimpleName());
			;
		}
	}

	public void dismissProgressDialog() {
		try {
			progressDialog.dismiss();
			progressDialog.dismissAllowingStateLoss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResponseError(GsonResponse response) {

		dismissProgressDialog();
		showAlertDialog(getString(R.string.dialog_title_error), getString(R.string.dialog_body_network_problem));
	}

	@Override
	public void onVolleyError(VolleyError volleyError) {

		dismissProgressDialog();
		showAlertDialog(getString(R.string.dialog_title_error), getString(R.string.dialog_body_network_problem));
	}

}
