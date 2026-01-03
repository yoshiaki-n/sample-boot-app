package com.example.samplebootapp.presentation.shared;

import com.example.samplebootapp.domain.inventory.model.InsufficientStockException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * グローバル例外ハンドラ.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 在庫不足例外ハンドリング.
     *
     * @param e 在庫不足例外
     * @return 400 Bad Request
     */
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStockException(InsufficientStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * 楽観ロック例外ハンドリング.
     *
     * @param e 楽観ロック例外
     * @return 409 Conflict
     */
    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<String> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("競合が発生しました。再試行してください。");
    }

    /**
     * 商品が見つからないなどのIllegalArgumentExceptionハンドリング.
     *
     * @param e IllegalArgumentException
     * @return 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
