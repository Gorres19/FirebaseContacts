package com.contacts.firebase.firebasecontacts;

/**
 * Created by joshua on 8/6/2016.
 */
public class Contacts {

    private String ContactNumber;
    private String friendsName;

    public String getContactNumber() {

        return ContactNumber;
    }
    public void setContactNumber(String contactNumber) {

        this.ContactNumber = contactNumber;
    }
    public String getFriendsName() {

        return friendsName;
    }
    public void setFriendsName(String friendName) {

        this.friendsName = friendName;
    }
    public Contacts(String friendName, String contactNumber) {

        this.friendsName = friendName;
        this.ContactNumber = contactNumber;
    }

    public Contacts() {
    }
}
