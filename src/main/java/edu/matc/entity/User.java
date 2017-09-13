package edu.matc.entity;
import java.time.*;

// TODO Add instance variable for the date of birth
// TODO Add a calculation for the user's age. Age should not be stored, it should be calculated only.

/**
 * A class to represent a user.
 *
 * @author pwaite
 */
public class User {
    private String firstName;
    private String lastName;
    private String userId;
    private String dateOfBirth;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Instantiates a new User.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param userId    the userId
     */
    public User(String firstName, String lastName, String userId, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
    }


    public int getAge() {

        return Period.between(LocalDate.parse(dateOfBirth), LocalDate.now()).getYears();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets userId.
     *
     * @return the userId
     */
    public String getuserId() {
        return userId;
    }

    /**
     * Sets userId.
     *
     * @param userId the userId
     */
    public void setuserId(String userId) {
        this.userId = userId;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}