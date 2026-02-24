package org.example.back.user;

import lombok.RequiredArgsConstructor;
import org.example.back.user.model.AuthUserDetails;
import org.example.back.user.model.User;
import org.example.back.user.model.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public void signup(UserDto.SignupReq dto) {
        User user = dto.toEntity();
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
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