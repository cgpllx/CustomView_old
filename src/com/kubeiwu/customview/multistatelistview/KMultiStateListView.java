package com.kubeiwu.customview.multistatelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kubeiwu.customview.R;
import com.kubeiwu.customview.pulltorefresh.listview.XListView;

public class KMultiStateListView extends XListView {
	private View mLoadingView;
	private View mEmptyView;
	private View mErrorView;

	public KMultiStateListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public KMultiStateListView(Context context) {
		this(context, null);
	}

	public KMultiStateListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiStateListView);
		int loadingViewResId = a.getResourceId(R.styleable.MultiStateListView_loadingView, R.layout.multistatelistview_loadingview);// 正在加载的view
		int emptyViewResId = a.getResourceId(R.styleable.MultiStateListView_emptyView, R.layout.multistatelistview_emptyview);// 空数据的view
		int errorViewResId = a.getResourceId(R.styleable.MultiStateListView_errorView, R.layout.multistatelistview_errorview);// 错误的view

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
		initEmptyViews();
	}

	/**
	 * 默认值显示mLoadingView，这个最常用
	 */
	public void initEmptyViews() {
		ViewGroup parent = (ViewGroup) getParent();
		if (parent == null) {
			throw new IllegalStateException(getClass().getSimpleName() + " is not attached to parent view.");
		}

		ViewGroup container = getContainerView();
		container.removeAllViews();

		parent.removeView(container);
		parent.addView(container);

		if (mEmptyView != null) {
			container.addView(mEmptyView);
			mEmptyView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (multiStateListViewListener != null) {
						multiStateListViewListener.onEmptyViewClick();
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
					if (multiStateListViewListener != null) {
						multiStateListViewListener.onErrorViewClick();
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
					if (multiStateListViewListener != null) {
						multiStateListViewListener.onLoadingViewClick();
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
	private ViewGroup getContainerView() {
		ViewGroup container = createContainerView();
		return container;
	}

	/**
	 * 创建FrameLayout布局
	 * 
	 * @return
	 */
	private ViewGroup createContainerView() {
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		FrameLayout container = new FrameLayout(getContext());
		container.setLayoutParams(lp);
		return container;
	}

	private IKMultiStateListViewListener multiStateListViewListener;

	public void setMultiStateListViewListener(IKMultiStateListViewListener multiStateListViewListener) {
		this.multiStateListViewListener = multiStateListViewListener;
	}

	public interface IKMultiStateListViewListener {
		public void onLoadingViewClick();

		public void onEmptyViewClick();

		public void onErrorViewClick();
	}

	/**
	 * IKMultiStateListViewListener的空实现
	 * 
	 * @author Administrator
	 *
	 */
	public class BaseMultiStateListViewListener implements IKMultiStateListViewListener {
		@Override
		public void onLoadingViewClick() {

		}

		@Override
		public void onEmptyViewClick() {
		}

		@Override
		public void onErrorViewClick() {

		}

	}
}
