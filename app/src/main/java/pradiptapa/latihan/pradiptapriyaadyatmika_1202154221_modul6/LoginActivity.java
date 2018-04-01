package pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText login, password;
    ProgressDialog pbDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    login = findViewById(R.id.loginInput);
    password = findViewById(R.id.passwordInput);
    pbDialog = new ProgressDialog(this);

       }

    public void login(View view) {
        if(login.getText().toString().isEmpty()) {
            login.setError("Required");
            return;
        }
        if(password.getText().toString().isEmpty()) {
            password.setError("Required");
            return;
        }
        pbDialog.setMessage("Wait ....");
        pbDialog.setIndeterminate(true);
        pbDialog.show();
        loginFirebase();
    }

    public void loginFirebase() {
        String stringLogin = login.getText().toString();
        String stringPassword = password.getText().toString();

        Firebase.mAuth.signInWithEmailAndPassword(stringLogin, stringPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithEmail:success");
                            FirebaseUser user = Firebase.mAuth.getCurrentUser();
                            Firebase.currentUser = user;
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            pbDialog.dismiss();
                        }

                        // ...
                    }
                });
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
