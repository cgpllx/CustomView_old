package com.kubeiwu.customview.multistate;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kubeiwu.customview.R;
import com.kubeiwu.customview.multistate.core.KProgressClickListener;

public class KProgressLayout extends FrameLayout {
	private View mLoadingView;
	private View mEmptyView;
	private View mErrorView;
	private boolean isInited;

	public KProgressLayout(Context context) {
		this(context, null);
	}

	public KProgressLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public KProgressLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.kprogresslayout_layout, this, true);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KProgressLayout);

		int progressViewRef_id = a.getResourceId(R.styleable.KProgressLayout_loadingView, R.layout.kprogresslayout_loadingview);
		mLoadingView = inflater.inflate(progressViewRef_id, this, false);
		int errorViewRef_id = a.getResourceId(R.styleable.KProgressLayout_errorView, R.layout.kprogresslayout_errorview);
		mErrorView = inflater.inflate(errorViewRef_id, this, false);
		int emptyViewRef_id = a.getResourceId(R.styleable.KProgressLayout_emptyView, R.layout.kprogresslayout_errorview);
		mEmptyView = inflater.inflate(emptyViewRef_id, this, false);

		a.recycle();
	}

	int count = 0;

	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params) {
		super.addView(child, index, params);
		if (!isInited) {
			isInited = true;
			initKProgress();
		}
	}

	public void initKProgress() {

		if (mEmptyView != null) {
			this.addView(mEmptyView);
			mEmptyView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (kProgressClickListener != null) {
						kProgressClickListener.onEmptyViewClick();
					}
				}
			});
			mEmptyView.setVisibility(View.GONE);
		}

		if (mErrorView != null) {
			this.addView(mErrorView);
			mErrorView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (kProgressClickListener != null) {
						kProgressClickListener.onErrorViewClick();
					}
				}
			});
			mErrorView.setVisibility(View.GONE);
		}
		if (mLoadingView != null) {
			this.addView(mLoadingView);
			mLoadingView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (kProgressClickListener != null) {
						kProgressClickListener.onLoadingViewClick();
					}
				}
			});
			mLoadingView.setVisibility(View.GONE);
		}
	}

	public static interface State {
		int LOADING = 0, EMPTY = 1, ERROR = 2, CANCEL = 3;
	}

	public void showLoadingView() {
		showView(State.LOADING);
	}

	public void showEmptyView() {
		showView(State.EMPTY);
	}

	public void showErrorView() {
		showView(State.ERROR);
	}

	public void cancelAll() {
		showView(State.CANCEL);
	}

	public void showView(int state) {

		boolean showLoadingView = false;
		boolean showEmptyView = false;
		boolean showErrorView = false;

		switch (state) {
			case State.LOADING:
				showLoadingView = true;
				break;
			case State.EMPTY:
				showEmptyView = true;
				break;
			case State.ERROR:
				showErrorView = true;
				break;
			case State.CANCEL:
				break;
		}

		if (mLoadingView != null) {
			mLoadingView.setVisibility(showLoadingView ? View.VISIBLE : View.GONE);
		}

		if (mEmptyView != null) {
			mEmptyView.setVisibility(showEmptyView ? View.VISIBLE : View.GONE);
		}

		if (mErrorView != null) {
			mErrorView.setVisibility(showErrorView ? View.VISIBLE : View.GONE);
		}
	}

	private KProgressClickListener kProgressClickListener;

	public void setMultistateClickListener(KProgressClickListener kProgressClickListener) {
		this.kProgressClickListener = kProgressClickListener;
	}
}
