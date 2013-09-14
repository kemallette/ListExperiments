/**
 * 
 */
package com.kemallette.listexperiments.refreshinglists;


import java.util.ArrayList;
import java.util.List;

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

	int							textColor	= Color.BLACK;
	int							bgColor		= Color.WHITE;

	private static List<Task>	ITEM_LIST	= new ArrayList<Task>(25);

	private EditText			mTextColorInput, mBackgroundColorInput;
	private ListView			mList;

	private MyArrayAdapter		mAdapter;


	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.refreshing_listview_layout);

		if (ITEM_LIST.isEmpty())
			populateItemListData();

		initViews();
		populateList();
	}


	@Override
	public void onClick(View v){

		switch(v.getId()){

			case R.id.tester:
				setColors();
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


	private void populateItemListData(){

		for (int i = 0; i < 50; i++){

			ITEM_LIST.add(new Task(	i,
									"List Requirements"));
			ITEM_LIST.add(new Task(	i,
									"Select Enviornment, Framework and Tooling"));
			ITEM_LIST.add(new Task(	i,
									"Do work"));
			ITEM_LIST.add(new Task(	i,
									"Iterate!"));
			ITEM_LIST.add(new Task(	i,
									"Maintain"));
			ITEM_LIST.add(new Task(	i,
									"Rest"));
		}

	}


	private void populateList(){

		mAdapter = new MyArrayAdapter(	this,
										ITEM_LIST);
		mList.setAdapter(mAdapter);
	}


	/**
	 * 
	 * 
	 * Tip: Color.parseColor is pretty handy. It can take a few simple color
	 * strings like 'red' or 'blue' as well as the normal #FFFFFFFF format we're
	 * used to using.
	 */
	private void setColors(){

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


		View listItem;
		Holder mHolder;

		int firstVis = mList.getFirstVisiblePosition(); // Returns this item's
														// adapter position
		int lastVis = mList.getLastVisiblePosition();// Returns this item's
														// adapter position
		int count = lastVis
					- firstVis; // Need to "convert" our adapter positions to
								// ViewGroup child positions
		Log.i(	TAG,
				"firstVisPos: "
					+ firstVis
					+ "     lastVisPos: "
					+ lastVis
					+ "\nChildCount: "
					+ mList.getChildCount()
					+ "\n mCount: "
					+ count);

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
				listItem.setBackgroundColor(bgColor);

				mHolder = (Holder) listItem.getTag();
				if (mHolder == null){
					mHolder = new Holder();
					mHolder.mText = (TextView) listItem.findViewById(android.R.id.text1);
					listItem.setTag(mHolder);
				}

				mHolder.mText.setTextColor(textColor);

				Log.i(	TAG,
						"itemText: "
							+ mHolder.mText.getText());

			}else
				Log.d(	TAG,
						"getChildAt retrieved a null view :( ");


			count--;
		}

	}

	static class Holder{

		TextView	mText;
	}

	class MyArrayAdapter extends
						ArrayAdapter<Task>{


		public MyArrayAdapter(	Context context,
								List<Task> objects){

			super(	context,
					android.R.layout.simple_expandable_list_item_1,
					android.R.id.text1,
					objects);

		}


		@Override
		public View getView(int position, View convertView, ViewGroup parent){

			Holder mHolder;

			if (convertView == null){
				convertView =
								getLayoutInflater().inflate(android.R.layout.simple_list_item_1,
															parent,
															false);
				mHolder = new Holder();
				mHolder.mText = (TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(mHolder);
			}else
				mHolder = (Holder) convertView.getTag();

			// Since all visible items have their appropriate user selected
			// color, we make sure any item views that were in the recycler's
			// scrapped views reflect our new color choices.
			convertView.setBackgroundColor(bgColor);
			mHolder.mText.setTextColor(textColor);

			mHolder.mText.setText(getItem(position).toString());

			return convertView;
		}


	}

}
