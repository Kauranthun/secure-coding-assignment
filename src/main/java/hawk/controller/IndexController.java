package hawk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final String TITLE = "title";
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute(TITLE, "StackHawk Java Vulny Application");
        return "index";
    }

    @GetMapping("/jwt-auth")
    public String jwtAuth(Model model) {
        model.addAttribute(TITLE, "JWT Auth");
        return "jwt-auth";
    }

    @GetMapping("/token-auth")
    public String tokenAuth(Model model) {
        model.addAttribute(TITLE, "Token Auth");
        return "token-auth";
    }

    @GetMapping("/basic-auth")
    public String basicAuth(Model model) {
        model.addAttribute(TITLE, "Basic Auth");
        return "basic-auth";
    }
}