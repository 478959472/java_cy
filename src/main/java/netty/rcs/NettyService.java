package netty.rcs;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author lianghuageng
 * @date 2020/11/16 14:13
 * @since 1.0.0
 */
public interface NettyService {

    /**
     * 设置错误结果集, 返回 404
     *
     * @param ctx       请求上下文
     * @param keepAlive keepAlive
     */
    void errorResult(ChannelHandlerContext ctx, Boolean keepAlive);

    /**
     * 设置错误的结果集, 自定义错误
     *
     * @param ctx       ctx
     * @param result    自定义错误
     * @param keepAlive request里是否包含keepAlive
     */
    void errorResult(ChannelHandlerContext ctx, String result, Boolean keepAlive);

    /**
     * 设置成功结果集
     *
     * @param ctx       请求上下文
     * @param keepAlive keepAlive
     */
    void successResult(ChannelHandlerContext ctx, Boolean keepAlive);

    /**
     * 发送ret为成功
     *
     * @param ctx       ctx
     * @param keepAlive request的keepAlive
     */
    void sendRetSuccess(ChannelHandlerContext ctx, boolean keepAlive);


}
