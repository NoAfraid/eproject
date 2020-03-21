package com.eproject.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 验证密码
 */
public class PasswordUtils {
    public static boolean validatorPassord(String password){
        if(password==null){
            return false;
        }
        Pattern p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(password);
//		System.out.println(m.matches()+"---");
        return m.matches();
    }
}
