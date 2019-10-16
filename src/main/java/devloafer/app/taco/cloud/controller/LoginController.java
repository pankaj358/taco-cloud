package devloafer.app.taco.cloud.controller;

import devloafer.app.taco.cloud.domains.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {


    @PostMapping
    public void processLogin(User user)
    {
      log.info("processing ------------user--------" + user);
    }

    @GetMapping
    public String login()
    {
        return "login";
    }

}
