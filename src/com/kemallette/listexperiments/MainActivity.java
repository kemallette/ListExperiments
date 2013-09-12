package com.kemallette.listexperiments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		initViews();

	}

	@Override
	public void onClick(View v) {
		
		Intent intent;
		
		switch (v.getId()) {
			case R.id.listview_layout:
				intent = new Intent(this, ListViewActivity.class);
				startActivity(intent);
				break;
			case R.id.expandablelistview_layout:
				intent = new Intent(this, ExpandableListViewActivity.class);
				startActivity(intent);
				break;
		}
	}

	private void initViews() {

		Button launchListView = (Button) findViewById(R.id.listview_layout);
		launchListView.setOnClickListener(this);

		Button launchExpandableListView = (Button) findViewById(R.id.expandablelistview_layout);
		launchExpandableListView.setOnClickListener(this);
	}

}
