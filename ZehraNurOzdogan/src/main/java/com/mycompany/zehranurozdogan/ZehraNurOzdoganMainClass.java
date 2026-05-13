package com.mycompany.zehranurozdogan;

import javax.swing.SwingUtilities;

public class ZehraNurOzdoganMainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ZehraNurOzdoganGameFrame().setVisible(true);
        });
    }
}