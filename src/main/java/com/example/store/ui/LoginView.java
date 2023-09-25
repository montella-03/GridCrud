package com.example.store.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.Route;

@Route("login")
public class LoginView extends LoginForm{

    public LoginView(){
        setAction("login");

    }
}
