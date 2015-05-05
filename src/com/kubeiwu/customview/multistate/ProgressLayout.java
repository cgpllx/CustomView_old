package com.kubeiwu.customview.multistate;

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

public class ProgressLayout extends FrameLayout {
	private View mMainView;
	private View mloadingView;
	private View mErrorView;

	public ProgressLayout(Context context) {
		this(context, null);
	}

	public ProgressLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.multistatelayout_layout, this, true);
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MultistateLayout);

		int progressLayoutRef = attributes.getResourceId(R.styleable.MultistateLayout_loadingView, R.layout.multistatelayout_loadingview);
		View progressView = inflater.inflate(progressLayoutRef, this, false);

		// this.addView(progressView);

		int errorLayoutRef = attributes.getResourceId(R.styleable.MultistateLayout_errorView, R.layout.multistatelayout_errorview);
		View errorView = inflater.inflate(errorLayoutRef, this, false);
		// this.addView(errorView);

		attributes.recycle();
	}

	public void init() {
		mloadingView = getChildAt(0);
		mErrorView = getChildAt(1);
		mMainView = getChildAt(2);
	}

	/**
	 * Shows the progress UI and hides the container form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mMainView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
			mMainView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mMainView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
				}
			});

			mloadingView.setVisibility(show ? View.VISIBLE : View.GONE);
			mloadingView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mloadingView.setVisibility(show ? View.VISIBLE : View.GONE);
				}
			});

			mErrorView.setVisibility(View.GONE);
			mErrorView.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mErrorView.setVisibility(View.GONE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mloadingView.setVisibility(show ? View.VISIBLE : View.GONE);
			mMainView.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
			mErrorView.setVisibility(View.GONE);
		}
	}

	/**
	 * Shows the Error UI and hides all.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showError() {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mMainView.setVisibility(View.INVISIBLE);
			mMainView.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mMainView.setVisibility(View.INVISIBLE);
				}
			});

			mloadingView.setVisibility(View.GONE);
			mloadingView.animate().setDuration(shortAnimTime).alpha(0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mloadingView.setVisibility(View.GONE);
				}
			});

			mErrorView.setVisibility(View.VISIBLE);
			mErrorView.animate().setDuration(shortAnimTime).alpha(1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mErrorView.setVisibility(View.VISIBLE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mloadingView.setVisibility(View.GONE);
			mMainView.setVisibility(View.INVISIBLE);
			mErrorView.setVisibility(View.VISIBLE);

		}
	}

}
