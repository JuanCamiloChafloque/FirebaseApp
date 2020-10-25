package com.example.taller3;

import android.app.Activity;
import android.content.Context;
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

import androidx.core.content.ContextCompat;

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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mView = layoutInflater.from(this.context).inflate(R.layout.disponibles, viewGroup, false);
        TextView name = mView.findViewById(R.id.tvName);
        TextView apellido = mView.findViewById(R.id.tvLast);
        Button btnLoc = mView.findViewById(R.id.btnLocation);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("button", usuarios.get(i).getLatitude() + " " + usuarios.get(i).getLongitude());
            }
        });
        ImageView ivPhoto = mView.findViewById(R.id.ivProfile);

        //Bitmap bm = BitmapFactory.decodeByteArray(this.usuarios.get(i).getPhoto(), 0, this.usuarios.get(i).getPhoto().length);
        name.setText(this.usuarios.get(i).getName());
        apellido.setText(this.usuarios.get(i).getApellido());
        //ivPhoto.setImageBitmap(bm);
        ivPhoto.setImageResource(R.drawable.app);

        return mView;

    }
}
