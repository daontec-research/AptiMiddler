package kr.co.daontec.controller;

import kr.co.daontec.dto.Parameters;
import kr.co.daontec.service.ParkingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/parking")
public class ParkingController {
    private final ParkingService service;

    @PostMapping("/car")
    public ResponseEntity<?> registerCar(
            @RequestHeader Map<String,String> header,
            @RequestBody Parameters.RegisterCar registerCar
    ){
        return service.registerCar(registerCar);
    }

    @PostMapping("/reservationlist")
    public ResponseEntity<?> reservationList(
            @RequestHeader Map<String,String> header,
            @RequestBody Parameters.ReservationList reservationList
    ){
        return service.reservationList(reservationList);
    }

    @PostMapping("/reservation")
    public ResponseEntity<?> reservation(
            @RequestHeader Map<String,String> header,
            @RequestBody Parameters.Reservation reservation
    ){
        return service.reservation(reservation);
    }

    @PostMapping("/delreservation")
    public ResponseEntity<?> delReservation(
            @RequestHeader Map<String,String> header,
            @RequestBody Parameters.DelReservation delReservation
    ){
        return service.delReservation(delReservation);
    }

    @PostMapping("/inout")
    public ResponseEntity<?> inOut(
            @RequestHeader Map<String,String> header,
            @RequestBody Parameters.Inout inout
    ){
        return service.inOut(inout);
    }

}
