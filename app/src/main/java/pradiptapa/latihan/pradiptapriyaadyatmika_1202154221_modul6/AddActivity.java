package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddActivity extends AppCompatActivity {
    private StorageReference refPhotoProfile;
    private Uri photoUrl;
    private ProgressDialog pbDialog;
    TextView tittleInput, postInput;
    ImageView imagePost;
    FloatingActionButton postButton;
    Button chooseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        tittleInput = findViewById(R.id.tvTittle);
        postInput = findViewById(R.id.imgPhoto);
        postButton = findViewById(R.id.postButton);
        chooseButton = findViewById(R.id.chooseButton);
        imagePost = findViewById(R.id.imgChoose);

        pbDialog = new ProgressDialog(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.postButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postClicked();
            }
        });

    }

    public void chooseClicked(View view) {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .single() // single mode
                .limit(1) // max images can be selected (99 by default)
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false) // disabling log
                .start();
    }

    boolean isPicChange = false;

    public void postClicked() {
        if (tittleInput.getText().toString().isEmpty()) {
            tittleInput.setError("Required");
            return;
        }
        //validasi kosong
        if (postInput.getText().toString().isEmpty()) {
            postInput.setError("Required");
            return;
        }
        //validasi gambar sudah dipilih
        if (!isPicChange) {
            Toast.makeText(this, "Harap pilih gambar!", Toast.LENGTH_SHORT).show();
            return;
        }

        pbDialog.setMessage("Uploading..");
        pbDialog.setIndeterminate(true);
        pbDialog.show();

        //melakukan proses update foto
        refPhotoProfile = Firebase.storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg"); //akses path dan filename storage di firebase untuk menyimpan gambar
        StorageReference photoImagesRef = Firebase.storageRef.child("gambar/" + System.currentTimeMillis() + ".jpg");
        refPhotoProfile.getName().equals(photoImagesRef.getName());
        refPhotoProfile.getPath().equals(photoImagesRef.getPath());

        //mengambil gambar dari imageview yang sudah di set menjadi selected image sebelumnya
        imagePost.setDrawingCacheEnabled(true);
        imagePost.buildDrawingCache();
        Bitmap bitmap = imagePost.getDrawingCache(); //convert imageview ke bitmap
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos); //convert bitmap ke bytearray
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = refPhotoProfile.putBytes(data); //upload image yang sudah dalam bentuk bytearray ke firebase storage
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                photoUrl = taskSnapshot.getDownloadUrl(); //setelah selesai upload, ambil url gambar
                String key = Firebase.refPhoto.push().getKey(); //ambil key dari node firebase

                //push atau insert data ke firebase database
                Firebase.refPhoto.child(key).setValue(new PhotoModelData(
                        key,
                        photoUrl.toString(),
                        tittleInput.getText().toString(),
                        postInput.getText().toString(),
                        Firebase.currentUser.getEmail().split("@")[0],
                        Firebase.currentUser.getEmail()));
                pbDialog.dismiss();
                Toast.makeText(AddActivity.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) { // jika ada data dipilih
            Image image = ImagePicker.getFirstImageOrNull(data); //ambil first image
            File imgFile = new File(image.getPath()); // dapatkan lokasi gambar yang dipilih
            if (imgFile.exists()) { //jika ditemukan
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath()); //convert file ke bitmap
                imagePost.setImageBitmap(myBitmap); //set imageview dengan gambar yang dipilih
                isPicChange = true; // ubah state menjadi true untuk menandakan gambar telah dipilih
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
