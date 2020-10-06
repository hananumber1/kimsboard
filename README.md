![build_deploy](https://github.com/zxc7023/kimsboard/workflows/build_deploy/badge.svg?branch=master)
![java](https://img.shields.io/badge/java-1.8-orange?logo=java)
![springboot](https://img.shields.io/badge/springboot-2.3.1-green?logo=spring)
![vue](https://img.shields.io/badge/vue-2.6.11-green?logo=vue.js)

# SpringBoot와 Vue.js로 게시판 웹서비스 프로젝트

## 1. 프로젝트 목표
- 써봤던 개발 환경이 아닌 새롭고 사용해보지 못한 기술 스택 사용하기
    - 스프링 프레임워크가 아닌 스프링부트를 가지고 백엔드(Rest API)개발
    - Mybatis가 아닌 JPA(퍼시스턴트 프레임워크)를 가지고 개발
    - JSP가 아닌 자바스크립트 프레임워크(Vue.js OR React)를 가지고 프론트 엔드(SPA)개발
- "개발하고 끝!"이 아니라 실제 서비스를 제공한다고 가정하고 지속적인 개발과 배포를 통해 개선 사항과 새로운 기술 스택을 추가하며 버전업하기!

## 2. 개발 환경 및 기술 스택

- ### 개발 환경 
    - IDE : Visual Studio Code
    - OS : Windows (개발) / ubuntu (서비스)
    - 프로젝트 관리 및 협업 툴  : Notion 

- ### Front-End
    - Vue.js 2.x
    - Webpack 4.x
    - tailwindcss

- ### Back-End
    - 개발 언어 : JAVA (open jdk 1.8)
    - 개발 프레임 워크 : SpringBoot 2.x
        - SpringSecurity 5.x
        - Spring Data JPA 
        - ...
    - 빌드 및 배포 : 
        - gradle
        - Github Actions
    - AWS Service
        - EC2 (백엔드 / Rest API 서버)
        - S3 (프론트엔드 / 정적 웹사이트)
        - Cloudfront
        - RDS (Oracle 12c)
        - CodeDeploy
        

## 3. 프로젝트 실행 방법
- ### Back-end
   - VS Code의 확장팩 Lombok 플러그인 설치
   - src\main\resources 경로에 application-key.yml 파일을 작성후 아래 내용을 자신의 환경에 맞게 설정 (외부에 공개하면 안되는 키를 .yml 파일안에 정의해서 사용)  
   <pre>
    ###공통 profile
    spring:
    profiles: key-profile
    jwt:
        secret: '비밀키' 
    jasypt:
    encryptor:
        password: '비밀키'

    ---

    #개발용 DB profile
    spring:
    profiles: local
    datasource:
        driver-class-name: oracle.jdbc.OracleDriver
        url: jdbc:oracle:thin:@localhost:1521:orcl
        username: '아이디'
        password: '비밀번호'

    ---

    #운영용 DB 설정
    spring:
    profiles: production
    datasource:
        driver-class-name: oracle.jdbc.OracleDriver
        url: jdbc:oracle:thin:@'운영서버DB URL'
        username: '아이디'
        password: '비밀번호'
    </pre>
    - 어떤 설정파일을 적용할지 실행인자 옵션에 값을 명시 (VS Code IDE에서는 launch.json에 실행 인자를 적을 수 있음)
    ex) 개발 설정 적용: "vmArgs": "-Dspring.profiles.active=local"    
    ex) 서비스 설정 적용: "vmArgs": "-Dspring.profiles.active=production"
    - Springboot Run
    - API Doc : http://localhost:8080/swagger-ui.html
- ### Front-end
    - 이 프로젝트는 package.json에 webpack을 구동하는 script를 선언했습니다. 아래의 명령어를 입력하면 devserver로 프론트엔드 서버를 돌립니다.   
    <pre>
    yarn install
    yarn start
    http://localhost:8081
    </pre>

---

### Version 1.0
Restful 방식에서의 로그인(스프링 시큐리티와 JWT를 사용) 및 회원관리     
게시글, 댓글 CURD   
빌드 및 배포과정 자동화 (Github actions & Codedeploy 사용)  

---
### Version 2.x 예정
- 게시판 페이징 개선
- 게시글과 같이 자주쓰는 데이터 캐시를 쓸 수 있게 인 메모리 데이터 시스템 추가
- 로그인 토큰을 refresh할 수 있는 로직 추가
- 직접 vue의 router로 이동이 아닌 직접 호출해도 데이터를 가져올 수 있는 로직으로 수정
