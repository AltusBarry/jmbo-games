package praekelt.weblistingapp.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import praekelt.weblistingapp.R;
import praekelt.weblistingapp.Rest.API;
import praekelt.weblistingapp.Rest.Models.Item;
import praekelt.weblistingapp.Utils.Constants;
import praekelt.weblistingapp.Utils.JSONUtils;
import praekelt.weblistingapp.Utils.StringUtils;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ModelBaseDetailFragment extends Fragment {
    private Item item;
    private boolean firstLaunch = true;
    private Bundle extras;

    private ImageLoader imageLoader;


    public void onAttach(Activity activity) {
        super.onAttach(activity);

        extras = getArguments();
        imageLoader = new ImageLoader(getActivity().getApplicationContext());
    }

    public void onCreate(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            item = (Item) savedInstanceState.getSerializable("viewData");
            firstLaunch = false;
        } else {
            firstLaunch = true;
        }
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onStart();
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        return inflater.inflate(R.layout.modelbase_detail_view, container, false);
    }


    public void onStart() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.LOCAL_URL_BASE)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        API.JMBOApi api = restAdapter.create(API.JMBOApi.class);

        api.getItem(extras.getString("resource_uri").substring(1), new Callback<JsonElement>() {
            @Override
            public void success(JsonElement s, Response response) {
                Log.d("STRING", String.valueOf(s));
                try {
                    initDetail(s);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        super.onStart();
    }

    /**
     * Assigns data to the views in the layout
     */
    public void initDetail(JsonElement element) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object detail = null;
        try {
            detail = JSONUtils.getDetailObject(element);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }

        Method text = detail.getClass().getMethod("getTitle");
        TextView textTitle = (TextView) getView().findViewById(R.id.text_title);

        textTitle.setText((String) text.invoke(detail));


        text = detail.getClass().getMethod("getContent");
        TextView textBody = (TextView) getView().findViewById(R.id.text_body);

        textBody.setText(Html.fromHtml((String) text.invoke(detail)));

        ImageView image = (ImageView) getView().findViewById(R.id.image);

        text = detail.getClass().getMethod("getImage");

        // TODO reduce amount of code here
        imageLoader.displayImage(Constants.LOCAL_URL_BASE + (String) text.invoke(detail), image, (StringUtils.uniqueMD5(Constants.LOCAL_URL_BASE + (String)text.invoke(detail))),
                (getActivity().getApplicationContext().getExternalFilesDir(null)).toString() + "/images");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState (Bundle outState) {
        //outState.putSerializable("viewData", (Serializable) item);
        super.onSaveInstanceState(outState);
    }

}
