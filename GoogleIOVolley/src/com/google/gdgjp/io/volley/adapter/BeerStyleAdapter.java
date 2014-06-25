package com.google.gdgjp.io.volley.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gdgjp.io.volley.R;
import com.google.gdgjp.io.volley.model.BeerStyle;

public class BeerStyleAdapter extends BaseAdapter {

	private List<BeerStyle> mItems;
	private LayoutInflater mInflater;
	
	public BeerStyleAdapter(LayoutInflater inflater) {
		mInflater = inflater;
		
		mItems = new ArrayList<BeerStyle>();
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
			convertView = mInflater.inflate(R.layout.row_beer_style, parent, false);
			
			holder = new ViewHolder();
			holder.beerStyle = (TextView) convertView.findViewById(R.id.tv_beer_style);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		BeerStyle obj = mItems.get(position);
		
		holder.beerStyle.setText( obj.style );
		
		return convertView;
	}
	
	public void addItems(List<BeerStyle> items) {
		if (mItems == null) {
			mItems = new ArrayList<BeerStyle>();
		}
		
		mItems.addAll(items);
		notifyDataSetChanged();
	}
	
	private class ViewHolder {
		
		public TextView beerStyle;
		
	}

}
