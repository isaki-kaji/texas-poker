package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class Player {
    private String name="cpu";
    public int money = 100;
    private Card[] Cards=new Card[2];
    public int drawNum =0;

    public String getName() {
        return name;
    }

    public void setName(BufferedReader br) {
        while (true) {
            System.out.print("名前を決めてください＞");
            try {
                String name = br.readLine();

                if (name.length() > 1) {
                    this.name=name;
                    break;
                } else {
                    System.out.println("\n\n名前は2文字で入力してください。");
                }

            } catch (IOException e) {
                System.out.println("入力が適切ではありません。");
            }
        }
    }

    public void setCard(Card a,Card b) {
        this.Cards[0]=a;
        this.Cards[1]=b;
    }

    public Card[] getCards() {
        return Cards;
    }

    public void setName(String name) {
        this.name = name;
    }
}

