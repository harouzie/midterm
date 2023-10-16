package tdtu.fit.hrz.midterm.entity;

import java.util.Date;

/**
 * Singleton pattern, since we don't have many user
 */
final class User {
    private static User instance = null;
    private String userID;
    private String firstName = "NewUser";
    private String lastName = "GivenName";
    private String email;
    private String region;
    private String country;
    private Date dateJoined;
    private double balance = 0.0F;
    private User(){}
    private User(String userID, String firstName, String lastName) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    private static User getInstance(){
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }






}
