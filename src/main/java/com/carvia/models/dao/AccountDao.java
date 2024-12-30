package com.carvia.models.dao;

import com.carvia.controllers.*;
import com.carvia.models.vo.AccountVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AccountDao {

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(AccountDao.class);

    public AccountDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public boolean insertAccount(AccountVo account) {
        String query = "INSERT INTO Account (idUs, state) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, account.getIdUs());
            statement.setString(2, account.getState());
            logger.info("Cuenta del id de usuario " + account.getIdUs() + " creada con éxito: " + account.getState());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error creando la cuenta con id de usuario " + account.getIdUs());
            e.printStackTrace();
            return false;
        }
    }

    /* 
    public AccountVo getAccountById(int idAc) {
        String query = "SELECT * FROM Account WHERE idAc = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAc);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int idUs = resultSet.getInt("idUs");
                String state = resultSet.getString("state");
                logger.info("Account with ID " + idAc + " retrieved");
                return new AccountVo(idAc, idUs, state);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving account with ID " + idAc);
            e.printStackTrace();
        }
        return null;
    }

    public List<AccountVo> getAllAccounts() {
        String query = "SELECT * FROM Account";
        List<AccountVo> accountList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idAc = resultSet.getInt("idAc");
                int idUs = resultSet.getInt("idUs");
                String state = resultSet.getString("state");
                accountList.add(new AccountVo(idAc, idUs, state));
            }
            logger.info("All accounts retrieved");
        } catch (SQLException e) {
            logger.error("Error retrieving all accounts");
            e.printStackTrace();
        }
        return accountList;
    }

    public boolean updateAccountState(int idAc, String newState) {
        String query = "UPDATE Account SET state = ? WHERE idAc = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newState);
            statement.setInt(2, idAc);
            logger.info("Account with ID " + idAc + " updated to state: " + newState);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error updating account with ID " + idAc);
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAccount(int idAc) {
        String query = "DELETE FROM Account WHERE idAc = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idAc);
            logger.info("Account with ID " + idAc + " deleted");
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting account with ID " + idAc);
            e.printStackTrace();
            return false;
        }
    }
    */
}
