package com.carvia.models;

import java.util.Optional;

import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;

public class UserSession {
    private static Optional<String> loggedInUser = Optional.empty();
    private static String username = null;

    // Simula que un usuario ha iniciado sesión
    public static void login(String username) {
        loggedInUser = Optional.of(username);
        UserSession.username = username; //
    }

    // Simula que un usuario cierra sesión
    public static void logout() {
        loggedInUser = Optional.empty();
        username = null; //
    }

    // Verifica si hay un usuario logueado
    public static boolean isLoggedIn() {
        return loggedInUser.isPresent();
    }

    // Devuelve el nombre del usuario logueado (si existe)
    public static Optional<String> getLoggedInUser() {
        return loggedInUser;
    }

    //Devuelve el ID del usuario logueado (si existe)
    public static int getLoggedInUserId(){
        if(loggedInUser.isPresent()){
            UserDao userDao = new UserDao();
            UserVo user = userDao.getUserByUsername(loggedInUser.get());
            if (user != null) {
                return user.getId();
            }
        }
        return -1; //-1 si no hay usuario logueado o no se encuentra en la bbdd
    }

     // Obtener el nombre de usuario logueado
     public static String getUsername() {
        return username;
    }
    
}