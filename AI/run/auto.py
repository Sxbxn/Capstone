import glob
import cv2
import os
import shutil
import pandas as pd
import json
import boto3

# path = "/Users/kimtaekang/Desktop/study/capstone/AI/run/result/predict"
ACCESS_KEY_ID = '1'
ACCESS_SECRET_KEY = '2'
BUCKET_NAME = 'capstone-taekang-bucket'
# dir = '/Users/kimtaekang/Desktop/study/capstone/AI/'
dir = '/app/server/'
test_input = dir + 'run/data/original/'
cache3 = dir + 'run/data/'
patch_input = dir + 'run/data/patch/'
test_location = dir + 'yolor/'
result_output = dir + 'run/result/'
label_input = result_output + 'labels/'
merge_output = dir + 'run/result/predict/'
img_x_size = 1632
img_y_size = 1224
patch_x_size = 408
patch_y_size = 306
x_stride = 204
y_stride = 153


# ============================================================
# prediction method
# ============================================================
def handle_upload_img(f):  # f = 파일명
    s3_client = boto3.client(
        's3',
        aws_access_key_id=ACCESS_KEY_ID,
        aws_secret_access_key=ACCESS_SECRET_KEY
    )
    response = s3_client.upload_file(
        '/app/server/run/result/predict/' + f, BUCKET_NAME, f)
    location = s3_client.get_bucket_location(Bucket=BUCKET_NAME)['LocationConstraint']
    image_url = f'https://{BUCKET_NAME}.s3.{location}.amazonaws.com/{f}'

    return image_url


def delete():
    if os.path.isdir(result_output + 'labels'):
        shutil.rmtree(result_output + 'labels')
        print('delete labels directory')

    if os.path.isdir(patch_input[:-1]):
        shutil.rmtree(patch_input[:-1])
        os.makedirs(patch_input[:-1])
        print('update patch directory')

    # os.system('source venv/bin/activate')
    if os.path.isdir(test_location + 'runs/test/yolor_p6_val'):
        shutil.rmtree(test_location + 'runs/test/yolor_p6_val')
        print('delete test directory')

    if os.path.isfile(cache3 + 'patch.cache3'):
        os.remove(cache3 + 'patch.cache3')
        print('delete cache')

    if os.path.isdir(merge_output[:-1]):
        shutil.rmtree(merge_output[:-1])
        os.makedirs(merge_output[:-1])


def crop():
    # ============================<< crop >>============================
    print('============================<< crop >>============================')
    input_dir_image = test_input
    output_dir = patch_input
    img_list = glob.glob(input_dir_image + '*.jpg')
    f = open(patch_input + 'test.txt', 'w')
    if x_stride != 0 and y_stride != 0:
        for imname in img_list:
            name = imname.split('/')[-1].split('\\')[-1]
            img = cv2.imread(imname)
            for i in range(0, (img_x_size - patch_x_size) // x_stride + 1, 1):
                for k in range(0, (img_y_size - patch_y_size) // y_stride + 1, 1):
                    x = i * x_stride
                    y = k * y_stride
                    patch = img[y:y + patch_y_size, x:x + patch_x_size]
                    print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, y))
                    cv2.imwrite(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, y), patch)
                    f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, y) + '\n')
            # Use if you want to include a truncated image
            for i in range(0, (img_x_size - patch_x_size) // x_stride + 1, 1):
                x = i * x_stride
                patch = img[-patch_y_size:, x:x + patch_x_size]
                print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, img_y_size - patch_y_size))
                cv2.imwrite(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, img_y_size - patch_y_size), patch)
                f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, img_y_size - patch_y_size) + '\n')
            for i in range(0, (img_y_size - patch_y_size) // y_stride + 1, 1):
                y = i * y_stride
                patch = img[y:y + patch_y_size, -patch_x_size:]
                print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, y))
                cv2.imwrite(output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, y), patch)
                f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, y) + '\n')
            patch = img[-patch_y_size:, -patch_x_size:]
            print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size,
                                                               img_y_size - patch_y_size))
            cv2.imwrite(
                output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, img_y_size - patch_y_size),
                patch)
            f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size,
                                                       img_y_size - patch_y_size) + '\n')
    else:
        for imname in img_list:
            name = imname.split('/')[-1].split('\\')[-1]
            img = cv2.imread(imname)
            for i in range(0, img_x_size // patch_x_size, 1):
                for k in range(0, img_y_size // patch_y_size, 1):
                    x = i * patch_x_size
                    y = k * patch_y_size
                    patch = img[y:y + patch_y_size, x:x + patch_x_size]
                    print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, y))
                    cv2.imwrite(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, y), patch)
                    f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, y) + '\n')
        # Use if you want to include a truncated image
        for imname in img_list:
            name = imname.split('/')[-1].split('\\')[-1]
            img = cv2.imread(imname)
            for i in range(0, img_x_size // patch_x_size, 1):
                x = i * patch_x_size
                patch = img[-patch_y_size:, x:x + patch_x_size]
                print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, img_y_size - patch_y_size))
                cv2.imwrite(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, img_y_size - patch_y_size), patch)
                f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], x, img_y_size - patch_y_size) + '\n')
            for i in range(0, img_y_size // patch_y_size, 1):
                y = i * patch_y_size
                patch = img[y:y + patch_y_size, -patch_x_size:]
                print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, y))
                cv2.imwrite(output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, y), patch)
                f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, y) + '\n')
            patch = img[-patch_y_size:, -patch_x_size:]
            print('save ' + output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size,
                                                               img_y_size - patch_y_size))
            cv2.imwrite(
                output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size, img_y_size - patch_y_size),
                patch)
            f.write(output_dir + '{}_{}_{}.jpg'.format(name[:-4], img_x_size - patch_x_size,
                                                       img_y_size - patch_y_size) + '\n')
    f.close()


