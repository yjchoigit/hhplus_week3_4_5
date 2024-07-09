package com.hhplus.hhplus_week3_4_5.application.controllers.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    // 결제 API
    @PostMapping(value = "/payments/{orderId}")
    public boolean payment(@PathVariable("orderId") String orderId) {
        return true;
    }

}
