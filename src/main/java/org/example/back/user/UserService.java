package org.example.back.user;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.back.user.model.AuthUserDetails;
import org.example.back.user.model.EmailVerify;
import org.example.back.user.model.User;
import org.example.back.user.model.UserDto;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmailVerifyRepository emailVerifyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;


    public void signup(UserDto.SignupReq dto) {
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            String uuid = UUID.randomUUID().toString();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(dto.getEmail());
            String subject = "[안녕] 환영";
            String htmlContents = "<a href='http://localhost:8080/user/verify?uuid="+uuid+"'>이메일 인증</a>";
            helper.setSubject(subject);
            helper.setText(htmlContents, true);
            mailSender.send(message);
            EmailVerify emailVerify = EmailVerify.builder().email(dto.getEmail()).uuid(uuid).build();
            emailVerifyRepository.save(emailVerify);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional // 데이터 수정을 위해 트랜잭션 선언 필수
    public void verify(String uuid) {
        // 1. UUID로 인증 정보 찾기
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 인증 링크입니다."));

        // 2. 이메일로 사용자 찾기
        User user = userRepository.findByEmail(emailVerify.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 3. 사용자 활성화 처리
        user.setEnable(true);
        // 4. 인증 완료된 데이터 삭제 (선택 사항)
        emailVerifyRepository.delete(emailVerify);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserService 실행됨");

        User user = userRepository.findByEmail(username).orElseThrow();

        return AuthUserDetails.from(user);
    }

    public UserDetails login(UserDto.LoginReq dto) throws UsernameNotFoundException {
        System.out.println("UserService 실행됨");

        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow();


        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            // 로그인 성공
        }
        return AuthUserDetails.from(user);
    }
}