package com.ilnaz.server.dto.resp;

import lombok.Data;

@Data
public class TokenResp {
    private String token;
    public TokenResp(String token){
        this.token = token;
    }
}
