package com.wall.biz.ctrl;

import com.wxapi.process.WxMemoryCacheClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 手机端发送上墙消息
 * @author: wang.luo
 * @date 2016/11/2 17:55
 */
@Controller
@RequestMapping("/wall")
public class WallController {

    @RequestMapping(value = "/sendMsgMobile/{activityId}")
    public ModelAndView sendMsgMobile(@PathVariable String activityId ,HttpServletRequest request){
        ModelAndView mv = new ModelAndView("wall/sendMsgMobile");
        mv.addObject("activityId",activityId);
        String openid = WxMemoryCacheClient.getOpenid(request.getSession().getId());
        return mv;
    }
    @RequestMapping(value = "/show/{activityId}")
    public ModelAndView show(@PathVariable String activityId ){
        ModelAndView mv = new ModelAndView("wall/showMsg");
        mv.addObject("activityId",activityId);
        return mv;
    }
}