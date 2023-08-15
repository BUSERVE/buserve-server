package com.example.buserve.src.configure;

import com.example.buserve.src.bus.entity.*;
import com.example.buserve.src.bus.repository.*;
import com.example.buserve.src.pay.entity.ChargingMethod;
import com.example.buserve.src.pay.repository.ChargingMethodRepository;
import com.example.buserve.src.reservation.entity.Reservation;
import com.example.buserve.src.reservation.repository.ReservationRepository;
import com.example.buserve.src.user.Role;
import com.example.buserve.src.user.SocialType;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(
            BusRepository busRepository,
            RouteRepository routeRepository,
            StopRepository stopRepository,
            RouteStopRepository routeStopRepository,
            UserRepository userRepository,
            ChargingMethodRepository chargingMethodRepository,
            SeatRepository seatRepository,
            ReservationRepository reservationRepository) {

        return args -> {
            seedUsersAndChargingMethods(userRepository, chargingMethodRepository);
            seedBusStopsAndRoutesAndReservations(busRepository, routeRepository, stopRepository, routeStopRepository, seatRepository, userRepository, reservationRepository);
        };
    }

    private void seedUsersAndChargingMethods(UserRepository userRepository, ChargingMethodRepository chargingMethodRepository) {
        User user1 = User.builder()
                .email("user1@example.com")
                .nickname("User1")
                .role(Role.USER)
                .provider("GOOGLE")
                .busMoney(10000)
                .build();
        userRepository.save(user1);

        ChargingMethod method1 = ChargingMethod.builder()
                .name("신한은행")
                .details("1111-1111-1111")
                .user(user1)
                .build();
        method1.setUser(user1);
        chargingMethodRepository.save(method1);
        userRepository.save(user1);

        ChargingMethod method2 = ChargingMethod.builder()
                .name("신한은행")
                .details("2222-2222-2222")
                .user(user1)
                .build();
        chargingMethodRepository.save(method2);

        User user2 = User.builder()
                .email("user2@example.com")
                .nickname("User2")
                .role(Role.USER)
                .busMoney(2000)
                .build();
        userRepository.save(user2);

        ChargingMethod method3 = ChargingMethod.builder()
                .name("우리은행")
                .details("3333-3333-3333")
                .user(user2)
                .build();
        chargingMethodRepository.save(method3);
    }
    private void seedBusStopsAndRoutesAndReservations(
            BusRepository busRepository,
            RouteRepository routeRepository,
            StopRepository stopRepository,
            RouteStopRepository routeStopRepository,
            SeatRepository seatRepository,
            UserRepository userRepository,
            ReservationRepository reservationRepository)
    {
        // Creating Route
        Route route1 = new Route("9802");
        routeRepository.save(route1);

        // Creating Stops
        Stop stop1 = new Stop("공단사거리", 89074);
        stopRepository.save(stop1);

        Stop stop2 = new Stop("검단지식산업센타", 89054);
        stopRepository.save(stop2);


        // Linking Stops to Route via RouteStop
        RouteStop routeStop1 = new RouteStop(route1, stop1, 1, LocalTime.of(0, 0),Direction.UPWARD);
        routeStopRepository.save(routeStop1);

        RouteStop routeStop2 = new RouteStop(route1, stop2, 2, LocalTime.of(0, 5), Direction.UPWARD);
        routeStopRepository.save(routeStop2);

        // Creating Bus
        Bus bus1 = new Bus(20, LocalTime.of(5, 0), route1);
        busRepository.save(bus1);
        seatRepository.saveAll(bus1.getSeats());

        Bus bus2 = new Bus(20, LocalTime.of(5, 30), route1);
        busRepository.save(bus2);
        seatRepository.saveAll(bus2.getSeats());

        // Creating Reservation
        User user1 = userRepository.findByEmail("user1@example.com").get();

        Reservation reservation1 = new Reservation(user1, bus1.getSeats().get(5), stop1, LocalDateTime.of(2023, 8, 15, 7, 0));
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation(user1, bus2.getSeats().get(10), stop2, LocalDateTime.of(2023, 8, 15, 7, 30));
        reservationRepository.save(reservation2);
    }
}
