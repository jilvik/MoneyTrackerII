package com.jambau.moneytracker;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResult {

    private String status;
    private int id;

    @SerializedName("auth_token")
    private String token;
}
