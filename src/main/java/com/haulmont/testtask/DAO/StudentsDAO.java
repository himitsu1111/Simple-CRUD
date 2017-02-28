package com.haulmont.testtask.DAO;

import com.haulmont.testtask.DAO.POJO.Groups;
import com.haulmont.testtask.DAO.POJO.Students;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by secret on 22.02.2017.
 */
public class StudentsDAO {

    public List<Students> getStudents() {
        List<Students> listGroups = new ArrayList<Students>();
        String sql = "SELECT S.ID, S.NAME, S.LASTNAME, S.PATRONYMIC, " +
                "S.DATE_OF_BIRTH, G.NUMBER AS GROUP_ID FROM STUDENTS AS S, " +
                "GROUPS AS G WHERE S.GROUP_ID = G.ID";
        Connection con = ConnectionSingleton.getInstance();
        ResultSet rs = null;
        String result = "nothing";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next())
                listGroups.add(new Students(rs.getInt("ID"), rs.getString("NAME"),
                        rs.getString("LASTNAME"), rs.getString("PATRONYMIC"),
                        rs.getDate("DATE_OF_BIRTH"), rs.getInt("GROUP_ID")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGroups;
    }

    public void addStud(String name, String lname, String patro, Date dob, int group) {
        String sql = "INSERT INTO STUDENTS (NAME," +
                " LASTNAME, PATRONYMIC, DATE_OF_BIRTH,GROUP_ID )VALUES (?, ?, ?, ?, " +
                "(SELECT ID FROM GROUPS WHERE NUMBER = ?))";
        Connection con = ConnectionSingleton.getInstance();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lname);
            ps.setString(3, patro);
            ps.setDate(4, dob);
            ps.setInt(5, group);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updStud(String name, String lname, String patro, Date dob, int group, long id) {
        String sql = "UPDATE STUDENTS SET NAME = ?, LASTNAME = ?, PATRONYMIC = ?, " +
                "DATE_OF_BIRTH = ?, GROUP_ID = (SELECT ID FROM GROUPS WHERE NUMBER = ?) WHERE ID = ?";
        Connection con = ConnectionSingleton.getInstance();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lname);
            ps.setString(3, patro);
            ps.setDate(4, dob);
            ps.setInt(5, group);
            ps.setLong(6, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteStud(long id) {
        String sql = "DELETE FROM STUDENTS WHERE ID = ?";
        Connection con = ConnectionSingleton.getInstance();

//        PreparedStatement ps = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getStudIdForUpd(String name, String lname, String patro, Date dob, int groupNum) {
        String sql = "SELECT ID FROM STUDENTS WHERE NAME = ? AND LASTNAME = ? AND PATRONYMIC = ? " +
                "AND DATE_OF_BIRTH = ? AND GROUP_ID = (SELECT ID FROM GROUPS WHERE NUMBER = ?)";
        Connection con = ConnectionSingleton.getInstance();
        long result = 0;

        ResultSet rs = null;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lname);
            ps.setString(3, patro);
            ps.setDate(4, dob);
            ps.setInt(5, groupNum);
            rs = ps.executeQuery();

            if (rs.next()){
                result = rs.getInt("ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result; //return ID of selected row in students grid
    }
}
