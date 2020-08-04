package com.sunflash.jsf;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ManagedBean
@SessionScoped
public class userBean {
    private String firstname;
    private String msg;
    private  String lastname;
    private String gender;
    private String username;
    private String emailAddress;
    private String userpwd;
    public String dbName;
    public String dbPassword;
    DataSource ds;

    public userBean(){
        try{
            Context ctx =  new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/database");
        }catch(NamingException e){
            e.printStackTrace();
        }
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getMsg() {
        return msg;
    }

    public String getGender() {
        return gender;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getUserpwd() {
        return userpwd;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserpwd(String userpwd) {
        this.userpwd = userpwd;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    // Validate Email
    public void validateEmail(FacesContext context, UIComponent toValidate, Object value) throws ValidatorException {
        String emailStr = (String) value;
        if (-1 == emailStr.indexOf("@")) {
            FacesMessage message = new FacesMessage("Email Address is Valid");
            throw new ValidatorException(message);
        }
    }

    //Validate Sign up process
    public String registerNewUser(){
        int i = 0;
        if(firstname != null){
            PreparedStatement ps = null;
            Connection conn = null;
            try{
                if(ds != null){
                    conn = ds.getConnection();
                    if(conn !=null){
                        String sql = "insert into users(firstname,lastname,username,email,gender,password) VALUES ( ?,?,?,?,?,?)";
                        ps = conn.prepareStatement(sql);
                        ps.setString(1 , firstname);
                        ps.setString(2, lastname);
                        ps.setString(3, username);
                        ps.setString(4, emailAddress);
                        ps.setString(5, gender);
                        ps.setString(6, userpwd);
                        i = ps.executeUpdate();
                        System.out.println("Data Added Successifully");
                    }
                }
            }catch (Exception e){
                System.out.println(e);

            }finally {
                try{
                    conn.close();
                    ps.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        if(i>0){
            return "success";

        }else {
            return  "unsuccess";
        }
    }
    // Action Methods
    public String storePatientInfo() {
        boolean stored = true;
        FacesMessage message = null;
        String outcome = null;
        if (stored) {
            message = new FacesMessage("Patient Information is stored Successfully.");
            outcome = "success";
        } else {
            message = new FacesMessage("Patient Information is NOT stored Successfully.");
            outcome = "unsuccess";
        }
        FacesContext.getCurrentInstance().addMessage(null, message);
        return outcome;
    }

    public  void  dbData (String uName){
        if(uName != null){
            PreparedStatement ps = null;
            Connection conn = null;
            ResultSet rs = null;

            if(ds != null){
                try{
                    conn = ds.getConnection();
                    if(conn != null){
                        String sql = "select firstname, password from users where firstname = '"+uName+"'";
                        ps = conn.prepareStatement(sql);
                        rs = ps.executeQuery();
                        rs.next();
                        dbName = rs.getString("firstname");
                        dbPassword = rs.getString("password");
                    }
                }catch (SQLException sq){
                    sq.printStackTrace();
                }

            }
        }
    }
}

