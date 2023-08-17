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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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

        String key = "lF8UEJMTnm7SpZKEcgBRzazgp0JNAxAwLEu9H%2BG844NuHoC4DZS8qbdDNpM1WoBTq1jimtK%2BW2P6N4kksiuwBQ%3D%3D";
        String cityCode = "23";
        String endPoint = "http://apis.data.go.kr/1613000/";

        for (int r = 0; r < 4; r++) {
            // Creating Route
            // String details = "getRouteNoList";
            String routeId, rName;
            if (r == 0) { routeId = "ICB165000160"; rName = "9100"; }
            else if (r == 1) { routeId = "ICB165000161"; rName = "9200"; }
            else if (r == 2) { routeId = "ICB165000162"; rName = "9300"; }
            else { routeId = "ICB165000303"; rName = "9802"; }
            Route route = new Route(routeId, rName);
            routeRepository.save(route);

            // Creating Stops
            // List<String> routeIds = routeService.getAllRouteId();
            String service = "BusRouteInfoInqireService/";
            String details = "getRouteAcctoThrghSttnList";
            URL url = new URL(endPoint + service + details + "?serviceKey=" + key
                    + "&cityCode=" + cityCode + "&numOfRows=100" + "&routeId=" + routeId + "&_type=json");

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
                String gpslati = jsonObject.get("gpslati").toString();      // 정류소 위도(Y좌표)
                String gpslong = jsonObject.get("gpslong").toString();      // 정류소 경도(X좌표)
                String nodeord = jsonObject.get("nodeord").toString();      // 정류소 순번
                Direction updowncd;                                         // 상하행 [0: 상행 1: 하행]
                if (jsonObject.get("updowncd").toString().equals("0")) { updowncd = Direction.UPWARD; }
                else { updowncd = Direction.DOWNWARD; }

                Stop stop = new Stop(nodeid, nodenm, nodeno, gpslati, gpslong);
                stopRepository.save(stop);

                // Creating RouteStop
                RouteStop routeStop = new RouteStop(route, stop, Integer.parseInt(nodeord), LocalTime.of(0, 5), updowncd);
                routeStopRepository.save(routeStop);
            }
        }

        Route route1 = routeRepository.findById("ICB165000160").get();

        // Creating Bus
        Bus bus1 = new Bus(20, LocalTime.of(5, 0), route1);
        busRepository.save(bus1);
        seatRepository.saveAll(bus1.getSeats());

        Bus bus2 = new Bus(20, LocalTime.of(5, 30), route1);
        busRepository.save(bus2);
        seatRepository.saveAll(bus2.getSeats());

        // Creating Reservation
        User user1 = userRepository.findByEmail("user1@example.com").get();
        List<Stop> stops = stopRepository.findAll();
        Stop stop1 = stops.get(0);
        Stop stop2 = stops.get(1);

        Reservation reservation1 = new Reservation(user1, bus1.getSeats().get(5), stop1, LocalDate.now().plusDays(1).atTime(7, 0));
        reservationRepository.save(reservation1);

        Reservation reservation2 = new Reservation(user1, bus2.getSeats().get(10), stop2, LocalDate.now().plusDays(1).atTime(7, 30));
        reservationRepository.save(reservation2);
    }
}
