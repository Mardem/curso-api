package btm.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

public class AddProduct extends AppCompatActivity {

    private EditText txtName;
    private EditText txtDesc;
    private EditText txtValue;
    private HttpClient httpClient = HttpClientBuilder.create().build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        txtName = (EditText) findViewById(R.id.nome);
        txtDesc = (EditText) findViewById(R.id.desc);
        txtValue = (EditText) findViewById(R.id.valor);
    }

    public void save(View view) throws JSONException, IOException {
        HttpPost client = new HttpPost("http://192.168.1.3:8000/api/jumper-login");

        JSONObject obj = new JSONObject();

        obj.put("name", txtName.getText());
        obj.put("desc", txtDesc.getText());
        obj.put("value", txtValue.getText());

        client.addHeader("Content-Type", "application/json");
        client.addHeader("Accept","application/json");

        StringEntity strObj = new StringEntity(obj.toString());
        client.setEntity(strObj);

        HttpResponse response = httpClient.execute(client);

        int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode == 201) {
            Toast.makeText(this, "Criado com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
}
