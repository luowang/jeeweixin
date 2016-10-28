package com.wxcms.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: wang.luo
 * @date 2016/10/28 16:06
 */
@Controller
@RequestMapping("/wxcms")
public class WebSocketTestCtrl {

    @RequestMapping(value = "/test1")
    public ModelAndView toUploadMaterial(String[] newIds){
        ModelAndView mv = new ModelAndView("wxcms/test1");
        return mv;
    }
}
