package com.example.logan.test2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * Displays the team overview to the user.
 */
public class TeamOverview extends Fragment {
    @Override
    /**
     * Generates a fragment for the user interface.
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
     * @param container	If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here.
     * @return The view instance of the fragment.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_overview, container, false);
        return rootView;
    }
}
