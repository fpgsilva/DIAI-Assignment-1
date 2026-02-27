package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

@Service
@Primary
class BasicPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        return order.items.sumOf { it.quantity * it.price }
    }

}