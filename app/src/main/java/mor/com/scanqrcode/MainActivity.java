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

public class MainActivity extends AppCompatActivity
{
            EditText user_name;
            Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogar  = (Button) findViewById(R.id.btnLogar);
        btnLogar.setOnClickListener(new View.OnClickListener()//validações ao clicar no botão LOGAR
        {
            @Override
            public void onClick(View view)
            {
                    boolean validade = true;
                    TextView tLogin = (TextView) findViewById(R.id.user_name);
                    TextView tSenha = (TextView) findViewById(R.id.user_pass);
                    String login = tLogin.getText().toString();
                    String senha = tSenha.getText().toString();

                if(tLogin.getText().length()==0)// validando se usuário foi digitado
                {
                    tLogin.setError("Campo Obrigatório");
                    tLogin.requestFocus();
                    validade = false;
                }
                if(tSenha.getText().length()==0)// validando se senha foi digitado
                {
                    tLogin.setError("Campo Obrigatório");
                    tLogin.requestFocus();
                    validade = false;
                }
                if(validade)
                {
                    Toast.makeText(getApplicationContext(),"Validando seus dados ...espere",Toast.LENGTH_LONG).show();
                }
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
                }
            }
        });//FIM validações botão LOGAR
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
