package com.rednorte.bff.client;

import com.rednorte.bff.dto.LoginRequest;
import com.rednorte.bff.dto.LoginResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ms-auth", url = "${ms.auth.url:http://ms-auth:8084}")
public interface AuthClient {

    @PostMapping("/api/auth/login")
    LoginResponse login(@RequestBody LoginRequest request);
}
