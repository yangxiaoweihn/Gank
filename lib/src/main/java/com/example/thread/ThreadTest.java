package com.example.thread;

/**
 * Created by yangxiaowei on 16/10/9.
 */

public class ThreadTest {
    public static void main(String[] args) {
        ThreadTest tt = new ThreadTest();
//        for (int i = 0; i< 20; i++) {
//            tt.add();
//            tt.del();
//            System.out.println("----");
//        }


        tt.tt();
    }

    public void tt() {
        for (int i = 0; i < 20; i++) {
            final int x = i;
            Thread t = new Thread() {
                @Override
                public void run() {
                    super.run();
//                    System.out.println(""+Thread.currentThread().getName());
                    print(x);
                }
            };
            t.setName("Thread: "+i);
            t.start();
        }
    }

    private void print(int i) {
        String ss = new String("" +
                "fksgksgkskghsbnvfifhskgbksnvakjfoqur98wty8w9tyhwgnkwngkwgijowgjowjgowjgowugwhgwgjowugowgog" +
                "");

        for (int j = 0; j < 200; j++) {
            char[] ch = ss.toCharArray();
            for (int k = 0; k < ch.length - 1; k++) {
                final char e = ch[i];
//                ch[i] = ch[ch.length - k - 1];
//                ch[ch.length - k - 1] = e;
                new String(""+j);
            }
            i++;
            ss+=i;
        }
        num++;
        System.out.println(Thread.currentThread().getName()+" -> "+num);
    }

    private int num = 0;
    public void add() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1000);
                }catch (InterruptedException e) {
                }
//                num += 1;
                num++;
                System.out.println("add: "+num);
            }
        }.start();
    }


    public void del() {
        new Thread() {
            @Override
            public void run() {
                super.run();
//                num -= 1;
                num --;
                System.out.println("del: "+num);
            }
        }.start();
    }
}
