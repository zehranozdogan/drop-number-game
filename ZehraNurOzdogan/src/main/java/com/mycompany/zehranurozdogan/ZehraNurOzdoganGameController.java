package com.mycompany.zehranurozdogan;

public class ZehraNurOzdoganGameController {

   
    private ZehraNurOzdoganMultiLinkedList tahta;

    // ilk hamle
    private HamleNode hamlelerBasi;

    
    private HamleNode simdikiHamle;

    private int adimSayisi;
    private String durumMesaji;

    // her hamlenin deger ve sutunu 
    private class HamleNode {
        int deger;
        int sutun;
        HamleNode next;

        public HamleNode(int deger, int sutun) {
            this.deger = deger;
            this.sutun = sutun;
            this.next = null;
        }
    }

    public ZehraNurOzdoganGameController() {
        oyunuSifirla();
    }

    
    public void oyunuSifirla() {
        tahta = new ZehraNurOzdoganMultiLinkedList();
        hamlelerBasi = null;
        simdikiHamle = null;
        adimSayisi = 0;
        hamleler();
        durumMesaji = "Game started.";
    }

    
    private void hamleEkle(int deger, int sutun) {
        HamleNode yeniHamle = new HamleNode(deger, sutun);

        if (hamlelerBasi == null) {
            hamlelerBasi = yeniHamle;
            simdikiHamle = hamlelerBasi;
        } else {
            HamleNode temp = hamlelerBasi;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = yeniHamle;
        }
    }

    
    private void hamleler() {
        hamleEkle(2, 0);
        hamleEkle(2, 3);
        hamleEkle(4, 1);
        hamleEkle(2, 2);
        hamleEkle(4, 4);
        hamleEkle(2, 1);
        hamleEkle(4, 4);
        hamleEkle(8, 0);

        hamleEkle(8, 0);
        hamleEkle(32, 1);
        hamleEkle(2, 2);
        hamleEkle(64, 2);
        hamleEkle(16, 3);
        hamleEkle(64, 1);
        hamleEkle(32, 2);
        hamleEkle(16, 0);
        hamleEkle(16, 4);
        hamleEkle(32, 2);
        hamleEkle(64, 1);
        hamleEkle(8, 3);
        hamleEkle(4, 3);

        hamleEkle(2, 3);
        hamleEkle(2, 3);
        hamleEkle(2, 1);
        hamleEkle(64, 2);
        hamleEkle(32, 2);
        hamleEkle(16, 2);
        hamleEkle(8, 2);
        hamleEkle(8, 2);
        hamleEkle(4, 1);
        hamleEkle(8, 1);
    }

    // siradaki hamle
    public boolean sonrakiAdim() {
        if (simdikiHamle == null) {
            durumMesaji = "Game Over";
            return false;
        }

        boolean basarili = tahta.sayiDusur(simdikiHamle.sutun, simdikiHamle.deger);

        if (basarili) {
            adimSayisi++;

            durumMesaji = "Step " + adimSayisi
                    + " -> Value: " + simdikiHamle.deger
                    + ", Column: " + (simdikiHamle.sutun + 1);

            tahta.tahtayiYazdir();
            simdikiHamle = simdikiHamle.next;

            if (simdikiHamle == null) {
                durumMesaji = "Game Over";
            }

            return true;
        } else {
            durumMesaji = "Column is full.";
            return false;
        }
    }

    public int getValueAt(int row, int col) {
        return tahta.getDegerAt(row, col);
    }

    public boolean isGameOver() {
        return tahta.oyunBittiMi();
    }

    public String getStatusMessage() {
        return durumMesaji;
    }

    public int getStepCount() {
        return adimSayisi;
    }

    public int getNextValue() {
        if (simdikiHamle == null) {
            return -1;
        }
        return simdikiHamle.deger;
    }

    public int getNextColumn() {
        if (simdikiHamle == null) {
            return -1;
        }
        return simdikiHamle.sutun;
    }

    public boolean hasNextMove() {
        return simdikiHamle != null;
    }
}