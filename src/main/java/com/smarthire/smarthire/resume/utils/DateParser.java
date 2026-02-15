package com.smarthire.smarthire.resume.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateParser {
    public String extractProjectEndDate(String duration){
        if((duration == null) || !duration.contains("-")) return null;

        String end = duration.split("-")[1].trim();
        return formatDate(end);

    }

    public String formatDate(String dateStr){
        if(dateStr == null || dateStr.isEmpty()){
            return null;
        }

        if(dateStr.equalsIgnoreCase("Present") || dateStr.equalsIgnoreCase("Current")){
            return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE);
        }

        if (dateStr.matches("\\d{4}")) {
            return dateStr + "-01-01";
        }

        if (dateStr.matches("[A-Za-z]{3}\\s+\\d{4}")) {
            String[] parts = dateStr.split("\\s+");
            String month = convertMonthToNumber(parts[0]);
            String year = parts[1];
            return year + "-" + month + "-01";
        }

        return dateStr;
    }
    public String convertMonthToNumber(String monthAbbr) {
        return switch (monthAbbr.toLowerCase()) {
            case "jan", "january" -> "01";
            case "feb", "february" -> "02";
            case "mar", "march" -> "03";
            case "apr", "april" -> "04";
            case "may" -> "05";
            case "jun", "june" -> "06";
            case "jul", "july" -> "07";
            case "aug", "august" -> "08";
            case "sep", "september" -> "09";
            case "oct", "october" -> "10";
            case "nov", "november" -> "11";
            case "dec", "december" -> "12";
            default -> "01";
        };
    }
}
