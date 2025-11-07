package com.example.educonnect.entity.common;

public interface Ownable {
    String getTenantId();
    default String getOwnerIdentifier(){
        return null;
    }
}
