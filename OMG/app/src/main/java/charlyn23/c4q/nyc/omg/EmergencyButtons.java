package charlyn23.c4q.nyc.omg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.view.View;


public class EmergencyButtons extends Activity {


    //Sends an SMS message to another device.

    public void textAllYourFamilyMember(){

        // This is a fixed number. but needs to make it an input

        sendSMS("5556", "Hi You got a message!");

    }

    //pull up the 911 ready to call.

    public void call911(View view){
        String policeAndFireDep = "911";

        Intent callNineOneOne = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + policeAndFireDep));


        if(callNineOneOne.resolveActivity(getPackageManager()) != null){
            startActivity(callNineOneOne);
        }
    }


    private void sendSMS(String phoneNumber, String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

}


