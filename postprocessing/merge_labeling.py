import pandas as pd
import numpy as np
import glob

img_x_size = 3264
img_y_size = 2448
patch_x_size = 408
patch_y_size = 306
x_stride = 204
y_stride = 153

dir = '/home/dh/Project/Capstone/'
original_input = dir + 'data/K562_Pointing_data/test/img/'
patch_input = dir + 'data/patch_test/data/'
merge_output = dir + 'result/'

img_list = glob.glob(original_input+'*.jpg')

for img_name in img_list:
    df = pd.DataFrame(columns=['LiveOrDead','center_x','center_y','width','height'])
    name = img_name.split('/')[-1][:-4]

    # 1. 정상적인 patch를 먼저 합치기
    for i in range(0,img_x_size//patch_x_size,1):
        x = i*patch_x_size
        for k in range(0,img_y_size//patch_y_size,1):
            y = k*patch_y_size

            result = pd.read_csv(patch_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height'])
            
            result.loc[:, 'center_x'] *= patch_x_size
            result.loc[:, 'center_y'] *= patch_y_size
            result.loc[:, 'width'] = 40
            result.loc[:, 'height'] = 40

            if x != 0:
                result = result[result['center_x']>20]
            if x != img_x_size-patch_x_size:
                result = result[result['center_x']<patch_x_size-20]
            if y != 0:
                result = result[result['center_y']>20]
            if y != img_y_size-patch_y_size:
                result = result[result['center_y']<patch_y_size-20]

            result.loc[:, 'center_x'] += x
            result.loc[:, 'center_y'] += y
            df = pd.concat([df, result])


    # 2. 좌우 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size-1,1):
        x = i*patch_x_size + x_stride
        for k in range(0,img_y_size//patch_y_size,1):
            y = k*patch_y_size
    
    # 3. 상하 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size,1):
        x = i*patch_x_size
        for k in range(0,img_y_size//patch_y_size-1,1):
            y = k*patch_y_size + y_stride
           
    # 4. 상하 좌우 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size-1,1):
        x = i*patch_x_size + x_stride
        for k in range(0,img_y_size//patch_y_size-1,1):
            y = k*patch_y_size + y_stride

df = df.reset_index(drop=True)
print(df)