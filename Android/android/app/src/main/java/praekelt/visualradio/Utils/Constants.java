package praekelt.visualradio.Utils;

public final class Constants {

    private Constants() {
        // restrict instantiation
    }
    public static final String JAC_BASE_URL = "http://m.jacaranda.kagiso.qa.praekelt.com/v1/api";
    //public static final String JAC_BASE_URL = "http://192.168.0.3:8000/v1/api";
    public static final String VR_BASE_URL = "http://qa-visual-radio.za.prk-host.net/v1/api";

    public static final String LOCAL_URL_BASE = "http://192.168.0.242:8000/api/v1";

    public static final String USER_NAME = "pietiex";
    public static final String USER_PASSWORD = "local";

    public static final String INDEX_LIST = "vs.indexList";
    public static final String INFLATED_TRAFFIC = "vs.inflatedTraffic";
    public static final String INFLATED_MUSIC = "vs.inflatedMusic";
    public static final String INFLATED_NEWS = "vs.inflatedNews";
    public static final String INFLATED_BULLETIN = "vs.inflatedBulletin";
    public static final String INFLATED_FILTER = "vs.inflatedFilter";
    public static final String INFLATED_CALLIN = "vs.inflatedCallin";
    public static final int LIST_LIMIT = 20;

    public static final int NOTIFICATION_ERROR_COLOUR = 0xfff1100;
    public static final int NOTIFICATION_SUCCESS_COLOUR = 0xff04ff00;
}

