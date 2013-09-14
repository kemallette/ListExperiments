package com.kemallette.listexperiments.refreshinglists;


import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.kemallette.listexperiments.R;
import com.kemallette.listexperiments.SeedDataUtil;
import com.kemallette.listexperiments.WorldCity;

public class ExpandableListViewActivity	extends
										Activity implements
												OnClickListener,
												OnGroupClickListener,
												OnChildClickListener,
												OnGroupCollapseListener,
												OnGroupExpandListener{

	private static final String	TAG				= "ExpandableListViewActivity";


	private int							groupTextColor	= Color.BLACK;
	private int							childTextColor	= Color.BLACK;
	private int							childBgColor	= Color.WHITE;
	private int							groupBgColor	= Color.WHITE;

	private EditText			mChildTextColorInput, mGroupTextColorInput,
								mChildBackgroundColorInput,
								mGroupBackgroundColorInput;

	private MyAdapter			mAdapter;
	private ExpandableListView	mList;


	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.refresh_expandablelistview_layout);

		initViews();
		populateViews();
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
	public void onGroupCollapse(int groupPosition){

	}


	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
								int groupPosition, int childPosition, long id){

		return false;
	}


	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
								int groupPosition, long id){

		return false;
	}


	@Override
	public void onGroupExpand(int groupPosition){

	}


	private void initViews(){

		RelativeLayout mGroupInputs = (RelativeLayout) findViewById(R.id.groupInputLayout);
		mGroupInputs.setVisibility(View.VISIBLE);

		Button mExpandableTesterButton = (Button) findViewById(R.id.tester);
		mExpandableTesterButton.setOnClickListener(this);

		mGroupTextColorInput = (EditText) findViewById(R.id.groupTextColorInput);
		mGroupBackgroundColorInput = (EditText) findViewById(R.id.groupBackgroundColorInput);
		mChildTextColorInput = (EditText) findViewById(R.id.textColorInput);
		mChildBackgroundColorInput = (EditText) findViewById(R.id.backgroundColorInput);

		mList = (ExpandableListView) findViewById(R.id.expandable_list);
		mList.setOnChildClickListener(this);
		mList.setOnGroupClickListener(this);
		mList.setOnGroupExpandListener(this);
		mList.setOnGroupCollapseListener(this);
	}


	private void populateViews(){

		mAdapter = new MyAdapter(this);
		mList.setAdapter(mAdapter);

	}


	/**
	 * Take a look at {@link Color#parseColor(String)}. It's quite handy!
	 */
	private void retrieveColors(){

		if (mGroupTextColorInput.getText()
								.length() > 0)
			groupTextColor = Color.parseColor(mGroupTextColorInput.getText()
																	.toString()
																	.toLowerCase());
		if (mChildBackgroundColorInput.getText()
										.length() > 0)
			childBgColor = Color.parseColor(mChildBackgroundColorInput.getText()
																		.toString()
																		.toLowerCase());

		if (mChildTextColorInput.getText()
								.length() > 0)
			childTextColor = Color.parseColor(mChildTextColorInput.getText()
																	.toString()
																	.toLowerCase());
		if (mGroupBackgroundColorInput.getText()
										.length() > 0)
			groupBgColor = Color.parseColor(mGroupBackgroundColorInput.getText()
																		.toString()
																		.toLowerCase());
	}


	private void applyColors(){

		View listItem;
		ViewHolder mHolder;

		retrieveColors();

		int packedPositionType, groupPosition, childPosition;

		// These are both implemented in AdapterView which means
		// they're 'raw'/'flat' list positions
		int firstVis = mList.getFirstVisiblePosition();
		int lastVis = mList.getLastVisiblePosition();
		int count = lastVis
					- firstVis;

		long packedPosition;

		while (count >= 0){ // looping through visible list items which
							// are the only items that will need to be
							// refreshed. The adapter's getView will
							// take care of all non-visible items when
							// the list is scrolled

			listItem = mList.getChildAt(count);// getChildAt(pos) is
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

				// Returns a packed position from the 'raw'/'flat' position we
				// got above
				packedPosition = mList.getExpandableListPosition(count
																	+ firstVis);

				// ExpandableListView has static helpers to extract the type
				// (group or child position) and the group and/or child
				// positions from a packed position
				packedPositionType = ExpandableListView.getPackedPositionType(packedPosition);

				if (packedPositionType != ExpandableListView.PACKED_POSITION_TYPE_NULL){

					groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);

					if (packedPositionType == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
						childPosition = ExpandableListView.getPackedPositionChild(packedPosition);
						listItem.setBackgroundColor(childBgColor); // Setting
																	// our
						// user input
						// background
						// color
						mHolder.mText.setTextColor(childTextColor);// Setting
																	// our
						// user input
						// text color
					}else{

						listItem.setBackgroundColor(groupBgColor); // Setting
																	// our
																	// user
																	// input
																	// background
																	// color
						mHolder.mText.setTextColor(groupTextColor);// Setting
																	// our
																	// user
																	// input
																	// text
																	// color
					}
				}else
					Log.d(	TAG,
							"Packed position type was null.");
			}else
				Log.d(	TAG,
						"getChildAt didn't retrieve a non-null view");


			count--;
		}
	}


	class MyAdapter	extends
					BaseExpandableListAdapter{

		private HashMap<Character, List<WorldCity>>	mData;
		private Context								ctx;


		public MyAdapter(Context ctx){

			this.ctx = ctx;

			mData = SeedDataUtil.getAphabetGroupedCities(	ctx,
															15);
		}

		@Override
		public View getGroupView(int groupPosition,
									boolean isExpanded,
									View convertView,
									ViewGroup parent){

			ViewHolder mHolder;

			if (convertView == null){
				convertView = getLayoutInflater().inflate(
															android.R.layout.simple_expandable_list_item_1,
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
			convertView.setBackgroundColor(groupBgColor);
			mHolder.mText.setTextColor(groupTextColor);

			mHolder.mText.setText(getGroup(groupPosition).toString()
															.toUpperCase());

			return convertView;
		}


		@Override
		public View getChildView(int groupPosition,
									int childPosition,
									boolean isLastChild,
									View convertView,
									ViewGroup parent){

			ViewHolder mHolder;

			if (convertView == null){
				convertView = getLayoutInflater().inflate(
															android.R.layout.simple_expandable_list_item_1,
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
			convertView.setBackgroundColor(childBgColor);
			mHolder.mText.setTextColor(childTextColor);

			mHolder.mText.setText(getChild(	groupPosition,
											childPosition).toString());

			return convertView;
		}


		@Override
		public int getGroupCount(){

			return mData.keySet()
						.size();
		}


		@Override
		public int getChildrenCount(int groupPosition){

			return mData.get((char) ('a'
								+ groupPosition))
						.size();
		}


		@Override
		public Object getGroup(int groupPosition){

			return (char) ('a'
			+ groupPosition);
		}


		@Override
		public Object getChild(int groupPosition, int childPosition){

			return mData.get((char) ('a'
								+ groupPosition))
						.get(childPosition);
		}


		@Override
		public long getGroupId(int groupPosition){

			return groupPosition;
		}


		@Override
		public long getChildId(int groupPosition, int childPosition){

			return mData.get((char) ('a'
								+ groupPosition))
						.get(childPosition)
						.getId();
		}


		@Override
		public boolean hasStableIds(){

			return true;
		}


		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition){

			return false;
		}

	}
}
