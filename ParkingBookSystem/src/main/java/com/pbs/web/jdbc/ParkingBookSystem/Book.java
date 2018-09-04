package com.pbs.web.jdbc.ParkingBookSystem;

import java.util.Date;

public class Book {
	private int number;
    private int phone;
    private Date start;
    private Date end;
    private String userName;

    public Book(int number, Date start, Date end, String userName, int phone) {
        this.number = number;
        this.phone = phone;
        this.start = start;
        this.end = end;
        this.userName = userName;
    }

    /**
     * @return the number
     */
    public int getNumber() {
        return number;
    }

    /**
     * @param number the number to set
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * @return the phone
     */
    public int getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(int phone) {
        this.phone = phone;
    }

    /**
     * @return the start
     */
    public Date getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Date start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public Date getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(Date end) {
        this.end = end;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() { // just for debugging purposes
        return "Book{" + "number=" + number + ", phone=" + phone + ", start=" + start + ", end=" + end + ", userName=" + userName + '}';
    }
}
