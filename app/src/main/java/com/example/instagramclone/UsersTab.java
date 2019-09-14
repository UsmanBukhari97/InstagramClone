package com.example.instagramclone;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;



    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_users_tab, container, false);
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        listView = view.findViewById(R.id.listView);
        arrayList = new ArrayList();
        //context, list item,  data > 3args here
        arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, arrayList);

        //whenever users taps on users itemwe can open that user
        listView.setOnItemClickListener(UsersTab.this);

        //for long click listener
        listView.setOnItemLongClickListener(UsersTab.this);

        final TextView txtLoadingUsers = view.findViewById(R.id.txtLoadingUsers);

        //importing all users in list view
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();

        //current user shouldnot see his name on the list view
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        //find in background to get all from server
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {
                if (e == null){

                    if (users.size() > 0){

                        for (ParseUser user : users){

                            //users we get are in an array list so for that we need to get an array adapter
                            arrayList.add(user.getUsername());


                        }

                        listView.setAdapter(arrayAdapter);
                        //it takes time if net is slow to load users so showing text forthe mean time.
                        txtLoadingUsers.animate().alpha(0).setDuration(2000);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        //opening users profile.
        Intent intent = new Intent(getContext(), UsersPosts.class);
        //when opening users profile the user we selcted it should show its name
        //wanted to pass array list so we innstantiate its data type in class.
        // so its going to givr us the value of the user positioned at that position
        intent.putExtra("username", arrayList.get(position));
        startActivity(intent);



    }

    //this method will be called when we long click on an item
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {


        //gets a query from the server
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        //getting the query username from the server
        parseQuery.whereEqualTo("username", arrayList.get(position));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser user, ParseException e) {

                if (user != null && e == null){

//                    FancyToast.makeText(getContext(),
//                            user.get("profileProfession") + "", Toast.LENGTH_LONG,
//                            FancyToast.SUCCESS,true).show();

                    final PrettyDialog prettyDialog =  new PrettyDialog(getContext());

                    prettyDialog.setTitle(user.getUsername() + " 's Info")
                            .setMessage(user.get("profileBio") + "\n"
                                    + user.get("profileProfession") + "\n"
                                    + user.get("profileHobbies") + "\n"
                                    + user.get("profileSport"))
                            .setIcon(R.drawable.ic_person_black_24dp)
                            .addButton(
                                    "OK",     // button text
                                    R.color.pdlg_color_white,  // button text color
                                    R.color.pdlg_color_green,  // button background color
                                    new PrettyDialogCallback() {  // button OnClick listener
                                        @Override
                                        public void onClick() {
                                            // Do what you gotta do
                                            prettyDialog.dismiss();
                                        }
                                    }
                            )
                            .show();




                }

            }
        });


        return true;
    }
}
