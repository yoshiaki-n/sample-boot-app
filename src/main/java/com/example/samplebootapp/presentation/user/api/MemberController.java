package com.example.samplebootapp.presentation.user.api;

import com.example.samplebootapp.application.security.MemberUserDetails;
import com.example.samplebootapp.application.user.MemberApplicationService;
import com.example.samplebootapp.application.user.query.UserDto;
import com.example.samplebootapp.application.user.query.UserQueryService;
import com.example.samplebootapp.presentation.user.request.MemberRequest;
import com.example.samplebootapp.presentation.user.request.MemberUpdateRequest;
import com.example.samplebootapp.presentation.user.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 会員APIコントローラ. */
@RestController
@RequestMapping("/api/users")
@Tag(name = "会員", description = "会員に関連する操作を提供します。")
public class MemberController {

  @SuppressWarnings("PMD.SingularField")
  private final MemberApplicationService memberApplicationService;

  @SuppressWarnings("PMD.SingularField")
  private final UserQueryService userQueryService;

  public MemberController(
      MemberApplicationService memberApplicationService, UserQueryService userQueryService) {
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
    memberApplicationService.register(request.getName(), request.getEmail(), request.getPassword());
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
  public ResponseEntity<UserResponse> me(@AuthenticationPrincipal MemberUserDetails principal) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    UserDto dto = userQueryService.findById(principal.getMember().getId());
    UserResponse response = new UserResponse(dto.getId(), dto.getName(), dto.getEmail());
    return ResponseEntity.ok(response);
  }

  /**
   * ログイン中の会員情報を更新します.
   *
   * @param principal 認証情報
   * @param request 会員更新リクエスト
   * @return レスポンス（ボディなし）
   */
  @PutMapping("/me")
  @Operation(summary = "会員情報更新", description = "ログイン中の会員情報を更新します。")
  public ResponseEntity<Void> update(
      @AuthenticationPrincipal MemberUserDetails principal,
      @RequestBody @Validated MemberUpdateRequest request) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    memberApplicationService.update(
        principal.getMember().getId(), request.getName(), request.getEmail());
    return ResponseEntity.ok().build();
  }

  /**
   * ログイン中の会員を退会します.
   *
   * @param principal 認証情報
   * @return レスポンス（ボディなし）
   */
  @DeleteMapping("/me")
  @Operation(summary = "会員退会", description = "ログイン中の会員を退会します。退会後はログアウトされます。")
  public ResponseEntity<Void> withdraw(@AuthenticationPrincipal MemberUserDetails principal) {
    if (principal == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    memberApplicationService.withdraw(principal.getMember().getId());
    SecurityContextHolder.clearContext();
    return ResponseEntity.ok().build();
  }
}
