package com.example.weathertrack.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.weathertrack.R;
import com.example.weathertrack.model.WeatherData;
import com.example.weathertrack.viewmodel.WeatherViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeatherDetailActivity extends AppCompatActivity {
    private TextView tvDate, tvTemp, tvHumidity, tvCondition;
    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        tvDate = findViewById(R.id.tvDate);
        tvTemp = findViewById(R.id.tvTempDetail);
        tvHumidity = findViewById(R.id.tvHumidity);
        tvCondition = findViewById(R.id.tvConditionDetail);

        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        long timestamp = getIntent().getLongExtra("timestamp", -1);
        if (timestamp != -1) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            viewModel.getWeatherForDay(year, month, day).observe(this, new Observer<List<WeatherData>>() {
                @Override
                public void onChanged(List<WeatherData> weatherDataList) {
                    if (weatherDataList != null && !weatherDataList.isEmpty()) {
                        WeatherData data = weatherDataList.get(0);
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
                        tvDate.setText(sdf.format(data.getTimestamp()));
                        tvTemp.setText(String.format(Locale.getDefault(), "Temperature: %.1f°C", data.getTemperature()));
                        tvHumidity.setText(String.format(Locale.getDefault(), "Humidity: %d%%", data.getHumidity()));
                        tvCondition.setText(String.format("Condition: %s", data.getCondition()));
                    }
                }
            });
        }
    }
}
