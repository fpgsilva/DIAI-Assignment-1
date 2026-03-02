package pt.unl.fct.iadi.orderprocessingplatform

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pt.unl.fct.iadi.orderprocessingplatform.domain.Order
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.payment.PaymentGateway
import pt.unl.fct.iadi.orderprocessingplatform.pricing.PriceCalculator
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant

@Component
class OrderProcessor(
        val priceCalculator: PriceCalculator,
        val paymentGateway: PaymentGateway
) : CommandLineRunner {

    fun processOrder(order : Order) : List<String>{
        val out = mutableListOf(
                "Order ID: ${order.id}",
                "User ID: ${order.userId}",
                "Created at: ${order.createdAt}",
                "",
                "Items:"
        )

        for (i in order.items){
            //itemTotal wont change - val and not var
            val itemTotal = BigDecimal(i.quantity * i.price).setScale(2, RoundingMode.HALF_UP) //2 nums dps da virgula, obg martim
            out.add("  - ${i.productId}: ${i.quantity} x $${i.price} = $${itemTotal}")
        }

        val calculatedTotal = priceCalculator.calculateTotalPrice(order)

        out.add("")
        out.add("Total Price: $calculatedTotal")
        out.add("Calculator Used: ${priceCalculator::class.simpleName}") //claude
        out.add("")

        val paymentRequest = PaymentRequest(
                orderId = order.id,
                amount = calculatedTotal
        )
        val receipt = paymentGateway.processPayment(paymentRequest)

        out.add("Payment Status: ${receipt.status}")
        out.add("Payment Gateway: ${receipt.metadata["gateway"]}")

        if (receipt.metadata.containsKey("transactionId"))
            out.add("Transaction ID: ${receipt.metadata["transactionId"]}")

        if (receipt.metadata.containsKey("reason"))
            out.add("Reason: ${receipt.metadata["reason"]}")

        out.add("")
        out.add("=== Processing Complete ===")

        return out
    }

    override fun run(vararg args: String?) {
        val order = Order(
                id = "ORD-2026-001",
                userId = "user123",
                createdAt = Instant.now(),
                items = listOf(
                        Order.OrderItem(productId = "LAPTOP-001", quantity = 6, price = 999.99),
                        Order.OrderItem(productId = "MOUSE-042", quantity = 3, price = 29.99),
                        Order.OrderItem(productId = "KEYBOARD-123", quantity = 100, price = 149.99)
                )
        )

        val output = processOrder(order)
        for (line in output) {
            println(line)
        }
    }
}