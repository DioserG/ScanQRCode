package mor.com.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
{
            String urlWebServicesDesenvolvimento = "http://192.168.2.42:80/sucata/validaLogin.php";
            String urlWebServicesProducao = "";

            StringRequest stringRequest; //validação login
            RequestQueue requestQueue;   //validação login

            EditText user_name, editLogin, editSenha;
            Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    //TextView tLogin = (TextView) findViewById(R.id.user_name);
                    //TextView tSenha = (TextView) findViewById(R.id.user_pass);
                    //String login = tLogin.getText().toString();
                    //String senha = tSenha.getText().toString();

                if(editLogin.getText().length()==0)// validando se usuário foi digitado
                {
                    editLogin.setError("Campo Obrigatório");
                    editLogin.requestFocus();
                    validade = false;
                }
                if(editSenha.getText().length()==0)// validando se senha foi digitado
                {
                    editSenha.setError("Campo Obrigatório");
                    editSenha.requestFocus();
                    validade = false;
                }
                if(validade)
                {
                    Toast.makeText(getApplicationContext(),"Validando seus dados ...espere",Toast.LENGTH_LONG).show();

                    validarLogin();
                }/*
                if(login.equals("dioser") && senha.equals("123"))
                {
                    alert("Login realizado com sucesso");

                    Intent intent  = new Intent(MainActivity.this,
                            Main2Activity.class);
                    intent.putExtra("user_name", login);
                    intent.putExtra("user_pass", senha);
                    startActivity(intent);
                }else
                {
                    alert("Login ou senha incorretos");
                }*/
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

    public void btnMenu(View view)
    {
        user_name = (EditText) findViewById(R.id.user_name);
        String user_name2 =(user_name.getText().toString());

        Intent intent  = new Intent(MainActivity.this,
                Main2Activity.class);
        intent.putExtra("user_name", user_name2);
        startActivity(intent);
    }
}
