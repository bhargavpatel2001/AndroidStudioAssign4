//Bhargav Patel N01373029 Section B
package bhargav.patel.n01373029.ui.Download;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import bhargav.patel.n01373029.R;

public class DownloadFrag extends Fragment {

    private ArrayList<SpinnerFrag> mSpinnerList;
    private SpinnerAdapter mSpinnerAdapter;
    Button downloadBtn;
    ImageView downloadImg;
    String web;
    ProgressDialog p;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        downloadBtn = view.findViewById(R.id.BhargavBtnDown);
        downloadImg = view.findViewById(R.id.BhargavImageDown);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoadImage asyncTask = new LoadImage(downloadImg);
                asyncTask.execute(web);
            }
        });

        initList();

        Spinner spinner = view.findViewById(R.id.BhargavSpinner);

        mSpinnerAdapter = new SpinnerAdapter(getContext(), mSpinnerList);
        spinner.setAdapter(mSpinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    web = getString(R.string.link1);
                }

                else if (position == 1) {
                    web = getString(R.string.link2);
                }
                else if (position == 2) {
                    web = getString(R.string.link3);

                }
                else if (position == 3) {
                    web = getString(R.string.link4);
                }
                else if (position == 4) {
                    web = getString(R.string.link5);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setTitle(getString(R.string.Loading));
            p.setMessage(getString(R.string.Message2));
            p.setIcon(android.R.drawable.ic_menu_upload);
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        public LoadImage(ImageView downloadImg) {
            this.imageView = downloadImg;

        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlLink = strings[0];
            Bitmap bitmap = null;
            try {
                Thread.sleep(5000);
                InputStream inputStream = new URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (downloadImg != null) {
                downloadImg.setImageBitmap(bitmap);
                p.hide();

            }


        }
    }
    private void initList() {
        mSpinnerList = new ArrayList<>();
        mSpinnerList.add(new SpinnerFrag(getString(R.string.Name1), R.mipmap.sai_foreground));
        mSpinnerList.add(new SpinnerFrag(getString(R.string.Name2), R.mipmap.sasuke_foreground));
        mSpinnerList.add(new SpinnerFrag(getString(R.string.Name3), R.mipmap.naruto_foreground));
        mSpinnerList.add(new SpinnerFrag(getString(R.string.Name4), R.mipmap.sakura_foreground));
        mSpinnerList.add(new SpinnerFrag(getString(R.string.Name5), R.mipmap.kakashi_foreground));
    }
}