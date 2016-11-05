package com.core.server;

/**
 * @author: wang.luo
 * @date 2016/10/28 15:50
 */

import org.apache.commons.lang.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{activityId}/{openId}")
public class WebSocketServer {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    private static HashMap<String,WebSocketServer> webSocketMap = new HashMap<String,WebSocketServer>();//map中key存活动id

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam("activityId") String activityId,Session session){
        this.session = session;
        webSocketMap.put(activityId,this);
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */

    /**
     * 消息上墙的几种入口 //TODO
     * 1.通过扫描二维码。二维码中包含活动id
     * 2.自定义菜单
     * 3.关注后自动回复文字，夹杂入口链接
     * 4.在微信对话框中输入关键字（这种方式需绑定微信号）
     *
     *
     * 逻辑 //TODO
     * 1.客户端发送活动id、类型（手机客户端、pc展示端）消息到服务端、
     * 2.服务端接收到消息后，         openid 夹杂到消息里面
     *  2.1如果是手机客户端类型
     *      2.1.1判断是否敏感词汇
     *      2.1.2判断活动状态是否正常、时间是否结束
     *              后台是否需要审核
     *      2.1.3群发消息（包含头像、昵称、消息内容、openid）
     *      2.1.4手机客户端收到返回消息
     *           判断是否本人，如果是则显示在右边，另外一种样式
     *           否则，显示在左边
     *      2.1.5pc展示端收到消息后，展示到页面位置（异步发送）
     *  2.2如果是pc展示端，则发送警告邮件
     *
     */
    @OnMessage
    public void onMessage(@PathParam("activityId") String activityId,@PathParam("openId") String openId,
                          String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        StringBuffer sb = new StringBuffer();
        sb.append("发送内容：").append(message).append(";");
        sb.append("activityId：").append(activityId).append(";");
        sb.append("openId：").append(openId).append(";");
        //群发消息
        for(WebSocketServer item: webSocketSet){
            try {
                item.sendMessage(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }

        //给相应活动的发送消息

    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
        if(StringUtils.isNotBlank(message)){
            this.session.getAsyncRemote().sendText(message);
//            this.session.getBasicRemote().sendText(message);
        }

    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}