package com.example.test;

/**
 * Created by yangxiaowei on 16/9/22.
 */
public interface Parent {
    String getName();

    class Sub implements Parent{
        @Override
        public String getName() {
            return null;
        }
    }
}
