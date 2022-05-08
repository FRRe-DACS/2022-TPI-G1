package dacs.tpi.login.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import dacs.tpi.login.dto.UserDTO;

public interface AuthService {
    public UserDTO createUser(UserDTO user);
    public UserDTO deleteUser(Long id);
    public UserDTO getUser();
    public void refreshToken(HttpServletRequest request,HttpServletResponse response) throws StreamWriteException, DatabindException, IOException;
}
