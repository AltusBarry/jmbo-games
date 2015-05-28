package praekelt.visualradio.ListView;

import android.text.format.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

import praekelt.visualradio.Utils.JSONValues;

/**
 * Created by altus on 2015/01/20.
 * The class is used as a variable to store all the list data in one object type
 */
public class ModelBase implements Serializable {
    public String title;
    public String imageUrl;
    public String publishOn;
    public String imageDir;
    public String imageName;
    // Extra variable set by DataStore for now as there are no different types. For testing purpouses
    public String type;
    public int id;
    public String subtitle;
    public String operation;

    public ModelBase(JSONObject jsonObject) throws JSONException {
        title = jsonObject.getJSONObject("card").getString("title");

        //TODO Add value for neither existing
        if(jsonObject.getJSONObject("card").has("thumbnail_url")) {
            imageUrl = jsonObject.getJSONObject("card").getString("thumbnail_url");
        }else if(jsonObject.getJSONObject("card").has("image_url")) {
            imageUrl = jsonObject.getJSONObject("card").getString("image_url");
        }

        publishOn = getDate(Long.parseLong(jsonObject.getJSONObject("card").getString("publish_on")));
        type = JSONValues.contentType(jsonObject);
        id = jsonObject.getJSONObject("card").getInt("id");

        // operations: New, Update, Delete

        subtitle = jsonObject.getJSONObject("card").getString("subtitle");
        operation = jsonObject.getJSONObject("card").getString("operation");
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time*1000);
        String date = DateFormat.format("hh:mm aa", cal).toString();
        return date;
    }
}

class Music extends ModelBase {
    public String artist;

    public Music(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        artist = jsonObject.getJSONObject("card").getString("artist");
    }
}

class Post extends ModelBase {
    public String content;

    public Post(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        content = jsonObject.getJSONObject("card").getString("content");
    }
}

class Weather extends ModelBase {
    public JSONArray cities;

    public Weather(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        cities = jsonObject.getJSONObject("card").getJSONArray("cities");
    }
}

class Traffic extends ModelBase {
    public JSONArray incidents;

    public Traffic(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        incidents = jsonObject.getJSONObject("card").getJSONArray("incidents");
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(incidents.toString());
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException, JSONException {
        JSONTokener tokener = new JSONTokener((String) stream.readObject());
        incidents = new JSONArray(tokener);
    }

    private void readObjectNoData() throws ObjectStreamException{

    }
}

class Bulletin extends  ModelBase {
    public String content;
    public JSONArray headlines;

    public Bulletin(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        content = jsonObject.getJSONObject("card").getString("content");
        headlines = jsonObject.getJSONObject("card").getJSONArray("headlines");
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(headlines.toString());
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException, JSONException {
        JSONTokener tokener = new JSONTokener((String) stream.readObject());
        headlines = new JSONArray(tokener);
    }
}

class BaseNotification extends ModelBase {
    public int colour;

    public BaseNotification(JSONObject jsonObject) throws JSONException {
        super(jsonObject);

        colour = jsonObject.getJSONObject("card").getInt("bg_colour");
    }
}



