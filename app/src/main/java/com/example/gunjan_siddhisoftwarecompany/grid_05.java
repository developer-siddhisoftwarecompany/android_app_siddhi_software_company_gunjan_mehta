package com.example.gunjan_siddhisoftwarecompany;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gunjan_siddhisoftwarecompany.util.ChangeTracker;
import com.example.gunjan_siddhisoftwarecompany.util.SettingsStore;

public class grid_05 extends AppCompatActivity {

    ImageView btnGridBack;

    TextView gridNone, grid3x3, gridPhi, grid4x2;
    ImageView tickNone, tick3x3, tickPhi, tick4x2;

    private static final String KEY_GRID = "camera_grid";

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

        // Restore saved grid
        restoreGrid();

        btnGridBack.setOnClickListener(v -> finish());

        gridNone.setOnClickListener(v -> selectGrid("none"));
        tickNone.setOnClickListener(v -> selectGrid("none"));

        grid3x3.setOnClickListener(v -> selectGrid("3x3"));
        tick3x3.setOnClickListener(v -> selectGrid("3x3"));

        gridPhi.setOnClickListener(v -> selectGrid("phi"));
        tickPhi.setOnClickListener(v -> selectGrid("phi"));

        grid4x2.setOnClickListener(v -> selectGrid("4x2"));
        tick4x2.setOnClickListener(v -> selectGrid("4x2"));
    }

    private void restoreGrid() {
        String grid = SettingsStore.get(this, KEY_GRID, "none");
        selectGrid(grid, false);
    }

    private void selectGrid(String grid) {
        selectGrid(grid, true);
    }

    private void selectGrid(String grid, boolean save) {

        tickNone.setImageResource(
                grid.equals("none") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );
        tick3x3.setImageResource(
                grid.equals("3x3") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );
        tickPhi.setImageResource(
                grid.equals("phi") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );
        tick4x2.setImageResource(
                grid.equals("4x2") ? R.drawable.tick_circle_05_pg : R.drawable.untick_circle__05pg_
        );

        if (save) {
            SettingsStore.save(this, KEY_GRID, grid);
            ChangeTracker.mark();
        }
    }
}
