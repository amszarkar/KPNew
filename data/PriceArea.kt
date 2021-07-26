package velociter.kumar.property.data

class PriceArea(
    internal var mPriceFrom: String,
    internal var mAreaFrom: String,
    internal var mAreaTo: String,
    internal var mPriceSuffix: String,
    internal var mFlatType: String
) {

    fun getmPriceFrom(): String {
        return mPriceFrom
    }

    fun setmPriceFrom(mPriceFrom: String) {
        this.mPriceFrom = mPriceFrom
    }

    fun getmAreaFrom(): String {
        return mAreaFrom
    }

    fun setmAreaFrom(mAreaFrom: String) {
        this.mAreaFrom = mAreaFrom
    }

    fun getmAreaTo(): String {
        return mAreaTo
    }

    fun setmAreaTo(mAreaTo: String) {
        this.mAreaTo = mAreaTo
    }

    fun getmPriceSuffix(): String {
        return mPriceSuffix
    }

    fun setmPriceSuffix(mPriceSuffix: String) {
        this.mPriceSuffix = mPriceSuffix
    }

    fun getmFlatType(): String {
        return mFlatType
    }

    fun setmFlatType(mFlatType: String) {
        this.mFlatType = mFlatType
    }
}
