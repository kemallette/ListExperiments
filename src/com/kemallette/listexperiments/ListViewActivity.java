/**
 * 
 */
package com.kemallette.listexperiments;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author kyle
 * 
 */
public class ListViewActivity extends Activity implements OnItemClickListener,
		OnClickListener {

	protected class Item {

		final int id;
		final String title;

		public Item(final int id, final String mTitle) {
			super();

			this.id = id;
			this.title = mTitle;
		}

		@Override
		public String toString() {
			return title;
		}

	}

	private static List<Item> ITEM_LIST = new ArrayList<Item>(25);
	private ListView mList;
	private MyArrayAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_layout);

		if (ITEM_LIST.isEmpty())
			populateItemListData();
		
		initViews();
		populateList();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.tester:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

	private void initViews() {
		Button mTesterButton = (Button) findViewById(R.id.tester);
		mTesterButton.setOnClickListener(this);

		mList = (ListView) findViewById(android.R.id.list);
		mList.setOnItemClickListener(this);
		mList.setAdapter(mAdapter);

	}

	private void populateItemListData() {

	}

	private void populateList() {
		mAdapter = new MyArrayAdapter(this,
				android.R.layout.simple_list_item_1, ITEM_LIST);
	}

	class MyArrayAdapter extends ArrayAdapter<Item> {

		public MyArrayAdapter(Context context, int textViewResourceId,
				List<Item> objects) {
			super(context, textViewResourceId, objects);
		}

	}

}
