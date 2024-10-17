package com.example.sales_manager.entity;



import java.time.Instant;
import java.time.LocalDateTime;

import com.example.sales_manager.domain.BaseEntity;
import com.example.sales_manager.util.constant.PaymentStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name = "payments", uniqueConstraints = {
    
})
public class Bill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quan hệ n-1 với bảng Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;  // Liên kết với đơn hàng

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

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;  // Phương thức thanh toán (Credit Card, COD, PayPal, etc.)

    @Column(name = "status", nullable = false)
    private PaymentStatusEnum status;  // Trạng thái thanh toán (Completed, Pending, Failed)

    @Column(name= "note" , columnDefinition = "TEXT")
    private String note; // Ghi chú

    public Bill() {
    }

    public Bill(Order order, Instant billDate, Double merchandiseSubtotal, Double discountAmount, Double taxAmount, Double shippingFee, Double totalAmount, Instant paymentDate, PaymentMethod paymentMethod, PaymentStatusEnum status, String note) {
        this.order = order;
        this.billDate = billDate;
        this.merchandiseSubtotal = merchandiseSubtotal;
        this.discountAmount = discountAmount;
        this.taxAmount = taxAmount;
        this.shippingFee = shippingFee;
        this.totalAmount = totalAmount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Instant getBillDate() {
        return billDate;
    }

    public void setBillDate(Instant billDate) {
        this.billDate = billDate;
    }

    public Double getMerchandiseSubtotal() {
        return merchandiseSubtotal;
    }

    public void setMerchandiseSubtotal(Double merchandiseSubtotal) {
        this.merchandiseSubtotal = merchandiseSubtotal;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}