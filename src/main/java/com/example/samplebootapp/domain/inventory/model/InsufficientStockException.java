package com.example.samplebootapp.domain.inventory.model;

/** 在庫不足例外. */
public class InsufficientStockException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public InsufficientStockException(String message) {
    super(message);
  }
}
