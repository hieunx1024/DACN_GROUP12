package com.laptrinhoop.constants;

public enum VNPayVersion {
    VERSION_2_0_1("2.0.1"),
    VERSION_2_1_0("2.1.0");
    private String version;

    VNPayVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}
