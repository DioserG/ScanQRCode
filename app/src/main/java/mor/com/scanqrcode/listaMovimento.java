package mor.com.scanqrcode;

import android.app.Application;
import android.content.Context;
import android.widget.SimpleAdapter;

import com.android.volley.Response;

import java.util.List;
import java.util.Map;

// sendo utilizado em Main3Activity para gerar a listView
public class listaMovimento extends SimpleAdapter {
    public listaMovimento(Application ctx, List<Map<String, Object>> lista, int layout, String[] de, int[] para) {
        super(ctx, lista, layout, de, para);
    }
}

