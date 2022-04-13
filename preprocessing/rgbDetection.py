import cv2
import numpy as np

img_name = 'ObjectDetection/data/k05-2_result.jpg'
downsample = 0.4

img = cv2.imread(img_name)
img - cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
#img = cv2.resize(img, (0,0), fx=downsample, fy= downsample)

# RED
bgrLower_R = np.array([0, 0, 80])
bgrUpper_R = np.array([70, 255, 255])

# GREEN
bgrLower_G = np.array([0, 128, 0])
bgrUpper_G = np.array([80, 255, 255])

img_mask = cv2.inRange(img, bgrLower_G, bgrUpper_G)
result_G = cv2.bitwise_and(img, img, mask=img_mask)

img_mask = cv2.inRange(img, bgrLower_R, bgrUpper_R)
result_R = cv2.bitwise_and(img, img, mask=img_mask)

cv2.imwrite('{}_Live_cell.jpg'.format(img_name[21:-4]), result_G)
cv2.imwrite('{}_Dead_cell.jpg'.format(img_name[21:-4]), result_R)