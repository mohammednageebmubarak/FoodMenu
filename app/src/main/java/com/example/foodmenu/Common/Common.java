package com.example.foodmenu.Common;

import com.example.foodmenu.Model.User;

/**
 * Created by mubarak on 12/9/17.
 */

public class Common {

    public static User currentUser;
    public static final String DELETE = "Delete";

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "On my way";
        else
            return "Shipped";
    }


}
