package com.ogre.slide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Hungry Ogre on 4/26/2016.
 */
public class JSONParser {

    HashMap<String,String> doctors=new HashMap<>();
    public HashMap<String,String> getDoctors()
    {
        doctors.put("Pramod Gulati","Surgeon");
        doctors.put("Ranu Sharma","Cardeologist");
        doctors.put("Sanjeev Gupta","Physiotherapist");
        doctors.put("Nalin Naag","gynaecologist");
        doctors.put("Vinod Sahu","physician");
        doctors.put("Piyush Khare","Dentist");
        doctors.put("Divya Tiwari","Eye Specialist");
        doctors.put("Vrishti Gahlot","Orthodontist");
        doctors.put("Jasdeep Singh Khurana","orthopedist");
        doctors.put("Saloni Singh","Dermotologist");
        return doctors;
    }
}
