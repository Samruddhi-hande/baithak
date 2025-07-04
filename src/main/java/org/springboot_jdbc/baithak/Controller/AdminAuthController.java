package org.springboot_jdbc.baithak.Controller;
import org.springboot_jdbc.baithak.service.AdminAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "https://baithak-sigma.vercel.app/")
@RequestMapping("/admin")
public class AdminAuthController {

    @Autowired
    private AdminAuthService adminAuthService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean isValid = adminAuthService.authenticate(username, password);
        return isValid ? "Login successful" : "Invalid credentials";
    }
}
