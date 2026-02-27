package pt.unl.fct.iadi.orderprocessingplatform.domain

class PaymentRequest(
        val orderId: String,
        val amount: Double
) {
}