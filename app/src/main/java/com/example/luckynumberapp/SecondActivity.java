package com.example.luckynumberapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SecondActivity extends AppCompatActivity {

    Button shareButton;
    TextView randomNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shareButton = findViewById(R.id.shareButton);
        randomNumberView = findViewById(R.id.randomNumberView);

        // Retrieve the data from MainActivity
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        System.out.println("Name: " + name);

        // Generate a random number
        int randomNumber = generateRandomNumber();
        randomNumberView.setText(String.valueOf(randomNumber));

        // Share the random number
        shareData(name, randomNumber);
    }

    private void shareData(String name, int randomNumber) {
        shareButton.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");

            // Additional Info
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, name + " got lucky today!");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "His lucky number is: " + randomNumber);

            Intent shareIntent = Intent.createChooser(sendIntent, "Choose a Platform");
            startActivity(shareIntent);
        });
    }

    public int generateRandomNumber() {
        var randomNum = new AtomicInteger();
        Random random = new Random();
        random.ints(1, 0, 1001).findFirst().ifPresent(randomNum::set);
        return randomNum.get();
    }
}