package com.lanan.comment;

import org.junit.jupiter.api.Test;

/**
 * @author LanAn
 * @date 2022/10/12-13:41
 */
public class CommentApplicationTest {

    @Test
    public void test() {
        int[] prices = new int[]{8, 6, 5, 3, 2, 1};

        int max = 0;
        int len = prices.length;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (prices[i] <= prices[j]) {
                    max = Math.max(max, prices[j] - prices[i]);
                }
            }
        }
        System.out.println(max);
    }
}
