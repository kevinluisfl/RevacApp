package com.example.rocaappg18.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rocaappg18.R;
import com.example.rocaappg18.models.Post;
import com.example.rocaappg18.providers.AuthProviders;
import com.example.rocaappg18.providers.ImageProvider;
import com.example.rocaappg18.providers.PostProvider;
import com.example.rocaappg18.utils.FileUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class PostActivity extends AppCompatActivity {

    ImageView mImageViewPost1;
    File mImageFile;
    Button mButtonPost;
    ImageProvider mImageProvider;

    TextInputEditText mTextInputNombre;
    TextInputEditText mTextInputDocumento;
    TextInputEditText mTextInputTelefono;
    TextInputEditText mTextInputCorreo;
    ImageView mImageViewAstraZ;
    ImageView mImageViewJanssen;
    ImageView mImageViewSinovav;
    ImageView mImageViewPfizer;
    String mVacuna="";
    PostProvider mPostProvider;
    String mNombre="";
    String mDocumento="";
    String mCorreo="";
    String mTelefono="";
    AuthProviders mAuthProvider;
    TextView mTextViewVacuna;

    private final int Gallery_REQUEST_CODE=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mImageViewPost1=findViewById(R.id.imageViewPost1);
        mButtonPost=findViewById(R.id.btnPost);
        mTextInputNombre = findViewById(R.id.textInputNombreCliente);
        mTextInputDocumento = findViewById(R.id.textInputDocumento);
        mTextInputTelefono = findViewById(R.id.textInputTelefono);
        mTextInputCorreo = findViewById(R.id.textInputCorreo);
        mImageViewAstraZ=findViewById(R.id.imageViewAstraZ);
        mImageViewJanssen=findViewById(R.id.imageViewJanssen);
        mImageViewPfizer=findViewById(R.id.imageViewPfizer);
        mImageViewSinovav= findViewById(R.id.imageViewSinovav);
        mTextViewVacuna = findViewById(R.id.textViewVacuna);



        mImageProvider = new ImageProvider();
        mPostProvider = new PostProvider();

        mAuthProvider = new AuthProviders();
        
        mButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPost();
            }
        });
        
        mImageViewPost1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
        mImageViewAstraZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVacuna="Astra";
                mTextViewVacuna.setText(mVacuna);
            }
        });
        mImageViewJanssen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVacuna="Janssen";
                mTextViewVacuna.setText(mVacuna);
            }
        });
        mImageViewPfizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVacuna="Pfizer";
                mTextViewVacuna.setText(mVacuna);
            }
        });
        mImageViewSinovav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVacuna="Sinovav";
                mTextViewVacuna.setText(mVacuna);
            }
        });
    }

    private void clickPost() {
        mNombre = mTextInputNombre.getText().toString();
        mDocumento = mTextInputDocumento.getText().toString();
        mCorreo = mTextInputCorreo.getText().toString();
        mTelefono = mTextInputTelefono.getText().toString();
        if(!mNombre.isEmpty() && !mDocumento.isEmpty() && !mVacuna.isEmpty()&& !mCorreo.isEmpty()&& !mTelefono.isEmpty()){
            if(mImageFile != null){
                saveImage();
            }else{
                Toast.makeText(this, "Seleccione una imagen", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {
        mImageProvider.save(PostActivity.this, mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    mImageProvider.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url=uri.toString();
                            Post post=new Post();
                            post.setImagen1(url);
                            post.setNombre(mNombre);
                            post.setDocumento(mDocumento);
                            post.setCorreo(mCorreo);
                            post.setTelefono(mTelefono);
                            post.setIdUser(mAuthProvider.getUid());
                            post.setVacuna(mVacuna);

                            mPostProvider.save(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> taskSave) {

                                        if(taskSave.isSuccessful()){
                                            Toast.makeText( PostActivity.this, "La informacion se almaceno correctamente", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(PostActivity.this, "No se pudo almacenar la información", Toast.LENGTH_SHORT).show();
                                        }

                                }
                            });
                        }
                    });

                }else{
                    Toast.makeText(PostActivity.this,"La imagen no se pudo  almacenar ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_REQUEST_CODE && resultCode==RESULT_OK){
            try{
                mImageFile= FileUtil.from(this,data.getData());
                mImageViewPost1.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath()));
            }catch (Exception e){
                Log.d("ERROR","Se produjo un error " + e.getMessage());
                Toast.makeText(this,"Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}