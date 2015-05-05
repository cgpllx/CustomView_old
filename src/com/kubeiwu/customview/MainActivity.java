package com.kubeiwu.customview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kubeiwu.customview.progress.KMultistateLayout;
import com.kubeiwu.customview.progress.KMultistateListView;
import com.kubeiwu.customview.progress.core.IKMultistateClickListener;

public class MainActivity extends Activity {
	KMultistateListView listview;
	MyAdapter adatper;
	KMultistateLayout kprogresslayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (KMultistateListView) findViewById(R.id.listview);
		kprogresslayout = (KMultistateLayout) findViewById(R.id.kprogresslayout);
		// kprogresslayout.showLoadingView();
		adatper = new MyAdapter();
		listview.setAdapter(adatper);
		adatper.setItems(20);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				adatper.setItems(20);
				// kprogresslayout.cancelAll();
				// kprogresslayout.showEmptyView();
				// kprogresslayout.showErrorView();
				// listview.showEmptyView();
			}
		}, 2000);
		// listview.initViews();
		listview.setMultistateClickListener(new IKMultistateClickListener() {

			@Override
			public void onLoadingViewClick() {

			}

			@Override
			public void onErrorViewClick() {

			}

			@Override
			public void onEmptyViewClick() {

			}
		});
		// mLoadingView.
		// listview.showLoadingView();
	}

	class MyAdapter extends BaseAdapter {
		int count = 0;

		public void setItems(int count) {
			this.count = count;
			notifyDataSetChanged();
		}

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textview = new TextView(MainActivity.this);
			textview.setText("position" + position);
			return textview;
		}

	}
}
