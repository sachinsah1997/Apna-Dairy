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
import com.xdeveloperart.apnadairy.adapter.ProductAdapter;
import java.util.List;

    public class ProductFirebase extends ArrayAdapter<ProductAdapter> {
        private FragmentActivity context;
        private int resource;
        private List<ProductAdapter> list;

        public ProductFirebase(@NonNull FragmentActivity context, @LayoutRes int resource, @NonNull List<ProductAdapter> objects) {
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
        public ProductAdapter getItem(int position) {

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
            TextView productName =  v.findViewById(R.id.textView);
            productName.setText(list.get(position).getProductName());
            return v;
        }
    }





