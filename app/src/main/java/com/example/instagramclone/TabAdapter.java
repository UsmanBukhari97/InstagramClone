package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {
        //every tab should be a case

        switch (tabPosition){

            case 0:
                //first tab is profile tab
                ProfileTab profileTab = new ProfileTab();
                return profileTab;
            case 1:
//                UsersTab usersTab = new UsersTab();
//                return usersTab;
                // or
                return new UsersTab();
            case 2:
                return new SharePictureTab();
            default:
                //if we dont go to any tab then must have default
                return null;





        }

        //return null;
    }

    @Override
    public int getCount() {

        //return the number of cases
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //in each position we can know what tab we are dealing with and the title of the tab

        switch (position){
            case 0:
                return "Profile";
            case 1:
                return "Users";
            case 2:
                return "Share Pictures";
                default:
                    //if we dont go to any tab then must have default
                    return null;



        }
        //return super.getPageTitle(position);
    }
}
