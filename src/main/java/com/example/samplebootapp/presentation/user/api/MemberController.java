package com.example.samplebootapp.presentation.user.api;

import com.example.samplebootapp.application.user.MemberApplicationService;
import com.example.samplebootapp.presentation.user.request.MemberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会員APIコントローラ.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "会員", description = "会員に関連する操作を提供します。")
public class MemberController {

    private final MemberApplicationService memberApplicationService;

    public MemberController(MemberApplicationService memberApplicationService) {
        this.memberApplicationService = memberApplicationService;
    }

    /**
     * 会員を登録します.
     *
     * @param request 会員登録リクエスト
     * @return レスポンス（ボディなし）
     */
    @PostMapping("/")
    @Operation(summary = "会員登録", description = "新しい会員を登録します。")
    public ResponseEntity<Void> register(@RequestBody @Validated MemberRequest request) {
        memberApplicationService.register(
                request.getName(), request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
