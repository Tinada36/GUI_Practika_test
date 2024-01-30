package com.example.gui_practika;

import java.util.*;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

public class KinderGardenController {
    public static List<Group> groups;
    public static HashMap<Integer, Group> groupAndChild = new HashMap<>();
    @FXML
    private VBox mainMenu;

    @FXML
    private Pane createGroupBox;
    @FXML
    private Pane addChildBox;
    @FXML
    private Pane editGroupBox;
    @FXML
    private Pane editChildBox;
    @FXML
    private Pane removeChildBox;
    @FXML
    private Pane showInfoBox;

    @FXML
    private TextField groupNameField;

    @FXML
    private TextField groupNumberField;
    @FXML
    private TextField childNameField;
    @FXML
    private TextField childAgeField;
    @FXML
    private TextField childGenderField;
    @FXML
    private TextField newGroupNameField;
    @FXML
    private TextField oldChildName;
    @FXML
    private TextField newChildName;
    @FXML
    private TextField newChildAge;
    @FXML
    private TextField newChildGender;
    @FXML
    private TextField removeChildName;

    @FXML
    private TextArea showInfoText;

    @FXML
    private Button createGroupButton;

    @FXML
    private Button addChildButton;

    @FXML
    private Button editGroupButton;

    @FXML
    private Button editChildButton;

    @FXML
    private Button removeChildButton;

    @FXML
    private Button removeGroupButton;

    @FXML
    private Button showInfoButton;

    @FXML
    private Button exitButton;

    public KinderGardenController(){
        groups = new ArrayList<>();
    }
    public int changeGroup(){
        // Подготавливаем список номеров групп
        List<Integer> groupNumbers = new ArrayList<>();
        for (Group g : groups) {
            groupNumbers.add(g.getNumber());
        }

        // Показываем диалог выбора группы
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(groupNumbers.get(0), groupNumbers);
        dialog.setTitle("Выбор группы");
        dialog.setHeaderText("Выберите группу:");
        dialog.setContentText("Группа:");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getStyleClass().add("choice-dialog");

        Optional<Integer> result = dialog.showAndWait();

        // Обрабатываем результат
        if (result.isPresent()) {
            int selectedGroupNumber = result.get();
            System.out.println("Выбрана группа с номером " + selectedGroupNumber);
            return selectedGroupNumber;
        } else {
            System.out.println("Отменено");
            return -1; // или другое значение, чтобы обозначить отмену
        }
    }
    private void showSuccessNotification(String s) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Уведомление");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }

    @FXML
    private void ButtonCreateGroupPressed() {
        try {
            String groupName = groupNameField.getText();
            int groupNumber = Integer.parseInt(groupNumberField.getText());

            Group group = new Group(groupName, groupNumber);

            groupAndChild.put(group.getNumber(), group);
            groups.add(group);
            showSuccessNotification("Группа создана!");

            createGroupBox.setVisible(false);

            groupNameField.clear();
            groupNumberField.clear();
        } catch (Exception e){
        showSuccessNotification(String.valueOf(e));
        showSuccessNotification("Не верный ввод данных.");
        }
    }

    @FXML
    public void addGroup() {
        createGroupBox.setVisible(true);
    }

