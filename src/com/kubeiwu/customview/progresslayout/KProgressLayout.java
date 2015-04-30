package com.kubeiwu.customview.progresslayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kubeiwu.customview.R;
import com.kubeiwu.customview.multistatelistview.KMultiStateListView.State;

public class KProgressLayout extends FrameLayout {
	private View mMainView;
	private View mLoadingView;
	private View mEmptyView;
	private View mErrorView;

	public KProgressLayout(Context context) {
		this(context, null);
	}

	public KProgressLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public KProgressLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.progresslayout_layout, this, true);
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressLayout);

		int progressViewRef_id = attributes.getResourceId(R.styleable.ProgressLayout_loadingView, R.layout.progresslayout_loadingview);
		mLoadingView = inflater.inflate(progressViewRef_id, this, false);
		int errorViewRef_id = attributes.getResourceId(R.styleable.ProgressLayout_errorView, R.layout.progresslayout_errorview);
		mErrorView = inflater.inflate(errorViewRef_id, this, false);
		int emptyViewRef_id = attributes.getResourceId(R.styleable.ProgressLayout_emptyView, R.layout.progresslayout_errorview);
		mEmptyView = inflater.inflate(emptyViewRef_id, this, false);

		attributes.recycle();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		init();
	} 

	public void init() {
		this.addView(mErrorView);
		this.addView(mEmptyView);
		this.addView(mLoadingView);
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
	public void cancelProgress() {
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

}
