/**
 * 
 */
package com.kemallette.listexperiments.refreshinglists;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kemallette.listexperiments.R;
import com.kemallette.listexperiments.SeedDataUtil;
import com.kemallette.listexperiments.WorldCity;

/**
 * @author kyle
 * 
 */
public class ListViewActivity	extends
								Activity implements
										OnItemClickListener,
										OnClickListener{

	private static final String	TAG	= "ListViewActivity";

	protected class Task{

		final int		id;
		final String	title;


		public Task(final int id,
					final String mTitle){

			super();

			this.id = id;
			this.title = mTitle;
		}


		@Override
		public String toString(){

			return title;
		}

	}

	int						textColor	= Color.BLACK;
	int						bgColor		= Color.WHITE;

	private EditText		mTextColorInput, mBackgroundColorInput;
	private ListView		mList;

	private MyArrayAdapter	mAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.refreshing_listview_layout);

		initViews();
		populateList();
	}


	@Override
	public void onClick(View v){

		switch(v.getId()){

			case R.id.tester:
				applyColors();
				break;
		}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id){

	}


	private void initViews(){

		Button mTesterButton = (Button) findViewById(R.id.tester);
		mTesterButton.setOnClickListener(this);

		mTextColorInput = (EditText) findViewById(R.id.textColorInput);
		mBackgroundColorInput = (EditText) findViewById(R.id.backgroundColorInput);

		mList = (ListView) findViewById(android.R.id.list);
		mList.setOnItemClickListener(this);
		mList.setAdapter(mAdapter);

	}


	private void populateList(){

		// SeedDataUtil.getCities should be run off the UI thread, but it
		// doesn't matter for example purposes
		mAdapter = new MyArrayAdapter(	this,
										SeedDataUtil.getCities(	this,
																15));
		mList.setAdapter(mAdapter);
	}


	/**
	 * Take a look at {@link Color#parseColor(String)}. It's quite handy!
	 */
	private void retrieveColors(){

		if (mTextColorInput.getText()
							.length() > 0)
			textColor = Color.parseColor(mTextColorInput.getText()
														.toString()
														.toLowerCase());
		if (mBackgroundColorInput.getText()
									.length() > 0)
			bgColor = Color.parseColor(mBackgroundColorInput.getText()
															.toString()
															.toLowerCase());
	}


	/**
	 * First, we collect user inputs from our {@link EditText} widgets and
	 * convert that to proper integer values using {@link #retrieveColors()}.
	 * Then, we translate our adapter's visible positions to {@link ViewGroup}
	 * positions in order to use {@link ViewGroup#getChildAt(int)}. After that,
	 * we make our color changes which will be reflected immediately.
	 * 
	 */
	private void applyColors(){

		View listItem;
		ViewHolder mHolder;

		retrieveColors();

		int firstVis = mList.getFirstVisiblePosition(); // Returns this item's
														// adapter position
		int lastVis = mList.getLastVisiblePosition();// Returns this item's
														// adapter position
		int count = lastVis
					- firstVis; // Converting our adapter
								// positions to
								// ViewGroup child positions

		while (count >= 0){ // looping through visible list items which
			// are the only items that will need to be
			// refreshed. The adapter's getView will
			// take care of all non-visible items when
			// the list is scrolled

			listItem = mList.getChildAt(count); // getChildAt(pos) is
												// implemented in ViewGroup and
												// as such, position has a
												// different meaning than your
												// adapter's positions.
												// ViewGroup tracks visible
												// ListView items as children
												// and is 0 indexed. This means
												// you'll have 0 - X positions
												// where X is however many
												// ListView items it takes to
												// fill the visible area of your
												// screen; usually less than 10.

			if (listItem != null){

				mHolder = (ViewHolder) listItem.getTag();
				if (mHolder == null){ // This shouldn't happen, but we'll make
										// sure in case some strange concurrency
										// bugs appear
					mHolder = new ViewHolder();
					mHolder.mText = (TextView) listItem
														.findViewById(android.R.id.text1);
					listItem.setTag(mHolder);
				}

				listItem.setBackgroundColor(bgColor); // Setting our user input
														// background color
				mHolder.mText.setTextColor(textColor);// Setting our user input
														// text color
			}else
				Log.d(	TAG,
						"getChildAt retrieved a null view :( ");

			count--;
		}

	}


	class MyArrayAdapter extends
						ArrayAdapter<WorldCity>{

		public MyArrayAdapter(	Context context,
								WorldCity[] mCities){

			super(	context,
					android.R.layout.simple_expandable_list_item_1,
					android.R.id.text1,
					mCities);

		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			ViewHolder mHolder;

			if (convertView == null){
				convertView = getLayoutInflater().inflate(
															android.R.layout.simple_list_item_1,
															parent,
															false);
				mHolder = new ViewHolder();
				mHolder.mText = (TextView) convertView
														.findViewById(android.R.id.text1);
				convertView.setTag(mHolder);
			}else
				mHolder = (ViewHolder) convertView.getTag();

			// Since all visible items have had their appropriate user selected
			// colors applied, we need to make sure any of the recycler's
			// scrapped views (which wouldn't be touched by our visible item
			// changes) that may get returned also reflect our changes.
			convertView.setBackgroundColor(bgColor);
			mHolder.mText.setTextColor(textColor);

			mHolder.mText.setText(getItem(position).toString());

			return convertView;
		}

	}

}
