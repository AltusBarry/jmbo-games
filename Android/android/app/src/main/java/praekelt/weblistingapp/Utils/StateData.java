package praekelt.weblistingapp.Utils;

import android.os.Bundle;

import praekelt.weblistingapp.Rest.Models.Item;

/**
 * Created by altus on 2015/03/24.
 */
public class StateData {
    public Bundle listPosition;
    public String filter;
    public Item inflatedData;

    public StateData() {

    }

    public StateData(Bundle listPosition, boolean inflatedState, String filter, String inflatedView, String prevInflatedView, Item inflatedData) {
        this.listPosition = listPosition;
        this.filter = filter;
        this.inflatedData = inflatedData;
    }
}
