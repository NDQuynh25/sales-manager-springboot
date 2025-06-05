package com.example.sales_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultPagination {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Meta {
        @JsonProperty("page")
        private int page;

        @JsonProperty("page_size")
        private int pageSize;

        @JsonProperty("total_elements")
        private long totalElements; 

        @JsonProperty("total_pages")
        private int totalPages;
  

    }
    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("results")
    private Object result;

    
    
}