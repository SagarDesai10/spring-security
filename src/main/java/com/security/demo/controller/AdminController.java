package com.security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.security.demo.controller.common.BaseController;
import com.security.demo.dto.response.SuccessResponse;
import com.security.demo.service.AdminService;
import com.security.demo.utils.StringLiterals;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController extends BaseController<Object>{
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/hello")
	public String helloFromAdmin()
	{
		return "hello from admin";
	}
	
	@GetMapping("/search")
    public ResponseEntity<SuccessResponse<Object>> searchUsers(@RequestParam(name = "search", required = false) String search,
                                                               @RequestParam(name = "size", defaultValue =
                                                                       StringLiterals.STRING_SIZE) Integer size,
                                                                   @RequestParam(name = "page", defaultValue =
                                                                       StringLiterals.STRING_PAGE) Integer page) {
        return success(adminService.searchUser(search,page, size),
            StringLiterals.OK, HttpStatus.OK);
    }
}
