package com.example.gui_practika;

import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
public class Group {

    private String nickname;
    private int number;
    private List<Child> children;
    public Group(String nickname, int number){
        this.nickname = nickname;
        this.number = number;
        children = new ArrayList<>();
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getNumber() {
        return number;
    }
    public List<Child> getChildren() {
        return children;
    }
    public void addChild(Child child) {
        children.add(child);
    }
    public void removeChild(Child child) {
        children.remove(child);
    }
    /*public static Group makeNewGroup(Scanner in){
        System.out.println("Введите название группы:");
        String newName = in.nextLine();
        System.out.println("Введите номер группы:");
        int newNumber = in.nextInt();
        return new Group(newName, newNumber);
    }*/
}