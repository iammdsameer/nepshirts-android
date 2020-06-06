package com.nepshirts.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nepshirts.android.models.SliderModel;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.SliderViewHolder> {
    Context context;
    List<SliderModel> images;

    public ImageSliderAdapter(Context context, List<SliderModel> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_view,parent,false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        viewHolder.sliderImage.setImageResource(images.get(position).getImage());
    }

    @Override
    public int getCount() {
        return images.size();
    }

    class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView sliderImage;
        public SliderViewHolder(View itemView) {
            super(itemView);
            sliderImage = itemView.findViewById(R.id.slider_image);

        }
    }
}

