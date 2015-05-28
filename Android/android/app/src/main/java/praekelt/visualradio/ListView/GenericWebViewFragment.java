package praekelt.visualradio.ListView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.Serializable;

import praekelt.visualradio.R;
import praekelt.visualradio.Rest.Models.Item;

public class GenericWebViewFragment extends Fragment {
        private Item item;
        public fragmentCallback callback;
        private boolean firstLaunch = true;

        public void onAttach(Activity activity) {
            super.onAttach(activity);
            try {
                callback = (fragmentCallback) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
            }
        }

        public interface fragmentCallback {
            public void initView(String id);
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
            return inflater.inflate(R.layout.generic_webview, container, false);
        }


        public void onStart() {
            if(firstLaunch) {
                callback.initView("game");
            } else {
                initDetail();
            }
            super.onStart();
        }

        public void setDetail(Item data) {
            item = data;
            initDetail();
        }

        /**
         * Assigns data to the views in the layout
         */
        public void initDetail() {
            WebView textBody = (WebView) getView().findViewById(R.id.text_body);
                textBody.loadData("http://127.0.0.1:8000" + (item.getPermalink()), "text/html; charset=UTF-8", null);
                getActivity().getActionBar().setTitle(R.string.title_news);

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
            outState.putSerializable("viewData", (Serializable) item);
            super.onSaveInstanceState(outState);
        }

}
