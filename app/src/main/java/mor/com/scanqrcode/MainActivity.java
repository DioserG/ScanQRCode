package mor.com.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = (Button) findViewById(R.id.btnScan);
        final Activity activity = this;

        btnScan.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
               IntentIntegrator integrador = new IntentIntegrator(activity);
               integrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
               integrador.setPrompt("Câmera Scan");
               integrador.setCameraId(0); // se 1 é camera frontal
               integrador.initiateScan();

           }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){
                alert(result.getContents());
            }else{
                alert("Scan cancelado!");
            }
        }else{
            super.onActivityResult(requestCode,resultCode, data);
        }
    }
    private void alert(String msg){
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
