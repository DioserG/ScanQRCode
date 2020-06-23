package mor.com.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText user_name, user_pass;
    Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_name = (EditText) findViewById(R.id.user_name);
        user_pass = (EditText) findViewById(R.id.user_pass);
        btnLogar  = (Button) findViewById(R.id.btnLogar);

    }

    public void btnMenu(View view) {
        Intent intent  = new Intent(MainActivity.this,
                Main2Activity.class);
        startActivity(intent);
    }


}
