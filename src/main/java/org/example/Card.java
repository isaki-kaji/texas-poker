package org.example;

public class Card {
    private int mark;
    private int number;
    private boolean isDealed = false;

    public Card(int mark,int number) {
        this.mark=mark;
        this.number=number;
    }

    public void displayCard() {
        String[] marks = { "♠", "♥", "♦", "♣" };

        if (number < 9) {
            System.out.print(marks[mark] + (number + 2)+" ");
        } else {
            switch (number) {
                case 9:
                    System.out.print(marks[mark] + "J ");
                    break;
                case 10:
                    System.out.print(marks[mark] + "Q ");
                    break;
                case 11:
                    System.out.print(marks[mark] + "K ");
                    break;
                case 12:
                    System.out.print(marks[mark] + "A ");
                    break;
                default:
                    System.out.println("");
            }
        }
    };

    public int getMarks() {
        return mark;
    }

    public void setMarks(int mark) {
        this.mark = mark;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isDealed() {
        return isDealed;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public void deal() {
        this.isDealed = true;
    }

}
