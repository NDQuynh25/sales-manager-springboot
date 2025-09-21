package com.example.sales_manager.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.example.sales_manager.util.constant.OrderStatusEnum;
import com.example.sales_manager.util.constant.PaymentMethodEnum;
import com.example.sales_manager.util.constant.PaymentStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mã đơn hàng để tra cứu (khác với id DB)
    @Column(name = "order_code", unique = true, nullable = false, length = 50)
    private String orderCode;

    // Quan hệ n-1 với bảng User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Thông tin người nhận
    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "recipient_phone_number")
    private String recipientPhoneNumber;

    @Column(name = "recipient_code_province")
    private String recipientCodeProvince;

    @Column(name = "recipient_code_district")
    private String recipientCodeDistrict;

    @Column(name = "recipient_code_ward")
    private String recipientCodeWard;

    @Column(name = "recipient_address")
    private String recipientAddress;

    // Quan hệ 1-n với bảng Bill (có thể thanh toán nhiều lần)
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<Bill> bills;

    // Phương thức thanh toán (Credit Card, COD, PayPal, etc.)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethodEnum paymentMethod;

    // Quan hệ 1-n với bảng OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;

    // Quan hệ 1-1 với bảng Review
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    // Tạm tính
    @Column(name = "subtotal_amount", nullable = false)
    private BigDecimal subtotalAmount;

    // Thành tiền cuối cùng
    @Column(name = "grand_total", nullable = false)
    private BigDecimal grandTotal;


    // Phí ship
    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    // Giảm giá
    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    // @Column(name = "voucher_code")
    // private String voucherCode;

    // // Thông tin vận chuyển
    // @Column(name = "shipping_provider")
    // private String shippingProvider;

    // @Column(name = "tracking_number")
    // private String trackingNumber;

    // Ngày đặt hàng
    @Column(name = "order_date")
    private Instant orderDate;

    // Trạng thái đơn hàng
    @Enumerated(EnumType.STRING)
    @Column(name = "status_order", nullable = false)
    private OrderStatusEnum statusOrder;

    // Trạng thái thanh toán
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatusEnum paymentStatus;

    // Thời gian hoàn thành / hủy
    @Column(name = "completed_at")
    private Instant completedAt;

    @Column(name = "canceled_at")
    private Instant canceledAt;

    @Column(name = "note", length = 500)
    private String note;
}
