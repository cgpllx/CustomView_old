package com.kubeiwu.customview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kubeiwu.customview.multistatelistview.KMultiStateListView;
import com.kubeiwu.customview.multistatelistview.KMultiStateListView.IKMultiStateListViewListener;
import com.kubeiwu.customview.progresslayout.KProgressLayout;

public class MainActivity extends Activity {
	KMultiStateListView listview;
	MyAdapter adatper;
	KProgressLayout kprogresslayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (KMultiStateListView) findViewById(R.id.listview);
		kprogresslayout = (KProgressLayout) findViewById(R.id.kprogresslayout);
		kprogresslayout.showLoadingView();
		adatper = new MyAdapter();
		listview.setAdapter(adatper);
		adatper.setItems(20);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				adatper.setItems(20);
				kprogresslayout.cancelProgress();
				kprogresslayout.showEmptyView();
				kprogresslayout.showErrorView();
//				listview.showEmptyView();
			}
		}, 2000);
//		listview.initViews();
		listview.setMultiStateListViewListener(new IKMultiStateListViewListener() {
			
			@Override
			public void onLoadingViewClick() {
				System.out.println("onLoadingViewClick");
			}
			
			@Override
			public void onErrorViewClick() {
				System.out.println("onErrorViewClick");
				
			}
			
			@Override
			public void onEmptyViewClick() {
				System.out.println("onEmptyViewClick");
				
			}
		});
//		mLoadingView.
//		listview.showLoadingView();
	}

	class MyAdapter extends BaseAdapter {
		int count=0;
		public void setItems(int count) {
			this.count=count;
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
