package ir.sep.android.merchantapp.ui.exceptiondisplay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.databinding.ActivityExceptionDisplayBinding;
import ir.sep.android.merchantapp.ui.MainActivity;

public class ExceptionDisplayActivity extends AppCompatActivity {

    ActivityExceptionDisplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExceptionDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnBack.setOnClickListener(view -> intentData());
    }

    public void intentData() {
        Intent setIntent = new Intent(this, MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        intentData();
    }
}