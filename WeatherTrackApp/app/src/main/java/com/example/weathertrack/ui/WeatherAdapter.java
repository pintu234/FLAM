package com.example.weathertrack.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private List<WeatherData> weatherList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WeatherData data);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setWeatherList(List<WeatherData> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_day, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        WeatherData data = weatherList.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        holder.tvDateTime.setText(sdf.format(data.getTimestamp()));
        holder.tvTemp.setText(String.format(Locale.getDefault(), "%.1f°C", data.getTemperature()));
    }

    @Override
    public int getItemCount() {
        return weatherList != null ? weatherList.size() : 0;
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView tvDateTime, tvTemp;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onItemClick(weatherList.get(getAdapterPosition()));
                    }
                }
            });
        }
    }
}
