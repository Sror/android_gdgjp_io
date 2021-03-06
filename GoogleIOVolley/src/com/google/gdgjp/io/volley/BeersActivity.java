package com.google.gdgjp.io.volley;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gdgjp.io.volley.adapter.BeerAdapter;
import com.google.gdgjp.io.volley.model.Beer;
import com.google.gdgjp.io.volley.util.Constants;

public class BeersActivity extends Activity implements Listener<JSONObject>,
		ErrorListener {

	public static final String EXTRA_STYLE_ID = "extra_style_id";
	
	private ListView mListView;
	private BeerAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // ListView Adapter
        mAdapter = new BeerAdapter(getLayoutInflater());
        
        // ListView
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);
        
        // Request data
        requestBeers();
    }
    
    /**
     * Request beers list
     */
    private void requestBeers() {
    	long beerStyle = getIntent().getExtras().getLong(EXTRA_STYLE_ID);
    	
		JsonObjectRequest req = new JsonObjectRequest(
				String.format(Constants.URL_BEERS, beerStyle),
				null, this, this);
		ApplicationController.getInstance().addToRequestQueue(req);
    }
    
    /**
	 * Volley
	 */
	@Override
	public void onResponse(JSONObject response) {
		new ParseTask().execute(response);
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
	}
    
	/**
	 * Parse JSON
	 */
	private class ParseTask extends AsyncTask<JSONObject, Void, List<Beer>> {
		
		@Override
		protected List<Beer> doInBackground(JSONObject... params) {
			try {
				JSONArray data = params[0].getJSONArray("data");
				int len = data.length();
				
				ArrayList<Beer> beers = new ArrayList<Beer>();
				
				for (int i = 0; i < len; i++) {
					beers.add(new Beer( data.getJSONObject(i) ));
				}
				
				return beers;
			} catch (JSONException e) {
				Log.e(Constants.TAG, e.getMessage(), e);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<Beer> result) {
			super.onPostExecute(result);
			
			if ((result != null) && (result.size() > 0)) {
				mAdapter.addItems(result);
			} else {
				Toast.makeText(BeersActivity.this, "Eita!!", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
}
