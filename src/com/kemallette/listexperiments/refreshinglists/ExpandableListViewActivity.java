package com.kemallette.listexperiments.refreshinglists;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.kemallette.listexperiments.R;

public class ExpandableListViewActivity	extends
										Activity implements
												OnClickListener,
												OnGroupClickListener,
												OnChildClickListener,
												OnGroupCollapseListener,
												OnGroupExpandListener{

	private ExpandableListView	mList;


	@Override
	protected void onCreate(Bundle savedInstanceState){

		super.onCreate(savedInstanceState);
		setContentView(R.layout.refresh_expandablelistview_layout);

		initViews();
	}


	@Override
	public void onClick(View v){

		switch(v.getId()){

			case R.id.tester:
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

		Button mExpandableTesterButton = (Button) findViewById(R.id.tester);
		mExpandableTesterButton.setOnClickListener(this);

		mList = (ExpandableListView) findViewById(R.id.expandable_list);
		mList.setOnChildClickListener(this);
		mList.setOnGroupClickListener(this);
		mList.setOnGroupExpandListener(this);
		mList.setOnGroupCollapseListener(this);
	}

}
