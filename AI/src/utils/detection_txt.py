import cv2
import pandas as pd

#predict_dir = '/home/dh/Project/Capstone/yolor/runs/test/yolor_p6_val/labels/'
predict_dir = '/home/dh/Project/Capstone/data/patch_test/data/'
dir='/home/dh/Project/Capstone/data/patch_test/'
img_name = 'k025-2_0_306'
grean = (0, 255, 0)
red = (0, 0, 255)
x_size = 408
y_size = 306

result = pd.read_csv(predict_dir+'{}.txt'.format(img_name), header=None)[0]
img = cv2.imread(dir+'data/{}.jpg'.format(img_name))


for idx, row in enumerate(result):
    row = list(map(float, row.split(' ')))
    row[1] = int(row[1]*x_size)
    row[2] = int(row[2]*y_size)
    if row[0] == 1:
        img = cv2.rectangle(img, (row[1]-20,row[2]-20), (row[1]+20,row[2]+20), grean, 3)
    
    if row[0] == 0:
        img = cv2.rectangle(img, (row[1]-20,row[2]-20), (row[1]+20,row[2]+20), red, 3)

    print((row[1]-20,row[2]-20), (row[1]+20,row[2]+20))

cv2.imshow('img', img)
cv2.imwrite('img.jpg', img)
cv2.waitKey(0)
cv2.destroyAllWindows()