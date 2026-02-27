package pt.unl.fct.iadi.orderprocessingplatform.domain

class OrderItem(private val id: String, private val items: List<OrderItem>, private val userId: String, private val createdAt: String) {
}