package oodp.PROXY;

import java.util.ArrayList;

public class MyProgram {
    // 인터넷에서 받아와야 해서 오래 걸리거나 메모리 등의 문제로 객체로 여럿 생성하기 부담인 것들
    // 그 클래스의 proxy, 대리인을 두어 가벼운 일 처리
    // 유튜브 기본적으로 제목(proxy), 커서 갖다대야 프리뷰 재생(real)
    public static void main(String[] args) {
        ArrayList<Thumbnail> thumbnails = new ArrayList<Thumbnail>();

        thumbnails.add(new ProxyThumbnail("Git 강좌","/git.mp4"));
        thumbnails.add(new ProxyThumbnail("Rest API란?","/rest-api.mp4"));
        thumbnails.add(new ProxyThumbnail("도커 사용법","/docker.mp4"));
        thumbnails.add(new ProxyThumbnail("객체지향 프로그래밍","/oodp.mp4"));
        thumbnails.add(new ProxyThumbnail("블록체인의 원리","/blockchain.mp4"));

        for (Thumbnail thumbnail: thumbnails){
            thumbnail.showTitle();
        }

        thumbnails.get(2).showPreview();
        thumbnails.get(2).showPreview();
        thumbnails.get(4).showPreview();
        thumbnails.get(1).showPreview();

    }
}
