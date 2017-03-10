package com.samuelagbede.freelance.andelacomp.Activity;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ListFragment;
import android.support.v4.text.util.LinkifyCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.samuelagbede.freelance.andelacomp.Adapter.ListAdapter;
import com.samuelagbede.freelance.andelacomp.Model.UsersModel;
import com.samuelagbede.freelance.andelacomp.R;
import com.samuelagbede.freelance.andelacomp.Volley.Singleton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListAdapter listAdapter;
    private ArrayList<String> usersModel;
    private Toolbar toolbar;
    private ArrayList<UsersModel> usersModelArrayList;
    private ListView listView;
    private AlertDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usersModelArrayList = (ArrayList<UsersModel>) getIntent().getSerializableExtra("serialize");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(R.layout.dialog);
        dialog = alertDialog.create();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        listView = (ListView)findViewById(R.id.list);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listAdapter = new ListAdapter(this, R.layout.individual_list, usersModelArrayList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final String username = usersModelArrayList.get(position).getUsername();
                final String github_url = usersModelArrayList.get(position).getProfile_url();

                //Get reference to imageview from the clicked listview line
                //This allows me get the drawable without having to try the network call to retrieve the image again.
                ImageView original_imageview = (ImageView) view.findViewById(R.id.user_image);
                dialog.show();

                TextView textView = (TextView) dialog.findViewById(R.id.user_name);
                ImageView imageView = (ImageView)dialog.findViewById(R.id.user_image);
                Button share = (Button)dialog.findViewById(R.id.share);

                imageView.setImageDrawable(original_imageview.getDrawable());

                SpannableString spannableString = new SpannableString(github_url);
                Linkify.addLinks(spannableString, Linkify.WEB_URLS);
                textView.setText("Github url : "+ github_url);
                textView.setMovementMethod(LinkMovementMethod.getInstance());
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_VIEW);
                        share.setData(Uri.parse(github_url));
                        startActivity(Intent.createChooser(share, "Check out this awesome developer @"+username + ", "+github_url));

                    }
                });
                dialog.setTitle(username);



            }
        });
    }


}
