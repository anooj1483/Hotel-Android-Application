package com.team.cafebeside.screenMappers;

import java.util.ArrayList;

import com.team.cafebeside.R;
import com.team.cafebeside.workers.Item;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TodaysMenu extends Activity {
	
	private LinearLayout mLinearListView;
	private ArrayList<Item> mArrayListData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_main);
		mLinearListView = (LinearLayout) findViewById(R.id.linear_listview);

		/**
		 * Add item into arraylist
		 */
		mArrayListData = new ArrayList<Item>();
		mArrayListData.add(new Item("South Indian Dishes"));
		mArrayListData.add(new Item("Desserts"));
		mArrayListData.add(new Item("Beverages"));
		mArrayListData.add(new Item("Ice Creams"));
		mArrayListData.add(new Item("Specials"));


		/***
		 * adding item into listview
		 */
		for (int i = 0; i < mArrayListData.size(); i++) {
			/**
			 * inflate items/ add items in linear layout instead of listview
			 */
			LayoutInflater inflater = null;
			inflater = (LayoutInflater) getApplicationContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View mLinearView = inflater.inflate(R.layout.row,null);
			/**
			 * getting id of row.xml
			 */
			TextView mFirstName = (TextView) mLinearView
					.findViewById(R.id.textViewName);
/*			TextView mLastName = (TextView) mLinearView
					.findViewById(R.id.textViewLastName);*/

			/**
			 * set item into row
			 */
			final String fName = mArrayListData.get(i).getmFirstName();
			//final String lName = mArrayListData.get(i).getmLastName();
			mFirstName.setText(fName);
			//mLastName.setText(lName);

			/**
			 * add view in top linear
			 */

			mLinearListView.addView(mLinearView);

			/**
			 * get item row on click
			 * 
			 */
			mLinearView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(TodaysMenu.this, "Clicked item;" + fName,
							Toast.LENGTH_SHORT).show();
				//	Intent nintent = new Intent(TodaysMenu.this,MainMenu.class);
					//startActivity(nintent);
				}
			});
		}

	}
}
