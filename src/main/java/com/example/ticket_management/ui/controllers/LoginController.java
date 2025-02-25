package com.example.ticket_management.ui.controllers;

public class LoginController {

    // Dans un vrai scénario, vous feriez appel au backend pour vérifier user/pass
    public String authenticate(String username, String password) {
        // Ex : on fait un appel REST pour vérifier le rôle. Simplifions :
        if ("employee".equalsIgnoreCase(username)) {
            return "EMPLOYEE";
        } else if ("it".equalsIgnoreCase(username)) {
            return "IT_SUPPORT";
        }
        return null;
    }
}
