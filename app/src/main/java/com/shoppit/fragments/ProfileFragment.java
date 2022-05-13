package com.shoppit.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shoppit.R;
import com.shoppit.activities.LoginActivity;
import com.shoppit.adapters.CategoriesAdapter;
import com.shoppit.adapters.ProfileHistoryAdapter;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private TextView tvProfileMessage;
    private TextView tvProfileHistory;
    private RecyclerView rvProfileHistory;
    private Button btnSignout;
    private ProfileHistoryAdapter profileHistoryAdapter;
    private List<Item> historyList;
    public static final String TAG = "Profile Fragment";

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar= Toolbar.class.cast(getActivity().findViewById(R.id.toolbar));
        toolbar.setTitle("Profile");
        historyList = new ArrayList<>();

        tvProfileMessage = view.findViewById(R.id.tvHistoryName);
        tvProfileHistory = view.findViewById(R.id.tvProfileHistory);
        rvProfileHistory = view.findViewById(R.id.rvProfileHistory);
        btnSignout = view.findViewById(R.id.btnSignout);

        tvProfileMessage.setText("Hi " + ParseUser.getCurrentUser().getString("username"));
        tvProfileHistory.setText("See your previous order: ");

        getUserItemHistory();
        profileHistoryAdapter = new ProfileHistoryAdapter(getContext(), historyList);

        rvProfileHistory.setAdapter(profileHistoryAdapter);
        rvProfileHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ParseUser.getCurrentUser() != null){
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null){
                                Toast.makeText(getContext(), "Issue with logging out", Toast.LENGTH_SHORT).show();
                            }
                            //return to login activity
                            Intent intent = new Intent(getContext(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().onBackPressed();
                        }
                    });
                }
            }
        });



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Hide the action_add menu item
        MenuItem itemAdd = menu.findItem(R.id.action_add);
        itemAdd.setVisible(false);
    }
    private void getUserItemHistory() {
        // Define the class we would like to query
        ParseQuery<Item> historyParseQuery = ParseQuery.getQuery(Item.class);

        // Include user key
        historyParseQuery.include(Category.KEY_CATEGORY_NAME);
        // Execute the find asynchronously
        historyParseQuery.findInBackground(new FindCallback<Item>() {
            @Override
            public void done(List<Item> items, ParseException e) {
                if (e != null){
                    Log.e(TAG, "ERROR while retrieving history", e);
                    return;
                }

                for (Item item: items){
                    Log.i(TAG,  " Item Name: " + item.getItemName());
                }

                // Add all categories to the list
                historyList.addAll(items);
                // Notify the adapter about the data change
                profileHistoryAdapter.notifyDataSetChanged();
            }
        });
    }
}