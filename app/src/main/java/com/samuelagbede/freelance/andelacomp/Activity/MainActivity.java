package com.samuelagbede.freelance.andelacomp.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.samuelagbede.freelance.andelacomp.Adapter.ListAdapter;
import com.samuelagbede.freelance.andelacomp.Model.UsersModel;
import com.samuelagbede.freelance.andelacomp.R;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListAdapter listAdapter;
    Toolbar toolbar;
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

        listView = (ListView)findViewById(R.id.list);
        toolbar = (Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listAdapter = new ListAdapter(this, R.layout.individual_list, usersModelArrayList);

        listView.setAdapter(listAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listView.setNestedScrollingEnabled(true);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                final String username = listAdapter.getItem(position).getUsername();
                final String github_url = listAdapter.getItem(position).getProfile_url();


                //Get reference to imageview from the clicked listview line
                //This allows me get the drawable without having to try the network call to retrieve the image again.
                ImageView original_imageview = (ImageView) view.findViewById(R.id.user_image);
                dialog.show();

                TextView textUsername = (TextView) dialog.findViewById(R.id.user_name);
                TextView textUserUrl = (TextView) dialog.findViewById(R.id.user_url);
                ImageView imageView = (ImageView)dialog.findViewById(R.id.user_image);
                Button share = (Button)dialog.findViewById(R.id.share);

                imageView.setImageDrawable(original_imageview.getDrawable());

                SpannableString spannableString = new SpannableString(github_url);
                Linkify.addLinks(spannableString, Linkify.WEB_URLS);
                textUsername.setText(username);
                textUserUrl.setText(github_url);
                textUserUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_VIEW);
                        share.setData(Uri.parse(github_url));
                        startActivity(Intent.createChooser(share, "Check out this awesome developer @"+username + ", "+github_url));
                    }
                });
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent share = new Intent(Intent.ACTION_VIEW);
                        share.setData(Uri.parse(github_url));
                        startActivity(Intent.createChooser(share, "Check out this awesome developer @"+username + ", "+github_url));

                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listAdapter.getFilter().filter(newText);
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener()
        {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                listAdapter.resetData();
                return true;
            }
        });



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
