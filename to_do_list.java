package com.example.studyDB;

public class to_do_list {
    int _id;
    String locationX;
    String locationY;
    String contents;

    public to_do_list(int _id, String todo){
        this._id = _id;
        this.contents = todo;
        //this.locationX = locationX;
        //this.locationY = locationY;
    }

    /*
    public to_do_list(int _id, String locationX, String locationY, String contents){
        this._id = _id;
        this.locationX = locationX;
        this.locationY = locationY;
        this.contents = contents;
    }


     */
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }


}
