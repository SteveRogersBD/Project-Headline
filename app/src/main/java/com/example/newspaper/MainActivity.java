package com.example.newspaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newspaper.Adapters.NewsPaperAdapter;
import com.example.newspaper.Models.NewsPapers;
import com.example.newspaper.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NewsPaperAdapter adapter;
    MyDBHelper dbHelper = new MyDBHelper(MainActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        


        ArrayList<NewsPapers>list = dbHelper.fetchData();
        adapter = new NewsPaperAdapter(list,this);
        binding.recycler.setAdapter(adapter);

        GridLayoutManager gm = new GridLayoutManager(this,2);
        binding.recycler.setLayoutManager(gm);

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.add_dialogue_layout);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                Button add_btn= dialog.findViewById(R.id.add_btn_d);
                add_btn.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("MissingInflatedId")
                    @Override
                    public void onClick(View v) {
                        EditText nameET=dialog.findViewById(R.id.name_et);
                        EditText linkET=dialog.findViewById(R.id.link_et);
                        String name=nameET.getText().toString();
                        String link=linkET.getText().toString();
                        NewsPapers a = new NewsPapers(0,name,link);
                        dbHelper.insertData(a);
                        adapter.notifyItemInserted(list.size()-1);
                        binding.recycler.scrollToPosition(list.size()-1);
                        dialog.dismiss();
                        Toast toast = new Toast(MainActivity.this);
                        View view = getLayoutInflater().inflate(R.layout.toast_layout,
                                (ViewGroup) findViewById(R.id.container));
                        toast.setView(view);

                        TextView tv = view.findViewById(R.id.tv_success);
                        tv.setText(name+" added!!!");
                        toast.setGravity(Gravity.CENTER|Gravity.BOTTOM,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.show();

                    }
                });
            }
        });

    }


}