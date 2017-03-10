package com.samuelagbede.freelance.andelacomp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.samuelagbede.freelance.andelacomp.Activity.SplashActivity;
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

public class ListAdapter extends ArrayAdapter {
    Context context;
    ArrayList<UsersModel> usersModels;
    LayoutInflater layoutInflater;

    public ListAdapter(Context context, int resource, ArrayList<UsersModel> usersModels) {
        super(context, resource);
        this.usersModels = usersModels;
        this.context = context;
    }


    @Override
    public int getCount() {
        return usersModels.size();
    }



    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.individual_list, null, true);
        }

        TextView userName = (TextView) convertView.findViewById(R.id.user_name);
        final CircleImageView imageView = (CircleImageView)convertView.findViewById(R.id.user_image);
        userName.setText(usersModels.get(position).getUsername());

        Picasso.with(context).load(usersModels.get(position).getImage_url()).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.mipmap.ic_launcher).fit().into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(usersModels.get(position).getImage_url()).placeholder(R.mipmap.ic_launcher).fit().into(imageView, new Callback()
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

        return convertView;
    }
}
