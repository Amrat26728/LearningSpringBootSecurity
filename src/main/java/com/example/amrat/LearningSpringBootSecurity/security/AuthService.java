package com.example.amrat.LearningSpringBootSecurity.security;

import com.example.amrat.LearningSpringBootSecurity.dto.LoginAndSignupRequestDto;
import com.example.amrat.LearningSpringBootSecurity.dto.LoginResponseDto;
import com.example.amrat.LearningSpringBootSecurity.dto.SignupResponseDto;
import com.example.amrat.LearningSpringBootSecurity.entity.User;
import com.example.amrat.LearningSpringBootSecurity.entity.type.AuthProviderType;
import com.example.amrat.LearningSpringBootSecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public LoginResponseDto login(LoginAndSignupRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    // email/password signup controller
    public SignupResponseDto signup(LoginAndSignupRequestDto signupRequestDto) {
        User user = signUpInternal(signupRequestDto, AuthProviderType.EMAIL, null);

        return modelMapper.map(user, SignupResponseDto.class);
    }

    public User signUpInternal(LoginAndSignupRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId){
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (user != null){
            throw new IllegalArgumentException("User already exists.");
        }
        user = User.builder()
                        .username(signupRequestDto.getUsername())
                        .providerType(authProviderType)
                        .providerId(providerId)
                        .build();
        if (authProviderType == AuthProviderType.EMAIL){
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }
        return userRepository.save(user);
    }


    public ResponseEntity<LoginResponseDto> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId){
        // fetch providerType and providerId
        AuthProviderType providerType = authUtil.getAuthProviderTypeFromRegistrationId(registrationId);
        String providerId = authUtil.determineProviderIdFromOAuth2User(oAuth2User, registrationId);

        // save the providerType and providerId info with user
        User user = userRepository.findByProviderIdAndProviderType(providerId, providerType).orElse(null);
        String email = oAuth2User.getAttribute("email");
        User emailUser = userRepository.findByUsername(email).orElse(null);

        if (user == null && emailUser == null){
            // signup flow
            String username = authUtil.determineUsernameFromOAuth2User(oAuth2User, registrationId, providerId);
            user = signUpInternal(new LoginAndSignupRequestDto(username, null), providerType, providerId);
        }else if (user != null){
            if (email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else{
            // user is null but emailUser in not null
            throw new BadCredentialsException("This email is already registered with provider: " + emailUser.getProviderId());
        }

        LoginResponseDto loginResponseDto = new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());

        return ResponseEntity.ok(loginResponseDto);
    }
}
