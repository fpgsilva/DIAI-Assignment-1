package pt.unl.fct.iadi.orderprocessingplatform.domain

class Receipt(
        val orderId: String,
        val status: ReceiptStatus,
        val metadata: Map<String, Any>
) {
}