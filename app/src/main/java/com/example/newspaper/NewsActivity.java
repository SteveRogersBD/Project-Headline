package com.example.newspaper;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newspaper.databinding.ActivityNewsBinding;

public class NewsActivity extends AppCompatActivity {

    private ActivityNewsBinding binding; // Complete the declaration

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        WebSettings webSettings = binding.newsWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.newsWeb.loadUrl(link);
        binding.newsWeb.setWebViewClient(new WebViewController());
    }
    @Override
    public void onBackPressed() {
        if (binding.newsWeb.canGoBack()) {
            binding.newsWeb.goBack(); // Go back in WebView history
        } else {
            super.onBackPressed(); // Default back button behavior
        }
    }
}
