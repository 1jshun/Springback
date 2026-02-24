package org.example.back.user;

import lombok.RequiredArgsConstructor;
import org.example.back.user.model.UserDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = "http://localhost:5173",
        allowCredentials = "true"
)

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserDto.SignupReq dto) {
        userService.signup(dto);
        return ResponseEntity.ok("성공");
    }

    @GetMapping("/verify")
    public ResponseEntity<String> verify(@RequestParam String uuid) {
        userService.verify(uuid);
        return ResponseEntity.ok("이메일 인증이 완료되었습니다. 이제 로그인이 가능합니다.");
    }


//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody UserDto.LoginReq dto) {
//        UserDto.LoginRes result = userService.login(dto);
//        if (result == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
//        }
//        return ResponseEntity.ok(result);
//    }
}
