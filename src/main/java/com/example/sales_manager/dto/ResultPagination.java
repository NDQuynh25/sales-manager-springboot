package com.example.sales_manager.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultPagination {

    public class Meta {
        @JsonProperty("page")
        private int page;

        @JsonProperty("page_size")
        private int pageSize;

        @JsonProperty("total_elements")
        private long totalElements; 

        @JsonProperty("total_pages")
        private int totalPages;

        public Meta() {
        }

        public Meta(int page, int pageSize, long totalElements, int totalPages) {
            this.page = page;
            this.pageSize = pageSize;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(long totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        

    }
    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("results")
    private Object result;

    public ResultPagination() {
    }
    public ResultPagination(Meta meta, Object result) {
        this.meta = meta;
        this.result = result;
    }
    public Meta getMeta() {
        return meta;
    }
    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    public Object getResult() {
        return result;
    }
    public void setResult(Object result) {
        this.result = result;
    }
    
}