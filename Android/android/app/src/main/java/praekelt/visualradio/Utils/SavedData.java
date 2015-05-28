package praekelt.visualradio.Utils;

import android.os.Bundle;

import praekelt.visualradio.ListView.ModelBase;
import praekelt.visualradio.Rest.Models.Game;

/**
 * Created by altus on 2015/03/24.
 */
public class SavedData {
    public Bundle listPosition;
    public boolean inflatedState = false;
    public String filter;
    public String inflatedView;
    public String prevInflatedView;
    public Game inflatedData;

        public SavedData(Bundle listPosition, boolean inflatedState, String filter, String inflatedView, String prevInflatedView, Game inflatedData) {
            this.listPosition = listPosition;
            this.inflatedState = inflatedState;
            this.filter = filter;
            this.inflatedView = inflatedView;
            this.prevInflatedView = prevInflatedView;
            this.inflatedData = inflatedData;
        }
}