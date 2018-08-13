package com.example.openweathermapforecast.models;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.openweathermapforecast.R;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

public class Forecast extends AbstractItem<Forecast, Forecast.ViewHolder>{
    private final String day;
    private final String iconUrl;
    private final String temperature;
    private final String condition;

    public Forecast(String day, String iconUrl, String temperature, String condition) {
        this.day = day;
        this.iconUrl = iconUrl;
        this.temperature = temperature;
        this.condition = condition;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(@NonNull View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.forecast_item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_forecast;
    }

    public String getDay() {
        return day;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getCondition() {
        return condition;
    }

    protected static class ViewHolder extends FastAdapter.ViewHolder<Forecast> {
        final TextView tvDate;
        final TextView tvCondition;
        final TextView tvTemp;
        final ImageView ivIcon;

        ViewHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_forecast_date);
            tvCondition = itemView.findViewById(R.id.tv_forecast_condition);
            tvTemp = itemView.findViewById(R.id.tv_forecast_temp);
            ivIcon = itemView.findViewById(R.id.iv_forecast_icon);
        }

        @Override
        public void bindView(@NonNull Forecast item, @NonNull List<Object> payloads) {
            tvDate.setText(item.day);
            tvCondition.setText(item.condition);
            tvTemp.setText(item.temperature);
            Glide.with(itemView.getContext())
                    .load(item.iconUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivIcon);
        }

        @Override
        public void unbindView(@NonNull Forecast item) {
            Glide.with(itemView.getContext()).clear(ivIcon);
        }
    }
}
