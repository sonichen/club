package com.cyj.util;




import com.cyj.domain.User;

import javax.servlet.http.HttpServletRequest;

public class SessionUtils {

    private static final String key = "user";

    public static void setUser(HttpServletRequest request, User user){
        request.getSession().setAttribute(key,user);
    }

    public static User getUser(HttpServletRequest request){
        if(request.getSession().getAttribute(key) != null){
            return (User) request.getSession().getAttribute(key);
        }else{
            return null;
        }
    }


}
