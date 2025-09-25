package com.example.sales_manager.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import com.example.sales_manager.util.constant.OrderStatusEnum;
import com.example.sales_manager.util.constant.PaymentMethodEnum;
import com.example.sales_manager.util.validation.enums.ValidEnum;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReq {

    @NotBlank(message = "Tên người nhận không được để trống")
    private String recipientName;

    @NotBlank(message = "Số điện thoại người nhận không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String recipientPhoneNumber;

    @NotBlank(message = "Mã tỉnh/thành phố không được để trống")
    private String recipientCodeProvince;

    @NotBlank(message = "Mã quận/huyện không được để trống")
    private String recipientCodeDistrict;

    @NotBlank(message = "Mã phường/xã không được để trống")
    private String recipientCodeWard;

    @NotBlank(message = "Địa chỉ chi tiết không được để trống")
    private String recipientAddress;

    @NotEmpty(message = "Danh sách sản phẩm không được rỗng")
    private List<@Valid OrderItemReq> orderItems;

    @NotNull(message = "Giá trị tạm tính không được để trống")
    @Positive(message = "Giá trị tạm tính phải lớn hơn 0")
    private BigDecimal subtotalAmount;

    @NotNull(message = "Tổng tiền không được để trống")
    @Positive(message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal grandTotal;

    @NotNull(message = "Phí vận chuyển không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Phí vận chuyển >= 0")
    private BigDecimal shippingFee;

    @NotNull(message = "Số tiền giảm giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Số tiền giảm giá >= 0")
    private BigDecimal discountAmount;

    @NotNull(message = "Phương thức thanh toán không được để trống")
    @ValidEnum(enumClass = PaymentMethodEnum.class, message = "Phương thức thanh toán không hợp lệ")
    private PaymentMethodEnum paymentMethod;

    @NotNull(message = "Ngày đặt hàng không được để trống")
    private Instant orderDate;

    @NotNull(message = "Trạng thái đơn hàng không được để trống")
    private OrderStatusEnum statusOrder;

    @Size(max = 1500, message = "Ghi chú không được vượt quá 1500 ký tự")
    private String note;
}

