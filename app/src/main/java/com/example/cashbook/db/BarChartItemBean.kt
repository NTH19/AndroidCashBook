package com.example.cashbook.db

class BarChartItemBean {
    var year = 0
    var month = 0
    var day = 0
    var summoney = 0f

    constructor() {}
    constructor(year: Int, month: Int, day: Int, summoney: Float) {
        this.year = year
        this.month = month
        this.day = day
        this.summoney = summoney
    }
}
