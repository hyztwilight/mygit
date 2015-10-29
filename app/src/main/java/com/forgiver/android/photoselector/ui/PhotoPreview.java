package com.forgiver.android.photoselector.ui;
/**
 * 
 * @author Aizaz AZ
 *
 */

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.forgiver.android.R;
import com.forgiver.android.photoselector.model.PhotoModel;
import com.forgiver.android.polites.GestureImageView;


public class PhotoPreview extends LinearLayout implements OnClickListener {

	private ProgressBar pbLoading;
	private GestureImageView ivContent;
	private OnClickListener l;

	public PhotoPreview(Context context) {
		super(context);
		LayoutInflater.from(context).inflate(R.layout.view_photopreview, this, true);

		pbLoading = (ProgressBar) findViewById(R.id.pb_loading_vpp);
		ivContent = (GestureImageView) findViewById(R.id.iv_content_vpp);
		ivContent.setOnClickListener(this);
	}

	public PhotoPreview(Context context, AttributeSet attrs, int defStyle) {
		this(context);
	}

	public PhotoPreview(Context context, AttributeSet attrs) {
		this(context);
	}



	public void loadImage(PhotoModel photoModel) {
		loadImage("file://" + photoModel.getOriginalPath());
	}

	private void loadImage(final String path) {
		ControllerListener controllerListener = new BaseControllerListener<ImageInfo>(){
			@Override
			public void onFinalImageSet(
					String id,
					@Nullable ImageInfo imageInfo,
					@Nullable Animatable animatable) {
				ivContent.setImageURI(Uri.parse(path));
				pbLoading.setVisibility(View.GONE);

			}

			@Override
			public void onFailure(String id, Throwable throwable) {
				ivContent.setImageDrawable(getResources().getDrawable(R.drawable.ic_picture_loadfailed));
				pbLoading.setVisibility(View.GONE);
			}

			@Override
			public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
				super.onIntermediateImageSet(id, imageInfo);
			}
		};

		DraweeController controller = Fresco.newDraweeControllerBuilder()
				.setControllerListener(controllerListener)
				.setUri(Uri.parse(path))
				.build();

		ivContent.setController(controller);
	}

	@Override
	public void setOnClickListener(OnClickListener l) {
		this.l = l;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.iv_content_vpp && l != null)
			l.onClick(ivContent);
	};

}
