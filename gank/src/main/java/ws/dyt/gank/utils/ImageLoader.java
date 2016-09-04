package ws.dyt.gank.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by yangxiaowei on 16/7/11.
 */
public class ImageLoader {

    private Context mContext;

    private static  ImageLoader mInstance;

    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();
    }


    public static ImageLoader newInstance(Context context) {
        if(mInstance == null) {
            synchronized (ImageLoader.class) {
                if(mInstance == null) {
                    mInstance = new ImageLoader(context);
                }
            }
        }
        return mInstance;
    }


    public void load(ImageView imageView,String imageUrl,int placeHolderResId) {

        Glide.with(mContext).load(imageUrl).placeholder(placeHolderResId).into(imageView);

    }


    public void loadWithCircle(ImageView imageView,String imageUrl,int placeHolderResId) {

        Glide.with(mContext).load(imageUrl).transform(new GlideCircleTransform(mContext)).placeholder(placeHolderResId).into(imageView);

    }



}
