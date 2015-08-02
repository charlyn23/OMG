package charlyn23.c4q.nyc.omg;

import charlyn23.c4q.nyc.omg.model.ContactInfo;
import charlyn23.c4q.nyc.omg.model.Program;

/**
 * Created by charlynbuchanan on 8/1/15.
 */
public class ResourceObject {
    //name, address, phone, hoursForEachDay,
    String name;
    String address;
    String phone;
    String hours;
    MainActivity mainActivity = new MainActivity();
    Program program = new Program();
    ContactInfo contactInfo;





//    List<Program> programs = mainActivity.searchResult;


    ResourceObject resourceObject(String name, String address, String phone, String hours) {
        return null;
    }

    ResourceObject(){};

    public String getAddress() {

        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getName() {
        program.getName();
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        contactInfo.getContact();
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
