package com.example.instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {

    private ListView listView;
    private ArrayList arrayList;
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

}
