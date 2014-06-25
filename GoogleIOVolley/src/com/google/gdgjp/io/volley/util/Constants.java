package com.google.gdgjp.io.volley.util;

public class Constants {

	public static final String TAG = "GDGJP";

	public static final String API_KEY = "9c740071c2d64a3b731ac4eee2f08102";
	
	public static final String URL_DOMAIN = "http://api.brewerydb.com/v2";
	public static final String URL_BEERS = URL_DOMAIN + "/beers?styleId=%s&key=" + API_KEY;
	public static final String URL_BEER_STYLES = URL_DOMAIN + "/styles?key=" + API_KEY;
	
}
