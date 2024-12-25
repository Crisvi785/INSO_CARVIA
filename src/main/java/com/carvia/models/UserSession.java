package com.carvia.models;

import java.util.Optional;

public class UserSession {
    private static Optional<String> loggedInUser = Optional.empty();

    // Simula que un usuario ha iniciado sesión
    public static void login(String username) {
        loggedInUser = Optional.of(username);
    }

    // Simula que un usuario cierra sesión
    public static void logout() {
        loggedInUser = Optional.empty();
    }

    // Verifica si hay un usuario logueado
    public static boolean isLoggedIn() {
        return loggedInUser.isPresent();
    }

    // Devuelve el nombre del usuario logueado (si existe)
    public static Optional<String> getLoggedInUser() {
        return loggedInUser;
    }

    
}