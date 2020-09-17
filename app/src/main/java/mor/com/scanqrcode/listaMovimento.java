package mor.com.scanqrcode;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import java.util.List;
import java.util.Map;

import static android.graphics.PorterDuff.*;

// sendo utilizado em Main3Activity para gerar a listView
public class listaMovimento extends SimpleAdapter {
    public listaMovimento(Application ctx, List<Map<String, Object>> lista, int layout, String[] de, int[] para) {
        super(ctx, lista, layout, de, para);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        //String tag = "Log Dioser";

            if(position %2 == 0){
                v.setBackgroundColor(Color.parseColor("#ddd9ce"));
            }else{
                v.setBackgroundColor(Color.WHITE);
            }

        return v;
    }
}

