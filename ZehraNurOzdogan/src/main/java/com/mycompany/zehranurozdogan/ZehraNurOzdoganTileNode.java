package com.mycompany.zehranurozdogan;

public class ZehraNurOzdoganTileNode {
    int value;
    int row;
    int col;

    ZehraNurOzdoganTileNode right;
    ZehraNurOzdoganTileNode down;

    public ZehraNurOzdoganTileNode(int value, int row, int col) {
        this.value = value; 
        this.row = row;
        this.col = col;
        this.right = null;
        this.down = null;
    }
}
