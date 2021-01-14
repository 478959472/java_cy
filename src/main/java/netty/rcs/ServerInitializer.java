package netty.rcs;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * java类作用描述
 *
 * @author :  raymond
 * @version :  1.0
 * @date :  2020/06/18
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private ServerHandler serverHandler;

    public ServerInitializer(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline
                //60s 没有读取写出数据断开
                .addFirst(new IdleStateHandler(0, 0, 60))
                // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                .addLast(new HttpRequestDecoder())
                // 消息聚合器（重要）。
                // 为什么能有FullHttpRequest这个东西，就是因为有他，HttpObjectAggregator，
                // 如果没有他，就不会有那个消息是FullHttpRequest的那段Channel，同样也不会有FullHttpResponse。
                //单位M
                .addLast(new HttpObjectAggregator(655360))
                // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                .addLast(new HttpResponseEncoder())

                .addLast(new ChunkedWriteHandler())
                //处理逻辑
                .addLast(serverHandler);
    }
}
