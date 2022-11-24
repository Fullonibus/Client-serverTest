package com.example.nurabotaypls;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

public class Connection {
    Thread thread;
    static final String HOST = "127.0.0.1";
    static final int PORT = 8007;
    static String clientName;
    boolean isThreadActive;
    public void sendPack(Object pack, String name, String description) throws Exception{
        //thread = new Thread(()->{
        isThreadActive = true;
        System.out.println("Thread started");
        Map<String, Object> map = new HashMap<>();
        map.put("file", pack);
        map.put("name", name);
        map.put("description", description);

        // Since this is client, it doesn't need boss group. Create single group.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            System.out.println("Boot trying...");
            Bootstrap b = new Bootstrap();
            b.group(group) // Set EventLoopGroup to handle all eventsf for client.
                    .channel(NioSocketChannel.class)// Use NIO to accept new connections.
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("Channel initialising");
                            ChannelPipeline p = ch.pipeline();
                            /*
                             * Socket/channel communication happens in byte streams. String decoder &
                             * encoder helps conversion between bytes & String.
                             */
                            p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled((null))));
                            p.addLast(new ObjectEncoder());


                            MyClientHandler mch = new MyClientHandler();
                            // This is our custom client handler which will have logic for chat.
                            p.addLast(mch);
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();
            Channel channel = f.sync().channel();
            channel.writeAndFlush(map);
            System.out.println("Sended");

            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

            isThreadActive = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    public void closeConnection() {

    }
    public void refreshTableView(TableView tableView) throws Exception{

        tableView.getItems().clear();

        //thread = new Thread(()->{
        isThreadActive = true;
        System.out.println("Thread started");
        // Since this is client, it doesn't need boss group. Create single group.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            System.out.println("Boot trying...");
            Bootstrap b = new Bootstrap();
            b.group(group) // Set EventLoopGroup to handle all eventsf for client.
                    .channel(NioSocketChannel.class)// Use NIO to accept new connections.
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("Channel initialising");
                            ChannelPipeline p = ch.pipeline();
                            /*
                             * Socket/channel communication happens in byte streams. String decoder &
                             * encoder helps conversion between bytes & String.
                             */
                            p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled((null))));
                            p.addLast(new ObjectEncoder());


                            // This is our custom client handler which will have logic for chat.
                            MyClientHandler mch = new MyClientHandler();
                            mch.tableView = tableView;
                            p.addLast(mch);
                        }
                    });

            ChannelFuture f = b.connect(HOST, PORT).sync();

            Channel channel = f.sync().channel();

            channel.writeAndFlush("requestData");

            System.out.println("Sended request");


            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

            isThreadActive = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }


    public void setImageFromServer(Integer id, ImageView imageView) throws Exception{
        //thread = new Thread(()->{
        System.out.println("Thread started");
        // Since this is client, it doesn't need boss group. Create single group.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            System.out.println("Boot trying...");
            Bootstrap b = new Bootstrap();
            b.group(group) // Set EventLoopGroup to handle all eventsf for client.
                    .channel(NioSocketChannel.class)// Use NIO to accept new connections.
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("Channel initialising");
                            ChannelPipeline p = ch.pipeline();
                            /*
                             * Socket/channel communication happens in byte streams. String decoder &
                             * encoder helps conversion between bytes & String.
                             */
                            p.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled((null))));
                            p.addLast(new ObjectEncoder());


                            // This is our custom client handler which will have logic for chat.
                            MyClientHandler mch = new MyClientHandler();
                            mch.imageView = imageView;
                            p.addLast(mch);
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();
            Channel channel = f.sync().channel();
            channel.writeAndFlush(id);
            System.out.println("Sended request");


            // Wait until the connection is closed.
            f.channel().closeFuture().sync();

            isThreadActive = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }

    }
}
