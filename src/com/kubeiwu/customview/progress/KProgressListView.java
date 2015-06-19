package com.kubeiwu.customview.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kubeiwu.customview.R;
import com.kubeiwu.customview.progress.core.KProgressClickListener;
import com.kubeiwu.customview.pulltorefresh.listview.KListView;

public class KProgressListView extends KListView {
	public static final String TAG = KProgressListView.class.getName();
	private View mLoadingView;
	private View mEmptyView;
	private View mErrorView;
	private boolean isInited;;

	public KProgressListView(Context context, AttributeSet attrs) {
		// R.attr.kProgressListViewStyle从系统中获取样式
		this(context, attrs, R.attr.kProgressListViewStyle);
	}

	public KProgressListView(Context context) {
		this(context, null);
	}

	public KProgressListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		// TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KProgressListView);
		
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.KProgressListView, defStyle, 0);

		int loadingViewResId = a.getResourceId(R.styleable.KProgressListView_loadingView, R.layout.kprogresslistview_loadingview);// 正在加载的view
		int emptyViewResId = a.getResourceId(R.styleable.KProgressListView_emptyView, R.layout.kprogresslistview_emptyview);// 空数据的view
		int errorViewResId = a.getResourceId(R.styleable.KProgressListView_errorView, R.layout.kprogresslistview_errorview);// 错误的view

		if (loadingViewResId > 0) {
			mLoadingView = mLayoutInflater.inflate(loadingViewResId, null);
		}
		if (emptyViewResId > 0) {
			mEmptyView = mLayoutInflater.inflate(emptyViewResId, null);
		}
		if (errorViewResId > 0) {
			mErrorView = mLayoutInflater.inflate(errorViewResId, null);
		}
		a.recycle();
	}

	public View getLoadingView() {
		return mLoadingView;
	}

	public View getEmptyView() {
		return mEmptyView;
	}

	public View getErrorView() {
		return mErrorView;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (!isInited) {
			isInited = true;
			initKProgress();
		}
	}

	/**
	 * 默认值显示mLoadingView，这个最常用
	 */
	public void initKProgress() {
		ViewGroup parent = (ViewGroup) getParent();
		if (parent == null) {
			throw new IllegalStateException(getClass().getSimpleName() + " is not attached to parent view.");
		}

		ViewGroup container = getContainerView(parent);
		container.removeAllViews();

		parent.removeView(container);
		parent.addView(container);

		if (mEmptyView != null) {
			container.addView(mEmptyView);
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
			container.addView(mErrorView);
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
			container.addView(mLoadingView);
			mLoadingView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (kProgressClickListener != null) {
						kProgressClickListener.onLoadingViewClick();
					}
				}
			});
			mLoadingView.setVisibility(View.VISIBLE);
		}

		super.setEmptyView(container);
	}

	// ================================================================================
	// State Handling
	// ================================================================================

	public static interface State {
		int LOADING = 0, EMPTY = 1, ERROR = 2;
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

	/**
	 * 如果在代码中设置布局时候用到Tag
	 * 
	 * @param parent
	 * @return
	 */
	// private ViewGroup getContainerView() {
	// ViewGroup container = createContainerView();
	// return container;
	// }
	private ViewGroup getContainerView(ViewGroup parent) {
		ViewGroup container = findContainerView(parent);
		if (container == null) {
			container = createContainerView();
		}
		return container;
	}

	private ViewGroup findContainerView(ViewGroup parent) {
		return (ViewGroup) parent.findViewWithTag(TAG);
	}

	/**
	 * 创建FrameLayout布局
	 * 
	 * @return
	 */
	private ViewGroup createContainerView() {
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout container = new FrameLayout(getContext());
		container.setTag(TAG);
		container.setLayoutParams(lp);
		return container;
	}

	private KProgressClickListener kProgressClickListener;

	public void setKProgressClickListener(KProgressClickListener progressClickListener) {
		this.kProgressClickListener = progressClickListener;
	}
}
