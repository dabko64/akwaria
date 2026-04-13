package ui;

import model.UserRole;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class LoginFrame extends JFrame {
    public LoginFrame(Consumer<UserRole> onLogin) {
        setTitle("Logowanie");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/akwarium.jpg");
        backgroundPanel.setLayout(new GridBagLayout());
        setContentPane(backgroundPanel);

        JPanel cardPanel = new JPanel(new GridBagLayout());
        cardPanel.setBackground(new Color(241, 237, 215, 210));
        cardPanel.setBorder(BorderFactory.createLineBorder(UIStyle.DARK_GRAY, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label = new JLabel("Wybierz typ użytkownika", SwingConstants.CENTER);
        UIStyle.styleLabel(label);

        JButton adminButton = new JButton("ADMINISTRATOR");
        JButton clientButton = new JButton("KLIENT");

        UIStyle.styleButton(adminButton, UIStyle.TURQUOISE, Color.WHITE);
        UIStyle.styleButton(clientButton, UIStyle.CORAL, Color.WHITE);

        adminButton.addActionListener(e -> {
            onLogin.accept(UserRole.ADMIN);
            dispose();
        });

        clientButton.addActionListener(e -> {
            onLogin.accept(UserRole.CLIENT);
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        cardPanel.add(label, gbc);

        gbc.gridy = 1;
        cardPanel.add(adminButton, gbc);

        gbc.gridy = 2;
        cardPanel.add(clientButton, gbc);

        backgroundPanel.add(cardPanel);
    }
}