def detection():
    # ============================<< detection >>============================
    print('============================<< detection >>============================')
    os.chdir(test_location)
    os.system(
        'python3 test.py --data data/test.yaml --img 512 --batch 16 --conf 0.001 --iou 0.65 --device cpu --cfg cfg/yolor_p6.cfg --weights runs/train/yolor_p6/weights/last.pt --name yolor_p6_val --names data/cell.names --save-txt --task test')
    shutil.move(test_location + 'runs/test/yolor_p6_val/labels', result_output)


def merge():
    # ============================<< merging >>============================
    print('============================<< merging >>============================')
    img_list = glob.glob(test_input + '*.jpg')

    for img_name in img_list:
        df = pd.DataFrame(columns=['LiveOrDead', 'center_x', 'center_y', 'width', 'height'])
        name = img_name.split('/')[-1].split('\\')[-1][:-4]
        # 1. 정상적인 patch를 먼저 합치기
        for i in range(0, img_x_size // patch_x_size, 1):
            x = i * patch_x_size
            for k in range(0, img_y_size // patch_y_size, 1):
                try:
                    y = k * patch_y_size
                    result = pd.read_csv(label_input + name + '_{}_{}.txt'.format(x, y), sep=' ',
                                         names=['LiveOrDead', 'center_x', 'center_y', 'width', 'height'])
                    result.loc[:, 'center_x'] *= patch_x_size
                    result.loc[:, 'center_y'] *= patch_y_size
                    result.loc[:, 'width'] = 40
                    result.loc[:, 'height'] = 40
                    if x != 0:
                        result = result[result['center_x'] >= 40]
                    if x != img_x_size - patch_x_size:
                        result = result[result['center_x'] < patch_x_size - 40]
                    if y != 0:
                        result = result[result['center_y'] >= 40]
                    if y != img_y_size - patch_y_size:
                        result = result[result['center_y'] < patch_y_size - 40]
                    result.loc[:, 'center_x'] += x
                    result.loc[:, 'center_y'] += y
                    df = pd.concat([df, result])
                except:
                    pass

        # 2. 좌우 중간 patch 덮어쓰기
        for i in range(0, img_x_size // patch_x_size - 1, 1):
            x = i * patch_x_size + x_stride
            for k in range(0, img_y_size // patch_y_size, 1):
                try:
                    y = k * patch_y_size
                    result = pd.read_csv(label_input + name + '_{}_{}.txt'.format(x, y), sep=' ',
                                         names=['LiveOrDead', 'center_x', 'center_y', 'width', 'height'])
                    result.loc[:, 'center_x'] *= patch_x_size
                    result.loc[:, 'center_y'] *= patch_y_size
                    result.loc[:, 'width'] = 40
                    result.loc[:, 'height'] = 40
                    result = result[(result['center_x'] >= (patch_x_size // 2 - 40)) & (
                            result['center_x'] < (patch_x_size // 2 + 40))]
                    if y != 0:
                        result = result[result['center_y'] >= 40]
                    if y != img_y_size - patch_y_size:
                        result = result[result['center_y'] < patch_y_size - 40]
                    result.loc[:, 'center_x'] += x
                    result.loc[:, 'center_y'] += y
                    df = pd.concat([df, result])
                except:
                    pass

        # 3. 상하 중간 patch 덮어쓰기
        for i in range(0, img_x_size // patch_x_size, 1):
            x = i * patch_x_size
            for k in range(0, img_y_size // patch_y_size - 1, 1):
                try:
                    y = k * patch_y_size + y_stride
                    result = pd.read_csv(label_input + name + '_{}_{}.txt'.format(x, y), sep=' ',
                                         names=['LiveOrDead', 'center_x', 'center_y', 'width', 'height'])
                    result.loc[:, 'center_x'] *= patch_x_size
                    result.loc[:, 'center_y'] *= patch_y_size
                    result.loc[:, 'width'] = 40
                    result.loc[:, 'height'] = 40
                    result = result[(result['center_y'] >= (patch_y_size // 2 - 40)) & (
                            result['center_y'] < (patch_y_size // 2 + 40))]
                    if x != 0:
                        result = result[result['center_x'] >= 40]
                    if x != img_x_size - patch_x_size:
                        result = result[result['center_x'] < patch_x_size - 40]
                    result.loc[:, 'center_x'] += x
                    result.loc[:, 'center_y'] += y
                    df = pd.concat([df, result])
                except:
                    pass

        # 4. 상하 좌우 중간 patch 덮어쓰기
        for i in range(0, img_x_size // patch_x_size - 1, 1):
            x = i * patch_x_size + x_stride
            for k in range(0, img_y_size // patch_y_size - 1, 1):
                try:
                    y = k * patch_y_size + y_stride
                    result = pd.read_csv(label_input + name + '_{}_{}.txt'.format(x, y), sep=' ',
                                         names=['LiveOrDead', 'center_x', 'center_y', 'width', 'height'])
                    result.loc[:, 'center_x'] *= patch_x_size
                    result.loc[:, 'center_y'] *= patch_y_size
                    result.loc[:, 'width'] = 40
                    result.loc[:, 'height'] = 40
                    result = result[(result['center_x'] >= (patch_x_size // 2 - 40)) & (
                            result['center_x'] < (patch_x_size // 2 + 40))]
                    result = result[(result['center_y'] >= (patch_y_size // 2 - 40)) & (
                            result['center_y'] < (patch_y_size // 2 + 40))]
                    result.loc[:, 'center_x'] += x
                    result.loc[:, 'center_y'] += y
                    df = pd.concat([df, result])
                except:
                    pass

        # df = df.reset_index(drop=True)
        # live = len(df[df['LiveOrDead'] == 1])
        # dead = len(df[df['LiveOrDead'] == 0])
        # js = {'totalCell': live + dead, 'liveCell': live, 'deadCell': dead}
        # textJSON = json.dumps(js, indent=4)
        # with open(merge_output + name + '.json', 'w') as f:
        #     f.write(textJSON)
        # df.to_csv(merge_output+name+'.csv', sep=',')
        img = cv2.imread(img_name)
        grean = (0, 255, 0)
        red = (0, 0, 255)
        for idx, row in df.iloc[:].iterrows():
            if row[0] == 1:
                img = cv2.rectangle(img, (int(row[1] - 20), int(row[2] - 20)), (int(row[1] + 20), int(row[2] + 20)),
                                    grean, 3)
            if row[0] == 0:
                img = cv2.rectangle(img, (int(row[1] - 20), int(row[2] - 20)), (int(row[1] + 20), int(row[2] + 20)),
                                    red, 3)
        cv2.imwrite(merge_output + name + '.jpg', img)  # 이미지 생성이겠지 ?
        df = df.reset_index(drop=True)
        live = len(df[df['LiveOrDead'] == 1])
        dead = len(df[df['LiveOrDead'] == 0])
        img_url = handle_upload_img(name + '.jpg')  # 이미지 업로드,
        js = {'totalCell': live + dead, 'liveCell': live, 'deadCell': dead, 'url': img_url}  # 뭐 쓰는거겠지 ?
        textJSON = json.dumps(js, indent=4)
        with open(merge_output + name + '.json', 'w') as f:
            f.write(textJSON)  # 아마도 JSON 파일 쓰는 것일듯 ?
        print('detect complete ' + merge_output + name + '.jpg')

        f.close()


def predict():
    delete()
    crop()
    detection()
    merge()

    if os.path.isdir(test_input[:-1]):
        shutil.rmtree(test_input[:-1])
    os.makedirs(test_input[:-1])

# <<testting>>
# predict()
