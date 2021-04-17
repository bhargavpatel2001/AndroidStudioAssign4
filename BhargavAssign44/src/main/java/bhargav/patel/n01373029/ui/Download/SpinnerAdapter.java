package bhargav.patel.n01373029.ui.Download;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import bhargav.patel.n01373029.R;

public class SpinnerAdapter extends ArrayAdapter{

    public SpinnerAdapter(Context context, ArrayList<SpinnerFrag> spinnerFrag){
       super(context,0,spinnerFrag);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_row, parent, false
            );
        }

        ImageView imageViewSpinner = convertView.findViewById(R.id.BhargavSpinIV);
        TextView textViewSpinner = convertView.findViewById(R.id.BhargavSpinTV);

        SpinnerFrag spinnerFrag = (SpinnerFrag) getItem(position);

        if(spinnerFrag != null) {
            imageViewSpinner.setImageResource(spinnerFrag.getSpinnerImg());
            textViewSpinner.setText(spinnerFrag.getSpinnerText());
        }
        return convertView;
    }
}
