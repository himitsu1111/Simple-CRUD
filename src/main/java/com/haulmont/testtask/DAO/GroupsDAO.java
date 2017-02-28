package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.POJO.Groups;
import com.vaadin.ui.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by secret on 22.02.2017.
 */
public class GroupsDAO {


    public List<Groups> getGroups() {

        List<Groups> listGroups = new ArrayList<Groups>();
        String sql = "SELECT * FROM GROUPS";
        Connection con = ConnectionSingleton.getInstance();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                listGroups.add(new Groups(rs.getInt("ID"),
                        rs.getInt("NUMBER"),
                        rs.getString("FACULTY")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGroups;
    }

    public void addGroup(String num, String fac) {

        String sql = "INSERT INTO GROUPS (NUMBER, FACULTY) VALUES (?, ?) ";
        Connection con = ConnectionSingleton.getInstance();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(num));
            ps.setString(2, fac);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateGroup(long id, int num, String fac) {
        String sql = "UPDATE GROUPS SET NUMBER = ?, FACULTY = ? WHERE ID = ?";
        Connection con = ConnectionSingleton.getInstance();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setString(2, fac);
            ps.setLong(3, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public long getIdGroup(int num, String fac) {

        String sql = "SELECT ID FROM GROUPS WHERE NUMBER = ? AND FACULTY = ?";
        Connection con = ConnectionSingleton.getInstance();
        long result = 0;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, num);
            ps.setString(2, fac);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                result = rs.getInt("ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void delGroup(String num) {
        String sql = "DELETE FROM GROUPS WHERE NUMBER = ?";
        Connection con = ConnectionSingleton.getInstance();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(num));
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            Notification.show("Can't delete this group");
        }

    }
}
