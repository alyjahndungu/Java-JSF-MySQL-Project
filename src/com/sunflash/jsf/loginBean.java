package com.sunflash.jsf;

        import javax.faces.bean.ManagedBean;
        import javax.faces.bean.SessionScoped;
        import javax.sql.DataSource;
        import java.sql.*;

@ManagedBean
@SessionScoped
public class loginBean {
    private  String userName;
    private  String password;
    private String dbUserName;
    private  String dbPassword;


    Statement statement;
    ResultSet resultSet;
    Connection connection;
    DataSource ds;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void dbData (String userName) throws SQLException {
        connection = ds.getConnection();
        if(connection !=null) {
            String sql = "select * users where userName=?";
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            dbUserName = resultSet.getString(1 );
            dbPassword = resultSet.getString(2);
        }else {
            System.out.println("Unable to connect to database. No Connection");
        }
    }

    public String checkValidUser(){
        if (userName.equalsIgnoreCase(dbUserName)){
            if (password.equals(dbPassword)){
                return "success";
            }else {
                return "unsuccess";
            }
        }else {
            return "unsuccess";
        }
    }
}

