package db

enum class Currency {
    USD {
        override fun getDefaultRatio(): Double = 74.5
    },
    EUR {
        override fun getDefaultRatio(): Double = 88.49
    },
    RUB {
        override fun getDefaultRatio(): Double = 1.0
    };

    abstract fun getDefaultRatio(): Double

    fun fromDefault(priceInDefaultCurrency: Double): Double {
        return priceInDefaultCurrency / this.getDefaultRatio()
    }

    fun toDefault(priceInCurrentCurrency: Double): Double {
        return priceInCurrentCurrency * this.getDefaultRatio()
    }

    companion object {
        fun convertPrice(currencyFrom: Currency, currencyTo: Currency, value: Double): Double {
            return currencyTo.fromDefault(currencyFrom.toDefault(value))
        }
    }
}
