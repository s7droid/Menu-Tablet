package com.s7design.menutablet.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.s7design.menutablet.R;
import com.s7design.menutablet.dataclasses.OrderItem;
import com.s7design.menutablet.utils.Settings;
import com.s7design.menutablet.views.OrderItemView;
import com.s7design.menutablet.volley.VolleySingleton;
import com.s7design.menutablet.volley.requests.CompleteOrderRequest;
import com.s7design.menutablet.volley.requests.GetOrdersRequest;
import com.s7design.menutablet.volley.responses.CompleteOrderResponse;
import com.s7design.menutablet.volley.responses.GetOrdersResponse;

public class OrderActivity extends BaseActivity {

	private static final String TAG = OrderActivity.class.getSimpleName();

	private ListView listView;
	private TextView textViewActive;
	private TextView textViewFinished;
	private TextView textViewLogOut;
	private TextView textViewServedOrders;
	private TextView textViewServiceTimeline;
	private LinearLayout globalContainer;

	private ImageButton imageButtonRefresh;

	private ArrayList<OrderItem> ordersActive;
	private ArrayList<OrderItem> ordersFinished;

	private Adapter adapterActive;
	private Adapter adapterFinished;

	private int rowNumber = 0;

	private boolean isActive = true;

	private GestureDetector gestureDetector;
	View.OnTouchListener gestureListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);

		showProgressDialogLoading();

		globalContainer = (LinearLayout) findViewById(R.id.globalContainer);
		listView = (ListView) findViewById(R.id.listView);
		textViewActive = (TextView) findViewById(R.id.textViewActive);
		textViewFinished = (TextView) findViewById(R.id.textViewFinished);
		textViewLogOut = (TextView) findViewById(R.id.textViewLogOut);
		textViewServedOrders = (TextView) findViewById(R.id.textViewOrderActivityServedOrders);
		textViewServiceTimeline = (TextView) findViewById(R.id.textViewServiceTimeline);

		imageButtonRefresh = (ImageButton) findViewById(R.id.imageButtonRefresh);

		// gestureListener = new View.OnTouchListener() {
		// public boolean onTouch(View v, MotionEvent event) {
		// System.out.println("CALLED oN TOUCH WHERE IT SHOULD");
		// gestureDetector.onTouchEvent(event);
		// return true;
		// }
		// };

		textViewActive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d(TAG, "onClick active");

				if (!isActive) {
					isActive = true;
					textViewServedOrders.setText(getResources().getString(
							R.string.order_activity_orders_to_serve));
					showProgressDialogLoading();
					textViewActive.setTextColor(getResources().getColor(
							R.color.menu_main_orange));
					textViewActive.setTypeface(Typeface.createFromAsset(
							getAssets(), "fonts/GothamRounded-Medium.otf"));
					textViewFinished.setTextColor(getResources().getColor(
							R.color.menu_main_gray_light));
					textViewFinished.setTypeface(Typeface.createFromAsset(
							getAssets(), "fonts/GothamRounded-Book.otf"));
					textViewServiceTimeline.setText(getResources().getString(
							R.string.main_activity_service_timeline));

					listView.setAdapter(adapterActive);
					listView.post(new Runnable() {

						@Override
						public void run() {
							dismissProgressDialog();
						}
					});
				}
			}
		});

		textViewFinished.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Log.d(TAG, "onClick finished");

				if (isActive) {
					isActive = false;
					showProgressDialogLoading();
					textViewServedOrders.setText(getResources().getString(
							R.string.order_activity_served_orders));
					textViewActive.setTextColor(getResources().getColor(
							R.color.menu_main_gray_light));
					textViewActive.setTypeface(Typeface.createFromAsset(
							getAssets(), "fonts/GothamRounded-Book.otf"));
					textViewFinished.setTextColor(getResources().getColor(
							R.color.menu_main_orange));
					textViewFinished.setTypeface(Typeface.createFromAsset(
							getAssets(), "fonts/GothamRounded-Medium.otf"));
					textViewServiceTimeline
							.setText(getResources()
									.getString(
											R.string.main_activity_service_timeline_past_orders));

					listView.setAdapter(adapterFinished);
					listView.post(new Runnable() {

						@Override
						public void run() {
							dismissProgressDialog();
						}
					});
				}
			}
		});

		textViewLogOut.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Settings.setAccessToken(OrderActivity.this, "");
				startActivity(new Intent(OrderActivity.this, MainActivity.class));
				finish();
			}
		});

		imageButtonRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showProgressDialogLoading();

				isActive = true;
				textViewServedOrders.setText(getResources().getString(
						R.string.order_activity_orders_to_serve));
				showProgressDialogLoading();
				textViewActive.setTextColor(getResources().getColor(
						R.color.menu_main_orange));
				textViewActive.setTypeface(Typeface.createFromAsset(
						getAssets(), "fonts/GothamRounded-Medium.otf"));
				textViewFinished.setTextColor(getResources().getColor(
						R.color.menu_main_gray_light));
				textViewFinished.setTypeface(Typeface.createFromAsset(
						getAssets(), "fonts/GothamRounded-Book.otf"));

				loadOrders();
			}
		});

		loadOrders();

	}

	private void loadOrders() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("accesstoken", Settings.getAccessToken(this));

		GetOrdersRequest getOrdersRequest = new GetOrdersRequest(
				OrderActivity.this, params, new Listener<GetOrdersResponse>() {

					@Override
					public void onResponse(
							final GetOrdersResponse getOrdersResponse) {

						if (getOrdersResponse.response != null
								&& getOrdersResponse.response.equals("success")) {

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

									// FIXME: clean this shit
//									OrderItem item = new OrderItem();
//									Item i = new Item();
//									i.amount = 1;
//									i.comment = "This is comment left for this item. This comment should be a little bit longer.";
//									i.itemname = "Green Goddess soup";
//									i.label = "Great dish";
//									i.imagesrc = "http://wwwwww.error";
//									Item[] items = { i, i, i, i, i, i, i, i, i,
//											i, i, i, i, i, i };
//
//									item.items = items;
//									item.status = "active";
//									item.orderid = "dsandjsayuwy1msdk12Kkjdsak21212121";
//									item.name = "Marlon";
//									item.tablenumber = "25";
//									item.time = "2:45";
//									ordersActive.add(item);
//									maxElNum = 15;
									// /////////////
									rowNumber = maxElNum / 3
											+ (maxElNum % 3 > 0 ? 1 : 0);

									adapterActive = new Adapter(
											OrderActivity.this, ordersActive);
									adapterFinished = new Adapter(
											OrderActivity.this, ordersFinished);

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
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {

						dismissProgressDialog();
						showAlertDialog(R.string.dialog_title_error,
								R.string.dialog_body_network_problem);
					}
				});

		VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(
				getOrdersRequest);
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

			OrderItem item = getItem(position);
			if (item.status.equals("active")) {
				((OrderItemView) convertView)
						.setActionButtonResource(R.drawable.check);
			} else {
				((OrderItemView) convertView)
						.setActionButtonResource(R.drawable.delete);
			}

			((OrderItemView) convertView).setData(item);
			((OrderItemView) convertView).setButtonOnClickListener(position,
					new OnClickListener() {

						@Override
						public void onClick(View v) {

							final int position = (int) v.getTag();

							Map<String, String> params = new HashMap<String, String>();
							params.put("accesstoken",
									Settings.getAccessToken(OrderActivity.this));
							params.put("orderid", getItem(position).orderid);

							CompleteOrderRequest loginRequest = new CompleteOrderRequest(
									OrderActivity.this, params,
									new Listener<CompleteOrderResponse>() {

										@Override
										public void onResponse(
												CompleteOrderResponse loginResponse) {

											Log.e(TAG, "response");

											if (loginResponse.response != null
													&& loginResponse.response
															.equals("success")) {

												OrderItem item = getItem(position);

												if (item.status
														.equals("active")) {
													item.status = "finished";
													ordersFinished
															.add(0,
																	ordersActive
																			.remove(position));
													adapterActive
															.notifyDataSetChanged();
												} else {
													item.status = "active";
													ordersActive
															.add(0,
																	ordersFinished
																			.remove(position));
													adapterFinished
															.notifyDataSetChanged();
												}
											} else {

											}

										}
									}, new ErrorListener() {

										@Override
										public void onErrorResponse(
												VolleyError arg0) {

											showAlertDialog(
													R.string.dialog_title_error,
													R.string.dialog_body_network_problem);
										}
									});

							VolleySingleton
									.getInstance(getApplicationContext())
									.addToRequestQueue(loginRequest);

						}
					});

			return convertView;
		}
	}

}