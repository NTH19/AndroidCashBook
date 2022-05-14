package com.example.cashbook.db


class ChartItemBean {
    var sImageId = 0
    var type: String? = null
    var ratio = 0f
    var totalMoney = 0f

    constructor() {}

    constructor(sImageId: Int, type: String?, ratio: Float, totalMoney: Float) {
        this.sImageId = sImageId
        this.type = type
        this.ratio = ratio
        this.totalMoney = totalMoney
    }
}
