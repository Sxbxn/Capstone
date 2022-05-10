# 03-YOLOR 모델 학습
> 22-04-21

## 개요  

YOLOX 모델은 CoCo dataset format만 지원하기 때문에 비교적 만들기 쉽고 성능도 준수한 YOLOR 모델을 사용하기로 했다.  
YOLOR 모델로 넣기 위해 json 파일을 YOLO에서 사용하능한 txt 파일로 만들고 학습하였다.  

## Convert YOLO format  

계속 파일 형식을 바꾸면서 좀 익숙해지기는 했지만 YOLO format은 다른 형식들과는 다른점이 있었다.
YOLO는 정규화(nomalization)과정을 거친 것을 전제로 학습하기 때문에 라벨링 값이 항상 0~1 사이였다.  

다만 정규화를 어떤 방식으로 했는지를 잘 모르기 때문에 [convert2YOLO](https://github.com/ssaru/convert2Yolo)프로그램을 사용하려 했다.  
여기서 한가지 문제점이 우리 데이터셋은 내가 임의로 전처리 했기 때문에 부가적인 파일들이 없었다. (cla_list, manifest_path 등..)  

그래서 그런지 정상적으로 실행이 안되었다.  

![img](./Assets/4.png)  

value error가 뜨는데 아무리봐도 코드가 그냥 오류가 있는게 아닌가싶은.. config 2개를 가지고 3개를 가져온다.. CoCo dataset만 코드가 이렇게 되어있었다. 

그래서 어짜피 우리는 정규화 코드만 이해하면 우리가 직접 만들수 있기 때문에 코드를 뜯어보았다.  
코드는 예상대로 x중점, y중점, 너비, 높이에 이미지의 각 축 사이즈를 나누는 형식으로 정규화가 되어있었다.  

![img](./Assets/5.png)  

우리 patch는 408 x 306 사이즈로 통일 되어있어서 csv를 전처리 하는 코드를 제작했다.  

## YOLOR config

이제 전반적인 준비가 완료되었다. Yolo-R에 들어가서 requirements.txt의 항목들을 다 다운받고, ```yolor/data/cel.yaml```을 만들어 경로 설정을 해주었다.

```yaml
# train and val datasets (image directory or *.txt file with image paths)
train: /home/dh/Project/Capstone/data/patch_train/train.txt  # 118k images
val: /home/dh/Project/Capstone/data/patch_val/val.txt  # 5k images
test: /home/dh/Project/Capstone/data/patch_test/test.txt  # 20k images for submission to https://competitions.codalab.org/competitions/20794

# number of classes
nc: 2

# class names
names: ['dead', 'live']
```

train/test/detection 코드는 다음과 같이 작성했다.  

```bash
python train.py --batch-size 16 --img 512 512 --data cell.yaml --cfg cfg/yolor_p6.cfg --weights '' --device 0 --name yolor_p6 --hyp hyp.scratch.640.yaml --epochs 200
```

```bash
python test.py --data data/cell.yaml --img 512 --batch 16 --conf 0.001 --iou 0.65 --device 0 --cfg cfg/yolor_p6.cfg --weights runs/train/yolor_p6/weights/last.pt --name yolor_p6_val --names data/cell.names --save-txt --task test
```

```bash
python detect.py --source /home/dh/Project/Capstone/data/patch_test/data/k025-2_204_1530.jpg --cfg cfg/yolor_p6.cfg --weights runs/train/yolor_p6/weights/last.pt --conf 0.25 --img-size 512 --device 0 --names data/cell.names
```