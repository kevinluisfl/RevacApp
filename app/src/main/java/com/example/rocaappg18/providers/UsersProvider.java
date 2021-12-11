package com.example.rocaappg18.providers;

import com.example.rocaappg18.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UsersProvider {

      private CollectionReference mcolletion;


    public UsersProvider(){

        mcolletion=FirebaseFirestore.getInstance().collection("Users");

    }

    public Task<DocumentSnapshot> getUser(String id){
        return mcolletion.document(id).get();
    }

    public  Task<Void> create(User user){
        return mcolletion.document(user.getId()).set(user);
    }

    public  Task<Void> update(User user) {
        Map<String,Object> map= new HashMap<>();
        map.put("rut",user.getRut());
        map.put("nombreEmpresa", user.getNombreEmpresa());
        map.put("telefono",user.getTelefono());
        map.put("direccion",user.getDireccion());

        return mcolletion.document(user.getId()).update(map);
    }
}
