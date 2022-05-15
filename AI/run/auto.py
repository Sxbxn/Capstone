import glob
import cv2
import os
import shutil
import pandas as pd

dir = '/home/dh/Project/Capstone/AI/'
test_input = dir + 'run/data/original/'
patch_input = dir + 'run/data/patch/'
test_location = dir + 'yolor/'

result_output = dir + 'run/result/'

label_input = result_output + 'labels/'
merge_output = dir + 'run/result/predict/'

img_x_size = 3264
img_y_size = 2448
patch_x_size = 408
patch_y_size = 306
x_stride = 204
y_stride = 153

os.system('source venv/bin/activate')
if os.path.isdir(test_location+'runs/test/yolor_p6_val'):
    shutil.rmtree(test_location+'runs/test/yolor_p6_val')

# ============================<< crop >>============================
print('============================<< crop >>============================')

input_dir_image = test_input
output_dir = patch_input

img_list = glob.glob(input_dir_image+'*.jpg')
f = open(patch_input+'test.txt', 'w')

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
                f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y)+'\n')

        # Use if you want to include a truncated image
        for i in range(0,(img_x_size-patch_x_size)//x_stride+1,1):
            x = i*x_stride
            patch = img[-patch_y_size:,x:x+patch_x_size]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size), patch)
            f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size)+'\n')

        for i in range(0,(img_y_size-patch_y_size)//y_stride+1,1):
            y = i*y_stride
            patch = img[y:y+patch_y_size, -patch_x_size:]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y), patch)
            f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y)+'\n')

        patch = img[-patch_y_size:, -patch_x_size:]
        print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size))
        cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size), patch)
        f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size)+'\n')

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
                f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y)+'\n')

    # Use if you want to include a truncated image
    for imname in img_list:
        name = imname.split('/')[-1]
        img = cv2.imread(imname)

        for i in range(0,img_x_size//patch_x_size,1):
            x = i*patch_x_size
            patch = img[-patch_y_size:,x:x+patch_x_size]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size), patch)
            f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, img_y_size-patch_y_size)+'\n')

        for i in range(0,img_y_size//patch_y_size,1):
            y = i*patch_y_size
            patch = img[y:y+patch_y_size, -patch_x_size:]
            print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y))
            cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y), patch)
            f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, y)+'\n')

        patch = img[-patch_y_size:, -patch_x_size:]
        print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size))
        cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size), patch)
        f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], img_x_size-patch_x_size, img_y_size-patch_y_size)+'\n')

f.close()

# ============================<< detection >>============================
print('============================<< detection >>============================')

os.chdir(test_location)
os.system('python test.py --data data/test.yaml --img 512 --batch 16 --conf 0.001 --iou 0.65 --device 0 --cfg cfg/yolor_p6.cfg --weights runs/train/yolor_p6/weights/last.pt --name yolor_p6_val --names data/cell.names --save-txt --task test')
if os.path.isdir(result_output + '/labels'):
    shutil.rmtree(result_output + '/labels')

shutil.move(test_location+'runs/test/yolor_p6_val/labels', result_output)

if os.path.isdir(test_location+'runs/test/yolor_p6_val'):
    shutil.rmtree(test_location+'runs/test/yolor_p6_val')

# ============================<< merging >>============================
print('============================<< merging >>============================')

img_list = glob.glob(test_input+'*.jpg')

