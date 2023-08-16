package com.example.buserve.src.reservation.service;

import com.example.buserve.src.bus.entity.Seat;
import com.example.buserve.src.bus.entity.Stop;
import com.example.buserve.src.bus.repository.RouteStopRepository;
import com.example.buserve.src.bus.repository.SeatRepository;
import com.example.buserve.src.bus.repository.StopRepository;
import com.example.buserve.src.reservation.dto.ReservationRequestDto;
import com.example.buserve.src.reservation.dto.ReservationResponseDto;
import com.example.buserve.src.reservation.entity.Reservation;
import com.example.buserve.src.reservation.repository.ReservationRepository;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final int BUS_FARE = 2500;  // 버스 요금
    private static final int NO_SHOW_LIMIT = 2;  // 노쇼 제한 횟수
    private static final int NO_SHOW_PENALTY_LIMIT = 5;  // 노쇼 패널티 제한 횟수
    private static final double PENALTY_RATE = 0.20;  // 패널티 비율

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final StopRepository stopRepository;
    private final RouteStopRepository routeStopRepository;

    public List<ReservationResponseDto> getReservations(Long userId) {
        List<Reservation> reservations = reservationRepository.findAllByUserId(userId);
        return reservations.stream()
                .sorted((r1, r2) -> r2.getExpectedArrivalTime().compareTo(r1.getExpectedArrivalTime()))  // 최근순 정렬
                .map(reservation -> new ReservationResponseDto(
                        reservation.getSeat().getBus().getRoute().getRouteName(),
                        reservation.getStop().getStopName(),
                        reservation.getSeat().getSeatNumber(),
                        reservation.getExpectedArrivalTime(),
                        reservation.getBoardingStatus()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservationResponseDto createReservation(Long userId, ReservationRequestDto requestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        checkNoShowPenalty(user);

        // 버정머니 확인
        if (user.getBusMoney() < BUS_FARE) {
            throw new IllegalArgumentException("버정머니가 부족합니다.");
        }

        Seat seat = seatRepository.findById(requestDto.getSeatId()).orElseThrow(() -> new IllegalArgumentException("좌석을 찾을 수 없습니다."));

        // 좌석 예약 가능 여부 확인
        if (!seat.isAvailable()) {
            throw new IllegalArgumentException("이미 예약된 좌석입니다.");
        }

        Stop stop = stopRepository.findById(requestDto.getStopId()).orElseThrow(() -> new IllegalArgumentException("정류장을 찾을 수 없습니다."));
        LocalDateTime expectedArrivalTime = convertToExpectedArrivalTime(requestDto.getExpectedArrivalTime());

        // 사용자가 해당 시간에 이미 예약한 내역이 있는지 확인
        List<Reservation> userReservations = reservationRepository.findAllByUserAndExpectedArrivalTime(user, expectedArrivalTime);
        if (!userReservations.isEmpty()) {
            throw new IllegalArgumentException("이미 해당 시간에 예약이 있습니다.");
        }

        // 버정머니 차감
        user.useBusMoney(BUS_FARE);

        // 예약 객체 생성
        Reservation reservation = new Reservation(user, seat, stop, expectedArrivalTime);

        // 좌석 상태 변경
        seat.reserveSeat();

        // User에 예약 추가
        user.addReservation(reservation);

        // 변경된 엔티티 저장
        seatRepository.save(seat);
        reservationRepository.save(reservation);

        return new ReservationResponseDto(
                reservation.getSeat().getBus().getRoute().getRouteName(),
                reservation.getStop().getStopName(),
                reservation.getSeat().getSeatNumber(),
                reservation.getExpectedArrivalTime(),
                reservation.getBoardingStatus()
        );
    }

    private void checkNoShowPenalty(User user) {
        if (user.getNoShowCount() >= NO_SHOW_PENALTY_LIMIT) {
            int penaltyAmount = (int) (BUS_FARE * PENALTY_RATE);
            user.useBusMoney(penaltyAmount);  // 페널티로 버정머니 차감
        } else if (user.getNoShowCount() >= NO_SHOW_LIMIT) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime penaltyEndDate = user.getNoShowPenaltyDate().plusDays(7);  // 페널티 시작 날짜에서 7일 더함
            if (now.isBefore(penaltyEndDate)) {
                throw new IllegalArgumentException("노쇼로 인한 예약 제한 중입니다.");
            }
        }
    }

    // 예약 시간을 도착 예정 시간으로 변환하는 메서드
    // 다음 날만 예약할 수 있다고 가정하고 로직 구성
    // 그 외의 날도 포함 시 로직 수정 필요
    private LocalDateTime convertToExpectedArrivalTime(String time) {
        return LocalTime.parse(time).atDate(LocalDate.now().plusDays(1));
    }

    public ReservationResponseDto getReservationDetail(Long userId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("예약을 찾을 수 없습니다.");
        }
        return new ReservationResponseDto(
                reservation.getSeat().getBus().getRoute().getRouteName(),
                reservation.getStop().getStopName(),
                reservation.getSeat().getSeatNumber(),
                reservation.getExpectedArrivalTime(),
                reservation.getBoardingStatus()
        );
    }

    public ReservationResponseDto completeBoarding(Long userId, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new IllegalArgumentException("예약을 찾을 수 없습니다."));
        if (!reservation.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("예약을 찾을 수 없습니다.");
        }
        reservation.completeBoarding();
        reservationRepository.save(reservation);
        return new ReservationResponseDto(
                reservation.getSeat().getBus().getRoute().getRouteName(),
                reservation.getStop().getStopName(),
                reservation.getSeat().getSeatNumber(),
                reservation.getExpectedArrivalTime(),
                reservation.getBoardingStatus());
    }
}
