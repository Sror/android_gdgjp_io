package com.google.gdgjp.io.volley.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Beer {

	public String id;
	public String name;
	public String thumb;
	
	public Beer(String id, String name, String thumb) {
		this.id = id;
		this.name = name;
		this.thumb = thumb;
	}
	
	public Beer(JSONObject json) throws JSONException {
		this.id = json.getString("id");
		this.name = json.getString("name");
		
		if (json.has("labels")) {
			this.thumb = json.getJSONObject("labels").getString("icon");
		}
	}
	
}
