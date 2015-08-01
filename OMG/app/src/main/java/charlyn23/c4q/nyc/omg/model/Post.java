package charlyn23.c4q.nyc.omg.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by charlynbuchanan on 8/1/15.
 */
public class Post {

    private  String name;

    private String address;

    private Float latitude;

    private Float longitude;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("open_now")
    private boolean openNow;

}
