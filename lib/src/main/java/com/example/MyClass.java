package com.example;

import javax.net.ssl.SSLContext;

public class MyClass {

    public static void main(String[] args) {
        System.out.println("------");

//        sortWithBubble(get());
//        sortWithInsert(get());
//        sortWithSelect(get());
        int[] arr = get();
        sortWithQuick(arr, 0, arr.length - 1);

        System.out.println(((0xf0ff & 0x000f) | 0x00f0)+": "+Integer.toHexString((0xf0ff & 0x000f) | 0x00f0));

        System.out.println((0xff << 1)+" : "+Integer.toHexString(0xff << 1));
        int result = (((0xf0ff & 0x000f) | 0x00f0) << 1) / (4 >> 1);
        System.out.println("_____ "+result);

        System.out.println("_____ "+(10 / 2)+" , "+(10 >> 1));
    }
    private static int[] get() {
        return new int[]{3, 8, 1, 0, 6, 4, 9, 5, 2};
    }

    /**
     * 冒泡排序
     *
     * 两两交换
     *
     * 思路:
     * 1. 外层循环控制执行次数,(length-1 | length)次都可以, length次没必要(因为内层循环逻辑的关系)
     * 2. 内存循环控制每次执行的次数, 每次结束后都将挑出一个最大或者最小的数
     * 3. 内存循环每次两两比较剩余的数
     *
         3 1 0 6 4 8 5 2 9
         1 0 3 4 6 5 2 8 9
         0 1 3 4 5 2 6 8 9
         0 1 3 4 2 5 6 8 9
         0 1 3 2 4 5 6 8 9
         0 1 2 3 4 5 6 8 9
         0 1 2 3 4 5 6 8 9
         0 1 2 3 4 5 6 8 9
     */
    public static void sortWithBubble(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+1]) {
                    final int emp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = emp;
                }
            }
            print(arr, true);
        }
    }

    /**
     * 插入排序
     *
     * 查找位置
     *
     * 通过构建有序序列, 对未排序的序列依次在已排序序列中从后向前扫描找到合适的位置进行插入
     *
     * 思路:
     * 1. 外层循环负责依次选取未排序的数作为将要插入的数
     * 2. 内存循环从左侧已排序的数组中寻找插入位置, 同时移动其他数
     *
         3 8 1 0 6 4 9 5 2
         1 3 8 0 6 4 9 5 2
         0 1 3 8 6 4 9 5 2
         0 1 3 6 8 4 9 5 2
         0 1 3 4 6 8 9 5 2
         0 1 3 4 6 8 9 5 2
         0 1 3 4 5 6 8 9 2
         0 1 2 3 4 5 6 8 9
     */
    public static void sortWithInsert(int[] arr) {
        System.out.println("sortInsert()");
        for (int i = 1; i < arr.length; i++) {
            //插入位置,默认为当前位置
            int index = i;
            int currentValue = arr[i];
            //1. 从左侧排好的序列中倒序寻找插入位置(倒序是为了在保持原有顺序下做平移)
            //2. 平移
            for (int j = i - 1; j >= 0 ; j--) {
                if (arr[j] > currentValue) {
                    arr[j+1] = arr[j];
                    index = j;
                }else {
                    //因为左侧是有序的,所以只要第一个不符合就没必要做其他检查
                    break;
                }
            }
            //找到新的插入位置
            if (index != i) {
                arr[index] = currentValue;
            }
            print(arr, true);
        }
    }

    /**
     * 选择排序
     *
     * 选择最小的
     *
     * 思路:
     * 1. 外层控制次数(即无序有序边界)
     * 2. 内层负责从无序中寻找最小数的位置
     * 3. 找到之后与边界数进行交换
     *
         0 8 1 3 6 4 9 5 2
         0 1 8 3 6 4 9 5 2
         0 1 2 3 6 4 9 5 8
         0 1 2 3 6 4 9 5 8
         0 1 2 3 4 6 9 5 8
         0 1 2 3 4 5 9 6 8
         0 1 2 3 4 5 6 9 8
         0 1 2 3 4 5 6 8 9
     */
    public static void sortWithSelect(int[] arr) {
        System.out.println("sortWithSelect()");
        for (int i = 0; i < arr.length - 1; i++) {
            //记录所选择数的位置
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                //与找到的位置进行比较
                if (arr[j] < arr[index]) {
                    index = j;
                }
            }

            if (index != i) {
                final int emp = arr[i];
                arr[i] = arr[index];
                arr[index] = emp;
            }

            print(arr, true);
        }
    }

    /**
     * 以基准元素为准进行排序,结果就是基准元素左面都比基准元素小,右面都比基准元素大
     *
     * @param arr
     * @param low
     * @param high
     * @return 基准元素位置
     */
    public static int find(int[] arr, int low, int high) {
        int pivot = arr[low];
        //3, 8, 1, 0, 6, 4, 9, 5, 2
        while (low < high) {
            while (low < high && arr[high] >= pivot) {
                high -= 1;
            }
            arr[low] = arr[high];
            StringBuffer sb = new StringBuffer("-");
            for (int i = 0; i < arr.length; i++) {
                sb.append(" "+arr[i]);
            }
            System.err.println(sb);

            while (low < high && arr[low] <= pivot) {
                low += 1;
            }
            arr[high] = arr[low];

            sb.setLength(0);
            sb.append("+");
            for (int i = 0; i < arr.length; i++) {
                sb.append(" "+arr[i]);
            }
            System.err.println(sb);
        }
        arr[low] = pivot;
        print(arr, false);
        return low;
    }
    /**
     * 快排(对冒泡的改进)
     *
     * 思路:
     * 从数列中挑出一个基准pivot元素,所有比基准元素小的放在基准元素前面,比基准元素大的放在基准元素后面
     * 分割之后基准元素就处于数列的中间位置,然后对左右两个子数列递归上面的流程
     *
     */
    public static void sortWithQuick(int[] arr, int low, int high) {
//        System.out.println("sortWithQuick()");

        if (low < high) {
            int index = find(arr, low, high);
//            System.out.println("sortWithQuick(): "+index);
            sortWithQuick(arr, low, index - 1);
//            sortWithQuick(arr, index + 1, high);
//        print(arr, true);
        }


    }

    static void swap(int[] arr, int l, int r){
        int e = arr[l];
        arr[l] = arr[r];
        arr[r] = e;
    }

    static void print(int[] arr, boolean d) {
        for (int i = 0; i < arr.length; i++) {
            if (d) {
                System.out.print(arr[i]+" ");
            }else {
                System.err.print(arr[i]+" ");
            }
        }
        if (d) {
            System.out.println();
        }else {
            System.err.println();

        }
    }
}


