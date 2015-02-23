package com.s7design.menutablet.volley.responses;

import com.s7design.menutablet.dataclasses.OrderItem;

public class GetOrdersResponse extends GsonResponse {

	public OrderItem[] orders;
	public String response;
	public String prioritize;

}
