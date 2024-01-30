package com.example.gui_practika;

//import java.util.Scanner;
public class Child {
    private String FIO;
    private String gender;
    private int age;
    public Child(String FIO, String gender, int age){
        this.FIO = FIO;
        this.gender = gender;
        this.age = age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }
    public void setFIO(String FIO) {
        this.FIO = FIO;
    }
    public String getFIO() {
        return FIO;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getGender() {
        return gender;
    }
    /*public static Child makeNewChild(Scanner in){
        in.nextLine();
        System.out.println("Введите ФИО ребёнка:");
        String newName = in.nextLine();
        System.out.println("Введите возраст ребёнка:");
        int newAge = in.nextInt();
        System.out.println("Введите пол ребёнка:");
        String newGender = in.next();
        return new Child(newName, newGender, newAge);
    }*/

    @Override
    public String toString() {
        return "ФИО: " + getFIO() + "\nПол: " + getGender() + "\nВозраст: " + getAge() + "\n____________________________\n";
    }
}

