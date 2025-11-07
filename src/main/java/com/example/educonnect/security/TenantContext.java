package com.example.educonnect.security;


public final class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();
    public static final String PUBLIC = "public";

    private TenantContext() {}

    public static void setTenantId(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static String getTenantId() {
        String id = CURRENT_TENANT.get();
        return (id == null || id.isBlank()) ? PUBLIC : id;
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}

