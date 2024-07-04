package com.hhplus.hhplus_week3.application.controllers.order;

import com.hhplus.hhplus_week3.application.controllers.order.dto.GetOrderApiResDto;
import com.hhplus.hhplus_week3.application.domain.payment.PaymentEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    // 주문 생성 API
    @PostMapping(value = "/orders/{memberId}")
    public Long orders(@PathVariable(name = "memberId") Long memberId, @RequestParam(name = "cartIdList") List<Long> cartIdList){
        return 1L;
    }

    // 주문 조회 API
    @GetMapping(value = "/orders/{memberId}")
    public GetOrderApiResDto orders(@PathVariable(name = "memberId") Long memberId, @RequestParam(name = "memberOrderId") Long memberOrderId){
        // 주문 상세
        List<GetOrderApiResDto.GetOrderDetailApiResDto> detail = new ArrayList<>();
        detail.add(new GetOrderApiResDto.GetOrderDetailApiResDto(
                1L, 1L, 1L, 3
        ));
        // 주문 결제
        List<GetOrderApiResDto.GetOrderPaymentApiResDto> payment = new ArrayList<>();
        payment.add(new GetOrderApiResDto.GetOrderPaymentApiResDto(
                1L, PaymentEnums.Type.PAYMENT, BigDecimal.valueOf(1000), LocalDateTime.now()
        ));

        // 주문
        return new GetOrderApiResDto(
                1L, "202407040001", 3,
                LocalDateTime.now(), detail, payment
        );
    }
}
