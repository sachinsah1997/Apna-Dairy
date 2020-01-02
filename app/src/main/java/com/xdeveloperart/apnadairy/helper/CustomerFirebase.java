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
import com.xdeveloperart.apnadairy.adapter.CustomerAdapter;
import java.util.List;

public class CustomerFirebase extends ArrayAdapter<CustomerAdapter> {
        private FragmentActivity context;
        private int resource;
        private List<CustomerAdapter> list;

        public CustomerFirebase(@NonNull FragmentActivity context, @LayoutRes int resource, @NonNull List<CustomerAdapter> objects) {
            super(context, resource, objects);
            this.context = context;
            this.resource = resource;
            list = objects;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CustomerAdapter getItem(int position) {

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
            TextView customerName =  v.findViewById(R.id.textView);
            customerName.setText(list.get(position).getCustomerName());
            return v;
        }
    }





