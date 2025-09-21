package com.example.sales_manager.service;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.example.sales_manager.config.VnPayConfig;
import com.example.sales_manager.dto.request.VnPayRefundReq;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder; 
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class VnPayService {
    


    public String createPaymentUrl(String amountStr, String bankCode, HttpServletRequest req) {
       
        //long amount = Integer.parseInt(amountStr) * 100;
        long amount = 10000*100;
        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
        String orderType = VnPayConfig.orderType;
        String vnp_Version = VnPayConfig.vnp_Version;
        String vnp_Command = VnPayConfig.vnp_Command;


        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_Locale", "vn");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode); // In test environment, bankCode is NCB
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        

        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = VnPayConfig.vnp_PayUrl + "?" + query;

        

        return paymentUrl;
    }

    public String refund(VnPayRefundReq vnPayRefundReq, HttpServletRequest req) throws Exception {
        String vnp_RequestId = VnPayConfig.getRandomNumber(8);
        String vnp_Version = VnPayConfig.vnp_Version;
        String vnp_Command = "refund";
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;
        long totalAmount = vnPayRefundReq.getAmount() * 100L; 
        String vnp_Amount = String.valueOf(totalAmount);
        String vnp_OrderInfo = "Hoan tien GD OrderId:" + vnPayRefundReq.getOrderId();
        String vnp_TransactionNo = "";

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        String vnp_IpAddr = VnPayConfig.getIpAddress(req);

        JsonObject vnp_Params = new JsonObject();
        vnp_Params.addProperty("vnp_RequestId", vnp_RequestId);
        vnp_Params.addProperty("vnp_Version", vnp_Version);
        vnp_Params.addProperty("vnp_Command", vnp_Command);
        vnp_Params.addProperty("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.addProperty("vnp_TransactionType", vnPayRefundReq.getTrantype());
        vnp_Params.addProperty("vnp_TxnRef", vnPayRefundReq.getOrderId());
        vnp_Params.addProperty("vnp_Amount", vnp_Amount);
        vnp_Params.addProperty("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.addProperty("vnp_TransactionDate", vnPayRefundReq.getTransDate());
        vnp_Params.addProperty("vnp_CreateBy", vnPayRefundReq.getUser());
        vnp_Params.addProperty("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.addProperty("vnp_IpAddr", vnp_IpAddr);

        // dữ liệu để hash
        String hashData = String.join("|",
                vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode,
                vnPayRefundReq.getTrantype(), vnPayRefundReq.getOrderId(), vnp_Amount, vnp_TransactionNo,
                vnPayRefundReq.getTransDate(), vnPayRefundReq.getUser(), vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);


     
        
        System.out.println(">>> [DEBUG] VNPAY Refund hashData: " + hashData);
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        vnp_Params.addProperty("vnp_SecureHash", vnp_SecureHash);

        // call API
        URL url = new URL(VnPayConfig.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(vnp_Params.toString());
            wr.flush();
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String output;
        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        return response.toString();
    }



}


