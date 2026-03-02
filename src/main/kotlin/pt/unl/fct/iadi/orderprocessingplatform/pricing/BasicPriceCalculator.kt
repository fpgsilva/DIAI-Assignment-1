package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@Primary
@ConditionalOnProperty(name = ["pricing.promo.enabled"], havingValue = "false", matchIfMissing = true)
class BasicPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        val itemsSum = order.items.sumOf { it.quantity * it.price }
        return BigDecimal(itemsSum).setScale(2, RoundingMode.HALF_UP).toDouble()
    }

}