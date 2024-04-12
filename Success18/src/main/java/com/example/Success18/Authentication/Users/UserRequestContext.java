package com.example.Success18.Authentication.Users;


//todo:designed to manage the current user's context within a thread.
// It uses InheritableThreadLocal to store the current user's username,
// which is a thread-safe way to pass data between threads
public class UserRequestContext {
    private static ThreadLocal<String> currentUser = new InheritableThreadLocal<>();
    public static String getCurrentUser() {
        return currentUser.get();
    }
    public static void setCurrentUser(String userName) {
        currentUser.set(userName);
    }
    public static void clear() {
        currentUser.set(null);
    }
}