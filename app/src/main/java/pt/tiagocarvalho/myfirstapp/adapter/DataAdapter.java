package pt.tiagocarvalho.myfirstapp.adapter;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import pt.tiagocarvalho.myfirstapp.R;
import pt.tiagocarvalho.myfirstapp.model.Recurso;

/**
 * Created by tiago.carvalho on 12/19/16.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<Recurso> recursoList;
    private final OnItemClickListener itemClickListener;

    public DataAdapter(ArrayList<Recurso> recursoList, OnItemClickListener listener) {
        this.recursoList = recursoList;
        this.itemClickListener = listener;
    }

    /*This method is called when the custom ViewHolder needs to be initialized.
    We specify the layout that each item of the RecyclerView should use.
    This is done by inflating the layout using LayoutInflater, passing the output to the constructor of the custom ViewHolder.*/
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    /*specify the contents of each item of the RecyclerView*/
    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(recursoList.get(i), itemClickListener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return recursoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv;
        private TextView tvUserName;
        private TextView tvUserAge;
        private ImageView ivUserImage;

        public ViewHolder(View view) {
            super(view);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            tvUserName = (TextView) view.findViewById(R.id.tvUserName);
            ivUserImage = (ImageView) view.findViewById(R.id.ivUserImage);
            tvUserAge = (TextView) view.findViewById(R.id.tvUserAge);
        }

        public void bind(final Recurso recurso, final OnItemClickListener listener) {
            tvUserName.setText(recurso.getName());
            Log.d("DEBUGTIAGO", recurso.getImage());
            if (recurso.getImage() != null) {
                Bitmap bMap = loadImageFromStorage(recurso.getImage());
                ivUserImage.setImageBitmap(bMap);
            } else {
                ivUserImage.setImageResource(R.drawable.android_3);
            }
            tvUserAge.setText(recurso.getAge() + " Years Old");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(recurso);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Recurso recurso);
    }

    private Bitmap loadImageFromStorage(String path) {

        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
