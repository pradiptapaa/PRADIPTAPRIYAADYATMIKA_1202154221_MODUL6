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

import static pradiptapa.latihan.pradiptapriyaadyatmika_1202154221_modul6.Firebase.mAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText loginR, passwordR, confirmPassword;
    ProgressDialog pbDialogR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginR = findViewById(R.id.loginRegisterInput);
        passwordR = findViewById(R.id.passwordRegisterInput);
        confirmPassword = findViewById(R.id.confirmationPasswordRegisterInput);
        pbDialogR = new ProgressDialog(this);
    }


    public void regis(View view) {
        if (loginR.getText().toString().isEmpty()) {
            loginR.setError("Required");
            return;
        } else
            if (passwordR.getText().toString().isEmpty()) {
                passwordR.setError("Required");
                return;
            } else if (!confirmPassword.getText().toString().equals(passwordR.getText().toString())){
                confirmPassword.setError("Tidak Cocok");
                passwordR.setError("Tidak Cocok");
        return;}
        pbDialogR.setMessage("Proses Mendaftarkan");
        pbDialogR.setIndeterminate(true);
        pbDialogR.show();
        RegisterFirebase();
    }

    public void RegisterFirebase() {
        String stringLogin = loginR.getText().toString();
        String stringPassword = passwordR.getText().toString();

        Firebase.mAuth.createUserWithEmailAndPassword(stringLogin, stringPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pbDialogR.dismiss();
                            Log.d("", "createUserWithEmail:success");
                            Firebase.currentUser = Firebase.mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Account created succesfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            pbDialogR.dismiss();
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();}

                        // ...
                    }
                });
    }

    public void back(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}
