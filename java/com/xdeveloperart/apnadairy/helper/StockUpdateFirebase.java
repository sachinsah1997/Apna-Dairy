package com.xdeveloperart.apnadairy.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.xdeveloperart.apnadairy.R;
import java.util.List;
import java.util.Map;

public class StockUpdateFirebase extends ArrayAdapter<Map> {
        private FragmentActivity context;
        private int resource;
        private List<Map> list;

        public StockUpdateFirebase(@NonNull FragmentActivity context, @LayoutRes int resource,@NonNull List<Map> objects) {
            super(context, resource,objects);
            this.context = context;
            this.resource = resource;
            list = objects;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map getItem(int position) {
            return list.get(getCount() - position - 1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            View v = inflater.inflate(resource, null);
            TextView salesmanName =  v.findViewById(R.id.textView);
            Object obj = list.get(position).get("date");
            salesmanName.setText(obj.);
            return v;
        }
    }





