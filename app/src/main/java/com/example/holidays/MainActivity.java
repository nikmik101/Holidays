package com.example.holidays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.holidays.model.ListHolidaysInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetDataFromInternet.AsyncResponce, Adapter.ListItemClickListener{

    private static final String TAG = "MainActivity";
    private Toast toast;
    private ListHolidaysInfo listHolidaysInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            URL url = new URL("https://calendarific.com/api/v2/holidays?api_key=6a2b2af26699c23048f7098dce1e8faa80b336d4&country=RU&year=2022");

            new GetDataFromInternet(this).execute(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void processFinish(String output) {

        try {
            JSONObject outputJSON = new JSONObject(output);
            JSONObject responceJSON = outputJSON.getJSONObject("response");
            JSONArray array = responceJSON.getJSONArray("holidays");
            int lenght = array.length();

            listHolidaysInfo = new ListHolidaysInfo(lenght);

            ArrayList <String> namesHolidays = new ArrayList<String>();

            for (int i = 0; i < lenght; ++i){

                JSONObject obj = array.getJSONObject(i);
                String name = obj.getString("name");

                JSONObject data_obj = obj.getJSONObject("date");
                String data = data_obj.getString("iso");

                namesHolidays.add(name);

                Log.d(TAG, "processFinish:" + name + " " + data);

                listHolidaysInfo.addHoliday(name, data, i);
            }

            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, namesHolidays);
            //ListView listHolidays = findViewById(R.id.listHolidays);
            //listHolidays.setAdapter(adapter);

            RecyclerView recyclerView = findViewById(R.id.recyler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new Adapter(listHolidaysInfo, lenght, this));

        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        CharSequence text = "This holiday is " + listHolidaysInfo.listHolidaysInfo[clickedItemIndex].getHoliday_name();
        int duration = Toast.LENGTH_SHORT;
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(this, text, duration);
        toast.show();
    }
}