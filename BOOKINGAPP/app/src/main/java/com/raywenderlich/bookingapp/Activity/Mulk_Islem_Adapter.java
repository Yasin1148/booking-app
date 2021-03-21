package com.raywenderlich.bookingapp.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.raywenderlich.bookingapp.R;

import java.util.List;

public class Mulk_Islem_Adapter extends ArrayAdapter<Mulk_islem> {

    private static final String TAG = "Mulk_Islem_Adapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder{
        TextView baslik;
        TextView Il;
        TextView Ilce;
        ImageView img;
    }

    public Mulk_Islem_Adapter(Context context, int resource,  List<Mulk_islem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource= resource;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        setupImageLoader();

        String baslik = getItem(position).getBaslik();
        String Il = getItem(position).getIl();
        String Ilce = getItem(position).getIlce();
        byte[]  imgURL = getItem(position).getImgURL();

        final View result;
        ViewHolder holder;

        if (convertView == null)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(mResource, parent , false);
            holder = new ViewHolder();
            holder.baslik = convertView.findViewById(R.id.baslik);
            holder.Il= convertView.findViewById(R.id.Il);
            holder.Ilce = convertView.findViewById(R.id.Ilce);
            holder.img = convertView.findViewById(R.id.imageView_gorsel);

            result = convertView;
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,(position > lastPosition) ? R.anim.slide_down : R.anim.slide_up);
        result.startAnimation(animation);
        lastPosition = position;

        ImageLoader imageLoader = ImageLoader.getInstance();
        int defaultImage = mContext.getResources().getIdentifier("@drawable/image_failed", null , mContext.getPackageName());
        DisplayImageOptions options =new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .showImageOnLoading(defaultImage).build();

        holder.baslik.setText(baslik);
        holder.Il.setText(Il);
        holder.Ilce.setText(Ilce);
        byte[] img=imgURL;
        Bitmap bitmap= BitmapFactory.decodeByteArray(img,0,img.length);
        holder.img.setImageBitmap(bitmap);

        return convertView;
    }
    private  void setupImageLoader()
    {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(configuration);
    }
}