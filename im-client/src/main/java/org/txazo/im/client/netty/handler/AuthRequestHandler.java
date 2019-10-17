package org.txazo.im.client.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.txazo.im.common.protocol.MessageBody;

public class AuthRequestHandler extends ChannelInboundHandlerAdapter {

    private Integer userId;
    private String token;

    public AuthRequestHandler(Integer userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MessageBody.AuthRequestPacket packet = MessageBody.AuthRequestPacket.newBuilder()
                .setUserId(userId)
                .setToken(token)
                .build();
        ctx.writeAndFlush(packet);
        super.channelActive(ctx);
    }

}
