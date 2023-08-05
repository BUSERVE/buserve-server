package com.example.buserve.src.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.buserve.src.common.ApiResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 실패한 경우
     private ApiResponse(ApiResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    public static <T> ApiResponse<T> success(T result) {
        return new ApiResponse<>(SUCCESS.isSuccess(), SUCCESS.getMessage(), SUCCESS.getCode(), result);
    }

    public static <T> ApiResponse<T> successWithNoContent() {
        return new ApiResponse<>(SUCCESS.isSuccess(), SUCCESS.getMessage(), SUCCESS.getCode(), null);
    }

    public static ApiResponse<?> error(ApiResponseStatus status) {
        return new ApiResponse<>(status.isSuccess(), status.getMessage(), status.getCode(), null);
    }
}