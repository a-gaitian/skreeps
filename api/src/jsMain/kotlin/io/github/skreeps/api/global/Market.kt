@file:OptIn(ExperimentalJsExport::class)
@file:Suppress("NON_EXPORTABLE_TYPE")

package io.github.skreeps.api.global

import io.github.skreeps.api.constants.Error
import io.github.skreeps.api.constants.Error.*
import io.github.skreeps.api.constants.Resource
import io.github.skreeps.api.utils.Code
import io.github.skreeps.api.utils.ResultMap
import io.github.skreeps.api.utils.Timestamp

/**
 * A global object representing the in-game market. You can use this object to track resource
 * transactions to/from your terminals, and your buy/sell orders.
 *
 * Learn more about the market system from [this article](https://docs.screeps.com/market.html)
 */
external class Market {

    /**
     * Your current credits balance
     */
    val credits: Number

    /**
     * An array of the last 100 incoming transactions to your terminals
     */
    val incomingTransactions: Array<Transaction>

    /**
     * An array of the last 100 outgoing transactions from your terminals
     */
    val outgoingTransactions: Array<Transaction>

    /**
     * An object with your active and inactive buy/sell orders on the market
     */
    val orders: ResultMap<Order>

    /**
     * Estimate the energy transaction cost of [StructureTerminal.send] and
     * [Game.market.deal][deal] methods. The formula:
     *
     * ```
     * Math.ceil( amount * ( 1 - Math.exp(-distanceBetweenRooms/30) ) )
     * ```
     *
     * @param amount Amount of resources to be sent
     * @param roomName1 The name of the first room
     * @param roomName2 The name of the second room
     *
     * @return The amount of energy required to perform the transaction
     */
    fun calcTransactionCost(amount: Number, roomName1: String, roomName2: String): Number

    /**
     * Cancel a previously created order. The 5% fee is not returned
     *
     * @param orderId The order ID as provided in [Game.market.orders][orders]
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_INVALID_ARGS] - The order ID is not valid
     */
    fun cancelOrder(orderId: String): Code<Error>

    /**
     * Change the price of an existing order. If [newPrice] is greater than old price,
     * you will be charged `(newPrice-oldPrice)*remainingAmount*0.05` credits
     *
     * @param orderId The order ID as provided in [Game.market.orders][orders]
     * @param newPrice The new order price
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER] - You are not the owner of the room's terminal or there is no terminal
     *
     * [ERR_NOT_ENOUGH_RESOURCES] - You don't have enough credits to pay a fee
     *
     * [ERR_INVALID_ARGS] - The arguments provided are invalid
     */
    fun changeOrderPrice(orderId: String, newPrice: Number): Code<Error>

    /**
     * Create a market order in your terminal. You will be charged `price*amount*0.05` credits when
     * the order is placed. The maximum orders count is 300 per player. You can create an order
     * at any time with any amount, it will be automatically activated and deactivated depending
     * on the resource/credits availability
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER] - You are not the owner of the room's terminal or there is no terminal
     *
     * [ERR_NOT_ENOUGH_RESOURCES] - You don't have enough credits to pay a fee
     *
     * [ERR_FULL] - You cannot create more than 50 orders
     *
     * [ERR_INVALID_ARGS] - The arguments provided are invalid
     */
    fun createOrder(params: CreateOrderParams): Code<Error>

    /**
     * Execute a trade deal from your Terminal in [yourRoomName] to another player's Terminal using
     * the specified buy/sell order. Your Terminal will be charged energy units of transfer cost
     * regardless of the order resource type. You can use [Game.market.calcTransactionCost][calcTransactionCost] method
     * to estimate it. When multiple players try to execute the same deal, the one with the shortest
     * distance takes precedence. You cannot execute more than 10 deals during one tick
     *
     * @param orderId The order ID as provided in [Game.market.getAllOrders][getAllOrders]
     * @param amount The amount of resources to transfer
     * @param yourRoomName The name of your room which has to contain an active Terminal with enough
     * amount of energy. This argument is not used when the order resource type is one of account-bound
     * resources (See [Resource.Intershard] constant)
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_NOT_OWNER] - You don't have a terminal in the target room
     *
     * [ERR_NOT_ENOUGH_RESOURCES] - You don't have enough credits or resource units
     *
     * [ERR_FULL] - You cannot execute more than 10 deals during one tick
     *
     * [ERR_INVALID_ARGS] - The arguments provided are invalid
     *
     * [ERR_TIRED] - The target terminal is still cooling down
     */
    fun deal(orderId: String, amount: Number, yourRoomName: String? = definedExternally): Code<Error>

    /**
     * Add more capacity to an existing order. It will affect `remainingAmount` and `totalAmount` properties.
     * You will be charged `price*addAmount*0.05` credits
     *
     * @param orderId The order ID as provided in [Game.market.orders][orders]
     * @param addAmount How much capacity to add. Cannot be a negative value
     *
     * @return One of the following codes:
     *
     * [OK] - The operation has been scheduled successfully
     *
     * [ERR_NOT_ENOUGH_RESOURCES] - You don't have enough credits to pay a fee
     *
     * [ERR_INVALID_ARGS] - The arguments provided are invalid
     */
    fun extendOrder(orderId: String, addAmount: Number): Code<Error>

    /**
     * Get other players' orders currently active on the market. This method supports internal indexing by `resourceType`
     *
     * @param filter An object or function that will filter the resulting list using the [lodash.filter](https://lodash.com/docs#filter) method
     */
    fun getAllOrders(filter: OrderFilter? = definedExternally): Array<Order>

    /**
     * Get other players' orders currently active on the market. This method supports internal indexing by `resourceType`
     *
     * @param filter An object or function that will filter the resulting list using the [lodash.filter](https://lodash.com/docs#filter) method
     */
    fun getAllOrders(filter: ((Order) -> Boolean)? = definedExternally): Array<Order>

    /**
     * Get other players' orders currently active on the market. This method supports internal indexing by `resourceType`
     *
     * @param filter An object or function that will filter the resulting list using the [lodash.filter](https://lodash.com/docs#filter) method
     */
    fun getAllOrders(filter: Any? = definedExternally): Array<Order>

    /**
     * Get daily price history of the specified resource on the market for the last 14 days
     *
     * @param resourceType One of the [Resource] constants. If undefined, returns history data for all resources
     */
    fun getHistory(resourceType: Resource? = definedExternally): Array<MarketHistoryEntry>

    /**
     * Retrieve info for specific market order
     */
    fun getOrderById(id: String): Order?
}

