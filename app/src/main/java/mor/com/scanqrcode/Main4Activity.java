package mor.com.scanqrcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;
// sendo utilizado em Main3Activity para gerar a listView
public class Main4Activity extends SimpleAdapter {
    public Main4Activity(Context ctx, List<Map<String, Object>> lista, int layout, String[] de, int[] para) {
        super(ctx, lista, layout, de, para);
    }
}

