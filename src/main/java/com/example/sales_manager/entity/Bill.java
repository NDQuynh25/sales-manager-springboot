package com.example.sales_manager.entity;



import java.time.Instant;
import java.util.List;

import com.example.sales_manager.util.constant.PaymentMethodEnum;
import com.example.sales_manager.util.constant.PaymentStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id")
})
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ n-1 với bảng Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // Liên kết với đơn hàng

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentTransaction> paymentTransactions;

    @Column(name = "bill_date", nullable = false)
    private Instant billDate;  // Ngày xuất hóa đơn

    @Column(name = "merchandise_subtotal", nullable = false)
    private Double merchandiseSubtotal;  // Tổng số tiền của hàng hóa

    @Column(name = "discount_amount")
    private Double discountAmount;  // Số tiền giảm giá (nếu có)
    
    @Column(name = "tax_amount")
    private Double taxAmount;  // Tiền thuế (nếu có)

    @Column(name = "shipping_fee")
    private Double shippingFee;  // Phí vận chuyển (nếu có)

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;  // Tổng số tiền cần thanh toán

    @Column(name = "payment_date")
    private Instant paymentDate;  // Ngày thanh toán (nếu đã thanh toán)

    @Column(name = "payment_method", nullable = false)
    private PaymentMethodEnum paymentMethod;  // Phương thức thanh toán (Credit Card, COD, PayPal, etc.)

    @Column(name = "status", nullable = false)
    private PaymentStatusEnum status;  // Trạng thái thanh toán (Completed, Pending, Failed)

    @Column(name= "note" , columnDefinition = "TEXT")
    private String note; // Ghi chú

    
}