package com.mycompany.jsfcsrfsimple.bean;

import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Named(value = "loginBean")
@SessionScoped
@Getter
@Setter
@NoArgsConstructor
public class LoginBean implements Serializable {

    private String userName;

    private String password;

    @PostConstruct
    public void init() {

    }

    public void onload() throws IOException {
        if (!FacesContext.getCurrentInstance().isPostback()) {

        }
    }

    public void logout() {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ((HttpServletRequest) ec.getRequest()).logout();
            ec.invalidateSession();
            ec.redirect("logout");
        } catch (ServletException | IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String login() {
        try {
            /*Guvenlik nedeniyle login olduktan sonra session un yenilenmesi istendigi icin var olan eski session kill edilip yeni session kullaniliyor!*/
            HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            HttpSession session = req.getSession();
//            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            if (session != null) {
                System.out.println("Old session Id : " + session.getId());
//                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//                session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                session = req.getSession(true);
                System.out.println("New session Id : " + session.getId());
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("content/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "content/index.xhtml";
    }
}
