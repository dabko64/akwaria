package ui;

import exceptions.OceanariumException;
import facade.OceanariumFacade;
import model.Aquarium;
import model.Fish;
import model.FishCondition;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientFrame extends JFrame {
    private final OceanariumFacade facade;
    private final GenericTableModel<Aquarium> aquariumModel;
    private final GenericTableModel<Fish> fishModel;
    private final JTable aquariumTable;
    private final JTable fishTable;
    private final JTextField filterTextField;
    private final JComboBox<Object> stateComboBox;

    public ClientFrame(OceanariumFacade facade) {
        this.facade = facade;

        setTitle("OCEANARIUM - klient");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/akwarium.jpg");
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(backgroundPanel);

        aquariumModel = new GenericTableModel<>(
                facade.getAquariums(),
                List.of(
                        new ColumnDefinition<>("NAZWA AKWARIUM", Aquarium::getAquariumName),
                        new ColumnDefinition<>("POJEMNOŚĆ", Aquarium::getMaxCapacity),
                        new ColumnDefinition<>("ZAPEŁNIENIE %", a -> String.format("%.2f", a.getFillPercentage()))
                )
        );

        fishModel = new GenericTableModel<>(
                List.of(),
                List.of(
                        new ColumnDefinition<>("IMIĘ", Fish::getName),
                        new ColumnDefinition<>("GATUNEK", Fish::getSpecies),
                        new ColumnDefinition<>("STAN", Fish::getCondition),
                        new ColumnDefinition<>("POCHODZENIE", Fish::getOrigin)
                )
        );

        aquariumTable = new JTable(aquariumModel);
        fishTable = new JTable(fishModel);

        UIStyle.styleTable(aquariumTable);
        UIStyle.styleTable(fishTable);

        filterTextField = new JTextField(15);
        UIStyle.styleTextField(filterTextField);

        stateComboBox = new JComboBox<>();
        stateComboBox.addItem("wszystkie");
        for (FishCondition condition : FishCondition.values()) {
            stateComboBox.addItem(condition);
        }
        UIStyle.styleComboBox(stateComboBox);

        buildLayout();
        bindEvents();
    }

    private void buildLayout() {
        JPanel topPanel = UIStyle.createCardPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton addAquariumButton = new JButton("dodaj akwarium");
        JButton removeAquariumButton = new JButton("usuń akwarium");
        JButton addFishButton = new JButton("dodaj rybę");
        JButton removeFishButton = new JButton("usuń rybę");
        JButton sortAquariumsButton = new JButton("sortuj wedlug zapelnienia");

        UIStyle.styleButton(addAquariumButton, UIStyle.TURQUOISE, Color.WHITE);
        UIStyle.styleButton(removeAquariumButton, UIStyle.DARK_GRAY, Color.WHITE);
        UIStyle.styleButton(addFishButton, UIStyle.CORAL, Color.WHITE);
        UIStyle.styleButton(removeFishButton, UIStyle.DARK_GRAY, Color.WHITE);
        UIStyle.styleButton(sortAquariumsButton, UIStyle.LIGHT_MINT, Color.BLACK);

        JScrollPane aquariumScroll = new JScrollPane(aquariumTable);
        JScrollPane fishScroll = new JScrollPane(fishTable);
        UIStyle.styleScrollPane(aquariumScroll);
        UIStyle.styleScrollPane(fishScroll);

        RoundedPanel leftCard = UIStyle.createCardPanel(new BorderLayout());
        leftCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        leftCard.add(aquariumScroll, BorderLayout.CENTER);

        RoundedPanel rightCard = UIStyle.createCardPanel(new BorderLayout());
        rightCard.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        rightCard.add(fishScroll, BorderLayout.CENTER);

        JPanel centerPanel = UIStyle.createTransparentPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.add(leftCard);
        centerPanel.add(rightCard);

        RoundedPanel bottomPanel = UIStyle.createCardPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        JLabel filterLabel = new JLabel("Filtr:");
        JLabel stateLabel = new JLabel("Stan:");

        UIStyle.styleLabel(filterLabel);
        UIStyle.styleLabel(stateLabel);
        UIStyle.styleTextField(filterTextField);
        UIStyle.styleComboBox(stateComboBox);

        filterTextField.setColumns(15);

        bottomPanel.add(filterLabel);
        bottomPanel.add(filterTextField);
        bottomPanel.add(stateLabel);
        bottomPanel.add(stateComboBox);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void bindEvents() {
        aquariumTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadFish();
            }
        });

        filterTextField.addActionListener(e -> filterFishByText());
        stateComboBox.addActionListener(e -> filterFishByState());
    }

    private String getSelectedAquariumName() {
        int row = aquariumTable.getSelectedRow();
        if (row < 0) return null;
        return aquariumModel.getRow(row).getAquariumName();
    }

    private void loadFish() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) {
            fishModel.setData(List.of());
            return;
        }

        try {
            fishModel.setData(facade.getAllFishForSelectedAquarium(aquariumName));
        } catch (OceanariumException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "blad", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterFishByText() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) return;

        try {
            String text = filterTextField.getText().trim();
            if (text.isEmpty()) {
                loadFish();
            } else {
                fishModel.setData(facade.filterFishByText(aquariumName, text));
            }
        } catch (OceanariumException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "blad", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterFishByState() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) return;

        try {
            Object selected = stateComboBox.getSelectedItem();
            if (selected instanceof FishCondition condition) {
                fishModel.setData(facade.filterFishByState(aquariumName, condition));
            } else {
                loadFish();
            }
        } catch (OceanariumException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "blad", JOptionPane.ERROR_MESSAGE);
        }
    }
}