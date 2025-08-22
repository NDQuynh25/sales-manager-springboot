package com.example.sales_manager.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.sales_manager.dto.response.ApiResponse;
import com.example.sales_manager.dto.response.PaymentRes;
import com.example.sales_manager.service.VnPayService;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    public final VnPayService vnPayService;

    public PaymentController(VnPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

    @PostMapping("/vnpay/create-payment-url")
    public ResponseEntity<ApiResponse<PaymentRes>> createPaymentUrl(
            @RequestParam("amount") String amountStr,
            HttpServletRequest request
        ) throws Exception {
            
            
            
        String paymentUrl = vnPayService.createPaymentUrl(amountStr, "NCB", request);
        ApiResponse<PaymentRes> response = new ApiResponse<>();
        response.setStatus(200);
        response.setMessage("Tạo URL thanh toán thành công");
        response.setData(new PaymentRes(paymentUrl));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vnpay/return")
    public ResponseEntity<ApiResponse<?>> vnpayReturn(HttpServletRequest request) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setStatus(200);
        response.setMessage("VNPAY return success");
        response.setData(null);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/vnpay/ipn")
    public ResponseEntity<ApiResponse<?>> vnpayIpn(@RequestParam Map<String,String> params) {
        ApiResponse<Object> response = new ApiResponse<>();
        System.out.println(">>> [INFO] VNPAY IPN params: " + params.size());
        response.setStatus(200);
        response.setMessage("VNPAY IPN success");
        response.setData(null);
        return ResponseEntity.ok(response);
    }
    


    
}
