package org.astrobrains.urlshortener.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("admin123"));
        System.out.println(passwordEncoder.encode("password123"));
        System.out.println(passwordEncoder.encode("password456"));
    }
}
