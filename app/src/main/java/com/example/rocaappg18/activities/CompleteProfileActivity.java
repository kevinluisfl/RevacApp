package com.example.rocaappg18.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class CompleteProfileActivity extends AppCompatActivity {


    TextInputEditText mTextInputEditTextRut;
    TextInputEditText mTextInputEditTextNombreEmpresa;
    TextInputEditText mTextInputEditTextTelefono;
    TextInputEditText mTextInputEditTextDireccion;



    Button mButtonRegister;
    AuthProviders mAuthProviders;
    UsersProvider mUsersProviders;
    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        mTextInputEditTextRut=findViewById(R.id.textImputRutNitU);
        mTextInputEditTextNombreEmpresa=findViewById(R.id.textImputNombreEmpresaU);
        mTextInputEditTextTelefono=findViewById(R.id.textImputTelefonoU);
        mTextInputEditTextDireccion=findViewById(R.id.textImputDireccionU);



        mButtonRegister=findViewById(R.id.btnregister);

        mAuthProviders=new AuthProviders();
        mUsersProviders=new UsersProvider();

        mDialog= new SpotsDialog.Builder()
                .setContext (this)
                .setMessage("Espere un momento")
                .setCancelable(false).build();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();
            }
        });
    }

    private void register() {




        String nombreEmpresaU = mTextInputEditTextNombreEmpresa.getText().toString();
        String rutNitU = mTextInputEditTextRut.getText().toString();
        String direccionU = mTextInputEditTextDireccion.getText().toString();
        String telefonoU = mTextInputEditTextTelefono.getText().toString();

        if (!nombreEmpresaU.isEmpty() && !rutNitU.isEmpty()) {

            updateUser(nombreEmpresaU,rutNitU, direccionU,telefonoU);
        }
        else {
            Toast.makeText(this, "Para continuar inserta todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser (final String nombreEmpresaU,final String rutNitU,final String direccionU,final String telefonoU) {

                    String id=mAuthProviders.getUid();
                    User user=new User();
                    user.setNombreEmpresa(nombreEmpresaU);
                    user.setRut(rutNitU);
                    user.setDireccion(direccionU);
                    user.setTelefono(telefonoU);
                    user.setId(id);
                    mDialog.show();

                    mUsersProviders.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mDialog.dismiss();
                            if (task.isSuccessful()){
                                Intent intent=new Intent(CompleteProfileActivity.this,HomeActivity.class);
                                startActivity(intent);
                            }else {

                                Toast.makeText(CompleteProfileActivity.this, "No se pudo almacernar el usuario", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

    }
}