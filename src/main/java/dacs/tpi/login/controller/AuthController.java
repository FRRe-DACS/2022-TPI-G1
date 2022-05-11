package dacs.tpi.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dacs.tpi.login.dto.forms.user.PostUserDTO;
import dacs.tpi.login.service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid PostUserDTO user){
        
        authService.createUser(user);
        return null;
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
            authService.refreshToken(request, response);
    }
    
}
