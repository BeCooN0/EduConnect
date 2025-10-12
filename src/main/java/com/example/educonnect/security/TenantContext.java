package com.example.educonnect.security;

public class TenantContext {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();

    public static String getCurrentContext() {
        return CONTEXT.get();
    }
    public static void setCurrentContext(String tenant) {
        CONTEXT.set(tenant);
    }
    public static void clear(){
        CONTEXT.remove();
    }
}
