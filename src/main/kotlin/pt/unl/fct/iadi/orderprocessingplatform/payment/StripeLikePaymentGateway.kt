package pt.unl.fct.iadi.orderprocessingplatform.payment

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import pt.unl.fct.iadi.orderprocessingplatform.domain.PaymentRequest
import pt.unl.fct.iadi.orderprocessingplatform.domain.Receipt
import pt.unl.fct.iadi.orderprocessingplatform.domain.ReceiptStatus
import java.util.UUID

@Service
@Profile("prod")
class StripeLikePaymentGateway : PaymentGateway{
    override fun processPayment(paymentRequest: PaymentRequest): Receipt {

        val amount = paymentRequest.amount

        if(amount <= 0)
            return Receipt(orderId = paymentRequest.orderId,
                    status = ReceiptStatus.REJECTED,
                    metadata = mapOf(
                            "gateway" to "stripe-like",
                            "reason" to "Invalid amount",
                            "amount" to paymentRequest.amount
                    ))

        else if(amount > 10000)
            return Receipt(orderId = paymentRequest.orderId,
                    status = ReceiptStatus.FLAGGED_FOR_REVIEW,
                    metadata = mapOf(
                            "gateway" to "stripe-like",
                            "reason" to "High value transaction requires review",
                            "amount" to paymentRequest.amount
                    ))
        else
        return Receipt(orderId = paymentRequest.orderId,
                status = ReceiptStatus.PAID,
                metadata = mapOf(
                        "gateway" to "stripe-like",
                        "transactionId" to UUID.randomUUID().toString(),
                        "amount" to paymentRequest.amount
                ))

    }
}