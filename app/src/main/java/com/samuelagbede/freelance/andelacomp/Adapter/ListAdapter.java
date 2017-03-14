package com.samuelagbede.freelance.andelacomp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samuelagbede.freelance.andelacomp.Model.UsersModel;
import com.samuelagbede.freelance.andelacomp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Agbede Samuel D on 3/6/2017.
 */

public class ListAdapter extends ArrayAdapter<UsersModel> implements Filterable {
    Context context;
    ArrayList<UsersModel> usersModels;
    ArrayList<UsersModel> originalUsersModels;
    LayoutInflater layoutInflater;
    AdapterFilter filter;


    public ListAdapter(Context context, int resource, ArrayList<UsersModel> usersModels) {
        super(context, resource);
        this.usersModels = usersModels;
        this.context = context;
        this.originalUsersModels = usersModels;
    }

    @Override
    public int getCount() {
        return usersModels.size();
    }

    @Nullable
    @Override
    public UsersModel getItem(int position) {
        return usersModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return usersModels.get(position).hashCode();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        Holder viewHolder = new Holder();

        if (convertView == null)
        {
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.individual_list, null, true);

            CircleImageView imageView = (CircleImageView)v.findViewById(R.id.user_image);
            TextView userName = (TextView) v.findViewById(R.id.user_name);

            viewHolder.imageView = imageView;
            viewHolder.textView = userName;

            v.setTag(viewHolder);
        }
        else
        {
            viewHolder = (Holder)v.getTag();
        }

        viewHolder.textView.setText(usersModels.get(position).getUsername());
        final Holder finalViewHolder = viewHolder;
        Picasso.with(context).load(usersModels.get(position).getImage_url()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).fit().into(viewHolder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("Running from cache", "Picasso is great");
            }

            @Override
            public void onError() {
                Picasso.with(context).load(usersModels.get(position).getImage_url()).placeholder(R.mipmap.ic_launcher).fit().into(finalViewHolder.imageView, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {

                    }

                    @Override
                    public void onError()
                    {
                        Toast.makeText(context, "Network is probably poor, try again later please", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }

    public void resetData(){
        usersModels = originalUsersModels;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
        filter = new AdapterFilter();
        return filter;
    }


    private static class Holder
    {
        CircleImageView imageView;
        TextView textView;
    }


    private class AdapterFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults filterResults = new FilterResults();
            if (constraint == null || constraint.length() == 0)
            {
                filterResults.count = originalUsersModels.size();
                filterResults.values = originalUsersModels;
            }

            else {
                ArrayList<UsersModel> mUsersModels = new ArrayList<>();

                for (UsersModel model : usersModels)
                {
                    if (model.getUsername().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        mUsersModels.add(model);
                    }
                }

                filterResults.count = mUsersModels.size();
                filterResults.values = mUsersModels;

            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count == 0){
                notifyDataSetInvalidated();
            }
            else {
                usersModels = (ArrayList<UsersModel>) results.values;
                notifyDataSetChanged();
            }

        }
    }


}
