import glob
import cv2

img_x_size = 3264
img_y_size = 2448
patch_x_size = 408
patch_y_size = 306
x_stride = 204
y_stride = 153
input_dir_image = '/home/dh/Project/Capstone/data/K562_Pointing_data/test/img/'
output_dir = '/home/dh/Project/Capstone/data/patch_test/img/'

img_list = glob.glob(input_dir_image+'*.jpg')

for imname in img_list:
    img = cv2.imread(imname)
    imname=imname.split('/')[-1]
    
    for i in range(0,(img_x_size-patch_x_size)//x_stride+1,1):
        for k in range(0,(img_y_size-patch_y_size)//y_stride+1,1):
            x = i*x_stride
            y = k*y_stride
            patch = img[y:y+patch_y_size, x:x+patch_x_size]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(imname[:-4],x, y))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(imname[:-4],x, y), patch)