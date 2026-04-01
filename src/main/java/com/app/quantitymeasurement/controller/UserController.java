package com.app.quantitymeasurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurement.dto.AuthResponse;
import com.app.quantitymeasurement.dto.LoginDTO;
import com.app.quantitymeasurement.dto.RegisterDTO;
import com.app.quantitymeasurement.service.AuthService;


@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private AuthService userService;

	@PostMapping("/register")
	public AuthResponse register(@RequestBody RegisterDTO dto) {
		return userService.register(dto);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginDTO dto) {
		return userService.login(dto);
	}
	@GetMapping("/check")
		public String open() {
			return "Working";
		}
	
}
