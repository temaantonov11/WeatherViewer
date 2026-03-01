package com.example.weatherviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button viewWeather;

    String[] times = {"Сегодня", "Завтра", "3 дня", "10 дней", "2 недели"};
    String[] cities = { "Нижний Новгород", "Москва", "Санкт-Петербург", "Казань", "Екатеринбург" };
    Map<String, String> cityToUrlCode = new HashMap<>() {{
       put("Нижний Новгород", "weather-nizhny-novgorod-4355");
       put("Москва", "weather-moscow-4368");
       put("Санкт-Петербург", "weather-sankt-peterburg-4079");
       put("Казань", "weather-kazan-4364");
       put("Екатеринбург", "weather-yekaterinburg-4517");
    }};
    Map<String, String> timeToUrlCode = new HashMap<>() {{
       put("Сегодня", "");
       put("Завтра", "tomorrow");
       put("3 дня", "3-days");
       put("10 дней", "10-days");
       put("2 недели", "2-weeks");
    }};
    String selectedCity;
    String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner cityChoice = findViewById(R.id.cityChoice);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cities);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityChoice.setAdapter(cityAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListenerCity = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        cityChoice.setOnItemSelectedListener(itemSelectedListenerCity);

        Spinner timeChoice = findViewById(R.id.timeChoice);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeChoice.setAdapter(timeAdapter);

        AdapterView.OnItemSelectedListener itemSelectedListenerTime = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = (String)parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        timeChoice.setOnItemSelectedListener(itemSelectedListenerTime);

        viewWeather = (Button)findViewById(R.id.viewWeather);
        viewWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.gismeteo.ru/" + cityToUrlCode.get(selectedCity) + "/" + timeToUrlCode.get(selectedTime);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
//                Toast.makeText(MainActivity.this, selectedCity != null ? selectedCity : "Значение не выбрано", Toast.LENGTH_SHORT).show();
            }
        });
    }
}