package com.wall.biz.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: wang.luo
 * @date 2016/11/2 17:55
 */
@Controller
@RequestMapping("/wall")
public class WallController {

    @RequestMapping(value = "/sendMsgMobile/{activityId}")
    public ModelAndView sendMsgMobile(@PathVariable String activityId ){
        ModelAndView mv = new ModelAndView("wall/sendMsgMobile");
        return mv;
    }
}