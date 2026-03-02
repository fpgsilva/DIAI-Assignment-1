package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@ConditionalOnProperty(name = ["pricing.promo.enabled"], havingValue = "true")
class PromoPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        var total = 0.0
        for (item in order.items) {
            if (item.quantity > 5) {
                total += item.quantity * item.price * 0.8
            } else
                total += item.quantity * item.price
        }
        return BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toDouble()

    }
    }
