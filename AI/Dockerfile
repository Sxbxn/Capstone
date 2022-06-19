# Set base Image (Host Os)
FROM python:3.8
# 해당 디렉토리 모든 하위항목 '/app/server' 로 복사
COPY . /app/server
# image 의 directory 로 이동
WORKDIR /app/server

# 필요한 의존성 file 설치
RUN apt-get update
RUN apt-get install ffmpeg libsm6 libxext6  -y
RUN pip3 install -r requirements.txt

# container 가 구동되면 실행
ENTRYPOINT ["python3", "run/test.py"]