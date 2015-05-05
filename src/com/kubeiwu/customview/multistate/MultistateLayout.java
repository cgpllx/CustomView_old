package com.kubeiwu.customview.multistate;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.kubeiwu.customview.R;
import com.kubeiwu.customview.multistate.core.IMultistateClickListener;

public class MultistateLayout extends FrameLayout {
	private View mLoadingView;
	private View mEmptyView;
	private View mErrorView;

	public MultistateLayout(Context context) {
		this(context, null);
	}

	public MultistateLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MultistateLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.progresslayout_layout, this, true);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultistateLayout);

		int progressViewRef_id = a.getResourceId(R.styleable.MultistateLayout_loadingView, R.layout.progresslayout_loadingview);
		mLoadingView = inflater.inflate(progressViewRef_id, this, false);
		int errorViewRef_id = a.getResourceId(R.styleable.MultistateLayout_errorView, R.layout.progresslayout_errorview);
		mErrorView = inflater.inflate(errorViewRef_id, this, false);
		int emptyViewRef_id = a.getResourceId(R.styleable.MultistateLayout_emptyView, R.layout.progresslayout_errorview);
		mEmptyView = inflater.inflate(emptyViewRef_id, this, false);

		a.recycle();
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		initMultistate();
	}

	public void initMultistate() {
		
		if (mEmptyView != null) {
			this.addView(mEmptyView);
			mEmptyView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (multistateClickListener != null) {
						multistateClickListener.onEmptyViewClick();
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
					if (multistateClickListener != null) {
						multistateClickListener.onErrorViewClick();
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
					if (multistateClickListener != null) {
						multistateClickListener.onLoadingViewClick();
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

	private IMultistateClickListener multistateClickListener;

	public void setMultistateClickListener(IMultistateClickListener multistateClickListener) {
		this.multistateClickListener = multistateClickListener;
	}
}
