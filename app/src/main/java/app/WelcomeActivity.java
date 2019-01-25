package app;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gaowk.shoppingmall.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
                finish();
            }
        }, 2000);

    }

    private void startMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
