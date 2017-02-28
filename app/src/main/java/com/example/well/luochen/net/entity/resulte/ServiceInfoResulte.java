package com.example.well.luochen.net.entity.resulte;

/**
 * Created by Well on 2017/2/15.
 */

public class ServiceInfoResulte {
    private String account_base_url = "";
    private String temperature_base_url = "";
    private String remarks_base_url = "";
    private String history_base_url = "";
    private String app_update_base_url = "";
    private String firmware_update_base_url = "";
    private String knowledge_url = "";
    private String about_us_url = "";
    private String app_log_base_url = "";

    @Override
    public String toString() {
        return "ServiceInfoResulte{" +
                "account_base_url='" + account_base_url + '\'' +
                ", temperature_base_url='" + temperature_base_url + '\'' +
                ", remarks_base_url='" + remarks_base_url + '\'' +
                ", history_base_url='" + history_base_url + '\'' +
                ", app_update_base_url='" + app_update_base_url + '\'' +
                ", firmware_update_base_url='" + firmware_update_base_url + '\'' +
                ", knowledge_url='" + knowledge_url + '\'' +
                ", about_us_url='" + about_us_url + '\'' +
                ", app_log_base_url='" + app_log_base_url + '\'' +
                '}';
    }
}
