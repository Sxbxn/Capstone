import glob
import cv2
import os
import shutil
import pandas as pd
import json


class Cell_predict:

    def __init__(self, dir='/home/dh/Project/Capstone/AI/'):
        dir = '/home/dh/Project/Capstone/AI/'
        self.test_input = dir + 'run/data/original/'
        self.patch_input = dir + 'run/data/patch/'
        self.test_location = dir + 'yolor/'

        self.result_output = dir + 'run/result/'

        self.label_input = self.result_output + 'labels/'
        self.merge_output = dir + 'run/result/predict/'

        self.img_x_size = 3264
        self.img_y_size = 2448
        self.patch_x_size = 408
        self.patch_y_size = 306
        self.x_stride = 204
        self.y_stride = 153

    # ============================================================
    # prediction method
    # ============================================================

    def predict(self):
        os.system('source venv/bin/activate')
        if os.path.isdir(self.test_location+'runs/test/yolor_p6_val'):
            shutil.rmtree(self.test_location+'runs/test/yolor_p6_val')

        # ============================<< crop >>============================
        print('============================<< crop >>============================')

        input_dir_image = self.test_input
        output_dir = self.patch_input

        img_list = glob.glob(input_dir_image+'*.jpg')
        f = open(self.patch_input+'test.txt', 'w')

        if self.x_stride != 0 and self.y_stride != 0:
            for imname in img_list:
                name = imname.split('/')[-1]
                img = cv2.imread(imname)
                for i in range(0,(self.img_x_size-self.patch_x_size)//self.x_stride+1,1):
                    for k in range(0,(self.img_y_size-self.patch_y_size)//self.y_stride+1,1):
                        x = i*self.x_stride
                        y = k*self.y_stride
                        patch = img[y:y+self.patch_y_size, x:x+self.patch_x_size]
                        print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y))
                        cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y), patch)
                        f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y)+'\n')

                # Use if you want to include a truncated image
                for i in range(0,(self.img_x_size-self.patch_x_size)//self.x_stride+1,1):
                    x = i*self.x_stride
                    patch = img[-self.patch_y_size:,x:x+self.patch_x_size]
                    print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, self.img_y_size-self.patch_y_size))
                    cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, self.img_y_size-self.patch_y_size), patch)
                    f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, self.img_y_size-self.patch_y_size)+'\n')

                for i in range(0,(self.img_y_size-self.patch_y_size)//self.y_stride+1,1):
                    y = i*self.y_stride
                    patch = img[y:y+self.patch_y_size, -self.patch_x_size:]
                    print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, y))
                    cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, y), patch)
                    f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, y)+'\n')

                patch = img[-self.patch_y_size:, -self.patch_x_size:]
                print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, self.img_y_size-self.patch_y_size))
                cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, self.img_y_size-self.patch_y_size), patch)
                f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, self.img_y_size-self.patch_y_size)+'\n')

        else:
            for imname in img_list:
                name = imname.split('/')[-1]
                img = cv2.imread(imname)
                for i in range(0,self.img_x_size//self.patch_x_size,1):
                    for k in range(0,self.img_y_size//self.patch_y_size,1):
                        x = i*self.patch_x_size
                        y = k*self.patch_y_size
                        patch = img[y:y+self.patch_y_size, x:x+self.patch_x_size]
                        print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y))
                        cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y), patch)
                        f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, y)+'\n')

            # Use if you want to include a truncated image
            for imname in img_list:
                name = imname.split('/')[-1]
                img = cv2.imread(imname)

                for i in range(0,self.img_x_size//self.patch_x_size,1):
                    x = i*self.patch_x_size
                    patch = img[-self.patch_y_size:,x:x+self.patch_x_size]
                    print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, self.img_y_size-self.patch_y_size))
                    cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, self.img_y_size-self.patch_y_size), patch)
                    f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4],x, self.img_y_size-self.patch_y_size)+'\n')

                for i in range(0,self.img_y_size//self.patch_y_size,1):
                    y = i*self.patch_y_size
                    patch = img[y:y+self.patch_y_size, -self.patch_x_size:]
                    print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, y))
                    cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, y), patch)
                    f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, y)+'\n')

                patch = img[-self.patch_y_size:, -self.patch_x_size:]
                print('save '+output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, self.img_y_size-self.patch_y_size))
                cv2.imwrite(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, self.img_y_size-self.patch_y_size), patch)
                f.write(output_dir+'{}_{}_{}.jpg'.format(name[:-4], self.img_x_size-self.patch_x_size, self.img_y_size-self.patch_y_size)+'\n')

        f.close()

        # ============================<< detection >>============================
        print('============================<< detection >>============================')

        os.chdir(self.test_location)
        os.system('python test.py --data data/test.yaml --img 512 --batch 16 --conf 0.001 --iou 0.65 --device 0 --cfg cfg/yolor_p6.cfg --weights runs/train/yolor_p6/weights/last.pt --name yolor_p6_val --names data/cell.names --save-txt --task test')
        if os.path.isdir(self.result_output + '/labels'):
            shutil.rmtree(self.result_output + '/labels')

        shutil.move(self.test_location+'runs/test/yolor_p6_val/labels', self.result_output)

        if os.path.isdir(self.test_location+'runs/test/yolor_p6_val'):
            shutil.rmtree(self.test_location+'runs/test/yolor_p6_val')

        # ============================<< merging >>============================
        print('============================<< merging >>============================')

        img_list = glob.glob(self.test_input+'*.jpg')

        if os.path.isdir(self.merge_output):
            shutil.rmtree(self.merge_output)
            os.system('mkdir {}'.format(self.merge_output))

        for img_name in img_list:
            df = pd.DataFrame(columns=['LiveOrDead','center_x','center_y','width','height'])
            name = img_name.split('/')[-1][:-4]

            # 1. 정상적인 patch를 먼저 합치기
            for i in range(0,self.img_x_size//self.patch_x_size,1):
                x = i*self.patch_x_size
                for k in range(0,self.img_y_size//self.patch_y_size,1):
                    try:
                        y = k*self.patch_y_size

                        result = pd.read_csv(self.label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height'])

                        result.loc[:, 'center_x'] *= self.patch_x_size
                        result.loc[:, 'center_y'] *= self.patch_y_size
                        result.loc[:, 'width'] = 40
                        result.loc[:, 'height'] = 40

                        if x != 0:
                            result = result[result['center_x']>=40]
                        if x != self.img_x_size-self.patch_x_size:
                            result = result[result['center_x']<self.patch_x_size-40]
                        if y != 0:
                            result = result[result['center_y']>=40]
                        if y != self.img_y_size-self.patch_y_size:
                            result = result[result['center_y']<self.patch_y_size-40]

                        result.loc[:, 'center_x'] += x
                        result.loc[:, 'center_y'] += y
                        df = pd.concat([df, result])
                    except:
                        pass
                    
            # 2. 좌우 중간 patch 덮어쓰기
            for i in range(0,self.img_x_size//self.patch_x_size-1,1):
                x = i*self.patch_x_size + self.x_stride
                for k in range(0,self.img_y_size//self.patch_y_size,1):
                    try:
                        y = k*self.patch_y_size

                        result = pd.read_csv(self.label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height']) 

                        result.loc[:, 'center_x'] *= self.patch_x_size
                        result.loc[:, 'center_y'] *= self.patch_y_size
                        result.loc[:, 'width'] = 40
                        result.loc[:, 'height'] = 40

                        result = result[(result['center_x'] >= (self.patch_x_size//2 - 40)) & (result['center_x'] < (self.patch_x_size//2 + 40))]

                        if y != 0:
                            result = result[result['center_y']>=40]
                        if y != self.img_y_size-self.patch_y_size:
                            result = result[result['center_y']<self.patch_y_size-40]


                        result.loc[:, 'center_x'] += x
                        result.loc[:, 'center_y'] += y
                        df = pd.concat([df, result])
                    except:
                        pass
                    
            # 3. 상하 중간 patch 덮어쓰기
            for i in range(0,self.img_x_size//self.patch_x_size,1):
                x = i*self.patch_x_size
                for k in range(0,self.img_y_size//self.patch_y_size-1,1):
                    try:
                        y = k*self.patch_y_size + self.y_stride

                        result = pd.read_csv(self.label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height']) 

                        result.loc[:, 'center_x'] *= self.patch_x_size
                        result.loc[:, 'center_y'] *= self.patch_y_size
                        result.loc[:, 'width'] = 40
                        result.loc[:, 'height'] = 40

                        result = result[(result['center_y'] >= (self.patch_y_size//2 - 40)) & (result['center_y'] < (self.patch_y_size//2 + 40))]

                        if x != 0:
                            result = result[result['center_x']>=40]
                        if x != self.img_x_size-self.patch_x_size:
                            result = result[result['center_x']<self.patch_x_size-40]

                        result.loc[:, 'center_x'] += x
                        result.loc[:, 'center_y'] += y
                        df = pd.concat([df, result])
                    except:
                        pass
                    
            # 4. 상하 좌우 중간 patch 덮어쓰기
            for i in range(0,self.img_x_size//self.patch_x_size-1,1):
                x = i*self.patch_x_size + self.x_stride
                for k in range(0,self.img_y_size//self.patch_y_size-1,1):
                    try:
                        y = k*self.patch_y_size + self.y_stride

                        result = pd.read_csv(self.label_input + name +'_{}_{}.txt'.format(x, y), sep=' ', names=['LiveOrDead','center_x','center_y','width','height']) 

                        result.loc[:, 'center_x'] *= self.patch_x_size
                        result.loc[:, 'center_y'] *= self.patch_y_size
                        result.loc[:, 'width'] = 40
                        result.loc[:, 'height'] = 40

                        result = result[(result['center_x'] >= (self.patch_x_size//2 - 40)) & (result['center_x'] < (self.patch_x_size//2 + 40))]
                        result = result[(result['center_y'] >= (self.patch_y_size//2 - 40)) & (result['center_y'] < (self.patch_y_size//2 + 40))]

                        result.loc[:, 'center_x'] += x
                        result.loc[:, 'center_y'] += y
                        df = pd.concat([df, result])
                    except:
                        pass
                    
            df = df.reset_index(drop=True)

            live = len(df[df['LiveOrDead']==1])
            dead = len(df[df['LiveOrDead']==0])

            js = {'totalCell': live+dead, 'liveCell': live, 'deadCell': dead}
            textJSON = json.dumps(js, indent=4)
            with open(self.merge_output+name+'.json', 'w') as f:
                f.write(textJSON)

            #df.to_csv(self.merge_output+name+'.csv', sep=',')
            img = cv2.imread(img_name)
            grean = (0, 255, 0)
            red = (0, 0, 255)
            for idx, row in df.iloc[:].iterrows():
                if row[0] == 1:
                    img = cv2.rectangle(img, (int(row[1]-20), int(row[2]-20)), (int(row[1]+20), int(row[2]+20)), grean, 3)

                if row[0] == 0:
                    img = cv2.rectangle(img, (int(row[1]-20), int(row[2]-20)), (int(row[1]+20), int(row[2]+20)), red, 3)
            cv2.imwrite(self.merge_output+name+'.jpg', img)
            print('detect complete '+self.merge_output+name+'.jpg')


# <<testting>>
# predict=Cell_predict()
# predict.predict()