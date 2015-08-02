package charlyn23.c4q.nyc.omg.model;

import java.util.List;

/**
 * Created by charlynbuchanan on 8/1/15. All model classes are specific to elements in the Json. They must be identified
 * and equipped with getters so that Gson can know which items to grab.
 */
public class Program {

    private String name;

    private List<ContactInfo> next_steps;

    public String getName() {
        return name;
    }

    public List<ContactInfo> getNext_steps() {
        return next_steps;
    }

    private List<Offices> offices;

    public List<Offices> getOffices() {
        return offices;
    }
}
