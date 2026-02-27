package pt.unl.fct.iadi.orderprocessingplatform.pricing

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order

@Service
@ConditionalOnProperty(name = ["pricing.promo.enabled"], havingValue = "true")
class PromoPriceCalculator : PriceCalculator {
    override fun calculateTotalPrice(order: Order): Double {
        //separate in a partition?
        return 0.0
        }
    }
