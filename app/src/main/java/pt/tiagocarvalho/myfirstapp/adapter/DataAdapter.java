package pt.tiagocarvalho.myfirstapp.adapter;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.tiagocarvalho.myfirstapp.R;
import pt.tiagocarvalho.myfirstapp.model.User;

/**
 * Created by tiago.carvalho on 12/19/16.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<User> userList;
    private final OnItemClickListener itemClickListener;

    public DataAdapter(ArrayList<User> userList, OnItemClickListener listener) {
        this.userList = userList;
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
        viewHolder.bind(userList.get(i), itemClickListener);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return userList.size();
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

        public void bind(final User item, final OnItemClickListener listener) {
            tvUserName.setText(item.getName());
            ivUserImage.setImageResource(R.mipmap.ic_launcher);
            tvUserAge.setText(item.getAge() + " Years Old");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }
}
