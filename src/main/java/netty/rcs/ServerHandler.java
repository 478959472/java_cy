package netty.rcs;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * 获取请求, 该服务只接口运营商上行和状态报告回调
 *
 * @author :  raymond
 * @version :  1.0
 * @date :  2020/06/18
 */
@Slf4j
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private boolean isOpenRcs = true;
    private final NettyService nettyService = new NettyServiceImpl();


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //判断是否是http请求
        if (!(msg instanceof FullHttpRequest)) {
            nettyService.errorResult(ctx, null);
            ReferenceCountUtil.release(msg);
            return;
        }
        String reqNum = IdUtil.fastUUID();
        FullHttpRequest httpRequest = (FullHttpRequest) msg;
        String data;
        String result;
        try {
            log.info("[REQUEST-{}]-请求头信息:{}", reqNum, httpRequest.headers());
            data = httpRequest.content().toString(Charset.defaultCharset());
            result = controlRequest(reqNum, httpRequest, data);
        } catch (JSONException e) {
            log.error("[REQUEST-{}]-请求参数解析异常,msg:{}", reqNum, msg, e);
            result = String.valueOf(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            log.error("[REQUEST-{}]-服务出现异常,msg:{}", reqNum, msg, e);
            result = String.valueOf(HttpStatus.NOT_FOUND.value());
        }
        boolean keepAlive = HttpUtil.isKeepAlive(httpRequest);
        log.info("[REQUEST-{}]-服务响应信息:{}", reqNum, result);
        if (StrUtil.isEmpty(result)) {
            nettyService.successResult(ctx, keepAlive);
            ReferenceCountUtil.release(msg);
            return;
        }
        if (String.valueOf(HttpStatus.NOT_FOUND.value()).equals(result)) {
            nettyService.errorResult(ctx, null);
            ReferenceCountUtil.release(msg);
            return;
        }
        nettyService.errorResult(ctx, result, keepAlive);
        ReferenceCountUtil.release(msg);
    }

    /**
     * 控制请求到那个类那个方法
     *
     * @param reqNum      任务 id
     * @param httpRequest httpRequest
     * @return 结果集
     */
    private String controlRequest(String reqNum, FullHttpRequest httpRequest, String data) {
        String uri = httpRequest.uri();
        System.out.println("uri:"+uri);
        System.out.println("data:"+data);
        return String.valueOf(HttpStatus.NOT_FOUND.value());
//        try {
//
//            RequestTypeEnum requestType = rcsCallbackService.getRequestType(reqNum, uri);
//            if (null == requestType) {
//                return String.valueOf(HttpStatus.NOT_FOUND.value());
//            }
//            switch (requestType) {
//                //5G状态报告
//                case RPT:
//                    if (!isOpenRcs) {
//
//                    }
//                    return rcsCallbackService.rptCallback(reqNum, data, uri);
//                //5G上行  消息接收通知  用于5G消息接入层向Chatbot发送上行消息的场景
//                case MO:
//                    if (!isOpenRcs) {
//                        return String.valueOf(HttpStatus.NOT_FOUND.value());
//                    }
//                    return rcsCallbackService.moCallback(reqNum, data, uri);
//                default:
//                    return String.valueOf(HttpStatus.NOT_FOUND.value());
//            }
//        } catch (Exception e) {
//            log.error("[REQUEST-{}]-接收服务异常:ServerHandler.controlRequest,data:{}", reqNum, data, e);
//            return String.valueOf(HttpStatus.NOT_FOUND.value());
//        }
    }


}
