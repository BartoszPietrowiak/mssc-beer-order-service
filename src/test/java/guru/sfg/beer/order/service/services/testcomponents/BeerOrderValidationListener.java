package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.common.events.ValidateOrderRequest;
import guru.sfg.common.events.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidationListener {

    public static final String FAIL_VALIDATION = "fail-validation";
    public static final String DONT_VALIDATE = "dont-validate";

    private final JmsTemplate jmsTemplate;
    boolean sendResponse = true;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listen(Message msg) {
        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        if (!DONT_VALIDATE.equals(request.getBeerOrderDto().getCustomerRef())) {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    ValidateOrderResult.builder()
                            .isValid(!FAIL_VALIDATION.equals(request.getBeerOrderDto().getCustomerRef()))
                            .orderId(request.getBeerOrderDto().getId())
                            .build());
        }
    }
}
