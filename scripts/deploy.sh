#!/bin/bash

REPOSITORY=/home/ubuntu/applications/kimsboard
PROJECT_NAME=kimsboard

echo "> Build 파일 복사"
# 재배포를 할 경우 많은 시간이 필요해서 긴급하게 배포를 하기위해서 jar를 따로 보관
cp -f $REPOSITORY/build/build/libs/*.jar $REPOSITORY/jar/

echo "> 현재 구동 중인 애플리케이션 pid 확인"

# 실행 중이면 종료하기 위해서 현재 수행 중인 프로세스id를 찾습니다.
# kimsboard으로 된 다른 프로그램들이 있을 수 있어 kimsboard된 jar 프로세스를 찾은 뒤 id를 찾습니다(awk '{print $1}').
CURRENT_PID=$(pgrep -fl $PROJECT_NAME | grep java | awk '{print $1}')

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"

#jar파일들 중에 -tr옵션을 통하여 가장 최근에 수정 날짜를 가진 파일을 배포할 jar로 지정
JAR_PATH=$REPOSITORY/jar
JAR_NAME=$(ls -tr $JAR_PATH/*.jar | tail -n 1)

echo "> JAR name: $JAR_NAME"
echo "> $JAR_NAME에 실행 권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
#  nohup실행 시 CodeDeploy는 무한 대기 합니다.
#  이 이슈를 해결하기 위해 nohub.out파일을 표준 입출력용으로 별도로 사용합니다.
#  이렇게 하지 않으면 nohup.out파일이 생기지 않고, CodeDeploy 로그에 표준 입출력이 출력됩니다.
#  nohub이 끝나기 전까지 CodeDeploy도 끝나지 않으니 꼭 이렇게 해야합니다.
nohup java -jar -Dspring.profiles.active=production $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
