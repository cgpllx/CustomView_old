package com.kubeiwu.customview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kubeiwu.customview.progress.KProgressLayout;
import com.kubeiwu.customview.progress.KProgressListView;
import com.kubeiwu.customview.progress.core.KProgressClickListener;
//github.com/cgpllx/CustomView.git
import com.kubeiwu.customview.pulltorefresh.listview.KListView.IKListViewListener;

public class MainActivity extends Activity {
	KProgressListView listview;
	MyAdapter adatper;
	KProgressLayout kprogresslayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listview = (KProgressListView) findViewById(R.id.listview);
		kprogresslayout = (KProgressLayout) findViewById(R.id.kprogresslayout);
		 kprogresslayout.cancelAll();
//		 kprogresslayout.initMultistate();
		adatper = new MyAdapter();
		listview.setAdapter(adatper);
		adatper.setItems(20);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				adatper.setItems(0);
				// kprogresslayout.cancelAll();
				// kprogresslayout.showEmptyView();
//				 kprogresslayout.showErrorView();
				 listview.showEmptyView();
//				new Handler().postDelayed(new Runnable() {
//
//					@Override
//					public void run() {
//						adatper.setItems(20);
//						// kprogresslayout.cancelAll();
//						 kprogresslayout.showLoadingView();
//						// kprogresslayout.showErrorView();
//						// listview.showEmptyView();
//					}
//				}, 3000);
			}
		}, 2000);
		// listview.initViews();
		listview.setKProgressClickListener(new KProgressClickListener() {

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
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setKListViewListener(new IKListViewListener() {
			
			@Override
			public void onRefresh() {
				if(listview.isRefreshing()){
					return;
				}
//				listview.isr
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
//						adatper.setItems(20);
						// kprogresslayout.cancelAll();
						// kprogresslayout.showEmptyView();
						// kprogresslayout.showErrorView();
						// listview.showEmptyView();
						listview.stopRefresh();
					}
				}, 10000);
			}
			
			@Override
			public void onLoadMore() {
				if(listview.isLoading()){
					return;
				}
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
//						adatper.setItems(20);
						// kprogresslayout.cancelAll();
						// kprogresslayout.showEmptyView();
						// kprogresslayout.showErrorView();
						// listview.showEmptyView();
						listview.stopLoadMore();
					}
				}, 10000);
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
