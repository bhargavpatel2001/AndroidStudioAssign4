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
                    web = "https://external-preview.redd.it/miWC3yoHKn8ODzWgVoSxbvfiXLl4vnnkPvN01FJxoww.jpg?auto=webp&s=cda910cd8679b4a25a77a6103089ed379b97fe0a";
                }

                else if (position == 1) {
                    web = "https://c4.wallpaperflare.com/wallpaper/574/16/1023/naruto-sasuke-uchiha-snake-hd-wallpaper-preview.jpg";
                }
                else if (position == 2) {
                    web = "https://wallpapercave.com/wp/wp5159771.jpg";

                }
                else if (position == 3) {
                    web = "https://m.media-amazon.com/images/M/MV5BYTkwNDBlYzktNjRlYS00MTI5LWI3MjItMDY1Y2U4OGQ2MDUwXkEyXkFqcGdeQXVyMjc2Nzg5OTQ@._V1_.jpg";
                }
                else if (position == 4) {
                    web = "https://i.quotev.com/img/q/u/12/05/11/701211-2918344-kakashi_bells_thumb.jpg";
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
            p.setTitle("Loading");
            p.setMessage("Please wait...It is downloading");
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
        mSpinnerList.add(new SpinnerFrag("Sai Yamanaka", R.mipmap.sai_foreground));
        mSpinnerList.add(new SpinnerFrag("Sasuke Uchiha", R.mipmap.sasuke_foreground));
        mSpinnerList.add(new SpinnerFrag("Naruto Uzumaki", R.mipmap.naruto_foreground));
        mSpinnerList.add(new SpinnerFrag("Sakura Haruno", R.mipmap.sakura_foreground));
        mSpinnerList.add(new SpinnerFrag("Kakashi Hatake", R.mipmap.kakashi_foreground));
    }
}