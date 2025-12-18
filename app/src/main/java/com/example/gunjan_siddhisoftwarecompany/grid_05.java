package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class grid_05 extends AppCompatActivity {

    // Back
    ImageView btnGridBack;

    // Text options
    TextView gridNone, grid3x3, gridPhi, grid4x2;

    // Tick icons
    ImageView tickNone, tick3x3, tickPhi, tick4x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_05);


        btnGridBack = findViewById(R.id.btnGridBack);

        gridNone = findViewById(R.id.gridNone);
        grid3x3  = findViewById(R.id.grid3x3);
        gridPhi  = findViewById(R.id.gridPhi);
        grid4x2  = findViewById(R.id.grid4x2);

        tickNone = findViewById(R.id.tickNone);
        tick3x3  = findViewById(R.id.tick3x3);
        tickPhi  = findViewById(R.id.tickPhi);
        tick4x2  = findViewById(R.id.tick4x2);

        // Default selection (None)
        selectNone();

        //  BACK
        btnGridBack.setOnClickListener(v -> finish());

        //  CLICK LISTENERS
        gridNone.setOnClickListener(v -> selectNone());
        tickNone.setOnClickListener(v -> selectNone());

        grid3x3.setOnClickListener(v -> select3x3());
        tick3x3.setOnClickListener(v -> select3x3());

        gridPhi.setOnClickListener(v -> selectPhi());
        tickPhi.setOnClickListener(v -> selectPhi());

        grid4x2.setOnClickListener(v -> select4x2());
        tick4x2.setOnClickListener(v -> select4x2());
    }

    // SELECTION METHODS

    private void selectNone() {
        tickNone.setImageResource(R.drawable.tick_circle_05_pg);
        tick3x3.setImageResource(R.drawable.untick_circle__05pg_);
        tickPhi.setImageResource(R.drawable.untick_circle__05pg_);
        tick4x2.setImageResource(R.drawable.untick_circle__05pg_);
    }

    private void select3x3() {
        tickNone.setImageResource(R.drawable.untick_circle__05pg_);
        tick3x3.setImageResource(R.drawable.tick_circle_05_pg);
        tickPhi.setImageResource(R.drawable.untick_circle__05pg_);
        tick4x2.setImageResource(R.drawable.untick_circle__05pg_);
    }

    private void selectPhi() {
        tickNone.setImageResource(R.drawable.untick_circle__05pg_);
        tick3x3.setImageResource(R.drawable.untick_circle__05pg_);
        tickPhi.setImageResource(R.drawable.tick_circle_05_pg);
        tick4x2.setImageResource(R.drawable.untick_circle__05pg_);
    }

    private void select4x2() {
        tickNone.setImageResource(R.drawable.untick_circle__05pg_);
        tick3x3.setImageResource(R.drawable.untick_circle__05pg_);
        tickPhi.setImageResource(R.drawable.untick_circle__05pg_);
        tick4x2.setImageResource(R.drawable.tick_circle_05_pg);
    }
}
