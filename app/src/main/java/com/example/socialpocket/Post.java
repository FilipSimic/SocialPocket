package com.example.socialpocket;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Post implements Parcelable {
    private String idApp = "";
    private String idPost = "";

    private String author = "";
    private String title = "";
    private String text = "";

    public Post(String idApp, String title, String author, String text) {
        this.idApp = idApp;
        this.idPost = UUID.randomUUID().toString().replace("-","");

        this.title = title;
        this.author = author;
        this.text = text;
    }

    public Post(Parcel in) {
        idApp = in.readString();
        idPost = in.readString();
        title = in.readString();
        author = in.readString();
        text = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idApp);
        dest.writeString(this.idPost);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeString(this.text);
    }

    @Override
    public String toString() {
        return title + " " + author + " " + text;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    public String getIdPost() {
        return idPost;
    }

    public void setIdPost(String idPost) {
        this.idPost = idPost;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
