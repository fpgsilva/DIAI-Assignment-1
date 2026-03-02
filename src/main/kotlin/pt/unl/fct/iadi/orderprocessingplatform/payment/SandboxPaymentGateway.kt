package pt.unl.fct.iadi.orderprocessingplatform.payment

import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.domain.Receipt
import pt.unl.fct.iadi.orderprocessingplatform.domain.ReceiptStatus

@Component
@Primary
@Profile("!prod")
class SandboxPaymentGateway : PaymentGateway{
    override fun processPayment(paymentRequest: PaymentRequest): Receipt {
        return Receipt(orderId = paymentRequest.orderId,
                status = ReceiptStatus.PAID,
                metadata = mapOf(
                        "gateway" to "sandbox",
                        "amount" to paymentRequest.amount
                ))
    }
}