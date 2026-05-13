package com.mycompany.zehranurozdogan;

public class ZehraNurOzdoganMultiLinkedList {

    public static final int ROWS = 7;
    public static final int COLS = 5;

    // listenin baslangic dugumu 
    private ZehraNurOzdoganTileNode head;

    public ZehraNurOzdoganMultiLinkedList() {
        basliklariOlustur();
    }

    // satir ve sutun basliklari
    private void basliklariOlustur() {
        head = new ZehraNurOzdoganTileNode(-1, -1, -1);

        ZehraNurOzdoganTileNode temp = head;

        for (int i = 0; i < ROWS; i++) {
            temp.down = new ZehraNurOzdoganTileNode(-1, i, -1);
            temp = temp.down;
        }

        temp = head;

        for (int j = 0; j < COLS; j++) {
            temp.right = new ZehraNurOzdoganTileNode(-1, -1, j);
            temp = temp.right;
        }
    }

    // istenen satirin basligi 
    private ZehraNurOzdoganTileNode satirBasligiGetir(int row) {
        ZehraNurOzdoganTileNode temp = head.down;

        while (temp != null) {
            if (temp.row == row) {
                return temp;
            }
            temp = temp.down;
        }

        return null;
    }

    // istenen sutunun basligi
    private ZehraNurOzdoganTileNode sutunBasligiGetir(int col) {
        ZehraNurOzdoganTileNode temp = head.right;

        while (temp != null) {
            if (temp.col == col) {
                return temp;
            }
            temp = temp.right;
        }

        return null;
    }

    // belirli konumdaki tasi getirir
    public ZehraNurOzdoganTileNode tasGetir(int row, int col) {
        ZehraNurOzdoganTileNode satirBasligi = satirBasligiGetir(row);

        if (satirBasligi == null) {
            return null;
        }

        ZehraNurOzdoganTileNode temp = satirBasligi.right;

        while (temp != null) {
            if (temp.col == col) {
                return temp;
            }
            temp = temp.right;
        }

        return null;
    }

    // hucre bos mu kontrol
    public boolean hucreBosMu(int row, int col) {
        return tasGetir(row, col) == null;
    }

    // sutun dolu mu kontrol
    public boolean sutunDoluMu(int col) {
        return !hucreBosMu(0, col);
    }

    // yeni tasi ekler hem satir hem sutun listesine
    public void tasEkle(int row, int col, int value) {
        if (!hucreBosMu(row, col)) {
            return;
        }

        ZehraNurOzdoganTileNode yeniNode = new ZehraNurOzdoganTileNode(value, row, col); //yeni tas olustuuruyor

        ZehraNurOzdoganTileNode satirBasligi = satirBasligiGetir(row);
        ZehraNurOzdoganTileNode prev = satirBasligi;
        ZehraNurOzdoganTileNode curr = satirBasligi.right;

        while (curr != null && curr.col < col) {
            prev = curr;
            curr = curr.right;
        }

        yeniNode.right = curr;
        prev.right = yeniNode;

        ZehraNurOzdoganTileNode sutunBasligi = sutunBasligiGetir(col);
        prev = sutunBasligi;
        curr = sutunBasligi.down;

        while (curr != null && curr.row < row) {
            prev = curr;
            curr = curr.down;
        }

        yeniNode.down = curr;
        prev.down = yeniNode;
    }

    // tasi listeden siler 
    public void tasSil(int row, int col) {
        ZehraNurOzdoganTileNode satirBasligi = satirBasligiGetir(row);
        ZehraNurOzdoganTileNode prevSatir = satirBasligi;
        ZehraNurOzdoganTileNode currSatir = satirBasligi.right;

        while (currSatir != null && !(currSatir.row == row && currSatir.col == col)) {
            prevSatir = currSatir;
            currSatir = currSatir.right;
        }

        if (currSatir == null) {
            return;
        }

        prevSatir.right = currSatir.right;

        ZehraNurOzdoganTileNode sutunBasligi = sutunBasligiGetir(col);
        ZehraNurOzdoganTileNode prevSutun = sutunBasligi;
        ZehraNurOzdoganTileNode currSutun = sutunBasligi.down;

        while (currSutun != null && !(currSutun.row == row && currSutun.col == col)) {
            prevSutun = currSutun;
            currSutun = currSutun.down;
        }

        if (currSutun != null) {
            prevSutun.down = currSutun.down;
        }
    }

    // sutundaki en alttaki bos satiri bulr
    public int enAltBosSatir(int col) {
        for (int row = ROWS - 1; row >= 0; row--) {
            if (hucreBosMu(row, col)) {
                return row;
            }
        }
        return -1;
    }

    // sayiyi verilen sutune dusurur
    public boolean sayiDusur(int col, int value) {
        if (sutunDoluMu(col)) {
            return false;
        }

        int row = enAltBosSatir(col);
        tasEkle(row, col, value);

        // ayni sayi  varsa merge 
        while (true) {
            ZehraNurOzdoganTileNode current = tasGetir(row, col);
            ZehraNurOzdoganTileNode below = tasGetir(row + 1, col);

            if (current != null && below != null && current.value == below.value) {
                below.value = below.value * 2;
                tasSil(row, col);
                row = row + 1;
            } else {
                break;
            }
        }

        return true;
    }

    // ekranda gostermek icin hucredeki degeri verir
    public int getDegerAt(int row, int col) {
        ZehraNurOzdoganTileNode tas = tasGetir(row, col);

        if (tas == null) {
            return 0;
        }

        return tas.value;
    }

    // oyun bitti mi kontrolu 
    public boolean oyunBittiMi() {
        for (int col = 0; col < COLS; col++) {
            if (!sutunDoluMu(col)) {
                return false;
            }
        }
        return true;
    }

    // tahtayi yaziya cevir
    public String tahtaYazisi() {
        String sonuc = "";

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int value = getDegerAt(row, col);

                if (value == 0) {
                    sonuc += ".\t";
                } else {
                    sonuc += value + "\t";
                }
            }
            sonuc += "\n";
        }

        return sonuc;
    }

    // konsola tahtayi yazdirir
    public void tahtayiYazdir() {
        System.out.println(tahtaYazisi());
    }
}