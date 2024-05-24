package qreol.project.bankingservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import qreol.project.bankingservice.domain.User;
import qreol.project.bankingservice.service.UserService;
import qreol.project.bankingservice.web.dto.RemoveEmailRequest;
import qreol.project.bankingservice.web.dto.RemovePhoneNumberRequest;
import qreol.project.bankingservice.web.dto.UpdateEmailRequest;
import qreol.project.bankingservice.web.dto.UpdatePhoneNumberRequest;
import qreol.project.bankingservice.web.validator.UserValidator;

import java.time.LocalDate;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User API")
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    @Operation(summary = "Update or add user email")
    @PostMapping("/{login}/emails")
    @PreAuthorize("@customSecurityExpression.canAccessUserByLogin(#login)")
    public ResponseEntity<User> updateEmail(
            @PathVariable("login") String login,
            @RequestBody @Validated UpdateEmailRequest emailRequest,
            BindingResult bindingResult) {
        userValidator.validate(emailRequest, bindingResult);

        return ResponseEntity.ok(userService.updateEmail(login, emailRequest));
    }

    @Operation(summary = "Update or add user phone number")
    @PostMapping("/{login}/phoneNumbers")
    @PreAuthorize("@customSecurityExpression.canAccessUserByLogin(#login)")
    public ResponseEntity<User> updatePhoneNumber(
            @PathVariable("login") String login,
            @RequestBody @Validated UpdatePhoneNumberRequest phoneNumberRequest,
            BindingResult bindingResult) {
        userValidator.validate(phoneNumberRequest, bindingResult);

        return ResponseEntity.ok(userService.updatePhoneNumber(login, phoneNumberRequest));
    }

    @Operation(summary = "Remove user email")
    @DeleteMapping("/{login}/emails")
    @PreAuthorize("@customSecurityExpression.canAccessUserByLogin(#login)")
    public ResponseEntity<User> removeEmail(
            @PathVariable("login") String login,
            @RequestBody @Validated RemoveEmailRequest removeEmailRequest) {

        return ResponseEntity.ok(userService.removeEmail(login, removeEmailRequest.getEmail()));
    }

    @Operation(summary = "Remove user phone number")
    @DeleteMapping("/{login}/phoneNumbers")
    @PreAuthorize("@customSecurityExpression.canAccessUserByLogin(#login)")
    public ResponseEntity<User> removePhoneNumber(
            @PathVariable("login") String login,
            @RequestBody @Validated RemovePhoneNumberRequest removePhoneNumberRequest) {

        return ResponseEntity.ok(userService.removePhoneNumber(login, removePhoneNumberRequest.getPhoneNumber()));
    }

    @Operation(summary = "Search users with filters and pagination",
            description = "Allows to search users by various filters such as fullName, phoneNumber, email, and dateOfBirth with pagination and sorting.",
            parameters = {
                    @Parameter(name = "page", description = "Page number (0-based index)", schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Number of items per page", schema = @Schema(type = "integer", defaultValue = "10")),
                    @Parameter(name = "sort", example = "id,asc", description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending", schema = @Schema(type = "string"))
            }
    )
    @GetMapping("/search")
    public Page<User> searchUsers(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
            @Parameter(hidden = true) @PageableDefault(sort = "id") Pageable pageable) {
        return userService.searchUsers(fullName, phoneNumber, email, dateOfBirth, pageable);
    }

}
