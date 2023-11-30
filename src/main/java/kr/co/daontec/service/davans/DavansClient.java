package kr.co.daontec.service.davans;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import kr.co.daontec.config.ConfigData;
import kr.co.daontec.service.DavansInit;
import kr.co.daontec.webclient.RequestApti;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class DavansClient extends Thread{
    private final DavansInit davansInit;
    private final RequestApti requestApti;
    private final ConfigData data;



    @Override
    @SneakyThrows
    public void run() {
        connect();
    }

    public void connect() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new IdleStateHandler(20, 0, 0), new DavansClientHandler(davansInit,requestApti,data));
                        }
                    });

            ChannelFuture f = b.connect(data.getHost(), data.getPort()).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
