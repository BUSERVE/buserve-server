package com.example.buserve.src.batch;

import com.example.buserve.src.reservation.entity.BoardingStatus;
import com.example.buserve.src.reservation.entity.Reservation;
import com.example.buserve.src.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationBatch {

    public static final int NO_SHOW_DURATION_MINUTES = 60;
    private final ReservationRepository reservationRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * ?")  // 매 시간 0분 0초마다 실행
    public void checkNoShowReservations() {
        List<Reservation> reservations = reservationRepository.findByBoardingStatus(BoardingStatus.PENDING);

        for (Reservation reservation : reservations) {
            if (isNoShow(reservation.getExpectedArrivalTime())) {
                reservation.markAsNoShow();
                reservationRepository.save(reservation);
            }
        }
    }

    // 노쇼 판단 로직
    private boolean isNoShow(LocalDateTime busExpectedArrivalTime) {
        Duration duration = Duration.between(busExpectedArrivalTime, LocalDateTime.now());
        return duration.toMinutes() >= NO_SHOW_DURATION_MINUTES;  // 버스 예상 출발 시간으로부터 1시간이 지날 시 노쇼 판단
    }
}
