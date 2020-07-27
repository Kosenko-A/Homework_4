package ru.geekbrains.homeworks;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        start();
    }
    static void start() {
        final char playerSign = 'X';
        final char computerSign = 'Y';

        char[][] field = initField();
        String winnerName;

        drawField(field);

        do {
            // Ход игрока
            doPlayerMove(field, playerSign);
            // Перерисовка поля, чтобы увидеть поставленные фишки
            drawField(field);
            // Проверка на победу
            if (checkWin(field, playerSign)) {
                winnerName = "are winner Mr. Player";
                break;
            }
            //Проверка на ничью
            if (checkDraw(field, playerSign)) {
                winnerName = "have a Draw"; //Google сказал, что это ничья по-английски
                break;
            }


            // Ход компьютера
            doAIMove(field, computerSign);
            // Перерисовка поля, чтобы увидеть поставленные фишки
            drawField(field);

            // Проверка на победу
            if (checkWin(field, computerSign)) {
                winnerName = "are winner Mr. Computer";
                break;
            }
        } while (true);

        System.out.println("Sir, congratulations!");
        System.out.println("You " + winnerName);
    }

    static void doAIMove(char[][] field, char sign) {
        Random random = new Random();
        System.out.println("Computer's move...");
        // Вводим координаты Х, Y
        int xVal =0;
        int yVal = 0;
        //Искусственный интеллект, чтобы он мог блокировать ходы игрока (работает не очень)
        for (int i = 0; i<field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] != '-') {
                    xVal = i;
                    if (j < 2) {
                        yVal = j + random.nextInt(2);
                    } else {
                        yVal = j - random.nextInt(2);
                    }
                } else if ((field[i][j] != '-')&&(field[i+1][j] != '-')){
                    yVal = j;
                    if (i < 2) {
                        xVal = i + random.nextInt(2);
                    } else {
                        xVal = i - random.nextInt(2);
                    }
                }
            }
        }

        // Если значение по координатам занято, то делаем перегенерацию координат, пока не найдем свободные
        while (field[xVal][yVal] != '-') {
            xVal = random.nextInt(3);
            yVal = random.nextInt(3);
        }

        System.out.println(String.format("Computer's X-value: %s", xVal));
        System.out.println(String.format("Computer's Y-value: %s", yVal));

        field[xVal][yVal] = sign;
    }

    static void doPlayerMove(char[][] field, char sign) {
        Scanner scanner = new Scanner(System.in);
        int xVal=0;
        int yVal = 0;
        System.out.println("Sir, you move...");

        // Вводим координаты Х, Y
            while (true) {
                System.out.println("Please enter X-value [1-3]");
                xVal = scanner.nextInt() - 1;

                System.out.println("Please enter Y-value [1-3]");
                yVal = scanner.nextInt() - 1;

                if ((xVal>=0 && xVal<=2)&& (yVal>=0 && yVal<=2)){
                    break;
                }
            }
            //Проверка диапазона вводимых значений, чтобы не выйти за пределы массива

        // Если значение по координатам занято, то повторяем ввод координат, пока не найдем свободные
        while (field[xVal][yVal] != '-') {
            System.out.println(String.format("Field[%s][%s] is already occupied", xVal + 1, yVal + 1));
            System.out.println("Please enter X-value [1-3]");
            xVal = scanner.nextInt() - 1;

            System.out.println("Please enter Y-value [1-3]");
            yVal = scanner.nextInt() - 1;
        }

        field[xVal][yVal] = sign;
    }


    // Проверка победы
    static boolean checkWin(char[][] field, char sign) {
        // По горизонтали
        for (int i = 0; i < field.length; i++) {
            if (field[i][0] == sign && field[i][1] == sign && field[i][2] == sign) {
                return true;
            }
        }
        // По вертикали
        for (int j = 0; j<field.length; j++){
            if (field[0][j] == sign && field[1][j] == sign && field[2][j] == sign) {
                return true;
            }
        }
        // По диагонали
        if (field[0][0] == sign && field[1][1] == sign && field[2][2] == sign) {
            return true;
            }
        if (field[0][2] == sign && field[1][1] == sign && field[2][0] == sign){
            return true;
        }

        return false;
    }
    //Проверка ничьи
    static boolean checkDraw(char[][] field, char sign) {
        if (checkThePlace(field) && !checkWin(field,sign)){
            return true;
        }
        return false;
    }


    // Отрисока пока как матрицы
    static void drawField(char[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

    static char[][] initField() {
        return new char[][]{
                {'-', '-', '-'},
                {'-', '-', '-'},
                {'-', '-', '-'},
        };
    }
    static boolean checkThePlace (char[][]field){

        for (int i = 0; i<field.length; i++){
            for (int j = 0; j<field[i].length; j++){
                if (field [i][j]== '-'){
                    return false;
                }
            }
        }
        return true;
    }
}
