package tech.wolfcompany.tpandroid.ui.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import tech.wolfcompany.tpandroid.R;

public class AboutActivity extends AppCompatActivity {

    TextView licenseTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initComponents();
        addListeners();

    }
    public void initComponents(){
        licenseTv=findViewById(R.id.license_tv);
    }
    public void addListeners(){
        licenseTv.setOnClickListener(v -> {
            Intent intent=new Intent(AboutActivity.this, LicensingActivity.class);
            startActivity(intent);

        });
    }
}