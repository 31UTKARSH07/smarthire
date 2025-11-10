package com.smarthire.smarthire.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    public AuthResponse(String token){
        this.token = token;
    }
}
