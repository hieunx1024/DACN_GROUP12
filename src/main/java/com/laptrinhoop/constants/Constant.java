package com.laptrinhoop.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constant {

    @UtilityClass
    public static class Timeout {
        public static final int RETROFIT_IN_MINUTE = 5;
    }

    @UtilityClass
    public static class DateTimeFormat {
        public static final String YYYY_MM_DD_T_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";
        public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        public static final String MMMM_DD_YYYY = "MMMM dd, YYYY";
        public static final String HH_MM_SS = "HH:mm:ss";
        public static final String HH_MM = "HH:mm";
        public static final String YYYYMMDD = "yyyyMMdd";
    }

    @UtilityClass
    public static class EmailTemplateData {
        public String CUSTOMER_NAME_KEY = "customer_name";
        public String USERNAME_KEY = "username";
        public String PASSWORD_KEY = "password";
        public String URL_ACTIVATE_KEY = "url_activate";
        public String PAYMENT_METHOD_KEY = "payment_method";
        public String EMAIL_KEY = "email";
        public String ADDRESS_KEY = "address";
        public String PHONE_NUMBER_KEY = "phone_number";
        public String ORDER_ID_KEY = "order_id";
        public String DELIVERY_KEY = "delivery_order";
        public String TOTAL_AMOUNT_KEY = "total_amount";
        public String URL_ORDER_DETAIL_KEY = "url_order_detail";
        public String URL_VIEW_SHOP_KEY = "url_view_shop";
        public String TRANSACTION_NO_KEY = "transaction_no";
        public String AMOUNT_PAYMENT_KEY = "amount";
        public String STATUS_PAYMENT_KEY = "status";
        public String BANK_PAYMENT_KEY = "bank";
        public String TRANSACTION_INFO_PAYMENT_KEY = "transaction_info";
        public String TRANSACTION_DATE_PAYMENT_KEY = "transaction_date";
    }

    @UtilityClass
    public static class ContextPath{
        public String ACTIVATE = "/account/activate/{0}";

    }
}
