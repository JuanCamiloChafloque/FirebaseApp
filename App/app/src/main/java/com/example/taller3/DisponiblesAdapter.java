package com.example.taller3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class DisponiblesAdapter extends ArrayAdapter<Usuario> {


    private Context context;
    private List<Usuario> usuarios;

    public DisponiblesAdapter(Context context, List<Usuario> usuarios){
        super(context, R.layout.disponibles, usuarios);
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = layoutInflater.from(this.context).inflate(R.layout.disponibles, viewGroup, false);
        TextView name = mView.findViewById(R.id.tvName);
        TextView apellido = mView.findViewById(R.id.tvLast);
        Button btnLoc = mView.findViewById(R.id.btnLocation);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.putExtra("key", usuarios.get(i).getKey());
                view.getContext().startActivity(intent);
            }
        });

        ImageView ivPhoto = mView.findViewById(R.id.ivProfile);

        //Bitmap bm = BitmapFactory.decodeByteArray(usuarios.get(i).getPhoto(), 0, usuarios.get(i).getPhoto().length);
        //ivPhoto.setImageBitmap(bm);
        ivPhoto.setImageResource(R.drawable.app);
        name.setText(this.usuarios.get(i).getName());
        apellido.setText(this.usuarios.get(i).getApellido());

        return mView;
    }
}
