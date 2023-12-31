package com.pacemaker.eta.domain.entity;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum UserRole {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");
    private final String key;
    private static final Map<String, UserRole> lookup = new HashMap<>();

    static{
        for(UserRole auth : UserRole.values()){
            lookup.put(auth.key, auth);
        }
    }

    public String getAbbreviation(){
        return this.key;
    }

    public static UserRole get(String abbreviation){
        return lookup.get(abbreviation);
    }

    public static boolean containsKey(String abbreviation){
        return lookup.containsKey(abbreviation);
    }
}
