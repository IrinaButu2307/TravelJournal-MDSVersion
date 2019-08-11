package com.example.ioana.travel_journal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.GregorianCalendar;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "Trips")
public class Trip implements Parcelable {
    public enum TripType {CITY_BREAK,SEA_SIDE,MOUNTAINS}

    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "destination")
    private String mDestination;
    @TypeConverters(UriConverter.class)
    @ColumnInfo(name = "picture")
    private Uri mPicture;
    @ColumnInfo(name = "rating")
    private float mRating;
    @TypeConverters(TypeConverter.class)
    @ColumnInfo(name = "type")
    private TripType mTripType;
    @ColumnInfo(name = "price")
    private float mPrice;
    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "start_date")
    private Calendar mStartDate;
    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "end_date")
    private Calendar mEndDate;
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "documentId")
    @NonNull
    private String mDocumentId;
    @Ignore
   // @ColumnInfo(name = "isFavorite")
    private boolean mIsFavourite;

    public Trip() {
    }

    @Ignore
    protected Trip(Parcel in) {
        mName = in.readString();
        mDestination = in.readString();
        mPicture = Uri.parse(in.readString());
        mRating = in.readFloat();
        mPrice = in.readFloat();
        mTripType = TripType.valueOf(in.readString());
        Calendar startDate = new GregorianCalendar();
        startDate.setTimeInMillis(in.readLong());
        mStartDate = startDate;
        Calendar endDate = new GregorianCalendar();
        endDate.setTimeInMillis(in.readLong());
        mEndDate = endDate;
        mDocumentId = in.readString();
        mIsFavourite = (in.readByte() == 1);
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDestination);
        dest.writeString(mPicture.toString());
        dest.writeFloat(mRating);
        dest.writeFloat(mPrice);
        dest.writeString(mTripType.toString());
        dest.writeLong(mStartDate.getTimeInMillis());
        dest.writeLong(mEndDate.getTimeInMillis());
        dest.writeString(mDocumentId);
        dest.writeByte((byte)(mIsFavourite == true ? 1 : 0));
    }

    @Ignore
    public Trip(String mName, String mDestination, Uri mPicture, float mRating, TripType mTripType,
                float mPrice, Calendar mStartDate, Calendar mEndDate) {
        this.mName = mName;
        this.mDestination = mDestination;
        this.mPicture = mPicture;
        this.mRating = mRating;
        this.mTripType = mTripType;
        this.mPrice = mPrice;
        this.mStartDate = mStartDate;
        this.mEndDate = mEndDate;
    }

    public TripType getMTripType() {
        return mTripType;
    }

    public void setMTripType(TripType mTripType) {
        this.mTripType = mTripType;
    }

    public float getMPrice() {
        return mPrice;
    }

    public void setMPrice(float mPrice) {
        this.mPrice = mPrice;
    }


    public float getMRating() {
        return mRating;
    }

    public void setMRating(float mRating) {
        this.mRating = mRating;
    }

    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }

    public String getMDestination() {
        return mDestination;
    }

    public void setMDestination(String mDestination) {
        this.mDestination = mDestination;
    }

    public Uri getMPicture() {
        return mPicture;
    }

    public void setMPicture(Uri mPicture) {
        this.mPicture = mPicture;
    }

    public Calendar getMStartDate() {
        return mStartDate;
    }

    public void setMStartDate(Calendar mStartDate) {
        this.mStartDate = mStartDate;
    }

    public Calendar getMEndDate() {
        return mEndDate;
    }

    public void setMEndDate(Calendar mEndDate) {
        this.mEndDate = mEndDate;
    }

    public boolean getmIsFavourite() {
        return mIsFavourite;
    }

    public void setmIsFavourite(boolean mIsFavourite) {
        this.mIsFavourite = mIsFavourite;
    }

    public String getMDocumentId() {
        return mDocumentId;
    }

    public void setMDocumentId(String mDocumentId) {
        this.mDocumentId = mDocumentId;
    }


    @Override
    public String toString() {
        return "Trip{" +
                "mName='" + mName + '\'' +
                ", mDestination='" + mDestination + '\'' +
                ", mPicture=" + mPicture +
                ", mRating=" + mRating +
                ", mTripType=" + mTripType +
                ", mPrice=" + mPrice +
                ", mStartDate=" + mStartDate +
                ", mEndDate=" + mEndDate +
                ", mDocumentId='" + mDocumentId + '\'' +
                ", mIsFavourite=" + mIsFavourite +
                '}';
    }
}
