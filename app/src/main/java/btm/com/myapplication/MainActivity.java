package btm.com.myapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private HttpClient httpCliente = HttpClientBuilder.create().build();
    private ArrayAdapter arrayAdapter;
    private List<Product> product = new ArrayList<Product>();
    private CustomAdapter ca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            findAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        if (item.getItemId() == R.id.add_product){
            Intent intent = new Intent(this, AddProduct.class);
            startActivity(intent);

            return true;
        }

        return false;
    }

    public void findAll() throws IOException, JSONException {
        HttpGet client = new HttpGet("http://192.168.1.18:8000/api/products");

        client.addHeader("Content-Type", "application/json");

        HttpResponse response = httpCliente.execute(client);

        String json = EntityUtils.toString(response.getEntity());
        JSONArray obj = new JSONArray(json);

        for(int i  = 0; i < obj.length(); i++){
            JSONObject data = obj.getJSONObject(i);
            product.add(new Product(data.getString("name"), data.getString("desc"), data.getString("value"), data.getInt("id")));
        }

        CustomAdapter ca = new CustomAdapter(this,0, (ArrayList<Product>) product);
        listView.setAdapter(ca);
    }

    public void remove(View view) throws IOException{
        View v = (View) view.getParent();

        TextView txtId = (TextView) v.findViewById(R.id.txt_label_id);
        Integer id = Integer.parseInt(String.valueOf(txtId.getText()));

        HttpDelete client = new HttpDelete("http://192.168.1.9:8000/api/product/" + id);
        client.addHeader("Content-Type", "application/json");
        HttpResponse response = httpCliente.execute(client);

        int statusCode = response.getStatusLine().getStatusCode();

        if(statusCode == 200) {
            Toast.makeText(this, "Apagado com sucesso!", Toast.LENGTH_SHORT).show();

            updateActivity();
        }


    }


    public void updateUi(ArrayList<Product> itens) {
        ca.clear();

        if (itens != null) {
            for (Object object : itens) {
                ca.insert((Product) object, ca.getCount());
            }
        }

        ca.notifyDataSetChanged();
    }

    public void updateActivity() {
        ViewGroup vg = findViewById(R.id.main);
        vg.invalidate();
    }
}
