package guru.sfg.common.events;

import guru.sfg.common.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateOrderRequest {
    private BeerOrderDto beerOrderDto;
}
