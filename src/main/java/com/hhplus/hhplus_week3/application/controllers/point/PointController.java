package com.hhplus.hhplus_week3.application.controllers.point;

import com.hhplus.hhplus_week3.application.controllers.point.dto.GetPointHistoryApiResDto;
import com.hhplus.hhplus_week3.application.domain.point.PointEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    // 포인트 조회 API
    @GetMapping(value = "/points/{memberId}")
    public int point(@PathVariable(name = "memberId") Long memberId) {
        return 100;
    }

    // 포인트 충전 API
    @PostMapping(value = "/points/{memberId}")
    public boolean chargePoint(@PathVariable(name = "memberId") Long memberId, @RequestParam(name = "point") int point) {
        return true;
    }
    
    // 포인트 내역 조회 API
    @GetMapping(value = "/points/history/{memberId}")
    public List<GetPointHistoryApiResDto> pointHistory(@PathVariable(name = "memberId") Long memberId) {
        List<GetPointHistoryApiResDto> list = new ArrayList<>();
        list.add(new GetPointHistoryApiResDto(1L, PointEnums.Type.CHARGE, 2000, LocalDateTime.now()));
        return list;
    }
}
