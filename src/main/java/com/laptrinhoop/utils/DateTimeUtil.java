package com.laptrinhoop.utils;

import com.laptrinhoop.constants.Constant;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@UtilityClass
public class DateTimeUtil {

    public static final String DEFAULT_DATE_TIME_FORMAT = Constant.DateTimeFormat.YYYY_MM_DD_HH_MM_SS;

    public static final String DEFAULT_DATE_FORMAT = Constant.DateTimeFormat.YYYY_MM_DD;

    public static String nowToString() {
        return localDateTimeToString(LocalDateTime.now(), DEFAULT_DATE_TIME_FORMAT);
    }

    public static String nowToString(String dateTimeFormat) {
        return localDateTimeToString(LocalDateTime.now(), dateTimeFormat);
    }

    public static String expireTimeInMinutes(int minutes, String dateTimeFormat) {
        LocalDateTime expireTime = LocalDateTime.now().plusMinutes(minutes);
        return localDateTimeToString(expireTime, dateTimeFormat);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTimeToString(localDateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    public static String localDateTimeToString(LocalDateTime localDateTime, String dateTimeFormat) {
        if (Objects.isNull(localDateTime)) {
            return StringUtils.EMPTY;
        }

        return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormat));
    }

    public static String localDateToString(LocalDate localDate) {
        return localDateToString(localDate, DEFAULT_DATE_FORMAT);
    }

    public static String localDateToString(LocalDate localDate, String dateFormat) {
        if (Objects.isNull(localDate)) {
            return StringUtils.EMPTY;
        }

        return localDateTimeToString(localDate.atStartOfDay(), dateFormat);
    }

    public static LocalDateTime stringToLocalDateTime(String dateTimeString, String dateTimeFormat) {
        return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern(dateTimeFormat));
    }

}

