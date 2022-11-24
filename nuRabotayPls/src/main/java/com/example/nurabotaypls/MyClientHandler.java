package com.example.nurabotaypls;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
public class MyClientHandler extends SimpleChannelInboundHandler<Object> {

        public TableView tableView;
        public ImageView imageView;
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
            System.out.println("object is: " + o.getClass());
            if(o instanceof String) {
                System.out.println("Message: " + o);
            }else if(o instanceof File file)  {
                imageView.setImage(new Image(file.toURI().toString()));
                channelHandlerContext.close();
            }else if(o instanceof ArrayList<?> list) {
                System.out.println("List is: " + list);

                list.forEach( element->{
                    tableView.getItems().add(
                            new TableRow(
                                    (Integer)((Map<String, Object>)element).get("idnew_table"),
                                    (String)((Map<String, Object>)element).get("lot_name"),
                                    (String)((Map<String, Object>)element).get("lot_description")));

                });
                channelHandlerContext.close();
            }
        }
    }

