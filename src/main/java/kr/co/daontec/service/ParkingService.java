package kr.co.daontec.service;

import kr.co.daontec.dto.AptiResponse;
import kr.co.daontec.dto.Parameters;
import kr.co.daontec.dto.ResDtos;
import kr.co.daontec.repository.SPCall;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingService {
    private final SPCall spCall;

    public ResponseEntity<?> registerCar(Parameters.RegisterCar registerCar) {
        List<ResDtos.RegisterCar> list =
                spCall.registerCar(registerCar);

        boolean isNext = registerCar.getCountPerPage() + 1 == list.size();

        if (isNext) {
            list.remove(list.size() - 1);
        }

        return AptiResponse.pagingRes(
                registerCar.getPageNumber(),
                registerCar.getCountPerPage(),
                isNext,
                list.size(),
                list
        );
    }

    public ResponseEntity<?> reservationList(Parameters.ReservationList reservationList) {
        List<ResDtos.ReservationList> list =
                spCall.reservationList(reservationList);

        boolean isNext = reservationList.getCountPerPage() + 1 == list.size();

        if (isNext) {
            list.remove(list.size() - 1);
        }

        return AptiResponse.pagingRes(
                reservationList.getPageNumber(),
                reservationList.getCountPerPage(),
                isNext,
                list.size(),
                list
        );
    }

    public ResponseEntity<?> reservation(Parameters.Reservation reservation) {
        Optional<ResDtos.InsertReservation> result =
                spCall.reservation(reservation);

        if (result.isEmpty()) {
            return AptiResponse.pagingErrRes(400,"400", "잘못된값입니다.", null);
        }

        return ResponseEntity.status(200).body(result.get());
    }

    public ResponseEntity<?> delReservation(Parameters.DelReservation delReservation) {
        Optional<AptiResponse> result =
                spCall.delReservation(delReservation);

        if (result.isEmpty()) {
            return AptiResponse.pagingErrRes(400,"400", "잘못된값입니다.", null);
        }

        return AptiResponse.reservationDeleteRes(result.get());
    }

    public ResponseEntity<?> inOut(Parameters.Inout inout) {
        List<ResDtos.Inout> list = spCall.inOut(inout);

        boolean isNext = inout.getCountPerPage() + 1 == list.size();

        if (isNext) {
            list.remove(list.size() - 1);
        }

        return AptiResponse.pagingRes(
                inout.getPageNumber(),
                inout.getCountPerPage(),
                isNext,
                list.size(),
                list
        );
    }
}
