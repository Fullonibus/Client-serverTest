package com.app.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Handles a server-side channel.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Object> {

    static final List<Channel> channels = new ArrayList<Channel>();

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Client joined - " + ctx);
        channels.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("Server received - " + o);
        DataControll dataControll = new DataControll();

        for (Channel c : channels) {
            //c.writeAndFlush("-> " + o + '\n');
            c.writeAndFlush("Data received from " + channelHandlerContext.name());
        }

        if(o instanceof File file){
            System.out.println("Object is File");
            handleFile(file);
        }

        if (o instanceof Map map){
            System.out.println("Object is Map");
            try {
                handleMap(map);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        Object answer = null;
        if (o instanceof String str && str.equals("requestData")){
            answer = dataControll.requestAllData();
            System.out.println("answer is List");
            System.out.println(answer);
        }else if(o instanceof Integer){

            answer = dataControll.getImageById((int)o);
        }

        for (Channel c : channels) {
            System.out.println("answer write and flush");
            c.writeAndFlush(answer);
        }

    }

    private void handleRequest() throws SQLException {
        System.out.println("String taken");
        DataControll dataControll = new DataControll();
    }

    private void handleMap(Map map) throws SQLException, FileNotFoundException {
        try {
            System.out.println("handleMap start");
        String name = (String) map.get("name");
        String description = (String) map.get("description");
        File file = (File) map.get("file");

            System.out.println("handleMap parse");
            DataControll dataControll = new DataControll();
            System.out.println("create data control");
            dataControll.saveMap("new_table", name, description, file);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    void handleFile(File f) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("File Handle Started");
        DataControll dataControll = new DataControll();
        dataControll.saveFile("new_table", f);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Closing connection for client - " + ctx);
        ctx.close();
    }
}