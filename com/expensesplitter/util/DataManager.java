package com.expensesplitter.util;

import com.expensesplitter.model.Group;
import java.io.*;

public class DataManager {

    public void saveData(Group group, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(group);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Group loadData(String filePath) {
        Group group = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            group = (Group) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return group;
    }
}