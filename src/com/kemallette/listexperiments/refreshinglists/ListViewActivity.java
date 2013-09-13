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

	int							textColor, bgColor;
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


	private void setColors(){

		if (mTextColorInput.getText()
							.length() > 0)
			textColor = Color.parseColor(mTextColorInput.getText()
														.toString());
		if (mBackgroundColorInput.getText()
									.length() > 0)
			bgColor = Color.parseColor(mBackgroundColorInput.getText()
															.toString());


		Log.i(	TAG,
				"TextColor: "
					+ textColor
					+ "\nBgColor: "
					+ bgColor);

		View listItem;
		Holder mHolder;

		// These are both implemented in ListView sub classes which means
		// they're 'raw'/'flat' list positions
		int firstVis = mList.getFirstVisiblePosition();
		int lastVis = mList.getLastVisiblePosition();
		int count = lastVis
					- firstVis;
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

			listItem = mList.getChildAt(count);

			if (listItem != null){

				if (bgColor > 0)
					listItem.setBackgroundColor(bgColor);

				mHolder = (Holder) listItem.getTag();
				if (mHolder == null){
					mHolder = new Holder();
					mHolder.mText = (TextView) listItem.findViewById(android.R.id.text1);
					listItem.setTag(mHolder);
				}

				if (textColor > 0)
					mHolder.mText.setTextColor(textColor);

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

			if (textColor > 0)
				mHolder.mText.setTextColor(textColor);

			if (bgColor > 0)
				convertView.setBackgroundColor(bgColor);

			// mHolder.mText.setText(getItem(position).toString());

			return super.getView(	position,
									convertView,
									parent);
		}


	}

}
