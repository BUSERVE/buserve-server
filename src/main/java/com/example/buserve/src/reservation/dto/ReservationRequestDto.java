package com.example.buserve.src.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ReservationRequestDto {
    private Long seatId;
    private String stopId;

    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "expectedArrivalTime 는 HH:mm 포맷으로 입력해야 합니다.")
    @ApiModelProperty(example = "07:30")
    private String expectedArrivalTime;
}
