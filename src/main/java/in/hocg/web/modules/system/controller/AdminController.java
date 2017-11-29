package in.hocg.web.modules.system.controller;

import in.hocg.web.global.aspect.ILog;
import in.hocg.web.modules.base.BaseController;
import in.hocg.web.modules.base.body.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hocgin on 2017/11/6.
 * email: hocgin@gmail.com
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
    public final String BASE_TEMPLATES_PATH = "/admin/%s";
    
    @ILog(value = "后台登陆界面")
    @GetMapping({"/login.html", ""})
    public String vLogin() {
        return String.format(BASE_TEMPLATES_PATH, "login");
    }
    
    @Autowired
    private SessionRegistry sessionRegistry;
    
    @GetMapping("/all")
    @ResponseBody
    public Results all() {
        for (Object o : sessionRegistry.getAllPrincipals()) {
            System.out.println(o);
        }
        return Results.success();
    }
}
