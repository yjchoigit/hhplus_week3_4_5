package com.hhplus.hhplus_week3_4_5.application.controllers.point;

import com.hhplus.hhplus_week3_4_5.application.controllers.point.dto.GetPointHistoryApiResDto;
import com.hhplus.hhplus_week3_4_5.application.domain.point.PointEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    // 포인트 조회 API
    @GetMapping(value = "/points/{buyerId}")
    public int point(@PathVariable(name = "buyerId") Long buyerId) {
        return 100;
    }

    // 포인트 충전 API
    @PostMapping(value = "/points/{buyerId}")
    public boolean chargePoint(@PathVariable(name = "buyerId") Long buyerId, @RequestParam(name = "point") int point) {
        return true;
    }
    
    // 포인트 내역 조회 API
    @GetMapping(value = "/points/history/{buyerId}")
    public List<GetPointHistoryApiResDto> pointHistory(@PathVariable(name = "buyerId") Long buyerId) {
        List<GetPointHistoryApiResDto> list = new ArrayList<>();
        list.add(new GetPointHistoryApiResDto(1L, PointEnums.Type.CHARGE, 2000, LocalDateTime.now()));
        return list;
    }
}
