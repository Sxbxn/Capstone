import cv2
import numpy as np

downsample = 0.4

img = cv2.imread('ObjectDetection/data/k2-2_result.jpg')
img = cv2.resize(img, (0, 0), fx=downsample, fy=downsample)
img - cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

# bgrLower = np.array([0, 128, 0])
# bgrUpper = np.array([80, 255, 255]) 

def trackbar(pos):
    l1 = cv2.getTrackbarPos('l_1', 'dst')
    l2 = cv2.getTrackbarPos('l_2', 'dst')
    l3 = cv2.getTrackbarPos('l_3', 'dst')
    u1 = cv2.getTrackbarPos('u_1', 'dst')
    u2 = cv2.getTrackbarPos('u_2', 'dst')
    u3 = cv2.getTrackbarPos('u_3', 'dst')

    img_mask = cv2.inRange(img, (l1, l2, l3), (u1, u2, u3))
    result = cv2.bitwise_and(img, img, mask=img_mask)
    cv2.imshow('dst', result)


cv2.imshow('src1', img)
cv2.namedWindow('dst')

cv2.createTrackbar('l_1', 'dst', 0, 255, trackbar)
cv2.createTrackbar('l_2', 'dst', 0, 255, trackbar)
cv2.createTrackbar('l_3', 'dst', 0, 255, trackbar)
cv2.createTrackbar('u_1', 'dst', 0, 255, trackbar)
cv2.createTrackbar('u_2', 'dst', 0, 255, trackbar)
cv2.createTrackbar('u_3', 'dst', 0, 255, trackbar)

trackbar(0)

cv2.waitKey(0)
cv2.destroyAllWindows()