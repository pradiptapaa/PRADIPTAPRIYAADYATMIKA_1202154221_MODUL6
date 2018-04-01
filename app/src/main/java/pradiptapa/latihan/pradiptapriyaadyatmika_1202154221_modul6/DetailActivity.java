package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    ImageView image;
    TextView description;
    EditText commentInput;
    Button sendButton;
    RecyclerView recyclerView;

    private ArrayList<CommentModelData> commentList; //arraylist untuk menyimpan hasil load komentar
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        image = findViewById(R.id.imgEvent);
        description = findViewById(R.id.tvDescription);
        commentInput = findViewById(R.id.commentInput);
        sendButton = findViewById(R.id.sendButton);
        recyclerView = findViewById(R.id.rvComment);

        commentList = new ArrayList<>();

        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llManager);
        mAdapter = new CommentAdapter(commentList);
        recyclerView.setAdapter(mAdapter);

        loadIntent();
    }

    PhotoModelData photo;

    private void loadIntent() { //mengambil value yang dipassing dari selected photo di PhotoAdapter
        if (getIntent().getExtras() != null) {
            photo = (PhotoModelData) getIntent().getSerializableExtra("photoData"); //ambil model yg dipassing
            Picasso.get().load(photo.getUrl()).into(image); //load gambar menggukanan picasso
            description.setText(photo.getDescription() + "\nby: " + photo.getNames());
            setTitle(photo.getTittle()); //set judul toolbar
            loadComment(); //load comment
        }
    }

    //method ini berfungsi untuk load komentar dari key/photo yang dipilih
    private void loadComment() {
        Firebase.refPhoto.child(photo.getKey()).child("commentList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        commentList.clear();
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                            CommentModelData model = ds.getValue(CommentModelData.class);
                            commentList.add(model); //dimasukkan kedalam list
                            mAdapter.notifyDataSetChanged(); //refresh data
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

    //menampilkan tombol back button diatas kiri
    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void sendButtonClicked(View view) {
        if (commentInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Harap isi komentar", Toast.LENGTH_SHORT).show();
            return;
        }

        //Insert atau push data komentar ke firebase
        Firebase.refPhoto.child(photo.getKey()).child("commentList").push().setValue(new CommentModelData(
                Firebase.currentUser.getEmail().split("@")[0],
                Firebase.currentUser.getEmail(),
                commentInput.getText().toString()
        ));

        commentInput.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
