package guru.sfg.beer.order.service.services.testcomponents;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.common.events.AllcateOrderRequest;
import guru.sfg.common.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderAllocationListener {

    public static final String FAIL_ALOCATION = "fail-alocation";
    public static final String PARTIAL_ALOCATION = "partial-allocation";
    public static final String DONT_ALLOCATE = "dont-allocate";

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_QUEUE)
    public void list(Message msg) {
        AllcateOrderRequest request = (AllcateOrderRequest) msg.getPayload();
        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            beerOrderLineDto.setQuantityAllocated(1);
        });
        if (!DONT_ALLOCATE.equals(request.getBeerOrderDto().getCustomerRef())) {
            jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE,
                    AllocateOrderResponse.builder()
                            .allocationError(FAIL_ALOCATION.equals(request.getBeerOrderDto().getCustomerRef()))
                            .pendingInventory(PARTIAL_ALOCATION.equals(request.getBeerOrderDto().getCustomerRef()))
                            .beerOrderDto(request.getBeerOrderDto())
                            .build());
        }
    }
}
