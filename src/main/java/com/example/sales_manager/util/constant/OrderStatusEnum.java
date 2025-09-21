package com.example.sales_manager.util.constant;


public enum OrderStatusEnum {
    PENDING,                 // 0 - Order đã tạo nhưng chưa thanh toán
    PAID,                    // 1 - Order đã thanh toán thành công
    WAITING_FOR_CONFIRMATION, // 2 - Chờ shop xác nhận
    CONFIRMED,               // 3 - Shop đã xác nhận
    CANCELLED,               // 4 - Đơn bị hủy
    DELIVERY,                // 5 - Đang giao
    COMPLETED,               // 6 - Hoàn tất
    RETURN                   // 7 - Trả hàng / hoàn tiền
}
