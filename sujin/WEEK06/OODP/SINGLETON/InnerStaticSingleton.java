package oodp.SINGLETON;

import java.io.Serializable;

// 멀티쓰레드에 안전 & Lazy loading 지원 & Serialization 에도 안전한 싱글톤
// Lazy loading : 객체가 필요할 때 생성 (InnerClass)
public class InnerStaticSingleton implements Serializable {

    private InnerStaticSingleton() {
    }

    public static InnerStaticSingleton getInstance() {
        return InnerClass.instance;
    }

    // static : 해당 객체를 여기저기서 써도 동일한 객체 / 메모리에 올려 프로그램 시작부터 종료까지 살아있음
    // final : 재할당 X / 값은 변할 수 있음 (collections 타입)
    // private static final : 재할당 X, 메모리 한번 올라가면 값은 값을 클래스 내부 전체  필드, 메서드 공유
    // private final
    // >> 해당 필드 메서드 별로 호출할 때마다 새로 값 할당(인스턴스화)
    // >> 상수로 사용하려고 하면, 값은 변하지 않을테니 굳이 매번 인스턴스화 X >> static 
    private static class InnerClass {
        private static final InnerStaticSingleton instance = new InnerStaticSingleton();
    }
}