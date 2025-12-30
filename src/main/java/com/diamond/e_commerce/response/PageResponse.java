package com.diamond.e_commerce.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageResponse<T> {
  
  private List<T> items;
  private Meta meta;

  @Data
  @AllArgsConstructor
  public static class Meta {
    private int page;
    private int size;
    private long totalItems;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
  }
}
