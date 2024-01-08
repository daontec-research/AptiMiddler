package kr.co.daontec.dto.aptireq;

import kr.co.daontec.dto.proto.DavansM09Broadcast;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventInOut {
    private String inout;
    private String visitType;
    private int rsvid;
    private String carNumber;
    private String regDtm;
    private String dong;
    private String ho;
    private String carImagePath;

    public static EventInOut convertEventInOutDto(DavansM09Broadcast.m0903_b_car_entry_complate m903, String inout) {
        return EventInOut.builder()
                .inout(inout)
                .visitType(m903.getCustCd().equals("PP0102") ? "family" : "visitor")
                .rsvid(m903.getInCustRegId())
                .carNumber(m903.getCarNo())
                .regDtm(m903.getInDtm())
                .dong(m903.getHouseholdInfo1())
                .ho(m903.getHouseholdInfo2())
                .carImagePath(m903.getImgPathEntry())
                .build();
    }

    public static EventInOut convertEventInOutDto(DavansM09Broadcast.m0906_b_car_exit_complate m906, String inout){
        return EventInOut.builder()
                .inout(inout)
                .visitType(m906.getCustCd().equals("PP0102") ? "family" : "visitor")
                .rsvid(m906.getInCustRegId())
                .carNumber(m906.getCarNo())
                .regDtm(m906.getOutDtm())
                .dong(m906.getHouseholdInfo1())
                .ho(m906.getHouseholdInfo2())
                .carImagePath(m906.getImgPathEntry())
                .build();
    }
}
