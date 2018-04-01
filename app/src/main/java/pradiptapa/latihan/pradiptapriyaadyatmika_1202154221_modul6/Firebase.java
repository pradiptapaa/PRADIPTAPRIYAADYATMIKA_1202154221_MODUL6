package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by monoc on 4/1/2018.
 */

public class Firebase {

    public final static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseUser currentUser;

    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static StorageReference storageRef = storage.getReference();

    public final static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public final static DatabaseReference refPhoto = database.getReference("photo");

}
