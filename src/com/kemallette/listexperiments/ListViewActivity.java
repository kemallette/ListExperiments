/**
 * 
 */
package com.kemallette.listexperiments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author kyle
 *
 */
public class ListViewActivity extends Activity implements
		OnItemClickListener {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_layout);
		
	}

	/* (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

	}

}
