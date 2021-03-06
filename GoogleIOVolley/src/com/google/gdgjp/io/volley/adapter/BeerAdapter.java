package com.google.gdgjp.io.volley.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.google.gdgjp.io.volley.ApplicationController;
import com.google.gdgjp.io.volley.R;
import com.google.gdgjp.io.volley.model.Beer;

public class BeerAdapter extends BaseAdapter {

	private List<Beer> mItems;
	private LayoutInflater mInflater;
	
	public BeerAdapter(LayoutInflater inflater) {
		mInflater = inflater;
		
		mItems = new ArrayList<Beer>();
	}
	
	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_beer, parent, false);
			
			holder = new ViewHolder();
			holder.beerName = (TextView) convertView.findViewById(R.id.tv_beer_name);
			holder.thumb = (NetworkImageView) convertView.findViewById(R.id.iv_thumb);
			
			holder.thumb.setDefaultImageResId(R.drawable.ic_launcher);
			holder.thumb.setErrorImageResId(R.drawable.ic_launcher);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Beer beer = mItems.get(position);
		
		holder.beerName.setText( beer.name );
		
		if (beer.thumb != null) {
			holder.thumb.setImageUrl( beer.thumb, ApplicationController.getInstance().getImageLoader());
			holder.thumb.setVisibility(View.VISIBLE);
		} else {
			holder.thumb.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	public void addItems(List<Beer> items) {
		if (mItems == null) {
			mItems = new ArrayList<Beer>();
		}
		
		mItems.addAll(items);
		notifyDataSetChanged();
	}
	
	private class ViewHolder {
		
		public TextView beerName;
		public NetworkImageView thumb;
		
	}

}
