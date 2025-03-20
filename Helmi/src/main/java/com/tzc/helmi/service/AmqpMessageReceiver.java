package com.tzc.helmi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tzc.helmi.entity.Statu;
import com.tzc.helmi.mapper.StatuMapper;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.transports.TransportOptions;
import org.apache.qpid.jms.transports.TransportSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AmqpMessageReceiver {

    private static final String ACCESS_KEY = "QmCCrMqk";
    private static final String PASSWORD = "mf6jVxUmECmTqjgIc65e2bZL3Hf7vLy2";
    private static final String HOST = "35c775ae56.st1.iotda-app.cn-north-4.myhuaweicloud.com";
    private static final int PORT = 5671;
    private static final String VHOST = "default";
    private static final String QUEUE_NAME = "DefaultQueue";

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 解析工具

    @Autowired
    private StatuMapper statuMapper;  // 注入 StatuMapper 用于数据库操作

    public AmqpMessageReceiver() {
        startListener();
    }

    private void startListener() {
        try {
            long timeStamp = System.currentTimeMillis();
            String userName = "accessKey=" + ACCESS_KEY + "|timestamp=" + timeStamp;
            String brokerUrl = "amqps://" + HOST + ":" + PORT + "?amqp.vhost=" + VHOST + "&amqp.idleTimeout=8000&amqp.saslMechanisms=PLAIN";

            System.out.println("🔵 正在连接到 AMQP：" + brokerUrl);

            JmsConnectionFactory cf = new JmsConnectionFactory(userName, PASSWORD, brokerUrl);

            // 信任所有 SSL 证书
            TransportOptions to = new TransportOptions();
            to.setTrustAll(true);
            cf.setSslContext(TransportSupport.createJdkSslContext(to));

            Connection connection = cf.createConnection();
            ((JmsConnection) connection).addConnectionListener(new AmqpConnectionListener());

            // 创建 Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            // 创建消息消费者
            Destination queue = session.createQueue(QUEUE_NAME);
            MessageConsumer consumer = session.createConsumer(queue);

            // 监听方式一：阻塞式获取消息
            receiveMessage(consumer);

        } catch (Exception e) {
            System.err.println("❌ AMQP 监听器初始化失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void receiveMessage(MessageConsumer consumer) {
        new Thread(() -> {
            while (true) {
                try {
                    Message message = consumer.receive();
                    if (message != null) {
                        executorService.submit(() -> processMessage(message));
                    }
                } catch (Exception e) {
                    System.err.println("❌ 消息接收异常：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void processMessage(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                String jsonText = textMessage.getText();
                System.out.println("📨 原始消息：" + jsonText);

                // 解析 JSON
                JsonNode propertiesNode = objectMapper.readTree(jsonText)
                        .path("notify_data").path("body")
                        .path("services").get(0)
                        .path("properties");

                if (propertiesNode.isMissingNode()) {
                    System.err.println("❌ 未找到 properties 节点");
                    return;
                }

                // Manually map "body_temperature" to "temperature" if it exists
                if (propertiesNode.has("body_temperature")) {
                    ((ObjectNode) propertiesNode).put("temperature", propertiesNode.get("body_temperature").asDouble());
                    ((ObjectNode) propertiesNode).remove("body_temperature"); // Remove the old field
                }

                // Manually map "pace" to "speed" if it exists
                if (propertiesNode.has("pace")) {
                    ((ObjectNode) propertiesNode).put("speed", propertiesNode.get("pace").asDouble());
                    ((ObjectNode) propertiesNode).remove("pace"); // Remove the old field
                }

                // 解析为 Statu 对象
                Statu statu = objectMapper.treeToValue(propertiesNode, Statu.class);
                statu.setDevice_id(1);  // Set device ID, assuming 1 for now
                statu.setUser_id(1);
                statu.setUsername("fantant");

                // Check if the device exists
                int count = statuMapper.countByDeviceId(statu.getDevice_id());
                if (count > 0) {
                    statuMapper.updateByDeviceId(statu); // Update if device exists
                    System.out.println("✅ 更新设备状态：" + statu);
                } else {
                    statuMapper.insert(statu); // Insert if device does not exist
                    System.out.println("✅ 插入新设备状态：" + statu);
                }

            } else {
                System.out.println("⚠️ 收到非文本消息：" + message);
            }
        } catch (Exception e) {
            System.err.println("❌ 处理消息失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}