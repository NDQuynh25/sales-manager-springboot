package com.example.sales_manager.dto.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReq {

    private Long id;
    private List<MultipartFile> productImages;
    private List<MultipartFile> promotionImages;
    private List<MultipartFile> descriptionImages;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 3, max = 255, message = "Tên sản phẩm phải từ 3-255 ký tự")
    private String productName;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotBlank(message = "Thương hiệu không được để trống")
    private String brand;

    @NotBlank(message = "Xuất xứ không được để trống")
    private String countryOfOrigin;

    @NotEmpty(message = "Danh sách chất liệu không được để trống")
    private List<String> materials;

    @NotEmpty(message = "Danh sách danh mục không được để trống")
    private List<Long> categoryIds;

    private String variation1;
    private List<String> options1;
    private String variation2;
    private List<String> options2;

    @NotEmpty(message = "Danh sách SKU không được để trống")
    @Valid
    private List<SkuReq> skus;

    @NotNull(message = "IsActive not be null")
    @Max(value = 1, message = "Trạng thái chỉ có thể là 0 (Không hoạt động) hoặc 1 (Hoạt động)")
    @Min(value = 0, message = "Trạng thái chỉ có thể là 0 (Không hoạt động) hoặc 1 (Hoạt động)")
    private Integer isActive;
}