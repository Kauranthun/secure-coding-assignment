package hawk.controller;

import hawk.api.ExtraAuthenticationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    private static final String COOKIE = "cookie-";
    private static final String TITLE = "title";

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(TITLE, "Login title");
        return "login";
    }

    Map<String, String> loginCodes = new ConcurrentHashMap<>();

    @GetMapping("/login-code")
    public String loginCode(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String sessId = req.getSession().getId();
        String cookieCode = UUID.randomUUID().toString();
        loginCodes.put(COOKIE + sessId, cookieCode);
        resp.addCookie(new Cookie("XLOGINID", cookieCode));
        return "redirect:/login-form-multi";
    }

    @GetMapping("/login-form-multi")
    public String loginFormMulti(HttpServletRequest req, Model model) {
        String sessId = req.getSession().getId();
        String loginCode = loginCodes.get(COOKIE + sessId);
        if (loginCode == null) {
            return "redirect:/login-code";
        }
        model.addAttribute(TITLE, "Login multi title");
        model.addAttribute("loginCode", loginCode);
        return "login-form-multi";
    }

    @PostMapping("/login-form-multi")
    public String loginFormMulti(HttpServletRequest req,
                                 HttpServletResponse resp,
                                 @CookieValue("XLOGINID") String xLoginId,
                                 @ModelAttribute ExtraAuthenticationRequest data) {
        try {
            String sessId = req.getSession().getId();
            String loginCode = loginCodes.get(COOKIE + sessId);

            if (data.getLoginCode() == null
                    || data.getRemember() == null
                    || data.getLoginCode().isEmpty()
                    || data.getRemember().isEmpty()
                    || !data.getLoginCode().equals(loginCode)
                    || !xLoginId.equals(loginCode)) {
                throw new BadCredentialsException("missing required fields");
            }
            String username = data.getUsername();
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (null == userDetails) {
                throw new UsernameNotFoundException("username");
            }

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);
            HttpSession session = req.getSession(true);
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

            return "redirect:/";
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @GetMapping("/login-multi-check")
    public String loginCheck(HttpServletRequest req, HttpServletResponse resp, Model model, @CookieValue("XLOGINID") String xLoginId) {
        String sessId = req.getSession().getId();
        String loginCode = loginCodes.get(COOKIE + sessId);
        if (loginCode == null || !loginCode.equals(xLoginId))
            return "redirect:/login-form-multi";
        model.addAttribute(TITLE, "StackHawk Java Vulny Application");
        return "index";
    }

}