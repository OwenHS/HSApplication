// Book.aidl.aidl
package com.hs.libs.sample.index.basic.aidltest;

import  com.hs.libs.sample.index.basic.aidltest.Book;

// Declare any non-default types here with import statements

interface BookManager {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Book> getBooks();

    void addBook(inout Book book);
}
