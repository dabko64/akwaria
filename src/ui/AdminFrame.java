package ui;

import dto.AquariumTableRowDto;
import exceptions.OceanariumException;
import facade.OceanariumFacade;
import model.Fish;
import model.FishCondition;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private final OceanariumFacade facade;

    private final GenericTableModel<AquariumTableRowDto> aquariumModel;
    private final GenericTableModel<Fish> fishModel;

    private final JTable aquariumTable;
    private final JTable fishTable;

    private final JTextField filterTextField;
    private final JComboBox<Object> stateComboBox;

    public AdminFrame(OceanariumFacade facade) {
        this.facade = facade;

        setTitle("Oceanarium - Admin");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("/images/akwarium.jpg");
        backgroundPanel.setLayout(new BorderLayout(10, 10));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(backgroundPanel);

        aquariumModel = new GenericTableModel<>(
                facade.getAquariumTableRows(),
                List.of(
                        new ColumnDefinition<>("NAZWA AKWARIUM", AquariumTableRowDto::getAquariumName),
                        new ColumnDefinition<>("POJEMNOŚĆ", AquariumTableRowDto::getMaxCapacity),
                        new ColumnDefinition<>("OBCIĄŻENIE", AquariumTableRowDto::getCurrentLoad),
                        new ColumnDefinition<>("ZAPEŁNIENIE", a -> String.format("%.2f", a.getFillPercentage())),
                        new ColumnDefinition<>("LICZBA OCEN", AquariumTableRowDto::getRatingsCount),
                        new ColumnDefinition<>("ŚREDNIA OCEN", a -> String.format("%.2f", a.getAverageRating()))
                )
        );

        fishModel = new GenericTableModel<>(
                List.of(),
                List.of(
                        new ColumnDefinition<>("imię", Fish::getName),
                        new ColumnDefinition<>("gatunek", Fish::getSpecies),
                        new ColumnDefinition<>("stan zdrowia", Fish::getCondition),
                        new ColumnDefinition<>("wiek", Fish::getAge),
                        new ColumnDefinition<>("waga", Fish::getWeight),
                        new ColumnDefinition<>("pochodzenie", Fish::getOrigin)
                )
        );

        aquariumTable = new JTable(aquariumModel);
        fishTable = new JTable(fishModel);

        UIStyle.styleTable(aquariumTable);
        UIStyle.styleTable(fishTable);

        filterTextField = new JTextField();
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
        JButton addAquariumButton = new JButton("DODAJ AKWARIUM");
        JButton removeAquariumButton = new JButton("USUŃ AKWARIUM");
        JButton addFishButton = new JButton("DODAJ RYBĘ");
        JButton removeFishButton = new JButton("USUŃ RYBĘ");
        JButton addRatingButton = new JButton("DODAJ OCENĘ");
        JButton sortAquariumsButton = new JButton("SORTUJ WEDŁUG OBCIĄŻENIA");
        JButton exportCsvButton = new JButton("EKSPORT CSV");
        JButton importCsvButton = new JButton("IMPORT CSV");
        JButton saveBinaryButton = new JButton("ZAPIS BIN");
        JButton loadBinaryButton = new JButton("ODCZYT BIN");

        UIStyle.styleButton(addAquariumButton, UIStyle.TURQUOISE, Color.WHITE);
        UIStyle.styleButton(removeAquariumButton, UIStyle.DARK_GRAY, Color.WHITE);
        UIStyle.styleButton(addFishButton, UIStyle.CORAL, Color.WHITE);
        UIStyle.styleButton(removeFishButton, UIStyle.DARK_GRAY, Color.WHITE);
        UIStyle.styleButton(addRatingButton, UIStyle.TURQUOISE, Color.WHITE);
        UIStyle.styleButton(sortAquariumsButton, UIStyle.LIGHT_MINT, Color.BLACK);
        UIStyle.styleButton(exportCsvButton, UIStyle.TURQUOISE, Color.WHITE);
        UIStyle.styleButton(importCsvButton, UIStyle.CORAL, Color.WHITE);
        UIStyle.styleButton(saveBinaryButton, UIStyle.LIGHT_MINT, Color.BLACK);
        UIStyle.styleButton(loadBinaryButton, UIStyle.DARK_GRAY, Color.WHITE);

        addAquariumButton.addActionListener(e -> addAquarium());
        removeAquariumButton.addActionListener(e -> removeAquarium());
        addFishButton.addActionListener(e -> addFish());
        removeFishButton.addActionListener(e -> removeFish());
        addRatingButton.addActionListener(e -> addRating());
        sortAquariumsButton.addActionListener(e -> sortAquariums());
        exportCsvButton.addActionListener(e -> exportCsv());
        importCsvButton.addActionListener(e -> importCsv());
        saveBinaryButton.addActionListener(e -> saveBinary());
        loadBinaryButton.addActionListener(e -> loadBinary());

        topPanel.add(addAquariumButton);
        topPanel.add(removeAquariumButton);
        topPanel.add(addFishButton);
        topPanel.add(removeFishButton);
        topPanel.add(addRatingButton);
        topPanel.add(sortAquariumsButton);
        topPanel.add(exportCsvButton);
        topPanel.add(importCsvButton);
        topPanel.add(saveBinaryButton);
        topPanel.add(loadBinaryButton);

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
                loadFishForSelectedAquarium();
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

    private Fish getSelectedFish() {
        int row = fishTable.getSelectedRow();
        if (row < 0) return null;
        return fishModel.getRow(row);
    }

    private void loadFishForSelectedAquarium() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) {
            fishModel.setData(List.of());
            return;
        }

        try {
            fishModel.setData(facade.getAllFishForSelectedAquarium(aquariumName));
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void addAquarium() {
        try {
            String name = JOptionPane.showInputDialog(this, "podaj nazwę akwarium");
            if (name == null) return;

            String capacityText = JOptionPane.showInputDialog(this, "podaj pojemność akwarium");
            if (capacityText == null) return;

            int capacity = Integer.parseInt(capacityText);
            facade.addAquarium(name, capacity);
            refreshAquariums();
        } catch (NumberFormatException ex) {
            showErrorMessage("podaj poprawną liczbę");
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void removeAquarium() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) {
            showErrorMessage("należy wybrać akwarium");
            return;
        }

        try {
            facade.removeAquarium(aquariumName);
            refreshAquariums();
            fishModel.setData(List.of());
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void addFish() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) {
            showErrorMessage("należy wybrać akwarium");
            return;
        }

        try {
            String name = JOptionPane.showInputDialog(this, "podaj imię ryby");
            if (name == null) return;

            String species = JOptionPane.showInputDialog(this, "podaj gatunek ryby");
            if (species == null) return;

            FishCondition condition = (FishCondition) JOptionPane.showInputDialog(
                    this,
                    "wybierz stan ryby",
                    "stan ryby",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    FishCondition.values(),
                    FishCondition.ZDROWA
            );
            if (condition == null) return;

            String ageText = JOptionPane.showInputDialog(this, "podaj wiek");
            if (ageText == null) return;
            int age = Integer.parseInt(ageText);

            String weightText = JOptionPane.showInputDialog(this, "podaj wagę");
            if (weightText == null) return;
            double weight = Double.parseDouble(weightText);

            String origin = JOptionPane.showInputDialog(this, "podaj pochodzenie");
            if (origin == null) return;

            Fish fish = new Fish(name, species, condition, age, weight, origin);
            facade.addFish(aquariumName, fish);

            loadFishForSelectedAquarium();
            refreshAquariums();
        } catch (NumberFormatException ex) {
            showErrorMessage("zły format liczby");
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void removeFish() {
        String aquariumName = getSelectedAquariumName();
        Fish fish = getSelectedFish();

        if (aquariumName == null || fish == null) {
            showErrorMessage("należy wybrać akwarium oraz rybę");
            return;
        }

        try {
            facade.removeFish(aquariumName, fish);
            loadFishForSelectedAquarium();
            refreshAquariums();
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void addRating() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) {
            showErrorMessage("należy wybrać akwarium");
            return;
        }

        try {
            String valueText = JOptionPane.showInputDialog(this, "podaj ocenę 0-5");
            if (valueText == null) return;
            int value = Integer.parseInt(valueText);

            String comment = JOptionPane.showInputDialog(this, "podaj komentarz");
            if (comment == null) return;

            facade.addRating(aquariumName, value, comment);
            refreshAquariums();
        } catch (NumberFormatException ex) {
            showErrorMessage("podaj poprawną liczbę");
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void sortAquariums() {
        List<AquariumTableRowDto> list = facade.getAquariumTableRows();
        facade.sortAquariumsByCurrentLoad(list);
        aquariumModel.setData(list);
    }

    private void filterFishByText() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) return;

        try {
            String text = filterTextField.getText().trim();
            if (text.isEmpty()) {
                loadFishForSelectedAquarium();
            } else {
                fishModel.setData(facade.filterFishByText(aquariumName, text));
            }
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void filterFishByState() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) return;

        Object selected = stateComboBox.getSelectedItem();

        try {
            if (selected instanceof FishCondition condition) {
                fishModel.setData(facade.filterFishByState(aquariumName, condition));
            } else {
                loadFishForSelectedAquarium();
            }
        } catch (OceanariumException ex) {
            showError(ex);
        }
    }

    private void refreshAquariums() {
        aquariumModel.setData(facade.getAquariumTableRows());
    }

    private void showError(OceanariumException ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "błąd", JOptionPane.ERROR_MESSAGE);
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "błąd", JOptionPane.ERROR_MESSAGE);
    }
    private void exportCsv() {
        String aquariumName = getSelectedAquariumName();
        if (aquariumName == null) {
            showErrorMessage("należy wybrać akwarium");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz plik CSV");

        int result = fileChooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".csv")) {
                filePath += ".csv";
            }

            facade.exportSelectedAquariumToCsv(aquariumName, filePath);
            JOptionPane.showMessageDialog(this, "Wyeksportowano CSV.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
        }
    }

    private void importCsv() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wybierz plik CSV");

        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            facade.importAquariumFromCsv(filePath);
            refreshAquariums();
            JOptionPane.showMessageDialog(this, "Zaimportowano CSV.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
        }
    }

    private void saveBinary() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Zapisz plik binarny");

        int result = fileChooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".bin")) {
                filePath += ".bin";
            }

            facade.saveAquariumsToBinary(filePath);
            JOptionPane.showMessageDialog(this, "Zapisano plik binarny.", "Sukces", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
        }
    }

    private void loadBinary() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Wczytaj plik binarny");

        int result = fileChooser.showOpenDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            facade.loadAquariumsFromBinary(filePath);
            JOptionPane.showMessageDialog(
                    this,
                    "Odczyt pliku binarnego zakończony.\nDane zostały wczytane do pamięci obiektów.",
                    "Sukces",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
        }
    }
}