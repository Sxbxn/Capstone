# 02-YOLOX 모델 학습
> 22-04-19

## 개요  

Object Detection 모델을 YOLOX로 선정하여 Cell Count를 학습한다. 그 전에 labeling 값을 Detection 모델에서 사용할 수 있도록 변환한다.

## Detection visualizing  

후에 안드로이드 상에서 검출한 Cell Count에 대해서 시각화를 하고싶다는 요청이 들어왔다.  
Detection을 한 결과를 이용해 cv2 상에서 시각화 하는 편이 빠를 것같아서 관련 로직을 작성했다.  

LiveOrDead Column을 참고해 1일경우 green 색상으로, 0일경우 red 생상으로 시각화를 진행했다.  

<img src="Assets/3.png" width="60%">

## csv to json

YOLOX는 labelme의 json 파일 형식을 지원한다고 하여 labelme의 json 형식 대로 csv를 변환하기로 했다.  

ex)
``` json
{
    "version": "4.0.0",
    "flags": {},
    "shapes": [
        {
            "label": "live",
            "points": [
                [
                    191,
                    107
                ],
                [
                    313,
                    329
                ]
            ],
            "group_id": null,
            "shape_type": "rectangle",
            "flags": {}
        }
    ],
    "imagePath": "k0-0_0_0.jpg",
    "imageData":null,
    "imageHeight": 306,
    "imageWidth": 408
}
```  

json으로 변환하는 프로그램은 제작할 때 padas로 하는 방식 등 여러가지를 고려해 봤으나 Dictionaly 형태로 직접 만들어서 넣는 방식이 가장 간편했다.  

중간에 막혔던 부분이 csv에서 json으로 값을 전달할 때, csv의 정수형이 ```<numpy.int64>```였기 때문에 json에서 지원하는 기본 ```<int>``` 형태로 변환해 줘야 했다.  

