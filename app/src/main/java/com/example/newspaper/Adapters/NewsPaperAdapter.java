package com.example.newspaper.Adapters;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newspaper.MainActivity;
import com.example.newspaper.Models.NewsPapers;
import com.example.newspaper.MyDBHelper;
import com.example.newspaper.NewsActivity;
import com.example.newspaper.R;

import java.util.ArrayList;

public class NewsPaperAdapter extends RecyclerView.Adapter<NewsPaperAdapter.ViewHolder> {

    private ArrayList<NewsPapers> list;
    private Context context;

    public NewsPaperAdapter(ArrayList<NewsPapers> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        NewsPapers newsPaper = list.get(position);
        holder.imageView.setImageResource(newsPaper.getImage());
        holder.textView.setText(newsPaper.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("link", newsPaper.getLink());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.delete_dialog_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                Button yes,no;
                yes = dialog.findViewById(R.id.yes_btn_dg);
                no = dialog.findViewById(R.id.no_btn_dg);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyDBHelper dbHelper = new MyDBHelper(context);
                        dbHelper.deleteDB(list.get(position));
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                        dialog.dismiss();
                        Toast toast = new Toast(context);
                        View view = LayoutInflater.from(context).inflate(R.layout.toast_layout,
                                (ViewGroup) dialog.findViewById(R.id.container));
                        toast.setView(view);

                        TextView tv = view.findViewById(R.id.tv_success);
                        tv.setText("Deleted!!!");
                        toast.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_news);
            textView = itemView.findViewById(R.id.news_name);
        }
    }

}
