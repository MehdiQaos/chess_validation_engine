package dev.mehdi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    Player turn;
    boolean isCheck;
    int moveNumber;
    boolean isValid;

    public static Response invalid() {
        return Response.builder()
                .isValid(false)
                .build();
    }
}