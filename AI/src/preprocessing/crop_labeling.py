import cv2
import pandas as pd
import numpy as np
import glob

img_x_size = 3264
img_y_size = 2448
patch_x_size = 408
patch_y_size = 306
x_stride = 204
y_stride = 153
input_dir_image = '/home/dh/Project/Capstone/data/K562_Pointing_data/test/result/'
output_dir = '/home/dh/Project/Capstone/data/patch_test/csv/'

img_list = glob.glob(input_dir_image+'*.csv')

for imname in img_list:
    df = pd.read_csv(imname)
    df = df.iloc[:, :-1]
    imname=imname.split('/')[-1]
    x_center = df['right']-20
    y_center = df['bottom']-20

    for i in range(0,(img_x_size-patch_x_size)//x_stride+1,1):
        x = i*x_stride
        for k in range(0,(img_y_size-patch_y_size)//y_stride+1,1):
            y = k*y_stride

            result = df[(x_center >= x) & (x_center < (x+patch_x_size)) & (y_center >=y) & (y_center < (y+patch_y_size))]

            result.loc[:,'left'] = result.loc[:,'left'] - x
            result.loc[:,'right'] = result.loc[:,'right'] - x
            result.loc[:,'top'] = result.loc[:,'top'] - y
            result.loc[:,'bottom'] = result.loc[:,'bottom'] - y

            print('save '+output_dir+'{}_{}_{}.csv'.format(imname.split('_')[0], x, y))
            result.to_csv(output_dir+'{}_{}_{}.csv'.format(imname.split('_')[0], x, y), sep=',')

        y = img_y_size - patch_y_size

        result = df[(x_center >= x) & (x_center < (x+patch_x_size)) & (y_center >=y) & (y_center < (y+patch_y_size))]

        result.loc[:,'left'] = result.loc[:,'left'] - x
        result.loc[:,'right'] = result.loc[:,'right'] - x
        result.loc[:,'top'] = result.loc[:,'top'] - y
        result.loc[:,'bottom'] = result.loc[:,'bottom'] - y

        print('save '+output_dir+'{}_{}_{}.csv'.format(imname.split('_')[0], x, y))
        result.to_csv(output_dir+'{}_{}_{}.csv'.format(imname.split('_')[0], x, y), sep=',')

    for i in range(0,(img_y_size-patch_y_size)//y_stride+1,1):
        x = img_x_size - patch_x_size
        y = i*y_stride
        
        result = df[(x_center >= x) & (x_center < (x+patch_x_size)) & (y_center >=y) & (y_center < (y+patch_y_size))]

        result.loc[:,'left'] = result.loc[:,'left'] - x
        result.loc[:,'right'] = result.loc[:,'right'] - x
        result.loc[:,'top'] = result.loc[:,'top'] - y
        result.loc[:,'bottom'] = result.loc[:,'bottom'] - y

        print('save '+output_dir+'{}_{}_{}.csv'.format(imname.split('_')[0], x, y))
        result.to_csv(output_dir+'{}_{}_{}.csv'.format(imname.split('_')[0], x, y), sep=',')