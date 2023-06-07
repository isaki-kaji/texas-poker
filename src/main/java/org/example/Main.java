package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static String[] hands = { "ノーハンド", "ワンペア", "ツーペア", "スリーカード", "ストレート", "フラッシュ", "フルハウス", "フォーカード",
            "ストレートフラッシュ" };
    private static List<Card> cards = new ArrayList<>();
    static Card[] fieldCards = new Card[5];
    private static int gameCount = 1;

    public static void main(String[] args) {
        var br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("ゲームを始めます!\n");
        var player = new Player();
        player.setName(br);

        var cpu = new Player();

        while (player.money > 0 && player.money < 1000) {
            if(gameCount!=1) {
                System.out.print("\n次の対戦に進みます。\n＞");
                try {
                    br.readLine();
                } catch (IOException e) {
                    System.out.println("真面目にやってください。");
                }
            }
            int displayedBet = player.money;
            System.out.println("\n\n＜第" + gameCount + "回戦＞");

            cards=new ArrayList<>();
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

            int bet = 0;
            while (true) {
                System.out.print("\n\nいくら掛けますか(1～" + player.money + "G)\n＞");
                try {
                    String betStr = br.readLine();
                    bet = Integer.parseInt(betStr);
                    displayedBet -= bet;
                    if (bet > 0 && bet <= player.money) {
                        break;
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    System.out.println("\n\n0～" + player.money + "の数字を入力してください。");
                }
            }

            System.out.println("\n【場のカード】");
            fieldCards[3] = dealCard();
            showCards(fieldCards);
            System.out.println("\n【" + player.getName() + "の手札】");
            showCards(player.getCards());

            if (displayedBet > 0) {
                while (true) {
                    System.out.print("\n\n掛け金を増やしますか？\n1:はい\n2:いいえ\n＞");
                    try {
                        String yesStr = br.readLine();
                        int yesNo = Integer.parseInt(yesStr);

                        if (yesNo == 1) {
                            System.out.print("\nいくら増やしますか？(1～" + displayedBet/2 + "G)\n＞");
                            try {
                                String plusBetStr = br.readLine();
                                int plusBet = Integer.parseInt(plusBetStr);

                                if (plusBet > 0 && plusBet <= displayedBet/2) {
                                    bet += plusBet;
                                    player.money -= bet;
                                    break;
                                } else {
                                    throw new Exception();
                                }
                            } catch (Exception e) {
                                System.out.println("\n\n1～" + displayedBet/2 + "の数値を入力してください。");
                            }
                        } else if (yesNo == 2) {
                            player.money -= bet;
                            break;
                        } else {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        System.out.println("\n\n1か2を入力してください。");
                    }
                }
            }else {
                player.money-= bet;
            }

            System.out.println("勝負!");
            fieldCards[4] = dealCard();
            System.out.println("\n【場のカード】");
            showCards(fieldCards);
            System.out.println("【" + player.getName() + "の手札】");
            showCards(player.getCards());
            System.out.println("【相手の手札】");
            showCards(cpu.getCards());

            System.out.println("\n\n【結果】");

            var hand1 = new Hand();
            var hand2 = new Hand();
            int playerHand = hand1.decideHand(player.getCards(), fieldCards);
            int cpuHand = hand2.decideHand(cpu.getCards(), fieldCards);

            System.out.print(player.getName() + "：");
            showHandName(playerHand, hand1.handNumbers, player);
            System.out.println("");
            System.out.print("相手：");
            showHandName(cpuHand, hand2.handNumbers, cpu);

            if (playerHand > cpuHand) {
                System.out.println("\n\nあなたの勝ちです!");
                player.money += bet *2;
            } else if (playerHand < cpuHand) {
                System.out.println("\n\nあなたの負けです…");
            } else {
                if (player.drawNum > cpu.drawNum) {
                    System.out.println("\nあなたの勝ちです!");
                    player.money += bet *2;
                } else if (player.drawNum < cpu.drawNum) {
                    System.out.println("\nあなたの負けです…");
                } else {
                    System.out.println("\n引き分けです。");
                    player.money += bet;
                }
            }

            System.out.println("所持金：" + player.money + "G");
            fieldCards = new Card[5];
            player.drawNum = 0;
            cpu.drawNum = 0;
            gameCount++;
        }
        if (player.money > 1000) {
            System.out.println("\n所持金が1000Gを超えました。\nおめでとうございます!");
        } else {
            System.out.println("\n所持金が底をつきました。\n貴様は哀れな敗北者です。");
        }
        System.out.println("\nゲームを終了します。");
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

    public static String displayNumber(int number) {
        if (number < 9) {
            return (number + 2) + " ";
        } else {
            return switch (number) {
                case 9 -> "J ";
                case 10 -> "Q ";
                case 11 -> "K ";
                case 12 -> "A ";
                default -> "";
            };
        }
    }

    public static void showHandName(int hand, int[] handNum, Player player) {
        switch (hand) {
            case 7 -> {
                System.out.print(displayNumber(handNum[2]) + "の" + hands[hand]);
                player.drawNum = handNum[2];
            }
            case 3 -> {
                System.out.print(displayNumber(handNum[1]) + "の" + hands[hand]);
                player.drawNum = handNum[1];
            }
            case 2, 1 -> {
                System.out.print(displayNumber(handNum[0]) + "の" + hands[hand]);
                player.drawNum = handNum[0];
            }
            default -> System.out.print(hands[hand]);
        }
    }
}
