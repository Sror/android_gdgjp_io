package com.google.gdgjp.io.volley.model;

import org.json.JSONException;
import org.json.JSONObject;

public class BeerStyle {

	public long id;
	public String style;
	
	public BeerStyle(long id, String style) {
		this.id = id;
		this.style = style;
	}
	
	public BeerStyle(JSONObject json) throws JSONException {
		this.id = json.getLong("id");
		this.style = json.getString("name");
	}
	
}
