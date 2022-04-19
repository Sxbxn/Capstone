import cv2
import pandas as pd

dir='/home/dh/Project/Capstone/data/patch_train/'
grean = (0, 255, 0)
red = (0, 0, 255)

result = pd.read_csv(dir+'result/k1-1_result_0_612.csv')
img = cv2.imread(dir+'img/k1-1_0_612.jpg')

for idx, row in result.iloc[:, 1:].iterrows():
    if row[1] == 1:
        img = cv2.rectangle(img, (row[2],row[3]), (row[4], row[5]), grean, 3)
    
    if row[1] == 0:
        img = cv2.rectangle(img, (row[2],row[3]), (row[4], row[5]), red, 3)


cv2.imshow('img', img)
cv2.waitKey(0)
cv2.destroyAllWindows()