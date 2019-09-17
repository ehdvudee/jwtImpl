[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![apache-2.0 License][license-shield]][license-url]

# jwtImpl

## 개요

JWT 안전하게 사용하자. 그리고 배보다 배꼽이 큰 JWT 데모 서버를 구성하자

## 사양
* Java: JDK 1.8
* DBMS: PostgreSQL 9.1
* DevTool: IntelliJ
* Server: CentOS 7.2

## 기능

* 사용자 인증 및 토큰 발급
* 토큰 검증
* 암호키 갱신(+스케줄링)
* JKU 지원
* HTTPS 미지원

## 참조문서

>https://ehdvudee.tistory.com/14

>https://ehdvudee.tistory.com/15

>https://ehdvudee.tistory.com/16

## API 리스트(테스트 파일을 참고한다.)

* GetJku: kid의 JKU를 출력한다.
* UpdateKey: PKCS8의 개인키 Base64와 X509 Certificate Base64를 지정한다.
* CreateToken: 토큰을 생성한다(유저 식별 및 인증 진행).
* Samples: 토큰을 사용하기 위한 샘플들이다. 

## 데모서버 URL
> http://jwt.glaso.net:8080

## POST Man Client 링크 공유
> https://www.getpostman.com/collections/78b8d32b13db2110bb38



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/ehdvudee/jwtImpl.svg?style=flat-square
[contributors-url]: https://github.com/ehdvudee/jwtImpl/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=flat-square
[forks-url]: https://github.com/ehdvudee/jwtImpl/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=flat-square
[stars-url]: https://github.com/ehdvudee/jwtImpl/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=flat-square
[issues-url]: https://github.com/ehdvudee/jwtImpl/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=flat-square


