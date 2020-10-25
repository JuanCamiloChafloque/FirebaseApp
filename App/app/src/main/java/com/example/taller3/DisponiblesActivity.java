package com.example.taller3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DisponiblesActivity extends AppCompatActivity {

    private static final String PATH_USERS = "users/";
    private static final String PATH_IMAGE = "images/";

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorage;
    private DatabaseReference mRef;
    private Usuario data;

    private DisponiblesAdapter adapter;
    private ListView listView;
    private List<Usuario> disponibles;

    private Switch swDisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disponibles);

       getSupportActionBar().setTitle("Disponibles");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference(PATH_USERS);
        mStorage = FirebaseStorage.getInstance().getReference();
        disponibles = new ArrayList<>();
        //disponibles.add(new Usuario("Juan Camilo", "Chafloque Mesia", 1020828518, 4.65, -74.5, false));
        //disponibles.add(new Usuario("Martin", "Chafloque Mesia", 1000201020, 4.76, -74.32, false));
        //disponibles.add(new Usuario("Julio", "Mejía Vera", 100431020, 4.43, -74.21, false));
        listView = findViewById(R.id.lvLayout);
        adapter = new DisponiblesAdapter(this, disponibles);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = mAuth.getCurrentUser();
        initCurrentUser(user);
        initDisponibles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem disp = menu.findItem(R.id.menuDisp);
        swDisp = (Switch) disp.getActionView();

        swDisp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(swDisp.isChecked()){
                    mRef = mDatabase.getReference(PATH_USERS + user.getUid() + "/" + "disponible");
                    mRef.setValue(true);
                } else {
                    mRef = mDatabase.getReference(PATH_USERS + user.getUid() + "/" + "disponible");
                    mRef.setValue(false);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menuLogOut) {
            mAuth.signOut();
            Intent intent = new Intent(DisponiblesActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        return true;
    }

    public void initCurrentUser(FirebaseUser user){
        if(user != null){
            mRef = mDatabase.getReference(PATH_USERS + user.getUid());
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    data = dataSnapshot.getValue(Usuario.class);
                    swDisp.setChecked(data.getDisponible());
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(DisponiblesActivity.this, "Data recollection failed!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initDisponibles(){
        mRef = mDatabase.getReference(PATH_USERS);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot entity: dataSnapshot.getChildren()){
                    final Usuario usuario = entity.getValue(Usuario.class);
                    if(usuario.getDisponible()){
                        final long ONE_MEGABYTE = 1024 * 1024;
                        StorageReference photoRef = mStorage.child(PATH_IMAGE + entity.getKey() + "/profile.png");
                        photoRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                usuario.setPhoto(bytes);
                                disponibles.add(usuario);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(DisponiblesActivity.this, "Data recollection failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}