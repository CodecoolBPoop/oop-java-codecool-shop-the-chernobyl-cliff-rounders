package com.codecool.shop.utilities;


import org.mindrot.jbcrypt.BCrypt;

public class Bcrypt {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean verifyPassword(String candidate_password, String hashedPassword) {
        return BCrypt.checkpw(candidate_password, hashedPassword);

    }

}
