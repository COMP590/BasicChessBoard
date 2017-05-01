package com.example.snpc.basicchessboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.snpc.basicchessboard.Constants.BLANK_BLACK;
import static com.example.snpc.basicchessboard.Constants.BLANK_WHITE;

public class ChessBoard extends AppCompatActivity {

    private boolean turn = true;
    private boolean white = true;
    private int holdP = BLANK_WHITE;
    private int holdX = -1;
    private int holdY = -1;
    private int[][] board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_board);

        board = new int[8][8];
        board[0][0] = 0b01110;
        board[0][1] = 0b10011;
        board[0][2] = 0b10110;
        board[0][3] = 0b11011;
        board[0][4] = 0b11110;
        board[0][5] = 0b10111;
        board[0][6] = 0b10010;
        board[0][7] = 0b01111;
        board[7][0] = 0b01101;
        board[7][1] = 0b10000;
        board[7][2] = 0b10101;
        board[7][3] = 0b11000;
        board[7][4] = 0b11101;
        board[7][5] = 0b10100;
        board[7][6] = 0b10001;
        board[7][7] = 0b01100;
        for(int row = 1; row < 7; row++){       //Board setup
            for(int col = 0; col < 8; col++){
                if(row == 1){                   //Black Pawns
                    if(col%2 == 1){
                        board[row][col] = 0b01010;
                    }else{
                        board[row][col] = 0b01011;
                    }
                }else if (row == 6){            //White Pawns
                    if(col%2 == 1){
                        board[row][col] = 0b01001;
                    }else{
                        board[row][col] = 0b01000;
                    }
                }else if(row%2 == 1){           //3rd and 5th blank row
                    if(col%2 == 1){
                        board[row][col] = BLANK_BLACK;
                    }else{
                        board[row][col] = BLANK_WHITE;
                    }
                }else{                          //4rd and 6th blank row
                    if(col%2 == 1){
                        board[row][col] = BLANK_WHITE;
                    }else{
                        board[row][col] = BLANK_BLACK;
                    }

                }
            }
        }
    }

    private void moveXY(int row, int col){
        if(turn){
            if((row != -1)&&(col != -1)){
                if((holdP == 0b01001) || (holdP == 0b01000)){       //White Pawn
                    emptyHold();
                }else if((holdP == 0b01011) || (holdP == 0b01010)){ //Black Pawn
                    emptyHold();
                }else if((holdP == 0b01111) || (holdP == 0b01110) || (holdP == 0b01101) || (holdP == 0b01100)){     //Rook
                    if((holdX == row) || (holdY == col)){
                        if((holdX == row)  && (holdY == col)){
                            emptyHold();
                            TextView error = (TextView) findViewById(R.id.error);
                            error.setText("Released Piece");
                        }else{
                            if(holdX == row){
                                boolean valid = true;
                                if(holdY > col){
                                    for(int y = holdY-1; y > col; y--){
                                        if ((board[holdX][y] != BLANK_WHITE) || (board[holdX][y] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Rook Move");
                                    }
                                }else{
                                    for(int y = holdY+1; y < col; y++){
                                        if ((board[holdX][y] != BLANK_WHITE) || (board[holdX][y] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Rook Move");
                                    }
                                }
                            }else{
                                boolean valid = true;
                                if(holdX > row){
                                    for(int x = holdX-1; x > row; x--){
                                        if ((board[x][holdY] != BLANK_WHITE) || (board[x][holdY] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Rook Move");
                                    }
                                }else{
                                    for(int x = holdX+1; x < row; x++){
                                        if ((board[x][holdY] != BLANK_WHITE) || (board[x][holdY] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Rook Move");
                                    }
                                }
                            }
                        }
                    }else{
                        emptyHold();
                        TextView error = (TextView) findViewById(R.id.error);
                        error.setText("Invalid Rook Move");
                    }
                }else if ((holdP == 0b10011) || (holdP == 0b10010) || (holdP == 0b10001) || (holdP == 0b10000)){    //Knight
                    if((Math.abs(holdX - row) == 2) && (Math.abs(holdY - col) == 1)){
                        onValidMove(row,col);
                    }else if((Math.abs(holdX - row) == 1) && (Math.abs(holdY - col) == 2)){
                        onValidMove(row,col);
                    }else{
                        emptyHold();
                        TextView error = (TextView) findViewById(R.id.error);
                        error.setText("Invalid Knight Move");
                    }
                }else if((holdP == 0b10111) || (holdP == 0b10110) || (holdP == 0b10101) || (holdP == 0b10100)){     //Bishop
                    if(Math.abs(holdX - row) == Math.abs(holdY - col)){
                        if((holdX == row)  && (holdY == col)){
                            emptyHold();
                            TextView error = (TextView) findViewById(R.id.error);
                            error.setText("Released Piece");
                        }else{
                            if(holdX > row){
                                boolean valid = true;
                                for(int x = holdX-1; x > row; x--){
                                    if(holdY > col){
                                        for(int y = holdY-1; y > col; y--){
                                            if ((board[x][y] != BLANK_WHITE) || (board[x][y] == BLANK_BLACK)){
                                                valid = false;
                                            }
                                        }
                                    }else{
                                        for(int y = holdY+1; y > col; y++){
                                            if ((board[x][y] != BLANK_WHITE) || (board[x][y] == BLANK_BLACK)){
                                                valid = false;
                                            }
                                        }
                                    }
                                }
                                if(valid){
                                    onValidMove(row,col);
                                }else{
                                    emptyHold();
                                    TextView error = (TextView) findViewById(R.id.error);
                                    error.setText("Invalid Bishop Move");
                                }
                            }else{
                                boolean valid = true;
                                for(int x = holdX+1; x > row; x++){
                                    if(holdY > col){
                                        for(int y = holdY-1; y > col; y--){
                                            if ((board[x][y] != BLANK_WHITE) || (board[x][y] == BLANK_BLACK)){
                                                valid = false;
                                            }
                                        }
                                    }else{
                                        for(int y = holdY+1; y > col; y++){
                                            if ((board[x][y] != BLANK_WHITE) || (board[x][y] == BLANK_BLACK)){
                                                valid = false;
                                            }
                                        }
                                    }
                                }
                                if(valid){
                                    onValidMove(row,col);
                                }else{
                                    emptyHold();
                                    TextView error = (TextView) findViewById(R.id.error);
                                    error.setText("Invalid Bishop Move");
                                }
                            }
                        }
                    }else{
                        emptyHold();
                        TextView error = (TextView) findViewById(R.id.error);
                        error.setText("Invalid Bishop Move");
                    }
                }else if((holdP == 0b11011) || (holdP == 0b11010) || (holdP == 0b11001) || (holdP == 0b11000)){     //Queen
                    if(Math.abs(holdX - row) == Math.abs(holdY - col)){

                    }else if((holdX == row) || (holdY == col)){
                        if((holdX == row)  && (holdY == col)){
                            emptyHold();
                            TextView error = (TextView) findViewById(R.id.error);
                            error.setText("Released Piece");
                        }else{
                            if(holdX == row){
                                boolean valid = true;
                                if(holdY > col){
                                    for(int y = holdY-1; y > col; y--){
                                        if ((board[holdX][y] != BLANK_WHITE) || (board[holdX][y] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Queen Move");
                                    }
                                }else{
                                    for(int y = holdY+1; y < col; y++){
                                        if ((board[holdX][y] != BLANK_WHITE) || (board[holdX][y] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Queen Move");
                                    }
                                }
                            }else{
                                boolean valid = true;
                                if(holdX > row){
                                    for(int x = holdX-1; x > row; x--){
                                        if ((board[x][holdY] != BLANK_WHITE) || (board[x][holdY] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Queen Move");
                                    }
                                }else{
                                    for(int x = holdX+1; x < row; x++){
                                        if ((board[x][holdY] != BLANK_WHITE) || (board[x][holdY] == BLANK_BLACK)){
                                            valid = false;
                                        }
                                    }
                                    if(valid){
                                        onValidMove(row,col);
                                    }else{
                                        emptyHold();
                                        TextView error = (TextView) findViewById(R.id.error);
                                        error.setText("Invalid Queen Move");
                                    }
                                }
                            }
                        }
                    }else{
                        emptyHold();
                        TextView error = (TextView) findViewById(R.id.error);
                        error.setText("Invalid Queen Move");
                    }
                }else {          //King
                    if ((Math.abs(holdX - row) < 2) && (Math.abs(holdY - col) < 2)) {
                        onValidMove(row,col);
                    } else {
                        emptyHold();
                        TextView error = (TextView) findViewById(R.id.error);
                        error.setText("Invalid King Move");
                    }
                }
            }else{
                emptyHold();
                TextView error = (TextView) findViewById(R.id.error);
                error.setText("That Square doesn't Exist");
            }
        }else{
            emptyHold();
            TextView error = (TextView) findViewById(R.id.error);
            error.setText("It's Not Your Turn");
        }
    }

    private void onValidMove(int row, int col){
        board[row][col] = (holdP&0b11110)+(board[row][col]&BLANK_BLACK);
        updateBoard(row,col,holdP);
        if((holdP & BLANK_BLACK) == BLANK_BLACK){
            board[holdX][holdY] = BLANK_BLACK;
            updateBoard(holdX,holdY,BLANK_BLACK);
        }else{
            board[holdX][holdY] = BLANK_WHITE;
            updateBoard(holdX,holdY,BLANK_WHITE);
        }
        emptyHold();
    }

    private void updateBoard(int row, int col, int piece){
        Button square;
        if(row == 0){                                       //Find Button equivilent of back end
            if(col == 0){
                square = (Button) findViewById(R.id.a8);
            }else if(col == 1){
                square = (Button) findViewById(R.id.b8);
            }else if(col == 2){
                square = (Button) findViewById(R.id.c8);
            }else if(col == 3){
                square = (Button) findViewById(R.id.d8);
            }else if(col == 4){
                square = (Button) findViewById(R.id.e8);
            }else if(col == 5){
                square = (Button) findViewById(R.id.f8);
            }else if(col == 6){
                square = (Button) findViewById(R.id.g8);
            }else{
                square = (Button) findViewById(R.id.h8);
            }
        }else if(row == 1) {
            if (col == 0) {
                square = (Button) findViewById(R.id.a7);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b7);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c7);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d7);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e7);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f7);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g7);
            } else {
                square = (Button) findViewById(R.id.h7);
            }
        }else if(row == 2) {
            if (col == 0) {
                square = (Button) findViewById(R.id.a6);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b6);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c6);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d6);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e6);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f6);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g6);
            } else {
                square = (Button) findViewById(R.id.h6);
            }
        }else if(row == 3) {
            if (col == 0) {
                square = (Button) findViewById(R.id.a5);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b5);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c5);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d5);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e5);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f5);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g5);
            } else {
                square = (Button) findViewById(R.id.h5);
            }
        }else if(row == 4) {
            if (col == 0) {
                square = (Button) findViewById(R.id.a4);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b4);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c4);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d4);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e4);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f4);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g4);
            } else {
                square = (Button) findViewById(R.id.h4);
            }
        }else if(row == 5) {
            if (col == 0) {
                square = (Button) findViewById(R.id.a3);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b3);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c3);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d3);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e3);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f3);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g3);
            } else {
                square = (Button) findViewById(R.id.h3);
            }
        }else if(row == 6) {
            if (col == 0) {
                square = (Button) findViewById(R.id.a2);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b2);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c2);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d2);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e2);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f2);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g2);
            } else {
                square = (Button) findViewById(R.id.h2);
            }
        }else{
            if (col == 0) {
                square = (Button) findViewById(R.id.a1);
            } else if (col == 1) {
                square = (Button) findViewById(R.id.b1);
            } else if (col == 2) {
                square = (Button) findViewById(R.id.c1);
            } else if (col == 3) {
                square = (Button) findViewById(R.id.d1);
            } else if (col == 4) {
                square = (Button) findViewById(R.id.e1);
            } else if (col == 5) {
                square = (Button) findViewById(R.id.f1);
            } else if (col == 6) {
                square = (Button) findViewById(R.id.g1);
            } else {
                square = (Button) findViewById(R.id.h1);
            }
        }

        if (piece == BLANK_WHITE){                                 //Print correct square
            square.setBackgroundResource(R.drawable.p00000);
        }else if (piece == BLANK_BLACK){
            square.setBackgroundResource(R.drawable.p00001);
        }else if (piece == 0b01000){
            square.setBackgroundResource(R.drawable.p01000);
        }else if (piece == 0b01001){
            square.setBackgroundResource(R.drawable.p01001);
        }else if (piece == 0b01010){
            square.setBackgroundResource(R.drawable.p01010);
        }else if (piece == 0b01011){
            square.setBackgroundResource(R.drawable.p01011);
        }else if (piece == 0b01100){
            square.setBackgroundResource(R.drawable.p01100);
        }else if (piece == 0b01101){
            square.setBackgroundResource(R.drawable.p01101);
        }else if (piece == 0b01110){
            square.setBackgroundResource(R.drawable.p01110);
        }else if (piece == 0b01111){
            square.setBackgroundResource(R.drawable.p01111);
        }else if (piece == 0b10000){
            square.setBackgroundResource(R.drawable.p10000);
        }else if (piece == 0b10001){
            square.setBackgroundResource(R.drawable.p10001);
        }else if (piece == 0b10010){
            square.setBackgroundResource(R.drawable.p10010);
        }else if (piece == 0b10011){
            square.setBackgroundResource(R.drawable.p10011);
        }else if (piece == 0b10100){
            square.setBackgroundResource(R.drawable.p10100);
        }else if (piece == 0b10101){
            square.setBackgroundResource(R.drawable.p10101);
        }else if (piece == 0b10110){
            square.setBackgroundResource(R.drawable.p10110);
        }else if (piece == 0b10111){
            square.setBackgroundResource(R.drawable.p10111);
        }else if (piece == 0b11000){
            square.setBackgroundResource(R.drawable.p11000);
        }else if (piece == 0b11001){
            square.setBackgroundResource(R.drawable.p11001);
        }else if (piece == 0b11010){
            square.setBackgroundResource(R.drawable.p11010);
        }else if (piece == 0b11011){
            square.setBackgroundResource(R.drawable.p11011);
        }else if (piece == 0b11100){
            square.setBackgroundResource(R.drawable.p11100);
        }else if (piece == 0b11101){
            square.setBackgroundResource(R.drawable.p11101);
        }else if (piece == 0b11110){
            square.setBackgroundResource(R.drawable.p11110);
        }else if (piece == 0b11111){
            square.setBackgroundResource(R.drawable.p11111);
        }
    }

    private void updateHold(int row, int col){
        if((board[row][col] == BLANK_WHITE) || (board[row][col] == BLANK_BLACK)){
            emptyHold();
        }else{
            holdX = row;
            holdY = col;
            holdP = board[row][col];
            TextView hold = (TextView) findViewById(R.id.hold);
            if(col == 0){
                hold.setText("Hold Piece: A" + (8 - row));
            }else if(col == 1){
                hold.setText("Hold Piece: B" + (8 - row));
            }else if(col == 2){
                hold.setText("Hold Piece: C" + (8 - row));
            }else if(col == 3){
                hold.setText("Hold Piece: D" + (8 - row));
            }else if(col == 4){
                hold.setText("Hold Piece: E" + (8 - row));
            }else if(col == 5){
                hold.setText("Hold Piece: F" + (8 - row));
            }else if(col == 6){
                hold.setText("Hold Piece: G" + (8 - row));
            }else{
                hold.setText("Hold Piece: H" + (8 - row));
            }
            TextView error = (TextView) findViewById(R.id.error);
            error.setText(" ");
        }
    }

    private void emptyHold(){
        holdP = BLANK_WHITE;
        holdX = -1;
        holdY = -1;

        TextView hold = (TextView) findViewById(R.id.hold);
        hold.setText("Hold Piece: None");
    }

    public void A8(View view) {
        int row = 0;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B8(View view) {
        int row = 0;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C8(View view) {
        int row = 0;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D8(View view) {
        int row = 0;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E8(View view) {
        int row = 0;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F8(View view) {
        int row = 0;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G8(View view) {
        int row = 0;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H8(View view) {
        int row = 0;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A7(View view) {
        int row = 1;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B7(View view) {
        int row = 1;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C7(View view) {
        int row = 1;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D7(View view) {
        int row = 1;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E7(View view) {
        int row = 1;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F7(View view) {
        int row = 1;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G7(View view) {
        int row = 1;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H7(View view) {
        int row = 1;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A6(View view) {
        int row = 2;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B6(View view) {
        int row = 2;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C6(View view) {
        int row = 2;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D6(View view) {
        int row = 2;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E6(View view) {
        int row = 2;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F6(View view) {
        int row = 2;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G6(View view) {
        int row = 2;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H6(View view) {
        int row = 2;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A5(View view) {
        int row = 3;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B5(View view) {
        int row = 3;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C5(View view) {
        int row = 3;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D5(View view) {
        int row = 3;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E5(View view) {
        int row = 3;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F5(View view) {
        int row = 3;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G5(View view) {
        int row = 3;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H5(View view) {
        int row = 3;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A4(View view) {
        int row = 4;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B4(View view) {
        int row = 4;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C4(View view) {
        int row = 4;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D4(View view) {
        int row = 4;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E4(View view) {
        int row = 4;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F4(View view) {
        int row = 4;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G4(View view) {
        int row = 4;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H4(View view) {
        int row = 4;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A3(View view) {
        int row = 5;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B3(View view) {
        int row = 5;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C3(View view) {
        int row = 5;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D3(View view) {
        int row = 5;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E3(View view) {
        int row = 5;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F3(View view) {
        int row = 5;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G3(View view) {
        int row = 5;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H3(View view) {
        int row = 5;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A2(View view) {
        int row = 6;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B2(View view) {
        int row = 6;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C2(View view) {
        int row = 6;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D2(View view) {
        int row = 6;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E2(View view) {
        int row = 6;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F2(View view) {
        int row = 6;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G2(View view) {
        int row = 6;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H2(View view) {
        int row = 6;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void A1(View view) {
        int row = 7;
        int col = 0;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void B1(View view) {
        int row = 7;
        int col = 1;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void C1(View view) {
        int row = 7;
        int col = 2;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void D1(View view) {
        int row = 7;
        int col = 3;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void E1(View view) {
        int row = 7;
        int col = 4;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void F1(View view) {
        int row = 7;
        int col = 5;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void G1(View view) {
        int row = 7;
        int col = 6;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
    public void H1(View view) {
        int row = 7;
        int col = 7;
        if(holdP != BLANK_WHITE){
            moveXY(row, col);
        }else{
            updateHold(row, col);
        }
    }
}
