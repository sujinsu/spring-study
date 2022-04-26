package com.gyheo.java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListPractice {
    public static void main(String[] args) {
        List<String> foodList = new ArrayList<>();

        foodList.add("galbi");
        foodList.add("bulgogi");
        foodList.add("kimchi");
        foodList.add("kimbab");

        System.out.println(foodList);

        // 중계 오퍼레이션 -> 스트림 리턴
        // 스트림은 저장소가 X
        Stream<String> stringStream = foodList.stream()
                .map(s -> s.toUpperCase());

        List<String> after = stringStream.collect(Collectors.toList());
        System.out.println("stream을 List로 변환");
        System.out.println(after);

        System.out.println("original 소스");
        System.out.println(foodList); // 원본을 변경하지 않는다.

        foodList.stream().map((s) -> {
            System.out.println(s);
            return s.toUpperCase();
        });
    }
}
