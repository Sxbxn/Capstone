# AI part

# 개요

4K (3264 x 2448) size의 세포 계수 영상에서 Object Detection을 수행  

## DataSet  

Dataset은 산학협력 기업 (주)솔에서 제공해준 dataset을 사용했다.  

총 12장의 4k 영상으로 구성되어있으며 Bounding Box에 대한 좌/우/상/하단 좌표(int)와 live/dead 값(boolean)을 알수 있는 csv 파일이 제공되었다.  

ex)
|n  |LiveorDead|Left|Top|Right|Bottom|  
|:-:|:--------:|:--:|:-:|:---:|:----:|  
|1  |1         |85  |81 |125  |121   |  

## 개발일지  

[01 모델 선정 및 데이터 전처리](./Development_log/01-모델%20선정%20및%20데이터%20전처리.md)  

[02 YOLOX 모델 학습](./Development_log/02-YOLOX%20%EB%AA%A8%EB%8D%B8%20%ED%95%99%EC%8A%B5.md)  

[03 YOLOR 모델 학습](./Development_log/03-YOLOR%20%EB%AA%A8%EB%8D%B8%20%ED%95%99%EC%8A%B5.md)  

[04 패치 수복화 작업](./Development_log/04-%ED%8C%A8%EC%B9%98%20%EC%88%98%EB%B3%B5%ED%99%94%20%EC%9E%91%EC%97%85.md)  