import glob
import cv2

img_x_size = 3264
img_y_size = 2448
patch_x_size = 408
patch_y_size = 306
x_stride = 204
y_stride = 153
dir = '/home/dh/Project/Capstone/'
input_dir_image = dir + 'data/K562_Pointing_data/test/img/'
output_dir = dir + 'data/patch_test/data/'

img_list = glob.glob(input_dir_image+'*.jpg')

if x_stride != 0 and y_stride != 0:
    for imname in img_list:
        name = imname.split('/')[-1]
        img = cv2.imread(imname)
        for i in range(0,(img_x_size-patch_x_size)//x_stride+1,1):
            for k in range(0,(img_y_size-patch_y_size)//y_stride+1,1):
                x = i*x_stride
                y = k*y_stride
                patch = img[y:y+patch_y_size, x:x+patch_x_size]
                print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y))
                cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y), patch)

        # Use if you want to include a truncated image
        for i in range(0,(img_x_size-patch_x_size)//x_stride+1,1):
            x = i*x_stride
            patch = img[-patch_y_size:,x:x+patch_x_size]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size), patch)

        for i in range(0,(img_y_size-patch_y_size)//y_stride+1,1):
            y = i*y_stride
            patch = img[y:y+patch_y_size, -patch_x_size:]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y), patch)

        patch = img[-patch_y_size:, -patch_x_size:]
        print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size))
        cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size), patch)

else:
    for imname in img_list:
        name = imname.split('/')[-1]
        img = cv2.imread(imname)
        for i in range(0,img_x_size//patch_x_size,1):
            for k in range(0,img_y_size//patch_y_size,1):
                x = i*patch_x_size
                y = k*patch_y_size
                patch = img[y:y+patch_y_size, x:x+patch_x_size]
                print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y))
                cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y), patch)

    # Use if you want to include a truncated image
    for imname in img_list:
        name = imname.split('/')[-1]
        img = cv2.imread(imname)

        for i in range(0,img_x_size//patch_x_size,1):
            x = i*patch_x_size
            patch = img[-patch_y_size:,x:x+patch_x_size]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size), patch)

        for i in range(0,img_y_size//patch_y_size,1):
            y = i*patch_y_size
            patch = img[y:y+patch_y_size, -patch_x_size:]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y), patch)

        patch = img[-patch_y_size:, -patch_x_size:]
        print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size))
        cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size), patch)