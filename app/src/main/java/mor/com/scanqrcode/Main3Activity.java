package mor.com.scanqrcode;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main3Activity extends AppCompatActivity {

        private ListView listView;
        private List<Map<String,Object>> lista;
        private listaMovimento adapter;
        private String[] de = {"usuario", "coletor", "peso", "data_hora"};
        private int[] para = {R.id.txUsuario, R.id.txColetor, R.id.txPeso, R.id.txData_Hora};

        String urlWebServicesLocal = " http://192.168.2.42:80/sucata/validaLogin.php";
        String urlWebServicesDesenvolvimento = "http://10.10.100.24:80/sucata/lista_coletor.php";
        String urlWebServicesHomologacao = "http://192.168.1.23:80/sucata_chamados/validaLogin.php";
        String urlWebServicesProducao = "http://192.168.1.23:80/sucata/validaLogin.php";

        StringRequest stringRequest; //validação login
        RequestQueue requestQueue;   //validação login

        Button btnBuscaTotal,btnBuscaDia;

        @Override
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main3);

            requestQueue = Volley.newRequestQueue(this); // necessário para buscar dados do banco

            btnBuscaTotal = (Button) findViewById(R.id.btnBuscaTotal);

            btnBuscaTotal.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                        // Toast.makeText(getApplicationContext(),"Validando seus dados ...espere",Toast.LENGTH_LONG).show();
                        exibeMovimentoColetaTotal();
                }
            });
            btnBuscaDia = (Button) findViewById(R.id.btnBuscaDia);

            btnBuscaDia.setOnClickListener(new View.OnClickListener()//validações ao clicar no botão LOGAR
            {
                @Override
                public void onClick(View view)
                {
                    // Toast.makeText(getApplicationContext(),"Validando seus dados ...espere",Toast.LENGTH_LONG).show();
                    exibeMovimentoColetaDia();
                }
            });
        }

        public void exibeMovimentoColetaTotal()
        {
            listView = findViewById(R.id.listView_coletar);
            lista = new ArrayList<>();

            final String controle1 = "1";

            stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    //Log.v("LogLogin", response);

                    try {
                        JSONObject object = new JSONObject(response);
                        JSONArray Jarray = object.getJSONArray("response");
                        String usuario = "", coletor = "", peso = "", data_hora = "";

                        for (int i = 0; i < Jarray.length(); i++)
                        {
                            Map<String,Object> items = new HashMap<>();

                            JSONObject Jasonobject = Jarray.getJSONObject(i);
                            usuario = Jasonobject.getString("usuario");
                            coletor = Jasonobject.getString("produto");
                            peso = Jasonobject.getString("peso");
                            data_hora = Jasonobject.getString("data_hora");
                            //Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_LONG).show();

                            items.put("usuario", "  Usuário: " + usuario);
                            items.put("coletor", "  Coletor: " + coletor);
                            items.put("peso", "  Peso Bruto: " + peso);
                            items.put("data_hora", "  Data/Hora: " + data_hora);
                            lista.add(items);
                        }

                        adapter = new listaMovimento( getApplication(), lista, R.layout.lista_movimento_coletor, de, para);
                        listView.setAdapter(adapter);
                    }
                    catch (Exception e)
                    {
                        Log.v("LogLogin", e.getMessage());
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
                    params.put("controle1", controle1);

                    return params;
                }
            };
            requestQueue.add(stringRequest);

        }

        private void alert (String s)
        {
            Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        }

    public void exibeMovimentoColetaDia()
    {
        listView = findViewById(R.id.listView_coletar);
        lista = new ArrayList<>();

         final String controle2 = "2";

        stringRequest = new StringRequest(Request.Method.POST, urlWebServicesDesenvolvimento, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String responseDia)
            {
                Log.v("LogLogin2", responseDia);
                //Toast.makeText(getApplicationContext(), responseDia, Toast.LENGTH_LONG).show();

                try {
                    JSONObject object = new JSONObject(responseDia);
                    JSONArray Jarray = object.getJSONArray("responseDia");
                    String usuario = "", coletor = "", peso = "", data_hora = "";

                    for (int i = 0; i < Jarray.length(); i++)
                    {
                        Map<String,Object> items = new HashMap<>();

                        JSONObject Jasonobject = Jarray.getJSONObject(i);
                        usuario = Jasonobject.getString("usuario");
                        coletor = Jasonobject.getString("produto");
                        peso = Jasonobject.getString("peso");
                        data_hora = Jasonobject.getString("data_hora");
                        //Toast.makeText(getApplicationContext(), usuario, Toast.LENGTH_LONG).show();

                        items.put("usuario", "  Usuário: " + usuario);
                        items.put("coletor", "  Coletor: " + coletor);
                        items.put("peso", "  Peso Bruto: " + peso);
                        items.put("data_hora", "  Data/Hora: " + data_hora);
                        lista.add(items);
                    }

                    adapter = new listaMovimento( getApplication(), lista, R.layout.lista_movimento_coletor, de, para);
                    listView.setAdapter(adapter);
                }
                catch (Exception e)
                {
                    Log.v("LogLogin", e.getMessage());
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
                params.put("controle2", controle2);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void alertDia (String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}

