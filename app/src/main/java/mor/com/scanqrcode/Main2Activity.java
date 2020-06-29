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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.net.ssl.TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ResourceBundle;

import java.util.HashMap;
import java.util.Map;

import static android.os.Build.HOST;

public class Main2Activity extends AppCompatActivity
{

    Button   btnScan, btnEnviar;   // leitura do QRCode
    String   scan_valor;           // captura ao valor do QRCode
    EditText qrcode, peso, nome;   // Envia para o banco


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // leitura variáveis
        btnScan             = (Button)   findViewById(R.id.btnScan);
        btnEnviar           = (Button)   findViewById(R.id.btnEnviar);
        peso                = (EditText) findViewById(R.id.peso);
        qrcode              = (EditText) findViewById(R.id.qrcode); // recebendo leitura do QRCode


        // Pegando usuário do activity_main
        Bundle extra        = getIntent().getExtras();
        String user_name    = extra.getString("user_name");
        String user_pass    = extra.getString("user_pass");
        nome                = (EditText) findViewById(R.id.nome);
        nome.setText(user_name);



        // Inicio chamada leitura QRCode
        final Activity activity = this;
        btnScan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator integrador = new IntentIntegrator(activity);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrador.setPrompt("Câmera Scan");
                integrador.setCameraId(0); // se 1 é camera frontal
                integrador.initiateScan();
            }
        }); // Fim chamada leitura QRCode

        //Enviando QRCode para tb_sucata, abertura de conexão
        btnEnviar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                executar("http://192.168.2.42:80/sucata/registro.php");
            }
        }); // Fim chamada leitura QRCode
    }

    // QRCode inicio funcionalidade
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
        {
            if(result.getContents() != null)
            {
                alert(result.getContents());
                scan_valor = String.valueOf(result.getContents());
                qrcode.setText(scan_valor);
            }else
             {
                alert("Scan cancelado!");
                Intent intent  = new Intent(Main2Activity.this,
                        Main2Activity.class);
                startActivity(intent);
             }
        }else
         {
            super.onActivityResult(requestCode,resultCode, data);
         }
    }
    private void alert(String msg)
    {
       Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }// QRCode Fim funcionalidade


//==================================================================================================
    //Conexão e passagem de dados para banco MySql Sucata utilizando PHP
    // Enviando valores lidos para a tabela tb_sucata
    private void executar(String URL)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(getApplicationContext(), "Conexão realizada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("qrcode", qrcode.getText().toString());
                parametros.put("peso",   peso.getText().toString());
                parametros.put("nome",   nome.getText().toString());
                return parametros;
            }
          };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }// Fim conexao banco MySql com PHP
    //==================================================================================================

    // Limpa tela
    public void btnLimpar(View view)
    {
        qrcode.setText("");
        peso.setText("");
    }
}
