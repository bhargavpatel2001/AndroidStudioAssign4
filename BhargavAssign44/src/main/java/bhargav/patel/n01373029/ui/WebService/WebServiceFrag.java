//Bhargav Patel N01373029 Section B
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
import android.util.Log;
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
    Button button;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_service, container, false);

        displayJson = view.findViewById(R.id.Bhargav_Json);
        button = view.findViewById(R.id.BhargavWebBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipCode = view.findViewById(R.id.BhargavET);
                zip = zipCode.getText().toString();

                int length = zip.length();

                if (length < 5) {
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.Error)
                            .setMessage(R.string.Required5)
                            .setNegativeButton(R.string.Tryagain, null)
                            .show();

                    zipCode.setError(getString(R.string.setError));
                }
                else if (length == 5){
                    getWeather(view);
                }
            }
        });
        return view;
    }

    public void getWeather(View v){
        zip = zipCode.getText().toString();
        String url = getString(R.string.url);
        url+=getString(R.string.zip)+zip;
        url+=getString(R.string.appid);
        url+=getString(R.string.conversion);
        Log.d(getString(R.string.URL),url);
        new ReadJSONFeedTask().execute(url);
    }

    public String readJSONFeed(String address) {
        URL url = null;
        try {
            url = new URL(address);
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
            InputStream content = new BufferedInputStream(
                    urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(content));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return stringBuilder.toString();
    }

    private class ReadJSONFeedTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return readJSONFeed(urls[0]);
        }
        protected void onPostExecute(String result) {
            try {
                JSONObject weatherJson = new JSONObject(result);
                JSONArray dataArray1= weatherJson.getJSONArray(getString(R.string.weather));
                String strResults=getString(R.string.Weather);
                for (int i = 0; i < dataArray1.length(); i++) {
                    JSONObject jsonObject = dataArray1.getJSONObject(i);
                    strResults +=getString(R.string.id_)+jsonObject.getString(getString(R.string.id));
                    strResults +=getString(R.string.main_)+jsonObject.getString(getString(R.string.main));
                    strResults +=getString(R.string.description_)+jsonObject.getString(getString(R.string.description));
                }

                JSONObject dataObject= weatherJson.getJSONObject(getString(R.string.coord));
                strResults +=getString(R.string.lat_)+dataObject.getString(getString(R.string.lat));
                strResults +=getString(R.string.lon_)+dataObject.getString(getString(R.string.lon));

                JSONObject dataObject1= weatherJson.getJSONObject(getString(R.string.sys));
                strResults +=getString(R.string.country_)+dataObject1.getString(getString(R.string.country));

                JSONObject dataObject2= weatherJson.getJSONObject(getString(R.string.main2));
                strResults +=getString(R.string.temp_)+dataObject2.getString(getString(R.string.temp));
                strResults +=getString(R.string.humidity_)+dataObject2.getString(getString(R.string.humidity));
                strResults +=getString(R.string.temp_min_)+dataObject2.getString(getString(R.string.temp_min));
                strResults +=getString(R.string.temp_max_)+dataObject2.getString(getString(R.string.temp_max));
                strResults +=getString(R.string.zipcode_)+ zip;

                displayJson.setText(strResults);

            } catch (Exception e) {

                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.Error)
                        .setMessage(R.string.Invalid)
                        .setNegativeButton(R.string.tryagain, null)
                        .show();
                e.printStackTrace();
            }
        }
    }
}