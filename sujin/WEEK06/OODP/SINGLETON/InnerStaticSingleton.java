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

    private static class InnerClass {
        private static final InnerStaticSingleton instance = new InnerStaticSingleton();
    }
}