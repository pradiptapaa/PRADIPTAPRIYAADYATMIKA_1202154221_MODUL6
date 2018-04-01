package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

import java.util.ArrayList;

public class MainFragment extends android.support.v4.app.Fragment{
    private static final String KEY_PARAM = "key_param";
    private ArrayList<PhotoModelData> photoList;
    private PhotoAdapter mAdapter;
    RecyclerView recyclerView;


    //method untuk menerima data yang dikirimkan/passing dari activity
    public static Bundle arguments(String param) {
        return new Bundler()
                .putString(KEY_PARAM, param)
                .get();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Bundle bundle = getArguments(); //mengambil nilai/data yang didapatkan dari activity
        menu = bundle.getString(KEY_PARAM);

        recyclerView = view.findViewById(R.id.recyclerView);

        photoList = new ArrayList<>();

        //konfig recyclerview layout manager dan adapter
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PhotoAdapter(photoList, getActivity());
        recyclerView.setAdapter(mAdapter);
        loadData();

        return view;
    }

    int commentCount = 0;

    //method untuk loaddata photo dari firebase
    private void loadData() {
        if(menu.equals("terbaru")) { //semua foto berdasarkan yang terbaru
            Firebase.refPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    photoList.clear();
                    commentCount = 0;
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        PhotoModelData model = ds.getValue(PhotoModelData.class);
                        photoList.add(model); //dimasukkan list photo
                        mAdapter.notifyDataSetChanged(); //refresh adapter
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                    //showProgress(false);
                }
            });
        } else { //hanya foto user tsb yang login
            Firebase.refPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    photoList.clear();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        PhotoModelData photo = ds.getValue(PhotoModelData.class);

                        if(photo.getEmail().equals(Firebase.currentUser.getEmail())) {
                            photoList.add(photo); //dimasukkan list photo
                            mAdapter.notifyDataSetChanged(); //refresh adapter
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                    //showProgress(false);
                }
            });
        }
    }
}