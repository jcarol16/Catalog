package com.test.grability.cataloggrability.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageManager {

	private static ImageLoader imageLoader;
	private static ImageLoaderConfiguration imageConfig;
	private static DisplayImageOptions imageDisplayOptions;

	public static void init(Activity activity) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			imageLoader.destroy();
		}
		imageLoader.init(getConfig(activity));
	}

	public static ImageLoaderConfiguration getConfig(Activity activity) {
		imageConfig = new ImageLoaderConfiguration.Builder(activity)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory().build();
		return imageConfig;
	}

	public static ImageLoader getImageLoader() {
		return imageLoader;
	}

	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getDisplayOptions() {
		imageDisplayOptions = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheInMemory()
				.cacheOnDisc().build();
		return imageDisplayOptions;
	}

	public static DisplayImageOptions getDisplayNoCacheOptions() {
		imageDisplayOptions = new DisplayImageOptions.Builder().imageScaleType(
				ImageScaleType.IN_SAMPLE_INT).build();
		return imageDisplayOptions;
	}

	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getDisplayOptionsRounded(int rounded) {
		imageDisplayOptions = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheInMemory()
				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(rounded))
				.build();
		return imageDisplayOptions;
	}

	public static DisplayImageOptions getDisplayNoCacheOptionsRounded(
			int rounded) {
		imageDisplayOptions = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.displayer(new RoundedBitmapDisplayer(rounded)).build();
		return imageDisplayOptions;
	}

	public static void displayImage(String url, ImageView imageView,
			DisplayImageOptions displayImageOptions) {
		getImageLoader().displayImage(url, imageView, displayImageOptions);
	}

	public static void displayImage(String url, ImageView imageView,
			DisplayImageOptions displayImageOptions,
			ImageLoadingListener mImageLoadingListener) {
		getImageLoader().displayImage(url, imageView, displayImageOptions,
				mImageLoadingListener);
	}

    public static void displayImage(String url, int[] imageSize, final ImageView imageView,
            DisplayImageOptions displayImageOptions) {
        if(imageSize.length == 2) {
            ImageSize targetSize = new ImageSize(imageSize[0], imageSize[1]);
            imageLoader.loadImage(url, targetSize, displayImageOptions, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageView.setImageBitmap(loadedImage);
                }
            });
        }
    }

	public static void destroy() {
		if (imageLoader.isInited()) {
			imageLoader.destroy();
		}
	}
}
