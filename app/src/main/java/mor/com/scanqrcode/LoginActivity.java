package mor.com.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity
{
            String urlWebServicesLocal = " http://192.168.2.42:80/sucata/validaLogin.php";
            String urlWebServicesDesenvolvimento = "http://10.10.100.24:80/sucata/validaLogin.php";
            String urlWebServicesProducao = " http://192.168.2.42:80/sucata/validaLogin.php";


            StringRequest stringRequest; //validação login
            RequestQueue requestQueue;   //validação login

            EditText user_name, editLogin, editSenha;
            Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this); // necessário para validação login

        editLogin = findViewById(R.id.user_name);
        editSenha = findViewById(R.id.user_pass);
        btnLogar  = (Button) findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener()//validações ao clicar no botão LOGAR
        {
            @Override
            public void onClick(View view)
            {
                    boolean validade = true;

                if(editLogin.getText().length()==0)// validando se usuário foi digitado
                {
                    editLogin.setError("Campo Login Obrigatório");
                    editLogin.requestFocus();
                    validade = false;
                }
                if(editSenha.getText().length()==0)// validando se senha foi digitado
                {
                    editSenha.setError("Campo Senha Obrigatório");
                    editSenha.requestFocus();
                    validade = false;
                }
                if(validade)
                {
                   // Toast.makeText(getApplicationContext(),"Validando seus dados ...espere",Toast.LENGTH_LONG).show();
                    validarLogin();
                }
            }
        });//FIM validações botão LOGAR
    }

    private void validarLogin()
    {
        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.v("LogLogin", response);

                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean isErro = jsonObject.getBoolean("erro");

                    if(isErro)
                    {
                        Toast.makeText(getApplicationContext(),"Dados inválidos",Toast.LENGTH_LONG).show();
                    }else
                    {
                        alert("Login realizado com sucesso");

                        Intent intent  = new Intent(LoginActivity.this,
                                Main2Activity.class);
                        intent.putExtra("user_name", editLogin.getText().toString());
                        intent.putExtra("user_pass", editSenha.getText().toString());
                        startActivity(intent);
                        finish();
                    }

                }catch (Exception e)
                {
                    Log.v("LogLgin", e.getMessage());
                }
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.e("LogLogin", error.getMessage());
            }
        })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("login", editLogin.getText().toString());
                params.put("senha", editSenha.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void alert (String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
