/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fr.legrain.classDB;

/**
 *
 * @author lee
 */
public class WsTaAcces_org {
    private int id;
    private String password_acces;
    private String ip_acces;
    private String user_acces;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp_acces() {
        return ip_acces;
    }

    public void setIp_acces(String ip_acces) {
        this.ip_acces = ip_acces;
    }

    public String getPassword_acces() {
        return password_acces;
    }

    public void setPassword_acces(String password_acces) {
        this.password_acces = password_acces;
    }

    public String getUser_acces() {
        return user_acces;
    }

    public void setUser_acces(String user_acces) {
        this.user_acces = user_acces;
    }
}
