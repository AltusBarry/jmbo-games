package praekelt.weblistingapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import praekelt.weblistingapp.ListView.ModelBaseDetailFragment;
import praekelt.weblistingapp.ListView.IndexListFragment;
import praekelt.weblistingapp.LoginArea.LoginActivity;
import praekelt.weblistingapp.Rest.API;
import praekelt.weblistingapp.Rest.Models.Item;
import praekelt.weblistingapp.Rest.Models.ReceivedProfileData;
import praekelt.weblistingapp.Rest.Models.VerticalThumbnailListing;
import praekelt.weblistingapp.Utils.Constants;
import praekelt.weblistingapp.Utils.StateData;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Altus Barry
 * @version 1.0
 *
 * Main Activity of the app.
 * Inflates the layouts and handles fragments
 */

public class MainActivity extends Activity implements IndexListFragment.listCallbacks,
        HelperFragment.helperCallbacks {

    private static final String HELPER_TAG = "helper_fragment";
    private FragmentManager manager;
    private StateData stateData;

    // Fragments
    private HelperFragment helper;

    // Initial Fragments
    private IndexListFragment listFragment;

    private ModelBaseDetailFragment modelBaseDetailFragment;

    ReceivedProfileData profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getSerializableExtra("profileData") != null) {
            profile = (ReceivedProfileData) getIntent().getSerializableExtra("profileData");
            Log.d("Profile Username", profile.getUsername());
            Log.d("Profile Authentication", profile.getAuthentication());
        }

        manager = getFragmentManager();

        setContentView(R.layout.main_activity);

        Log.i("MainActivity: ", "Layout set");

        // Retained Fragment that is used to store data and delete old files.
        helper = (HelperFragment) manager.findFragmentByTag(HELPER_TAG);

        if(helper == null) {
            helper = new HelperFragment();
            manager.beginTransaction().add(helper, HELPER_TAG).commit();
        }

        // Checks if savedInstanceState is null, if not assumes that data was saved ot to the retained fragment
        if(savedInstanceState == null) {
            stateData = new StateData();
            Log.i("SavedBundleState", "Null");
        }else {
            stateData = helper.getData();
            Log.i("SavedBundleState", savedInstanceState.toString());
        }
        initFragments();
    }

    protected void onNewIntent (Intent intent) {
        if(intent.getSerializableExtra("profileData") != null) {
            profile = (ReceivedProfileData) intent.getSerializableExtra("profileData");
            Log.d("Profile Username", profile.getUsername());
            Log.d("Profile Authentication", profile.getAuthentication());
        }
    }
    protected void onStop() {
        super.onStop();
    }
    protected void onPause() {
        super.onPause();
    }

    /**
     * Saves a boolean for the inflated state
     * also passes the old data of the inflated view to save bundle (empty/null if it wasn't inflated)
     * @param outState
     */
    public void onSaveInstanceState(Bundle outState) {
        // TODO save last played song
        // TODO save all this data in object as opposed to loose variables
        helper.setData(stateData);
        super.onSaveInstanceState(outState);
        Log.i("onSavedInstanceState", "data Passed to dataHandler");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_listing, menu);
        return true;
    }

    /**
     * When the up button on actionbar is pressed
     * Initiates the back method
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("Menu Button: ", "Up Navigation");
                onBackPressed();
                return true;
            case R.id.profile:
                Log.i("Menu Button: ", "Profile");
                if(profile == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    MainActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, UpdateProfileActivity.class);
                    intent.putExtra("profileData", profile);
                    MainActivity.this.startActivityForResult(intent, 1);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the android back button is pressed
     * Initiates the back method
     * Currently assumes that you never moved more than one view away from liste
     */
    public void onBackPressed() {
        if(listFragment != null) {
            reInflateIndex();
        Log.i("Button Press: ", "Back");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       Log.d("Update", "Successfully");

       if (requestCode == 1) {
           if (resultCode == RESULT_OK) {
               this.profile = new ReceivedProfileData();
               this.profile = (ReceivedProfileData) data.getSerializableExtra("profileData");
           }
       }
    }

    private static final Object detailObj = null;
    // END ANDROID SPECIFIC METHODS
    List<Item> list = new ArrayList<>();
    private void getList() {
        Object detailObj = null;
        final String responseJSON = "";
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.LOCAL_URL_BASE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.JMBOApi api = restAdapter.create(API.JMBOApi.class);

//        System.out.println(address.toString());
        api.getVerticalThumbnailListing(new Callback<VerticalThumbnailListing>() {

            @Override
            public void success(VerticalThumbnailListing listing, Response response) {
                //Log.i("TITLE LIST:", listing.getTitle());

                list = (listing.getItems());
                setData(listing.getItems());
                Log.d("List Length: ", String.valueOf(list.size()));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        setData(list);
    }

    // TODO
    /**
     * Adds Fragments to views or checks if Fragments already exist and re uses them
     */
    private void initFragments() {
        // Fragment that drives the list View and its contents
        // Only gets added if no other view is inflated
        listFragment = (IndexListFragment) manager.findFragmentByTag("main_index_list");

        if(findViewById(R.id.list_fragment) != null) {
            if(listFragment == null) {
                listFragment = new IndexListFragment();
                manager.beginTransaction().replace(R.id.list_fragment, listFragment, "main_index_list").commit();
            }
        }
    }

    /**
     * Replaces the list view with an inflated view
     * inflatedState is toggled to true
     * the inflated Data to be saved out is set
     * the Fragment is given its data to use
     * @param
     */
    // TODO init it via registry getDetailClass
    public void inflateView(String type, String uri) {
        Bundle extras = new Bundle();
        extras.putString("type", type);
        extras.putString("resource_uri", uri);

        if (modelBaseDetailFragment == null) {
            modelBaseDetailFragment = new ModelBaseDetailFragment();
            manager.beginTransaction().replace(R.id.list_fragment, modelBaseDetailFragment, type).commit();
            modelBaseDetailFragment.setArguments(extras);
        }else {
            manager.beginTransaction().replace(R.id.list_fragment, modelBaseDetailFragment, type).commit();
            modelBaseDetailFragment.setArguments(extras);
        }
    }

    /**
     * Sets the position of the listView each time the ListFragment's onPause methods is called
     * @param position
     */
    public void setPosition(Bundle position) {
        stateData.listPosition = position;
    }

    /**
     * Used by lit view to retrieve the most up to date data it should currently be displaying, usually on orient change and back navigation
     * @return
     */
    @Override
    public void updateList() {
        ExecutorService executorService =  Executors.newSingleThreadExecutor();
        getList();
    }

    @Override
    public String getFilter() {
        return null;
    }

    /**
     * As long as listFragment exists the data will be updated as soon as the loader passes new data along
     * @param data
     */

    public void setData(List<Item> data) {

        // Update List
        if(data.size() == 0) {
            return;
        }

        if(listFragment != null) {
                listFragment.setListData(data);
        }
    }

    /**
     * Set the detail for each respective Inflated View, called by each fragment to ensure no null pointer exceptions happen
     * due to view not being inflated yet
     * @param id
     */
    //@Override
    public void initView(String id) {
//        switch(id) {
//            case "InflatedList":
//                break;
//        }
    }

    //@Override
    public void sendMessage(String directory, String fileName) {
        helper.sendPost(directory, fileName);
    }

    private void reInflateIndex() {
        getActionBar().setDisplayHomeAsUpEnabled(false);
        getActionBar().setTitle(R.string.app_name);

        listFragment = (IndexListFragment) manager.findFragmentByTag("main_index_list");
        if(findViewById(R.id.list_fragment) != null) {
            if(listFragment == null) {
                listFragment = new IndexListFragment();
                manager.beginTransaction().replace(R.id.list_fragment, listFragment, "main_index_list").commit();
            } else {
                manager.beginTransaction().replace(R.id.list_fragment, listFragment, "main_index_list").commit();
            }
            listFragment.refocused(stateData.listPosition, true);
        }
    }

    private String filter = "";

    /**
     * Display a Toast on screen
     * @param message The text to be displayed
     */
    public void makeToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Post a notification to the list
     * Constants.NOTIFICATION_SUCCESS_COLOUR, Constants.NOTIFICATION_ERROR_COLOUR
     * @param colour int = 0 Success (Green), int = 1 Failure (Red);
     * @param text The text to be displayed
     * @param operation new or delete.
     */
    public void postNotification(int colour, String text, String operation) {
        makeToast(text);
    }
}



