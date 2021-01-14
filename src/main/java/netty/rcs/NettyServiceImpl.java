package netty.rcs;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.springframework.stereotype.Service;

import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;

/**
 * NettyService
 *
 * @author :  raymond
 * @version :  1.0
 * @date :  2019/7/16
 */
@Service
public class NettyServiceImpl implements NettyService {

    /**
     * 找不到方法
     *
     * @param ctx       请求上下文
     * @param keepAlive keepAlive
     */
    @Override
    public void errorResult(ChannelHandlerContext ctx, Boolean keepAlive) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        makeHeadAndSend(response, ctx, null != keepAlive && keepAlive);
    }

    /**
     * 找不到方法
     *
     * @param ctx       请求上下文
     * @param keepAlive keepAlive
     */
    @Override
    public void successResult(ChannelHandlerContext ctx, Boolean keepAlive) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        makeHeadAndSend(response, ctx, null != keepAlive && keepAlive);
    }

    private void makeHeadAndSend(DefaultFullHttpResponse httpResponse, ChannelHandlerContext ctx, Boolean isKeepAlive) {
        //构造头部
        //判断是否keepAlive写出数据
        if (isKeepAlive) {
            httpResponse.headers().set(HttpHeaderNames.CONNECTION, KEEP_ALIVE);
            ctx.channel().writeAndFlush(httpResponse);
        } else {
            ctx.channel().writeAndFlush(httpResponse).addListener(ChannelFutureListener.CLOSE);
        }

    }

    /**
     * 发送ret为成功
     *
     * @param ctx       ctx
     * @param keepAlive request的keepAlive
     */
    @Override
    public void sendRetSuccess(ChannelHandlerContext ctx, boolean keepAlive) {
        DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, ctx.alloc().heapBuffer());
        httpResponse.content().writeBytes(JSONObject.toJSONString(new Result()).getBytes());
        makeHeadAndSend(httpResponse, ctx, keepAlive);
    }

    /**
     * 自己组装结果集
     *
     * @param ctx       ctx
     * @param result    结果集
     * @param keepAlive request里是否包含keepAlive
     */
    @Override
    public void errorResult(ChannelHandlerContext ctx, String result, Boolean keepAlive) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, ctx.alloc().heapBuffer());
        response.content().writeBytes(result.getBytes());
        makeHeadAndSend(response, ctx, null != keepAlive && keepAlive);
    }

}