for img_name in img_list:
    df = pd.DataFrame(columns=['LiveOrDead','center_x','center_y','width','height'])
    name = img_name.split('/')[-1][:-4]

    # 1. 정상적인 patch를 먼저 합치기
    for i in range(0,img_x_size//patch_x_size,1):
        x = i*patch_x_size
        for k in range(0,img_y_size//patch_y_size,1):
            try:
                y = k*patch_y_size

                result = pd.read_csv(label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height'])

                result.loc[:, 'center_x'] *= patch_x_size
                result.loc[:, 'center_y'] *= patch_y_size
                result.loc[:, 'width'] = 40
                result.loc[:, 'height'] = 40

                if x != 0:
                    result = result[result['center_x']>=40]
                if x != img_x_size-patch_x_size:
                    result = result[result['center_x']<patch_x_size-40]
                if y != 0:
                    result = result[result['center_y']>=40]
                if y != img_y_size-patch_y_size:
                    result = result[result['center_y']<patch_y_size-40]

                result.loc[:, 'center_x'] += x
                result.loc[:, 'center_y'] += y
                df = pd.concat([df, result])
            except:
                pass

    # 2. 좌우 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size-1,1):
        x = i*patch_x_size + x_stride
        for k in range(0,img_y_size//patch_y_size,1):
            try:
                y = k*patch_y_size

                result = pd.read_csv(label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height']) 

                result.loc[:, 'center_x'] *= patch_x_size
                result.loc[:, 'center_y'] *= patch_y_size
                result.loc[:, 'width'] = 40
                result.loc[:, 'height'] = 40

                result = result[(result['center_x'] >= (patch_x_size//2 - 40)) & (result['center_x'] < (patch_x_size//2 + 40))]

                if y != 0:
                    result = result[result['center_y']>=40]
                if y != img_y_size-patch_y_size:
                    result = result[result['center_y']<patch_y_size-40]


                result.loc[:, 'center_x'] += x
                result.loc[:, 'center_y'] += y
                df = pd.concat([df, result])
            except:
                pass
    
    # 3. 상하 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size,1):
        x = i*patch_x_size
        for k in range(0,img_y_size//patch_y_size-1,1):
            try:
                y = k*patch_y_size + y_stride

                result = pd.read_csv(label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height']) 

                result.loc[:, 'center_x'] *= patch_x_size
                result.loc[:, 'center_y'] *= patch_y_size
                result.loc[:, 'width'] = 40
                result.loc[:, 'height'] = 40

                result = result[(result['center_y'] >= (patch_y_size//2 - 40)) & (result['center_y'] < (patch_y_size//2 + 40))]

                if x != 0:
                    result = result[result['center_x']>=40]
                if x != img_x_size-patch_x_size:
                    result = result[result['center_x']<patch_x_size-40]

                result.loc[:, 'center_x'] += x
                result.loc[:, 'center_y'] += y
                df = pd.concat([df, result])
            except:
                pass
           
    # 4. 상하 좌우 중간 patch 덮어쓰기
    for i in range(0,img_x_size//patch_x_size-1,1):
        x = i*patch_x_size + x_stride
        for k in range(0,img_y_size//patch_y_size-1,1):
            try:
                y = k*patch_y_size + y_stride

                result = pd.read_csv(label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height']) 

                result.loc[:, 'center_x'] *= patch_x_size
                result.loc[:, 'center_y'] *= patch_y_size
                result.loc[:, 'width'] = 40
                result.loc[:, 'height'] = 40

                result = result[(result['center_x'] >= (patch_x_size//2 - 40)) & (result['center_x'] < (patch_x_size//2 + 40))]
                result = result[(result['center_y'] >= (patch_y_size//2 - 40)) & (result['center_y'] < (patch_y_size//2 + 40))]

                result.loc[:, 'center_x'] += x
                result.loc[:, 'center_y'] += y
                df = pd.concat([df, result])
            except:
                pass

    df = df.reset_index(drop=True)

    live = len(df[df['LiveOrDead']==1])
    dead = len(df[df['LiveOrDead']==0])
    ratio = live / (live + dead)

    print('ratio: ', ratio*100, 'live: ', live, 'dead: ', dead)

    df.to_csv(merge_output+name+'.csv', sep=',')
    img = cv2.imread(img_name)
    grean = (0, 255, 0)
    red = (0, 0, 255)
    for idx, row in df.iloc[:].iterrows():
        if row[0] == 1:
            img = cv2.rectangle(img, (int(row[1]-20), int(row[2]-20)), (int(row[1]+20), int(row[2]+20)), grean, 3)
    
        if row[0] == 0:
            img = cv2.rectangle(img, (int(row[1]-20), int(row[2]-20)), (int(row[1]+20), int(row[2]+20)), red, 3)
    cv2.imwrite(merge_output+name+'.jpg', img)
    print('detect complete '+merge_output+name+'.jpg')