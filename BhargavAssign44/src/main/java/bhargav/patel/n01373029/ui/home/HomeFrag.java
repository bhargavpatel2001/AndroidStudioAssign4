package bhargav.patel.n01373029.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bhargav.patel.n01373029.R;


public class HomeFrag extends Fragment {

    Handler handler;

    TextView date;
    TextView time;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        date = view.findViewById(R.id.BhargavDateString);
        time = view.findViewById(R.id.BhargavTimeString);

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                time.setText(new SimpleDateFormat("hh:mm:ss", Locale.CANADA).format(new Date()));
                handler.postDelayed(this, 1000);
            }
        },10);

        SimpleDateFormat dateFormat= new SimpleDateFormat("MM / dd / YYYY");
        String dateChanger = dateFormat.format(new Date());
        date.setText(dateChanger);

        return view;
    }
}