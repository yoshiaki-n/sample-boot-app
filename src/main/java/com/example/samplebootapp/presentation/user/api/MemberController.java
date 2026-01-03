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

import com.example.samplebootapp.application.security.MemberUserDetails;
import com.example.samplebootapp.application.user.query.UserQueryService;
import com.example.samplebootapp.presentation.user.response.UserResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * 会員APIコントローラ.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "会員", description = "会員に関連する操作を提供します。")
public class MemberController {

    @SuppressWarnings("PMD.SingularField")
    private final MemberApplicationService memberApplicationService;
    @SuppressWarnings("PMD.SingularField")
    private final UserQueryService userQueryService;

    public MemberController(MemberApplicationService memberApplicationService,
            UserQueryService userQueryService) {
        this.memberApplicationService = memberApplicationService;
        this.userQueryService = userQueryService;
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

    /**
     * ログイン中の会員情報を取得します.
     *
     * @param principal 認証情報
     * @return 会員情報
     */
    @org.springframework.web.bind.annotation.GetMapping("/me")
    @Operation(summary = "会員情報取得", description = "ログイン中の会員情報を取得します。")
    public ResponseEntity<UserResponse> me(
            @AuthenticationPrincipal MemberUserDetails principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserResponse response = userQueryService
                .findById(principal.getMember().getId());
        return ResponseEntity.ok(response);
    }
}
