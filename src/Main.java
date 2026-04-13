import data.DataGenerator;
import facade.OceanariumFacade;
import model.OceanariumManager;
import model.UserRole;
import ui.AdminFrame;
import ui.ClientFrame;
import ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                OceanariumManager manager = DataGenerator.getInstance().createSampleData();
                OceanariumFacade facade = new OceanariumFacade(manager);

                LoginFrame loginFrame = new LoginFrame(role -> {
                    if (role == UserRole.ADMIN) {
                        new AdminFrame(facade).setVisible(true);
                    } else {
                        new ClientFrame(facade).setVisible(true);
                    }
                });

                loginFrame.setVisible(true);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "blad startu aplikacji", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}