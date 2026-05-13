package com.mycompany.zehranurozdogan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ZehraNurOzdoganGameFrame extends JFrame {

    private ZehraNurOzdoganGameController controller;

    private JLabel nextValueLabel;
    private JLabel nextColumnLabel;
    private JLabel statusLabel;
    private JLabel[][] cellLabels;

    private JButton nextStepButton;
    private JButton resetButton;

    public ZehraNurOzdoganGameFrame() {
        controller = new ZehraNurOzdoganGameController();

        setTitle("Drop Number Game - Zehra Nur Ozdogan");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        ekraniHazirla();
        tahtayiYenile();
    }

    private void ekraniHazirla() {
        JPanel topPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nextValueLabel = new JLabel("", SwingConstants.CENTER);
        nextValueLabel.setFont(new Font("Arial", Font.BOLD, 22));

        nextColumnLabel = new JLabel("", SwingConstants.CENTER);
        nextColumnLabel.setFont(new Font("Arial", Font.BOLD, 20));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JPanel buttonPanelTop = new JPanel(new GridLayout(1, 2, 10, 10));

        nextStepButton = new JButton("Next Step");
        nextStepButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextStepButton.addActionListener(e -> {
            controller.sonrakiAdim();
            tahtayiYenile();

            // Hamleler bittiyse mesaj göster
            if (!controller.hasNextMove()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Game Over",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                );
                nextStepButton.setEnabled(false);
            }

            // Tahta tamamen doluysa yine oyun biter
            if (controller.isGameOver()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Game Over",
                        "Game Finished",
                        JOptionPane.INFORMATION_MESSAGE
                );
                nextStepButton.setEnabled(false);
            }
        });

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.addActionListener(e -> {
            controller.oyunuSifirla();
            nextStepButton.setEnabled(true);
            tahtayiYenile();
        });

        buttonPanelTop.add(nextStepButton);
        buttonPanelTop.add(resetButton);

        topPanel.add(nextValueLabel);
        topPanel.add(nextColumnLabel);
        topPanel.add(statusLabel);
        topPanel.add(buttonPanelTop);

        add(topPanel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(
                ZehraNurOzdoganMultiLinkedList.ROWS,
                ZehraNurOzdoganMultiLinkedList.COLS,
                5, 5
        ));

        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cellLabels = new JLabel[ZehraNurOzdoganMultiLinkedList.ROWS][ZehraNurOzdoganMultiLinkedList.COLS];

        for (int row = 0; row < ZehraNurOzdoganMultiLinkedList.ROWS; row++) {
            for (int col = 0; col < ZehraNurOzdoganMultiLinkedList.COLS; col++) {
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground(new Color(240, 240, 240));
                label.setFont(new Font("Arial", Font.BOLD, 22));
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                cellLabels[row][col] = label;
                boardPanel.add(label);
            }
        }

        add(boardPanel, BorderLayout.CENTER);
    }

    private void tahtayiYenile() {
        if (controller.hasNextMove()) {
            nextValueLabel.setText("Next Value: " + controller.getNextValue());
            nextColumnLabel.setText("Next Column: " + (controller.getNextColumn() + 1));
        } else {
            nextValueLabel.setText("Next Value: -");
            nextColumnLabel.setText("Next Column: -");
        }

        statusLabel.setText(controller.getStatusMessage());

        for (int row = 0; row < ZehraNurOzdoganMultiLinkedList.ROWS; row++) {
            for (int col = 0; col < ZehraNurOzdoganMultiLinkedList.COLS; col++) {
                int value = controller.getValueAt(row, col);

                if (value == 0) {
                    cellLabels[row][col].setText("");
                    cellLabels[row][col].setBackground(new Color(240, 240, 240));
                } else {
                    cellLabels[row][col].setText(String.valueOf(value));
                    cellLabels[row][col].setBackground(degereGoreRenk(value));
                }
            }
        }
    }

    private Color degereGoreRenk(int value) {
        switch (value) {
            case 2:
                return new Color(173, 216, 230);
            case 4:
                return new Color(144, 238, 144);
            case 8:
                return new Color(255, 255, 153);
            case 16:
                return new Color(255, 204, 153);
            case 32:
                return new Color(255, 153, 204);
            case 64:
                return new Color(204, 153, 255);
            case 128:
                return new Color(255, 102, 102);
            case 256:
                return new Color(153, 102, 255);
            default:
                return new Color(200, 200, 200);
        }
    }
}