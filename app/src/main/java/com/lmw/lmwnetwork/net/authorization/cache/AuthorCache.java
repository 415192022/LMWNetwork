package com.lmw.lmwnetwork.net.authorization.cache;

import com.lmw.lmwnetwork.net.authorization.persistence.SharedPrefsAuthorPersistor;

public class AuthorCache {

    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setAuthor(String token) {
        this.author = new Author(SharedPrefsAuthorPersistor.AUTHORIZATION, "Bearer " + token);
    }

    public void setAuthor(String token, String id) {
        this.author = new Author(SharedPrefsAuthorPersistor.AUTHORIZATION, "Bearer " + token);
        author.setUserId(id);
    }

    public void clear() {
        author = null;
    }
}
