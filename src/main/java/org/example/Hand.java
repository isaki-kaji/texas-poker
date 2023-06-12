package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Hand {
    private final List<Card> sevenCards = new ArrayList<>();

    private boolean isStraight = false;
    private boolean isFlash = false;
    private boolean isStraightFlash = false;
    private boolean isFourCard = false;
    private boolean isFullHouse = false;
    private boolean isThreeCard = false;
    private boolean isTwoPair = false;
    private boolean isOnePair = false;

    List<Integer> sortedList = new ArrayList<>();
    List<Integer> markList = new ArrayList<>();
    List<Integer> originalSorted=new ArrayList<>();
    int[] handNumbers = { 0, 0, 0 };

    public int decideHand(Card[] ownCards, Card[] fieldCards) {

        for (Card card : ownCards) {
            this.sevenCards.add(card);
            sortedList.add(card.getNumber());
            markList.add(card.getMark());
            if(!originalSorted.contains(card.getNumber())) {
                originalSorted.add(card.getNumber());
            }
        }
        for (Card card : fieldCards) {
            this.sevenCards.add(card);
            sortedList.add(card.getNumber());
            markList.add(card.getMark());
            if(!originalSorted.contains(card.getNumber())) {
                originalSorted.add(card.getNumber());
            }
        }

        Collections.sort(sortedList);
        Collections.sort(originalSorted);

        isFlash();
        isStraight(originalSorted);
        isPair();

        if (isStraightFlash) {
            return 8;
        } else if (isFourCard) {
            return 7;
        } else if (isFullHouse) {
            return 6;
        } else if (isFlash) {
            return 5;
        } else if (isStraight) {
            return 4;
        } else if (isThreeCard) {
            return 3;
        } else if (isTwoPair) {
            return 2;
        } else if (isOnePair) {
            return 1;
        } else {
            return 0;
        }
    }

    //ストレートを判定
    public void isStraight(List<Integer> list) {
        outside: for (int i = 0; i < list.size()-4; i++) {
            int count = 0;
            for (int j = 0; j < 4; j++) {
                if ((list.get(i + j + 1) - list.get(i + j)) == 1) {
                    count++;
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

    //フラッシュ、ストレートフラッシュを判定
    public void isFlash() {
        Map<Object, List<Integer>> gMap = markList.stream().collect(Collectors.groupingBy(s -> s));
        for (Map.Entry<Object, List<Integer>> entry : gMap.entrySet()) {
            if (entry.getValue().size() >= 5) {
                isFlash = true;
                int flashMark = (int) entry.getKey();
                List<Integer> flashSortedList = new ArrayList<>();
                for (Card card : sevenCards) {
                    if (card.getMark() == flashMark) {
                        flashSortedList.add(card.getNumber());
                    }
                }
                List<Integer> originalFlashSorted= new ArrayList<>(new LinkedHashSet<>(flashSortedList));
                Collections.sort(originalFlashSorted);
                isStraight(originalFlashSorted);
                if (isStraight && isFlash) {
                    isStraightFlash = true;
                }
            }
        }
    }

    //その他ペア系の役を判定
    public void isPair() {
        int pairCount = 0;
        Map<Object, List<Integer>> gMap = sortedList.stream().collect(Collectors.groupingBy(s -> s));
        for (Map.Entry<Object, List<Integer>> entry : gMap.entrySet()) {
            if (entry.getValue().size() == 4) {
                isFourCard = true;
                handNumbers[2] = (int) entry.getKey();
            } else if (entry.getValue().size() == 3) {
                isThreeCard = true;
                if (handNumbers[1] < (int) entry.getKey()) {
                    handNumbers[1] = (int) entry.getKey();
                }
                if (isOnePair) {
                    isFullHouse = true;
                }
            } else if (entry.getValue().size() == 2) {
                isOnePair = true;
                if (handNumbers[0] < (int) entry.getKey()) {
                    handNumbers[0] = (int) entry.getKey();
                }
                ++pairCount;
                if (isThreeCard) {
                    isFullHouse = true;
                }
                if (pairCount >= 2) {
                    isTwoPair = true;
                }
            }
        }
    }
}
