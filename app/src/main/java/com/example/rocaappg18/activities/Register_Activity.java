package com.example.rocaappg18.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rocaappg18.R;
import com.example.rocaappg18.models.User;
import com.example.rocaappg18.providers.AuthProviders;
import com.example.rocaappg18.providers.UsersProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

public class Register_Activity extends AppCompatActivity {

    CircleImageView mCircleImagenViewback;

    TextInputEditText mTextInputEditTextRutNit;
    TextInputEditText mTextInputEditTextNombreEmpresa;
    TextInputEditText mTextInputEditTextEmail;
    TextInputEditText mTextInputEditTextDireccion;
    TextInputEditText mTextInputEditTextTelefono;
    TextInputEditText mTextInputEditTextPassword;
    TextInputEditText mTextInputEditTextConfirmPassword;

    Button mButtonRegister;
    AuthProviders mAuthProvider;
    UsersProvider mUsersProviders;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextInputEditTextRutNit=findViewById(R.id.textImputRutNit);
        mTextInputEditTextNombreEmpresa=findViewById(R.id.textImputNombreEmpresa);
        mTextInputEditTextEmail=findViewById(R.id.textImputEmailr);
        mTextInputEditTextDireccion=findViewById(R.id.textImputDireccion);
        mTextInputEditTextTelefono=findViewById(R.id.textImputTelefono);
        mTextInputEditTextPassword=findViewById(R.id.texImputPasswordr);
        mTextInputEditTextConfirmPassword=findViewById(R.id.texImputConfirPassword);

        mButtonRegister=findViewById(R.id.btnregister);

        mAuthProvider=new AuthProviders();
        mUsersProviders=new UsersProvider();

        mDialog=new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();



        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });

        mCircleImagenViewback= findViewById(R.id.CircleImagenViewback);
        mCircleImagenViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

    }

    private void register() {
        String nombreEmpresa = mTextInputEditTextNombreEmpresa.getText().toString();
        String rutNit = mTextInputEditTextRutNit.getText().toString();
        String email = mTextInputEditTextEmail.getText().toString();
        String direccion = mTextInputEditTextDireccion.getText().toString();
        String telefono = mTextInputEditTextTelefono.getText().toString();
        String password  = mTextInputEditTextPassword.getText().toString();
        String confirmPassword = mTextInputEditTextConfirmPassword.getText().toString();

        if (!nombreEmpresa.isEmpty() && !rutNit.isEmpty() && !email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
            if (isEmailValid(email)) {
                if (password.equals(confirmPassword)) {
                    if (password.length() >= 6) {
                        createUser(nombreEmpresa,rutNit,email,direccion,telefono,password);
                    }
                    else {
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Insertaste todos los campos pero el correo no es valido", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser (final String nombreEmpresa,final String rutNit,final String email,final String direccion,final String telefono,String password) {
        mDialog.show();

        mAuthProvider.register(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String id=mAuthProvider.getUid();

                    User user =new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setNombreEmpresa(nombreEmpresa);
                    user.setRut(rutNit);
                    user.setDireccion(direccion);
                    user.setTelefono(telefono);

                    mUsersProviders.create(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mDialog.dismiss();
                            if (task.isSuccessful()){
                                Toast.makeText(Register_Activity.this, "el usuario se registro correctamente", Toast.LENGTH_SHORT).show();

                            }else {
                                mDialog.dismiss();
                                Toast.makeText(Register_Activity.this, "no se pudo almacenar en la base de datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else{
                    Toast.makeText(Register_Activity.this, "no se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    /*verificar si un email es valido*/

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}