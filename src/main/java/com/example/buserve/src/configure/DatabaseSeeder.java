package com.example.buserve.src.configure;

import com.example.buserve.src.bus.entity.*;
import com.example.buserve.src.bus.repository.*;
import com.example.buserve.src.bus.service.RouteService;
import com.example.buserve.src.pay.entity.ChargingMethod;
import com.example.buserve.src.pay.repository.ChargingMethodRepository;
import com.example.buserve.src.reservation.entity.Reservation;
import com.example.buserve.src.reservation.repository.ReservationRepository;
import com.example.buserve.src.user.Role;
import com.example.buserve.src.user.SocialType;
import com.example.buserve.src.user.User;
import com.example.buserve.src.user.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(
            BusRepository busRepository,
            RouteRepository routeRepository,
            StopRepository stopRepository,
            RouteStopRepository routeStopRepository,
            RouteService routeService,
            UserRepository userRepository,
            ChargingMethodRepository chargingMethodRepository,
            SeatRepository seatRepository,
            ReservationRepository reservationRepository) {

        return args -> {
            seedUsersAndChargingMethods(userRepository, chargingMethodRepository);
            seedBusStopsAndRoutesAndReservations(busRepository, routeRepository, routeService, stopRepository, routeStopRepository, seatRepository, userRepository, reservationRepository);
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
            RouteService routeService,
            StopRepository stopRepository,
            RouteStopRepository routeStopRepository,
            SeatRepository seatRepository,
            UserRepository userRepository,
            ReservationRepository reservationRepository) throws IOException, ParseException {
        // Creating Route
        Route route1 = new Route("ICB165000160", "9100"); // 57
        routeRepository.save(route1);
        Route route2 = new Route("ICB165000161", "9200"); // 60
        routeRepository.save(route2);
        Route route3 = new Route("ICB165000162", "9300"); // 81
        routeRepository.save(route3);
        Route route4 = new Route("ICB165000303", "9802"); // 80
        routeRepository.save(route4);

        // Creating Stops
        List<String> routeIds = routeService.getAllRouteId();

        String key = "lF8UEJMTnm7SpZKEcgBRzazgp0JNAxAwLEu9H%2BG844NuHoC4DZS8qbdDNpM1WoBTq1jimtK%2BW2P6N4kksiuwBQ%3D%3D";
        String cityCode = "23";
        String endPoint = "http://apis.data.go.kr/1613000/BusRouteInfoInqireService/";
        String details = "getRouteAcctoThrghSttnList";

        for (int i = 0; i < routeIds.size(); i++) {
            String routeId = routeIds.get(i);
            URL url = new URL(endPoint + details + "?serviceKey=" + key
                    + "&cityCode=" + cityCode + "&numOfRows=100"
                    + "&routeId=" + routeId + "&_type=json");

            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String result = bf.readLine();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
            JSONObject parseItems = (JSONObject) ((JSONObject) ((JSONObject) jsonObject.get("response")).get("body")).get("items");
            JSONArray array = (JSONArray) parseItems.get("item");

            for (int j = 0; j < array.size(); j++) {
                jsonObject = (JSONObject) array.get(j);
                String routeid = jsonObject.get("routeid").toString();      // 노선 ID
                String nodeid = jsonObject.get("nodeid").toString();        // 정류소 ID
                String nodeno = jsonObject.get("nodeno").toString();        // 정류소 번호
                String nodenm = jsonObject.get("nodenm").toString();        // 정류소 명
                String gpslati = jsonObject.get("gpslati").toString();      // 정류소 Y좌표
                String gpslong = jsonObject.get("gpslong").toString();      // 정류소 X좌표
                String nodeord = jsonObject.get("nodeord").toString();      // 정류소 순번
                String updowncd = jsonObject.get("updowncd").toString();    // 상하행 [0: 상행 1: 하행]

                // DB 추가
                Stop stop = new Stop(nodeid, nodenm, nodeno);
                stopRepository.save(stop);
            }

        }

        Stop stop1 = new Stop(routeIds.get(1), "공단사거리", "89074");
        stopRepository.save(stop1);
        Stop stop2 = new Stop("id2", "검단지식산업센타", "89054");
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
