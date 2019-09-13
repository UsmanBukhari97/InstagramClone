package com.example.instagramclone;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtProfileBio, edtProfileProfession, edtProfileHobbies, edtProfileSport;
    private Button btnUpdateInfo;


    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //container is the view group our activity will contain
        //return inflater.inflate(R.layout.fragment_profile_tab, container, false);

        //in place of set content view in fragments we got inflate
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        //in fragments we instantiate as:
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtProfileBio = view.findViewById(R.id.edtProfileBio);
        edtProfileProfession = view.findViewById(R.id.edtProfileProfession);
        edtProfileHobbies = view.findViewById(R.id.edtProfileHobbies);
        edtProfileSport = view.findViewById(R.id.edtProfileSport);


        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        //accessing parse user
        final ParseUser parseUser = ParseUser.getCurrentUser();

        if (parseUser.get("profileName") == null){
            edtProfileName.setText("");
        } else {
            edtProfileName.setText(parseUser.get("profileName").toString());
        }

        if (parseUser.get("profileBio") == null) {
            edtProfileBio.setText("");
        } else {
            edtProfileBio.setText(parseUser.get("profileBio").toString());
        }

        if (parseUser.get("profileProfession") == null){
            edtProfileProfession.setText("");
        } else {
            edtProfileProfession.setText(parseUser.get("profileProfession").toString());
        }

        if (parseUser.get("profileHobbies") == null){
            edtProfileHobbies.setText("");
        } else {
            edtProfileHobbies.setText(parseUser.get("profileHobbies").toString());
        }

        if (parseUser.get("profileSport") == null){
            edtProfileSport.setText("");
        } else {
            edtProfileSport.setText(parseUser.get("profileSport").toString());
        }


//        //will convert to string to + "" because if a new user signs in it will crash the application
//        // becuase tostring does not rutrn nul value
//        //but this will show null on every object object so to reutn empty vaalue
//        // we will do the following uper if else statements
//        edtProfileName.setText(parseUser.get("profileName") + "");
//        edtProfileBio.setText(parseUser.get("profileBio") + "");
//        edtProfileProfession.setText(parseUser.get("profileProfession") + "");
//        edtProfileHobbies.setText(parseUser.get("profileHobbies") + "");
//        edtProfileSport.setText(parseUser.get("profileSport") + "");

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the profile we updated from the server
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtProfileBio.getText().toString());
                parseUser.put("profileProfession", edtProfileProfession.getText().toString());
                parseUser.put("profileHobbies", edtProfileHobbies.getText().toString());
                parseUser.put("profileSport", edtProfileSport.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info...");
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){


                            //cant call class name in context coz fragment so herefor calling getcontext
                            FancyToast.makeText(getContext(),
                                    "Info Updated", Toast.LENGTH_SHORT,
                                    FancyToast.INFO, true).show();

                        } else {

                            FancyToast.makeText(getContext(),
                                     e.getMessage(), Toast.LENGTH_LONG,
                                    FancyToast.ERROR, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });



        //must return on create view must return
        return view;


    }

}
