package com.google.gdgjp.io.volley;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache.Entry;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gdgjp.io.volley.adapter.BeerStyleAdapter;
import com.google.gdgjp.io.volley.model.BeerStyle;
import com.google.gdgjp.io.volley.util.Constants;

public class BeerStyleActivity extends Activity implements OnItemClickListener, Listener<JSONObject>,
		ErrorListener {

	private ListView mListView;
	private BeerStyleAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // ListView Adapter
        mAdapter = new BeerStyleAdapter(getLayoutInflater());
        
        // ListView
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        
        // Request data
        requestBeerStyles();
    }
    
    /**
     * Request beers list
     */
    private void requestBeerStyles() {
    	JsonObjectRequest req = new JsonObjectRequest(Constants.URL_BEER_STYLES, null, this, this);
    	req.setShouldCache(true);
    	
    	Entry cache = ApplicationController.getInstance().getRequestQueue().getCache().get(Constants.URL_BEER_STYLES);
		
		if ((cache != null) && (cache.data != null)) {
			try {
				onResponse(new JSONObject(new String(cache.data)));
			} catch (JSONException e) {
				ApplicationController.getInstance().addToRequestQueue(req);
			}
		} else {
			ApplicationController.getInstance().addToRequestQueue(req);
		}
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
    		long id) {
    	BeerStyle style = (BeerStyle) parent.getItemAtPosition(position);
    	
    	Intent intent = new Intent(this, BeersActivity.class);
    	intent.putExtra(BeersActivity.EXTRA_STYLE_ID, style.id);
    	
    	startActivity(intent);
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
	private class ParseTask extends AsyncTask<JSONObject, Void, List<BeerStyle>> {
		
		@Override
		protected List<BeerStyle> doInBackground(JSONObject... params) {
			try {
				JSONArray data = params[0].getJSONArray("data");
				int len = data.length();
				
				ArrayList<BeerStyle> styles = new ArrayList<BeerStyle>();
				
				for (int i = 0; i < len; i++) {
					styles.add(new BeerStyle( data.getJSONObject(i) ));
				}
				
				return styles;
			} catch (JSONException e) {
				Log.e(Constants.TAG, e.getMessage(), e);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(List<BeerStyle> result) {
			super.onPostExecute(result);
			
			if ((result != null) && (result.size() > 0)) {
				mAdapter.addItems(result);
			} else {
				Toast.makeText(BeerStyleActivity.this, "Eita!!", Toast.LENGTH_LONG).show();
			}
		}
		
	}
	
}
