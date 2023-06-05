package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Hand {
    private String[] hands = { "ノーハンド", "ワンペア", "ツーペア", "スリーカード", "ストレート", "フラッシュ", "フルハウス", "フォーカード", "ストレートフラッシュ" };
    private List<Card> sevenCards = new ArrayList<>();
    private boolean isStraight = false;
    private boolean isFlash = false;
    private int flashMark;
    List<Integer> sortedList = new ArrayList<>();

    List<Integer> markList = new ArrayList<>();

    public int decideHand(Card[] ownCards, Card[] fieldCards) {

        for (Card card : ownCards) {
            this.sevenCards.add(card);
            sortedList.add(card.getNumber());
            markList.add(card.getMark());
        }
        for (Card card : fieldCards) {
            this.sevenCards.add(card);
            sortedList.add(card.getNumber());
            markList.add(card.getMark());
        }

        Collections.sort(sortedList);

        //同じmarkの枚数を数えるためにグループ分け
        Map<Object, List<Integer>> gMap = markList.stream().collect(Collectors.groupingBy(s -> s));
        for (Map.Entry<Object, List<Integer>> entry : gMap.entrySet()) {
            if (entry.getValue().size() >= 5) {
                isFlash = true;
                flashMark = (int) entry.getKey();
                List<Integer> flashSortedList = new ArrayList<>();
                for (Card card : sevenCards) {
                    if (card.getMark() == flashMark) {
                        flashSortedList.add(card.getNumber());
                    }
                }
                isStraight(flashSortedList);
            }
        }

        if(isStraight&&isFlash) {
            return 9;
        }
    }

    //straight判定
    public void isStraight(List list) {
        outside: for (int i = 0; i < 2; i++) {
            for (int j = 1; j < 7; j++) {
                List<Integer> keepStraight = new ArrayList<>();
                keepStraight.add(i);
                int count = 0;
                if (sortedList.get(i + j + 1) - sortedList.get(i + j) == 1
                        || (sortedList.get(j) == sortedList.get(j + 1))) {
                    count++;
                    keepStraight.add(i + j);
                    if (count == 4) {
                        isStraight = true;
                        break outside;
                    }
                } else {
                    break;
                }

            }
        }

    }

}
