package dal;

import java.sql.*;
import javax.swing.JOptionPane;

public class Mod_conexao {

    public static Connection conector() {
        Connection conexao = null;
        //a linha abaixo chama o driver
        String url = "jdbc:mysql://localhost:3306/usuarios";

        String user = "root";
        String password = "";

        try {
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }

    }
}
