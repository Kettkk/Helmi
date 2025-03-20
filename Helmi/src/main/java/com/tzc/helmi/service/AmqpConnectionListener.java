package com.tzc.helmi.service;

import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;

import javax.jms.*;
import java.net.URI;

public class AmqpConnectionListener implements JmsConnectionListener {

    @Override
    public void onConnectionEstablished(URI remoteURI) {
        System.out.println("✅ 连接成功: " + remoteURI);
    }

    @Override
    public void onConnectionFailure(Throwable error) {
        System.err.println("❌ 连接失败: " + error.getMessage());
    }

    @Override
    public void onConnectionInterrupted(URI remoteURI) {
        System.err.println("⚠️ 连接中断: " + remoteURI);
    }

    @Override
    public void onConnectionRestored(URI remoteURI) {
        System.out.println("🔄 连接恢复: " + remoteURI);
    }

    @Override
    public void onInboundMessage(JmsInboundMessageDispatch envelope) {
        System.out.println("📨 收到新消息: " + envelope);
    }

    @Override
    public void onSessionClosed(Session session, Throwable cause) {
        System.err.println("⚠️ 会话关闭: " + session + "，原因：" + cause);
    }

    @Override
    public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {
        System.err.println("⚠️ 消费者关闭: " + consumer + "，原因：" + cause);
    }

    @Override
    public void onProducerClosed(MessageProducer producer, Throwable cause) {
        System.err.println("⚠️ 生产者关闭: " + producer + "，原因：" + cause);
    }
}