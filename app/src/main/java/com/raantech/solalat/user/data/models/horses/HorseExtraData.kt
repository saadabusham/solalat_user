package com.raantech.solalat.user.data.models.horses

import com.google.gson.annotations.SerializedName
import com.raantech.solalat.user.data.models.Price
import java.io.Serializable

data class HorseExtraData(

    @field:SerializedName("auction_ended")
    val auctionEnded: Boolean? = null,

    @field:SerializedName("auction_started")
    val auctionStarted: Boolean? = null,

    @field:SerializedName("minimum_bid")
    val minimumBid: Price? = null,

    @field:SerializedName("max_price")
    var maxPrice: Price? = null,

    @field:SerializedName("is_win")
    val isWin: Boolean? = null,

    @field:SerializedName("is_subscribed")
    val isSubscribed: Boolean? = null,

    @field:SerializedName("subscription")
    val subscription: Subscription? = null
) : Serializable