@JsExport
class CreateOrderParams(

    /**
     * The order type, either ORDER_SELL or ORDER_BUY
     */
    val type: OrderType,

    /**
     * Either one of the [Resource] constants or one of account-bound resources
     * (See [Resource.Intershard] constant). If your Terminal doesn't have the specified resource,
     * the order will be temporary inactive
     */
    val resourceType: Resource,

    /**
     * The price for one resource unit in credits. Can be a decimal number
     */
    val price: Number,

    /**
     * The amount of resources to be traded in total
     */
    val totalAmount: Number,

    /**
     * The room where your order will be created. You must have your own Terminal structure
     * in this room, otherwise the created order will be temporary inactive. This argument is not used
     * when [resourceType] is one of account-bound resources (See [Resource.Intershard] constant)
     */
    val roomName: String? = null,
)

interface OrderType {
    companion object {
        private fun of(value: String) =
            value.unsafeCast<OrderType>()

        val Buy = of("buy")
        val Sell = of("sell")
    }
}

external class Order {

    /**
     * The unique order ID
     */
    val id: String

    /**
     * The order creation time in game ticks. This property is absent for orders of the inter-shard market
     */
    val created: Number?

    /**
     * The order creation time [in milliseconds since UNIX epoch time](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/getTime#Syntax). This property is absent for old orders
     */
    val createdTimestamp: Timestamp?

    /**
     * Either [OrderType.Buy] or [OrderType.Sell]
     */
    val type: OrderType

    /**
     * Either one of the [Resource] constants or one of account-bound resources (See [Resource.Intershard] constant)
     */
    val resourceType: Resource

    /**
     * The room where this order is placed
     */
    val roomName: String

    /**
     * Currently available amount to trade
     */
    val amount: Number

    /**
     * How many resources are left to trade via this order
     */
    val remainingAmount: Number

    /**
     * The current price per unit
     */
    val price: Number
}

/**
 * @see [Order]
 */
@JsExport
data class OrderFilter(
    val id: String? = null,
    val created: Number? = null,
    val createdTimestamp: Timestamp? = null,
    val type: OrderType? = null,
    val resourceType: Resource? = null,
    val roomName: String? = null,
    val amount: Number? = null,
    val remainingAmount: Number? = null,
    val price: Number? = null
)

/**
 * ```
 * {
 *   "resourceType": "L",
 *   "date": "2019-06-24",
 *   "transactions": 4,
 *   "volume": 400,
 *   "avgPrice": 3.63,
 *   "stddevPrice": 0.27
 * }
 * ```
 */
external class MarketHistoryEntry {
    val resourceType: Resource
    val date: String
    val transactions: Number
    val volume: Number
    val avgPrice: Number
    val stddevPrice: Number
}

/**
 * ```
 * {
 *   transactionId : "56dec546a180ce641dd65960",
 *   time : 10390687,
 *   sender : {username: "Sender"},
 *   recipient : {username: "Me"},
 *   resourceType : "U",
 *   amount : 100,
 *   from : "W0N0",
 *   to : "W10N10",
 *   description : "trade contract #1",
 *   order: {        // optional
 *     id : "55c34a6b5be41a0a6e80c68b",
 *     type : "sell",
 *     price : 2.95
 *   }
 * }
 * ```
 */
external class Transaction {
    val transactionId: String
    val time: Number
    val sender: TransactionActor
    val recipient: TransactionActor
    val resourceType: Resource
    val amount: Number
    val from: String
    val to: String
    val description: String
    val order: TransactionOrder?
}

/**
 * ```
 * {
 *   username: "Me"
 * }
 * ```
 */
external class TransactionActor {
    val username: String
}

/**
 * ```
 * {
 *   id : "55c34a6b5be41a0a6e80c68b",
 *   type : "sell",
 *   price : 2.95
 * }
 * ```
 */
external class TransactionOrder {
    val id: String
    val type: OrderType
    val price: Number
}
