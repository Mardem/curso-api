package btm.com.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Marden on 23/09/2018.
 */

public class CustomAdapter extends ArrayAdapter<Product> {

    private Context context;

    public CustomAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int pos, View converView, ViewGroup parent){
        Product p = getItem(pos);

        if(converView == null) {
            converView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        TextView txtName = (TextView) converView.findViewById(R.id.txt_label);
        TextView txtId = (TextView) converView.findViewById(R.id.txt_label_id);

        txtName.setText(p.getName());
        txtId.setText(p.getId().toString());

        return converView;
    }
}
