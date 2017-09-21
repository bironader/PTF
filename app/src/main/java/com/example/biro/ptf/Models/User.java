package com.example.biro.ptf.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Biro on 3/29/2017.
 */

public class User implements Parcelable {

    private String full_name;
    private String id;
    private String profile_link;

    private String email;
    private String profile_pic_url;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String birthday;
    private String mobilePhone;
    private String gender;
    public User() {

    }

    public User(Parcel in) {
        full_name = in.readString();

        id = in.readString();
        profile_link = in.readString();

        email = in.readString();
        profile_pic_url = in.readString();
        birthday = in.readString();
        mobilePhone = in.readString();
        gender = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile_link() {
        return profile_link;
    }

    public void setProfile_link(String profile_link) {
        this.profile_link = profile_link;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getProfile_pic_url() {
        return profile_pic_url;
    }


    public void setProfile_pic_url(String profile_pic_url) {
        this.profile_pic_url = profile_pic_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(full_name);

        dest.writeString(id);
        dest.writeString(profile_link);

        dest.writeString(email);
        dest.writeString(profile_pic_url);
        dest.writeString(birthday);
        dest.writeString(mobilePhone);
        dest.writeString(gender);
    }
}
