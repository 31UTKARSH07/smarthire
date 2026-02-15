package com.smarthire.smarthire.resume.utils;

import com.smarthire.smarthire.resume.NestedClasses.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationParser {
    public Location parseLocation(String locationStr) {
        if(locationStr == null) return null;
        String[] parts = locationStr.split(",");

        Location.LocationBuilder builder = Location.builder();

        if (parts.length >= 1) {
            builder.city(parts[0].trim());
        }
        if (parts.length >= 2) {
            builder.region(parts[1].trim());
        }
        return builder.build();
    }
}

