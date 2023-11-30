package kr.co.daontec.repository;


import kr.co.daontec.dto.AptiResponse;
import kr.co.daontec.dto.Parameters;
import kr.co.daontec.dto.ResDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SPCall {
    private final JdbcTemplate jdbcTemplate;

    public List<ResDtos.RegisterCar> registerCar(Parameters.RegisterCar registerCar){
        return jdbcTemplate.query(
                "EXEC psp_mid_house_register_car_list_apti_restapi ?,?,?,?",
                ((rs, rowNum) -> ResDtos.RegisterCar.builder()
                        .carNo(rs.getString("car_no"))
                        .regDtm(rs.getString("reg_dtm"))
                        .build()
                ),
                registerCar.getDong(),
                registerCar.getHo(),
                registerCar.getPageNumber(),
                registerCar.getCountPerPage()
        );
    }

    public List<ResDtos.ReservationList> reservationList(Parameters.ReservationList reservationList){
        return jdbcTemplate.query(
                "EXEC psp_mid_visitor_car_list_apti_restapi ?, ?, ?, ?, ?, ?",
                ((rs, rowNum) -> ResDtos.ReservationList.builder()
                        .regNumber(rs.getInt("reg_number"))
                        .carNo(rs.getString("car_no"))
                        .startDt(rs.getString("start_dt"))
                        .endDt(rs.getString("end_dt"))
                        .reserveStatus(rs.getString("reserve_status"))
                        .regDtm(rs.getString("reg_dtm"))
                        .build()
                ),
                reservationList.getDong(),
                reservationList.getHo(),
                reservationList.getStartDt(),
                reservationList.getEndDt(),
                reservationList.getPageNumber(),
                reservationList.getCountPerPage()
        );
    }

    public Optional<ResDtos.InsertReservation> reservation(Parameters.Reservation reservation){
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "EXEC psp_mid_visitor_car_insert_apti_restapi ?, ?, ?, ?, ?",
                ((rs, rowNum) -> ResDtos.InsertReservation.builder()
                        .resultCd(rs.getString("result_cd"))
                        .resultMsg(rs.getString("result_msg"))
                        .regNumber(rs.getInt("reg_number"))
                        .build()
                ),
                reservation.getDong(),
                reservation.getHo(),
                reservation.getCarNo(),
                reservation.getStartDt(),
                reservation.getEndDt()
        ));
    }

    public Optional<AptiResponse> delReservation(Parameters.DelReservation delReservation){
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "EXEC psp_mid_visitor_car_delete_apti_restapi ?, ?, ?",
                ((rs, rowNum) -> AptiResponse.builder()
                        .resultCd(rs.getString("result_cd"))
                        .resultMsg(rs.getString("result_msg"))
                        .build()
                ),
                delReservation.getDong(),
                delReservation.getHo(),
                delReservation.getRegNumber()
        ));
    }

    public List<ResDtos.Inout> inOut(Parameters.Inout inout){
        return jdbcTemplate.query(
                "EXEC psp_mid_parking_inout_list_apti_restapi ?, ?, ?, ?, ?",
                ((rs, rowNum) -> ResDtos.Inout.builder()
                        .regNumber(rs.getInt("reg_number"))
                        .carNo(rs.getString("car_no"))
                        .carType(rs.getString("car_type"))
                        .type(rs.getString("type"))
                        .regDtm(rs.getString("reg_dtm"))
                        .build()
                ),
                inout.getDong(),
                inout.getHo(),
                inout.getCarType(),
                inout.getPageNumber(),
                inout.getCountPerPage()
        );
    }

}
