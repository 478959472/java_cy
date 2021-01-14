package netty.rcs;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * netty启动类
 *
 * @author :  raymond
 * @version :  1.0
 * @date :  2020/06/18
 */
@Slf4j
public class NettyStart {

    private Integer port;


    private ServerInitializer serverInitializer;

    public NettyStart(Integer port , ServerInitializer serverInitializer) {
        this.port = port;
        this.serverInitializer = serverInitializer;
    }

    public static void main(String[] args) {
        ServerInitializer serverInitializer = new ServerInitializer(new ServerHandler());
        NettyStart nettyStart = new NettyStart(8080,serverInitializer);
        nettyStart.init();
    }

    @Async
    public void init() {
        log.info("启动netty服务中,端口:{}", port);
        NioEventLoopGroup parent = new NioEventLoopGroup(2);
        NioEventLoopGroup child = new NioEventLoopGroup(4);
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(parent, child).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度 默认值50
                .option(ChannelOption.SO_BACKLOG, 1024 * 5)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                //关键是这句
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, false)
                //初始化pipelinechannelRead
                .childHandler(serverInitializer);
        //绑定接口，优雅关闭
        try {
            bootstrap.bind(port).sync().channel().closeFuture().sync();
            log.info("启动netty服务成功");
        } catch (Exception e) {
            log.error("启动netty服务异常", e);
        } finally {
            //优雅退出，释放线程池资源
            parent.shutdownGracefully();
            child.shutdownGracefully();
        }
    }
}
