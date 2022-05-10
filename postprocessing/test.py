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

x=0
y=306

try:
    result = pd.read_csv(patch_input + 'k025-2'+'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height'])

    result.loc[:, 'center_x'] *= patch_x_size
    result.loc[:, 'center_y'] *= patch_y_size
    result.loc[:, 'width'] = 40
    result.loc[:, 'height'] = 40

    result.loc[:, 'center_x'] += x
    result.loc[:, 'center_y'] += y

    print(result)

except:
    pass