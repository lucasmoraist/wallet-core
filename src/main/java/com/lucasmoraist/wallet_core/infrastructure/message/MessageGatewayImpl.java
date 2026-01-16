package com.lucasmoraist.wallet_core.infrastructure.message;

import com.lucasmoraist.wallet_core.application.gateway.MessageGateway;
import com.lucasmoraist.wallet_core.domain.enums.PaymentStatus;
import com.lucasmoraist.wallet_core.domain.message.PaymentMessage;
import com.lucasmoraist.wallet_core.infrastructure.queue.producer.WalletProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import static org.springframework.messaging.support.MessageBuilder.withPayload;

@Log4j2
@Component
@RequiredArgsConstructor
public class MessageGatewayImpl implements MessageGateway {

    private static final String TO_TRANSFER_MANAGER = "toProcessTransfer-out-0";
    private static final String TO_NOTIFICATION_SVC = "toSendNotification-out-0";
    private static final String NOTIFICATION_ROUTING_KEY = "notificationRoutingKey";
    private static final String TRANSFER_ROUTING_KEY = "transferRoutingKey";
    private static final String TRACE_ID_HEADER = "traceId";
    private static final String TRANSFER_FAILED = "transfer.failed";
    private static final String TRANSFER_SUCCESS = "transfer.success";

    private final WalletProducer walletProducer;

    @Override
    public void sendToTransferManager(PaymentMessage message) {
        log.debug("Sending message to Transfer Manager: {}", message);

        String transferStatus = getRoutingKey(message.status());
        log.debug("Determined routing key for transfer: {}", transferStatus);

        walletProducer.sendMessage(withPayload(message)
                .setHeader(TRANSFER_ROUTING_KEY, transferStatus)
                .setHeader(TRACE_ID_HEADER, message.payflow().traceId())
                .build(), TO_TRANSFER_MANAGER);
        log.info("Message sent to Transfer Manager: {}", message);
    }

    @Override
    public void sendToNotificationSvc(PaymentMessage message) {
        log.debug("Sending message to Notification Service: {}", message);

        String transferStatus = getRoutingKey(message.status());
        log.debug("Determined routing key for notification: {}", transferStatus);

        walletProducer.sendMessage(withPayload(message)
                .setHeader(NOTIFICATION_ROUTING_KEY, transferStatus)
                .setHeader(TRACE_ID_HEADER, message.payflow().traceId())
                .build(), TO_NOTIFICATION_SVC);
        log.info("Message sent to Notification Service: {}", message);
    }

    private String getRoutingKey(PaymentStatus status) {
        return PaymentStatus.FAILED.equals(status)
                ? TRANSFER_FAILED
                : TRANSFER_SUCCESS;
    }

}
