import cv2
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
    name = img_name.split('/')[-1][:-4]
    img = np.zeros((img_y_size, img_x_size), np.uint8)

    # 1. 정상적인 patch를 먼저 합치기
    for i in range(0,img_x_size//patch_x_size,1):
        x = i*patch_x_size
        for k in range(0,img_y_size//patch_y_size,1):
            y = k*patch_y_size
            patch = cv2.imread(patch_input + '{}_{}_{}.jpg'.format(name, x, y), cv2.IMREAD_GRAYSCALE)
            patch = patch[1:-1, 1:-1]
            patch = cv2.copyMakeBorder(patch, 1, 1, 1, 1, cv2.BORDER_CONSTANT)
            img[y:y+patch_y_size, x:x+patch_x_size] = patch

    # 2. 좌우 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size-1,1):
        x = i*patch_x_size + x_stride
        for k in range(0,img_y_size//patch_y_size,1):
            y = k*patch_y_size
            patch = cv2.imread(patch_input + '{}_{}_{}.jpg'.format(name, x, y), cv2.IMREAD_GRAYSCALE)
            patch = patch[:,patch_x_size//2 - 40: -patch_x_size//2 + 40]
            patch = patch[1:-1, 1:-1]
            patch = cv2.copyMakeBorder(patch, 1, 1, 1, 1, cv2.BORDER_CONSTANT)
            img[y:y+patch_y_size, x+patch_x_size//2 - 40:x+patch_x_size//2 + 40] = patch
    
    # 3. 상하 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size,1):
        x = i*patch_x_size
        for k in range(0,img_y_size//patch_y_size-1,1):
            y = k*patch_y_size + y_stride
            patch = cv2.imread(patch_input + '{}_{}_{}.jpg'.format(name, x, y), cv2.IMREAD_GRAYSCALE)
            patch = patch[patch_y_size//2 - 40: -patch_y_size//2 + 40, :]
            patch = patch[1:-1, 1:-1]
            patch = cv2.copyMakeBorder(patch, 1, 1, 1, 1, cv2.BORDER_CONSTANT)
            img[y+patch_y_size//2 - 40:y+patch_y_size//2 + 40, x:x+patch_x_size] = patch

    for i in range(0,img_x_size//patch_x_size-1,1):
        x = i*patch_x_size + x_stride
        for k in range(0,img_y_size//patch_y_size-1,1):
            y = k*patch_y_size + y_stride
            patch = cv2.imread(patch_input + '{}_{}_{}.jpg'.format(name, x, y), cv2.IMREAD_GRAYSCALE)
            patch = patch[patch_y_size//2 - 40: -patch_y_size//2 + 40, patch_x_size//2 - 40: -patch_x_size//2 + 40]
            patch = patch[1:-1, 1:-1]
            patch = cv2.copyMakeBorder(patch, 1, 1, 1, 1, cv2.BORDER_CONSTANT)
            img[y+patch_y_size//2 - 40:y+patch_y_size//2 + 40, x+patch_x_size//2 - 40:x+patch_x_size//2 + 40] = patch

    cv2.imwrite(merge_output+'{}.jpg'.format(name), img)
    print('save ' + merge_output + '{}.jpg'.format(name))