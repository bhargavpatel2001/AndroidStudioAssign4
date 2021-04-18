package bhargav.patel.n01373029.ui.WebService;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bhargav.patel.n01373029.R;

public class WebServiceFrag extends Fragment {
    private TextView displayJson;
    private EditText zipCode;
    String zip;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_service, container, false);
        displayJson = view.findViewById(R.id.Bhargav_Json);
        Button button = view.findViewById(R.id.BhargavWebBtn);
        zipCode = view.findViewById(R.id.BhargavET);
        zip = zipCode.getText().toString();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = zip.length();

                if (length < 5) {
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Error")
                            .setMessage("5 digits required")
                            .setNegativeButton("Try again", null)
                            .show();

                    zipCode.setError("This Field needs at least 5 digits");
                } else {
                    String url = "http://api.openweathermap.org/data/2.5/weather?";
                    url += "zip" + zip;
                    url += "&appid=bdaa2d98d75cdc4a10a13f4ec81d5be4";
                    new ReadJSONFeedTask().execute(url);
                }
            }
        });
        return view;
    }

    public void getWeather(View view){

    }

    @SuppressLint("StaticFieldLeak")
    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            URL url = null;
            try {
                url = new URL(urls[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            };
            StringBuilder stringBuilder = new StringBuilder();
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream content = new BufferedInputStream(
                            urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(content));
                    String line;
                    while ((line = reader.readLine()) != null){
                        stringBuilder.append(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
              urlConnection.disconnect();;
            }
            return stringBuilder.toString();
        }
        protected void onPostExecute(String result){
            try {
                JSONObject weatherJson = new JSONObject(result);
                JSONArray dataArray1 = weatherJson.getJSONArray("weather");
                String strResults="Weather\n";
                for (int i = 0; i < dataArray1.length(); i++){
                    JSONObject jsonObject = dataArray1.getJSONObject(i);
                    strResults +="id:" +jsonObject.getString("id");
                    strResults +="\nmain:" +jsonObject.getString("main");
                    strResults +="\ndescription" +jsonObject.getString("description");
                }
                JSONObject dataObject = weatherJson.getJSONObject("main");
                strResults += "\nlon: "+dataObject.getString("lon");
                strResults += "\nlat: "+dataObject.getString("lat");
                strResults += "\ntemp: "+dataObject.getString("temp");
                strResults += "\nhumidity: "+dataObject.getString("humidity");
                strResults += "\ntemp_min: "+dataObject.getString("temp_min");
                strResults += "\ntemp_max: "+dataObject.getString("temp_max");

                displayJson.setText(strResults);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}