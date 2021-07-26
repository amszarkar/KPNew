package velociter.kumar.property.data

class ProjectData(
    internal var mName: String,
    internal var mImg: String,
    internal var mId: String,
    internal var mLat: String,
    internal var mLong: String,
    internal var mLocation: String,
    internal var mCity: String
)  {

    fun getmName(): String {
        return mName
    }

    fun setmName(mName: String) {
        this.mName = mName
    }

    fun getmImg(): String {
        return mImg
    }

    fun setmImg(mImg: String) {
        this.mImg = mImg
    }

    fun getmId(): String {
        return mId
    }

    fun setmId(mId: String) {
        this.mId = mId
    }

    fun getmLat(): String {
        return mLat
    }

    fun setmLat(mLat: String) {
        this.mLat = mLat
    }

    fun getmLong(): String {
        return mLong
    }

    fun setmLong(mLong: String) {
        this.mLong = mLong
    }

    fun getmLocation(): String {
        return mLocation
    }

    fun setmLocation(mLocation: String) {
        this.mLocation = mLocation
    }

    fun getmCity(): String {
        return mCity
    }

    fun setmCity(mCity: String) {
        this.mCity = mCity
    }
}
