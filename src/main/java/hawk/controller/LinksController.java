package hawk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LinksController {

    @GetMapping(value = {"/links/**"})
    public String getPayload(Model model, HttpServletRequest request) {



        List<String> links = new ArrayList<>();
        links.add("blah");
        model.addAttribute("links", links);

        return "links";
    }

}
