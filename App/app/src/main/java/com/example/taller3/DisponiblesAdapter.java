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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DisponiblesAdapter extends BaseAdapter {

    private Context context;
    private List<Usuario> usuarios;

    public DisponiblesAdapter(Context context, List<Usuario> usuarios){
        this.context = context;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Usuario getItem(int i) {
        return usuarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if(view != null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = LayoutInflater.from(this.context).inflate(R.layout.disponibles, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.tvName);
        Button btnLoc = view.findViewById(R.id.btnLocation);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("button", usuarios.get(i).getLatitude() + " " + usuarios.get(i).getLongitude());
            }
        });
        //ImageView ivPhoto = view.findViewById(R.id.ivProfile);

        //Bitmap bm = BitmapFactory.decodeByteArray(this.usuarios.get(i).getPhoto(), 0, this.usuarios.get(i).getPhoto().length);
        name.setText(this.usuarios.get(i).getName() + " " + this.usuarios.get(i).getApellido());
        //ivPhoto.setImageBitmap(bm);

        return view;
    }
}
