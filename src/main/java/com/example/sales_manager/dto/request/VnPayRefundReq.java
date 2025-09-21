package com.example.sales_manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VnPayRefundReq {


    @NotBlank(message = "Loại giao dịch không được để trống")
    private String trantype;       // loại giao dịch (partial/full refund)
    @NotBlank(message = "Mã đơn hàng không được để trống")
    private String orderId;        // mã đơn hàng
    
    private Integer amount;        // số tiền
    @NotBlank(message = "Ngày giao dịch gốc không được để trống")
    private String transDate;      // ngày giao dịch gốc
    @NotBlank(message = "Người thực hiện hoàn tiền không được để trống")
    private String user;           // người thực hiện hoàn tiền
}
