package guru.sfg.beer.order.service.services.listeners;

import guru.sfg.beer.order.service.config.JmsConfig;
import guru.sfg.beer.order.service.services.BeerOrderManager;
import guru.sfg.common.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocationResponseListener {
    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listen(AllocateOrderResponse response) {
        if (response.isAllocationError() && !response.isPendingInventory()) {
            beerOrderManager.beerOrderAllocationFailed(response.getBeerOrderDto());
        } else if (!response.isAllocationError() && response.isPendingInventory()) {
            beerOrderManager.beerOrderAllocationPendingInventory(response.getBeerOrderDto());
        } else if (!response.isAllocationError() && !response.isPendingInventory()) {
            beerOrderManager.beerOrderAllocationPassed(response.getBeerOrderDto());
        }
    }
}
