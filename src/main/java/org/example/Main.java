package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static List<Card> cards = new ArrayList<>();
    static Card[] fieldCards = new Card[5];
    private static int gameCount = 1;
    private static int bet = 0;

    public static void main(String[] args) {
        var br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("ゲームを始めます!\n");
        var player = new Player();
        player.setName(br);

        var cpu = new Player();

        while ((player.getMoney() > 0 && player.getMoney() < 1000) && (cpu.getMoney() > 0 && cpu.getMoney() < 1000)) {
            System.out.println("\n\n＜第" + gameCount + "回戦＞");

            setCards();

            player.setCard(dealCard(), dealCard());
            cpu.setCard(dealCard(), dealCard());

            for (int i = 0; i < 3; i++) {
                fieldCards[i] = dealCard();
            }

            System.out.println("\n【場のカード】");
            showCards(fieldCards);

            System.out.println("\n【" + player.getName() + "の手札】");
            showCards(player.getCards());

            while (true) {
                System.out.print("\n\nいくら掛けますか(1～100G)\n＞");
                try {
                    String betStr = br.readLine();
                    bet = Integer.parseInt(betStr);
                    if (bet > 0 && bet < 101) {
                        break;
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("\n\n0～100の数字を入力してください。");
                }
            }

            System.out.println("\n【場のカード】");
            fieldCards[3] = dealCard();
            showCards(fieldCards);

            while (true) {
                System.out.print("\n\n掛け金を増やしますか？\n1:はい\n2:いいえ\n＞");
                try {
                    String yesStr = br.readLine();
                    int yesNo = Integer.parseInt(yesStr);

                    if (yesNo == 1) {
                        System.out.print("\nいくら増やしますか？(1～80G)\n＞");
                        try {
                            String plusBetStr = br.readLine();
                            int plusBet = Integer.parseInt(plusBetStr);

                            if (plusBet > 0 && plusBet < 81) {
                                bet += plusBet;
                                break;
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            System.out.println("\n\n1～80の数値を入力してください。");
                        }
                    } else if (yesNo == 2) {
                        break;
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("\n\n1か2を入力してください。");
                }
            }
            System.out.println("勝負!");
            fieldCards[4]=dealCard();
            System.out.println("\n【場のカード】");
            showCards(fieldCards);
            System.out.println("【"+player.getName()+"の手札】");
            showCards(player.getCards());
            System.out.println("【相手の手札】");
            showCards(cpu.getCards());

            System.out.println("\n\n【結果】");

            var hand=new Hand();
            hand.decideHand(player.getCards(), fieldCards);
            break;
        }
    }

    static void setCards() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                var card = new Card(i, j);
                cards.add(card);
            }
        }
    }

    static Card dealCard() {
        while (true) {
            int randNum = new Random().nextInt(52);
            if (!cards.get(randNum).isDealed()) {
                cards.get(randNum).deal();
                return cards.get(randNum);
            }
        }
    }

    static void showCards(Card[] cards) {
        try {
            for (Card card : cards) {
                card.displayCard();
            }
        } catch (Exception e) {
            System.out.print("");
        } finally {
            System.out.println("");
        }
    }
}
