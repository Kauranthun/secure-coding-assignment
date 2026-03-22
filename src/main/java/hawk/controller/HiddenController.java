package hawk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HiddenController {

    private static final String HIDDEN = "hidden";
    private static final String TITLE = "title";

    @GetMapping("/hidden")
    public String index(Model model) {
        model.addAttribute(TITLE, "Hidden Page");
        return HIDDEN;
    }

    @GetMapping("/hidden/hidden2")
    public String hidden(Model model) {
        model.addAttribute("Rando hidden page");
        return "hidden2";
    }

    @GetMapping("/hidden/cypress")
    public String cypress(Model model) {
        model.addAttribute(TITLE, "Hidden Page, found and tested with cypress tests");
        return HIDDEN;
    }

    @GetMapping("/hidden/selenium")
    public String selenium(Model model) {
        model.addAttribute(TITLE, "Hidden Page, found and tested with selenium tests");
        return HIDDEN;
    }

    @GetMapping("/hidden/playwright")
    public String playwright(Model model) {
        model.addAttribute(TITLE, "Hidden Page, found and tested with playwright tests");
        return HIDDEN;
    }
}