    @FXML
    public void removeGroup() {
        if (!groups.isEmpty()) {
            try {
                int changeNum = changeGroup();
                for (Group g : groups) {
                    if (changeNum == g.getNumber()) {
                        groupAndChild.remove(g.getNumber());
                        groups.remove(g);
                        showSuccessNotification("Группа удалена.");
                        break;
                    }
                }
            } catch (Exception e) {
                showSuccessNotification("Неверный ввод данных.");
            }
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }

    @FXML
    private void addChildButtonPressed() {
        if (!groups.isEmpty()) {
            try {
                // Вызываем метод для выбора группы
                int selectedGroupNumber = changeGroup();
                if (selectedGroupNumber == -1) {
                    // Обработка отмены выбора группы или других случаев
                    showSuccessNotification("Выбор группы отменен или произошла ошибка.");
                    mainMenu.setVisible(true);
                    addChildBox.setVisible(false);
                    return;
                }

                // Получаем выбранную группу
                Group selectedGroup = null;
                for (Group g : groups) {
                    if (g.getNumber() == selectedGroupNumber) {
                        selectedGroup = g;
                        break;
                    }
                }
                if (selectedGroup != null) {
                    // Получаем данные из полей ввода
                    String childName = childNameField.getText();
                    int childAge = Integer.parseInt(childAgeField.getText());
                    String childGender = childGenderField.getText();

                    // Создаем нового ребёнка
                    Child newChild = new Child(childName, childGender, childAge);

                    // Добавляем ребёнка в выбранную группу
                    selectedGroup.addChild(newChild);

                    showSuccessNotification("Ребёнок успешно добавлен");

                    // Скрываем поля ввода и возвращаемся в главное меню
                    addChildBox.setVisible(false);

                    childNameField.clear();
                    childAgeField.clear();
                    childGenderField.clear();
                } else {
                    showSuccessNotification("Группа не найдена.");
                    // Можно также добавить обработку ошибки, например, отображение сообщения пользователю.
                }
            } catch (Exception e){
                showSuccessNotification("Неверный ввод данных");
            }
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }
    @FXML
    public void addChildToGroup(){
        if (!groups.isEmpty()) {
            addChildBox.setVisible(true);
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }
    @FXML
    public void removeChildButtonPressed(){
        try {
            int change = changeGroup();
            String childName = removeChildName.getText();
            for (Group g : groupAndChild.values()) {
                for (Child c : g.getChildren()) {
                    if (c.getFIO().equals(childName)) {
                        removeChildFromGroup(change, c);
                        showSuccessNotification("Информация успешно удалена");

                        removeChildBox.setVisible(false);

                        break;
                    } else {
                        showSuccessNotification("Такого ребёнка нет");
                    }
                }
            }
            removeChildName.clear();
        } catch (Exception e){
            showSuccessNotification("Неверный ввод данных");
        }
    }
    @FXML
    public void removeChildGroup(){
        if (!groups.isEmpty()) {
            removeChildBox.setVisible(true);
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }
    public void removeChildFromGroup(int groupNumber, Child child) {
        Group group = groupAndChild.get(groupNumber);
        if (group != null) {
            group.removeChild(child);
        } else {
            showSuccessNotification("Группа не найдена");
        }
    }
    @FXML
    public void editGroupButtonPressed(){
        try {
            int selectedGroupNumber = changeGroup();
            if (selectedGroupNumber == -1) {
                // Обработка отмены выбора группы или других случаев
                showSuccessNotification("Выбор группы отменен или произошла ошибка.");
                mainMenu.setVisible(true);
                addChildBox.setVisible(false);
                return;
            }

            for (Group g : groups) {
                if (selectedGroupNumber == g.getNumber()) {
                    String newNickname = newGroupNameField.getText();
                    g.setNickname(newNickname);
                    showSuccessNotification("Название успешно изменено");
                }
            }

            editGroupBox.setVisible(false);
            newGroupNameField.clear();
        } catch (Exception e){
            showSuccessNotification("Неверный ввод данных");
        }
    }
    @FXML
    public void editGroup(){
        if (!groups.isEmpty()) {
            editGroupBox.setVisible(true);
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }

    @FXML
    public void editChildButtonPressed(){
        try {
            String childName = oldChildName.getText();
            Child childEdit = getChildByName(childName);
            if (childEdit != null) {
                String newFullName = newChildName.getText();
                String newGender = newChildGender.getText();
                int newAge = Integer.parseInt(newChildAge.getText());

                childEdit.setFIO(newFullName);
                childEdit.setGender(newGender);
                childEdit.setAge(newAge);

                showSuccessNotification("Информация изменена.");

                editChildBox.setVisible(false);
                oldChildName.clear();
                newChildName.clear();
                newChildAge.clear();
                newChildGender.clear();
            } else {
                showSuccessNotification("Ребёнок с таким ФИО не найден. Проверьте введенные данные.");
            }
        } catch (Exception e){
            showSuccessNotification("Неверный ввод данных");
        }
    }
    @FXML
    public void editChild(){
        if (!groups.isEmpty()) {
            editChildBox.setVisible(true);
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }
    public Child getChildByName(String childName) {
        for (Group group : groupAndChild.values()) {
            for (Child child : group.getChildren()) {
                if (child.getFIO().equals(childName)) {
                    return child;
                }
            }
        }
        return null; // Если ребенок не найден
    }
    @FXML
    public void showInfoButtonPressed(){
        showInfoBox.setVisible(false);
        showInfoText.clear();
    }
    @FXML
    public void showInfo(){
        if (!groups.isEmpty()) {
            showInfoBox.setVisible(true);
            try {
                int changeNum = changeGroup();
                for (Group g : groups) {
                    if (changeNum == g.getNumber()) {
                        Group gr = groupAndChild.get(changeNum);
                        showInfoText.appendText("Группа: " + gr.getNickname() + " номер " + gr.getNumber() + "\n");
                        List<Child> children = gr.getChildren();
                        showInfoText.appendText( "Дети в группе:\n");
                        for (Child ch : children) {
                            showInfoText.appendText(ch.toString());
                        }
                    }
                }
            } catch (Exception e){
                showSuccessNotification("Неверный ввод данных");
            }
        } else {
            showSuccessNotification("Сначала создайте группу.");
        }
    }
    @FXML
    public void exitProgram(){
        showSuccessNotification("Досвидания!!!");
        System.exit(0);
    }
}

