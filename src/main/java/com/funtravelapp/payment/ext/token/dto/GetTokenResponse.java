package com.funtravelapp.payment.ext.token.dto;

import com.funtravelapp.payment.dto.Response;
import com.funtravelapp.payment.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTokenResponse {

    private Response.Error error;
    private User data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error {
        private String message;
    }
}
